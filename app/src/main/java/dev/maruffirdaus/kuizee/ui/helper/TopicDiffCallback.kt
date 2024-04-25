package dev.maruffirdaus.kuizee.ui.helper

import androidx.recyclerview.widget.DiffUtil
import dev.maruffirdaus.kuizee.data.model.Topic

class TopicDiffCallback(
    private val oldTopicList: List<Topic>,
    private val newTopicList: List<Topic>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldTopicList.size

    override fun getNewListSize(): Int = newTopicList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTopicList[oldItemPosition] == newTopicList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTopicList[oldItemPosition].id == newTopicList[newItemPosition].id
    }
}