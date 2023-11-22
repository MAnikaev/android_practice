package com.itis.homework

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.PrecomputedText.Params
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.itis.homework.databinding.ActivityMainBinding
import com.itis.homework.models.BaseActivity
import com.itis.homework.models.CoroutinesDataListener
import com.itis.homework.ui.FlyModeDialogFragment
import com.itis.homework.utils.ActionType
import com.itis.homework.utils.ParamsKeys
import com.itis.homework.utils.PermissionsHandler
import kotlinx.coroutines.cancel
import java.lang.Exception
import java.util.concurrent.CancellationException

class MainActivity : BaseActivity(), CoroutinesDataListener {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private var permissionsHandler: PermissionsHandler? = null

    private var isStopOnBackground = false

    private var flyModeReceiver: BroadcastReceiver? = null
    override fun onStop() {
        super.onStop()
        if(isStopOnBackground) {
            lifecycleScope.cancel(CancellationException("PROGRESS TERMINATED"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver()

        val controller = (supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment).navController
        NavigationUI.setupWithNavController(binding.mainBnv, controller)



        val rejectAction = {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.reject_permission_dialog_title))
                .setMessage(getString(R.string.reject_permission_dialog_desc))
                .setPositiveButton(
                    getString(R.string.reject_permission_dialog_pos_btn_text)
                ) { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    //val uri = Uri.fromParts("package", packageName, null)  Это считается хардкодом?
                    val uri = Uri.fromParts(getString(R.string.package_scheme_text), packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }.create().show()
        }

        permissionsHandler = PermissionsHandler(
            activity = this,
            confirmCallback = {
                Snackbar.make(binding.root, getString(R.string.permission_confirmed_message), Snackbar.LENGTH_LONG).show()
            },
            rejectCallback = rejectAction,
            rationaleCallback = {
                Snackbar.make(binding.root, getString(R.string.rationale_permission_text), Snackbar.LENGTH_LONG).show()
            }
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        handleIntents(intent)
        super.onNewIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        flyModeReceiver = null
    }

    private fun requestPermission(permission: String) {
        permissionsHandler?.requestPermission(permission)
    }

    private fun handleIntents(intent: Intent?) {
        intent?.extras?.getInt(ParamsKeys.ACTION_KEY, -1).let {
            when(it) {
                ActionType.Toast.value -> {
                    Toast.makeText(this, getString(R.string.toast_intent_text), Toast.LENGTH_LONG).show()
                }
                ActionType.SettingsNavigate.value -> {
                    findNavController(R.id.main_container).popBackStack()
                    findNavController(R.id.main_container).navigate(R.id.notificationSettingsFragment)
                }
                -1 -> Unit
            }
        }
    }

    override fun onDataRecieved(isStopOnBackground: Boolean) {
        this.isStopOnBackground = isStopOnBackground
    }
    private fun registerReceiver() {
        flyModeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    val isAirplaneModeOn = intent.getBooleanExtra("state", false)
                    if(isAirplaneModeOn) {
                        FlyModeDialogFragment().show(supportFragmentManager, FlyModeDialogFragment.FLY_MODE_DIALOG_FRAGMENT_TAG)
                    } else {
                        (supportFragmentManager.findFragmentByTag(FlyModeDialogFragment.FLY_MODE_DIALOG_FRAGMENT_TAG) as? FlyModeDialogFragment)?.dismiss()
                    }
                }
            }
        }

        val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        ContextCompat.registerReceiver(
            this,
            flyModeReceiver,
            intentFilter,
            ContextCompat.RECEIVER_EXPORTED
        )
    }
}