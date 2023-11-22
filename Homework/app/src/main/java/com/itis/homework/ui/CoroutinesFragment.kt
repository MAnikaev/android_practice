package com.itis.homework.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.R
import com.itis.homework.databinding.FragmentCoroutinesBinding
import com.itis.homework.models.BaseActivity
import com.itis.homework.models.CoroutinesDataListener
import com.itis.homework.utils.NotificationImportance
import com.itis.homework.utils.NotificationSender
import com.itis.homework.utils.NotificationVisibility
import com.itis.homework.utils.ParamsKeys
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random


class CoroutinesFragment : Fragment(R.layout.fragment_coroutines) {
    private val binding: FragmentCoroutinesBinding by viewBinding(FragmentCoroutinesBinding::bind)
    private var coroutinesDataListener: CoroutinesDataListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        coroutinesDataListener = context as? CoroutinesDataListener
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            coroutineStartBtn.setOnClickListener {
                val coroutinesCount = coroutinesCountSb.progress
                val isAsync = asyncCb.isChecked
                val isStopOnBackground = stopOnBackgroundCb.isChecked
                coroutinesDataListener?.onDataRecieved(isStopOnBackground)
                processCoroutines(coroutinesCount = coroutinesCount, isAsync = isAsync)
            }
        }
    }
    private fun processCoroutines(coroutinesCount: Int, isAsync: Boolean = false, isStopOnBackground: Boolean = false) {
        var counter = 0

        val job = requireActivity().lifecycleScope.launch(Dispatchers.Main) {
            if(!isAsync) {
                for (i in 0 until coroutinesCount) {
                    withContext(Dispatchers.IO) {
                        delayRandomTime(i + 1)
                        counter += 1
                    }
                }
            } else {
                val tasks = mutableListOf<Deferred<Unit>>()
                for (i in 0 until coroutinesCount) {
                    tasks.add(async(Dispatchers.IO, start = CoroutineStart.LAZY) {
                        delayRandomTime(i + 1)
                        counter += 1
                    })
                }
                tasks.awaitAll()
            }
        }
        job.invokeOnCompletion {
            println("TEST TAG - $counter coroutines done")
            NotificationSender.sendNotification(
                ctx = requireContext(),
                id = Random.nextInt(-1000, 1000),
                importance = NotificationImportance.Urgent,
                visibility = NotificationVisibility.Public,
                title = "Coroutines done",
                body = "My job here is done $coroutinesCount",
                isHaveButtons = false,
                isDetail = false
            )
        }
    }

    private suspend fun delayRandomTime(number: Int) {
        val time = Random.nextInt(2, 5) * 1000L
        delay(time)
        println("TEST TAG - Coroutine $number done")
    }

}