package com.itis.hw.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Movie
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.hw.R
import com.itis.hw.data.entity.MovieEntity
import com.itis.hw.databinding.FragmentAdditionBinding
import com.itis.hw.di.ServiceLocator
import com.itis.hw.models.Session
import com.itis.hw.utils.ParamKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.random.Random

class AdditionFragment : Fragment(R.layout.fragment_addition) {
   private val binding: FragmentAdditionBinding by viewBinding(FragmentAdditionBinding::bind)

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      initButtons()
   }

   @SuppressLint("NewApi")
   private fun initButtons() {
      with(binding) {
         addBtn.setOnClickListener {
            val title = titleEt.text.toString()
            val description = descriptionEt.text.toString()
            val imageUrl = imageUrlEt.text.toString()

            if(title.isEmpty() || description.isEmpty() || yearEt.text.isEmpty() || imageUrl.isEmpty()) {
               AlertDialog.Builder(requireContext())
                  .setPositiveButton(getString(R.string.ok)
                  ) { dialogInterface, _ -> dialogInterface.dismiss() }
                  .setTitle(getString(R.string.type_all_data))
                  .show()
               return@setOnClickListener
            }

            val year = yearEt.text.toString().toInt()
            val movie = MovieEntity(
               id = joinBytes(title.toByteArray()).toInt() + year,
               title = title,
               description = description,
               imageUrl = imageUrl,
               releaseYear = year,
               rating = 0.0
            )
            lifecycleScope.launch(Dispatchers.IO) {
               runCatching {
                  ServiceLocator.getDbInstance().movieDao().createMovie(movie)
               }.onFailure {
                  withContext(Dispatchers.Main) {
                     AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.movie_already_exist))
                        .setPositiveButton(R.string.ok
                        ) { dialogInterface, _ -> dialogInterface.dismiss() }
                        .show()
                  }
               }
            }

            with(binding) {
               titleEt.text.clear()
               yearEt.text.clear()
               descriptionEt.text.clear()
               imageUrlEt.text.clear()
            }
         }
      }
   }

   private fun joinBytes(bytes: ByteArray) : String {
      val result = StringBuilder()
      for (byte in bytes) {
         result.append(byte.toString())
      }
      return if(result.toString().length >= 8)
                result.toString().substring(0, 8)
             else
                result.toString()
   }
}