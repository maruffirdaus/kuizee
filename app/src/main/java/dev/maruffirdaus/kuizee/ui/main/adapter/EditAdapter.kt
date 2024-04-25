package dev.maruffirdaus.kuizee.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.maruffirdaus.kuizee.data.model.Topic
import dev.maruffirdaus.kuizee.databinding.CardRowItemBinding
import dev.maruffirdaus.kuizee.ui.helper.TopicDiffCallback

class EditAdapter : RecyclerView.Adapter<EditAdapter.TopicViewHolder>() {
    private val listTopic = ArrayList<Topic>()
    private var deleteButtonStatus = false
    var selectAllStatus = false
    private var selectedItems = intArrayOf()
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onItemLongClickCallback: OnItemLongClickCallback

    fun setListTopic(listTopic: List<Topic>) {
        val diffCallback = TopicDiffCallback(this.listTopic, listTopic)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listTopic.clear()
        this.listTopic.addAll(listTopic)
        diffResult.dispatchUpdatesTo(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCheckbox(deleteButtonStatus: Boolean) {
        this.deleteButtonStatus = deleteButtonStatus
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun selectAll() {
        this.selectAllStatus = true
        notifyDataSetChanged()
    }

    fun getSelected(): IntArray {
        return selectedItems
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnItemLongClickCallback(onItemLongClickCallback: OnItemLongClickCallback) {
        this.onItemLongClickCallback = onItemLongClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding = CardRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(listTopic[position], position)
        holder.itemView.setOnClickListener {
            if (!deleteButtonStatus) {
                onItemClickCallback.onItemClicked(listTopic[position])
            } else {
                holder.binding.checkbox.isChecked = !holder.binding.checkbox.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return listTopic.size
    }

    inner class TopicViewHolder(internal val binding: CardRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topicData: Topic, adapterPosition: Int) {
            with(binding) {
                media.visibility = View.GONE
                title.text = topicData.title
                supportingText.text = topicData.desc
                card.setOnLongClickListener {
                    onItemLongClickCallback.onItemLongClicked(binding)
                    true
                }
                if (deleteButtonStatus) {
                    checkboxBar.visibility= View.VISIBLE
                } else {
                    checkboxBar.visibility = View.GONE
                    checkbox.isChecked = false
                }
                if (selectAllStatus) {
                    checkbox.isChecked = true
                }
                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedItems += adapterPosition
                    } else {
                        selectedItems = selectedItems.filter { it != adapterPosition }.toIntArray()
                    }
                }
                selectButton.visibility = View.GONE
                editButton.visibility = View.VISIBLE
                editButton.setOnClickListener {
                    onItemClickCallback.onItemClicked(topicData)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(topicData: Topic)
    }

    interface OnItemLongClickCallback {
        fun onItemLongClicked(cardBinding: CardRowItemBinding)
    }
}