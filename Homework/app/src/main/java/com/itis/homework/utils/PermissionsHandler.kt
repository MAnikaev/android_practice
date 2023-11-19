package com.itis.homework.utils

import androidx.activity.result.contract.ActivityResultContracts
import com.itis.homework.models.BaseActivity

class PermissionsHandler(
    private val activity: BaseActivity,
    private val confirmCallback: (() -> Unit)? = null,
    private val rationaleCallback: (() -> Unit)? = null,
    private val rejectCallback: (() -> Unit)? = null,
) {

    private var currentPermission = ""

    private val singlePermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                confirmCallback?.invoke()
            } else {
                if (currentPermission.isNotEmpty() && activity.shouldShowRequestPermissionRationale(currentPermission)) {
                    rationaleCallback?.invoke()
                } else {
                    rejectCallback?.invoke()
                }
            }
        }

    fun requestPermission(permission: String) {
        this.currentPermission = permission
        singlePermissionLauncher.launch(permission)
    }
}