package com.itis.homework.ui

import android.graphics.DashPathEffect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.R
import com.itis.homework.databinding.FragmentCoroutinesBinding
import com.itis.homework.utils.NotificationImportance
import com.itis.homework.utils.NotificationSender
import com.itis.homework.utils.NotificationVisibility
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class CoroutinesFragment : Fragment(R.layout.fragment_coroutines) {
    private val binding: FragmentCoroutinesBinding by viewBinding(FragmentCoroutinesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val coroutinesCount = coroutinesCountSb.progress
            val isAsync = asyncCb.isChecked
            val isStopOnBackground = stopOnBackgroundCb.isChecked

            coroutineStartBtn.setOnClickListener {
                processCoroutines(coroutinesCount = coroutinesCount, isAsync = isAsync)
            }
        }
    }
    private fun processCoroutines(coroutinesCount: Int, isAsync: Boolean = false, isStopOnBackground: Boolean = false) {
        lifecycleScope.launch(Dispatchers.Main) {
            if(!isAsync) {
                for (i in 0 until coroutinesCount) {
                    async(Dispatchers.IO) {  delayRandomTime(i)}.await()
                }
            } else {
                val tasks = mutableListOf<Deferred<Unit>>()
                for (i in 0 until coroutinesCount) {
                    tasks.add(async(Dispatchers.IO) {  delayRandomTime(i)})
                }
                tasks.awaitAll()
                var counter = 0
                tasks.forEach {
                    if(it.isCompleted) {
                        counter += 1
                    }
                }
                println("TEST TAG - $counter coroutines done")
            }
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