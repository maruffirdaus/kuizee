package dev.maruffirdaus.kuizee.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.maruffirdaus.kuizee.data.model.Topic
import dev.maruffirdaus.kuizee.databinding.CardRowItemBinding
import dev.maruffirdaus.kuizee.ui.helper.TopicDiffCallback

class PlayAdapter : RecyclerView.Adapter<PlayAdapter.TopicPlayViewHolder>() {
    private val listTopic = ArrayList<Topic>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setListTopic(listTopic: List<Topic>) {
        val diffCallback = TopicDiffCallback(this.listTopic, listTopic)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listTopic.clear()
        this.listTopic.addAll(listTopic)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicPlayViewHolder {
        val binding = CardRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicPlayViewHolder(parent, binding)
    }

    override fun onBindViewHolder(holder: TopicPlayViewHolder, position: Int) {
        holder.bind(listTopic[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listTopic[position])
        }
    }

    override fun getItemCount(): Int {
        return listTopic.size
    }

    inner class TopicPlayViewHolder(
        private val parent: ViewGroup,
        private val binding: CardRowItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topicData: Topic) {
            with(binding) {
                if (topicData.img != null) {
                    Glide.with(parent.context)
                        .load(topicData.img!!.toUri())
                        .into(media)
                }
                title.text = topicData.title
                title.maxLines = 1
                supportingText.text = topicData.desc
                selectButton.setOnClickListener {
                    onItemClickCallback.onItemClicked(topicData)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(topicData: Topic)
    }
}