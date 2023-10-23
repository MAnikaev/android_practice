package com.itis.homework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.homework.databinding.FragmentQuestionBinding
import com.itis.homework.model.AnswerData
import com.itis.homework.databinding.ItemAnswerBinding

class AnswerAdapter(
    val parentBinding: FragmentQuestionBinding,
    val items: MutableList<AnswerData>,
    private val onItemChecked: ((Int, Boolean) -> Unit)? = null
) : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    inner class AnswerViewHolder(
        private val binding: ItemAnswerBinding,
        private val onItemChecked: ((Int, Boolean) -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        private var questionText: String? = null

        init {
            with(binding) {
                answerCb.setOnCheckedChangeListener { _, isChecked ->
                    onItemChecked?.invoke(adapterPosition, isChecked)
                    answerCb.isEnabled = !binding.answerCb.isChecked
                    if (answerCb.isChecked) {
                        for(i in 0 until items.size) {
                            if (items[i].answerText != questionText) {
                                items[i].isChecked = false
                                notifyItemChanged(i)
                            }
                        }
                    }
                }
            }
        }

        fun bindItem(item: AnswerData) {
            with(binding) {
                answerTv.text = item.answerText
                answerCb.isChecked = item.isChecked
                questionText = answerTv.text as String
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        return AnswerViewHolder(
                binding = ItemAnswerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemChecked = onItemChecked
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bindItem(items[position])
    }
}