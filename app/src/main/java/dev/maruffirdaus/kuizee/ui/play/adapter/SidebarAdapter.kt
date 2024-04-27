package dev.maruffirdaus.kuizee.ui.play.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.maruffirdaus.kuizee.data.model.SidebarData
import dev.maruffirdaus.kuizee.databinding.SidebarCardRowItemBinding

class SidebarAdapter :
    RecyclerView.Adapter<SidebarAdapter.SidebarViewHolder>() {
    private val listSidebarData = ArrayList<SidebarData>()

    fun setListSidebarData(listSidebarData: List<SidebarData>) {
        this.listSidebarData.addAll(listSidebarData)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SidebarAdapter.SidebarViewHolder {
        val binding =
            SidebarCardRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SidebarViewHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: SidebarAdapter.SidebarViewHolder, position: Int) {
        holder.bind(listSidebarData[position], position)
    }

    override fun getItemCount(): Int {
        return listSidebarData.size
    }

    inner class SidebarViewHolder(
        internal val binding: SidebarCardRowItemBinding,
        val parent: ViewGroup
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(questionData: SidebarData, position: Int) {
            with(binding) {
                if (questionData.isActive) {
                    with(cardActive) {
                        visibility = View.VISIBLE
                        (position+1).toString().also { titleActive.text = it }
                    }
                } else if (questionData.isAnswered) {
                    with(cardAnswered) {
                        visibility = View.VISIBLE
                        (position+1).toString().also { titleAnswered.text = it }
                    }
                } else {
                    with(cardUnanswered) {
                        visibility = View.VISIBLE
                        (position+1).toString().also { titleUnanswered.text = it }
                    }
                }
            }
        }
    }
}