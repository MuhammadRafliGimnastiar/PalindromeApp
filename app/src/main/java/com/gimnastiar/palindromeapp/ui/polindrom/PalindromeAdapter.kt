package com.gimnastiar.palindromeapp.ui.polindrom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gimnastiar.palindromeapp.data.local.entity.Palindrome
import com.gimnastiar.palindromeapp.databinding.ItemDataBinding

class LocalPalindromeAdapter :
    ListAdapter<Palindrome, LocalPalindromeAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(var binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Palindrome) {
            with(binding) {
                tvId.text = data.id.toString()
                tvText.text = data.text
                tvPalindrome.text = data.isPalindrome.toString().toUpperCase()

                btnDelete.setOnClickListener { onItemClickCallback.onItemClicked(data) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Palindrome>() {
            override fun areItemsTheSame(oldItem: Palindrome, newItem: Palindrome): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Palindrome, newItem: Palindrome): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Palindrome)
    }
}
