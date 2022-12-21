package dev_ali_hassan.app.marad.ui.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dev_ali_hassan.app.marad.databinding.ListItemMaradInfoBinding
import dev_ali_hassan.app.marad.model.MaradRukun

class HomeItemsAdapter(): ListAdapter<MaradRukun, HomeItemsAdapter.ListItemViewHolder>(
    ItemsDiffUtil()
) {




    class ItemsDiffUtil : DiffUtil.ItemCallback<MaradRukun>() {
        override fun areItemsTheSame(oldItem: MaradRukun, newItem: MaradRukun): Boolean =
            oldItem.rukunNumber == newItem.rukunNumber


        override fun areContentsTheSame(oldItem: MaradRukun, newItem: MaradRukun): Boolean =
            oldItem == newItem
    }

    inner class ListItemViewHolder(private val binding: ListItemMaradInfoBinding) : ViewHolder(binding.root) {

         fun bind(rukun: MaradRukun) {
            binding.rkunNameTv.text = rukun.rukunName
            binding.rkunImageView.setImageResource(rukun.rukunImage)
            binding.rukunNumberTv.text = rukun.rukunNumber.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = ListItemMaradInfoBinding.inflate(LayoutInflater.from(parent.context))
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val rukun = getItem(position)
        holder.bind(rukun)
    }
}