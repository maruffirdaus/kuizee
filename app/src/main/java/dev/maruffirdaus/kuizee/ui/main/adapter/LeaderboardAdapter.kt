package dev.maruffirdaus.kuizee.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.maruffirdaus.kuizee.data.model.Leaderboard
import dev.maruffirdaus.kuizee.databinding.LeaderboardRowItemBinding
import dev.maruffirdaus.kuizee.ui.helper.LeaderboardDiffCallback

class LeaderboardAdapter :
    RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {
    private val listLeaderboard = ArrayList<Leaderboard>()

    fun setListLeaderboard(listLeaderboard: List<Leaderboard>) {
        val diffCallback = LeaderboardDiffCallback(this.listLeaderboard, listLeaderboard)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listLeaderboard.clear()
        this.listLeaderboard.addAll(listLeaderboard)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeaderboardAdapter.LeaderboardViewHolder {
        val binding =
            LeaderboardRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LeaderboardViewHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: LeaderboardAdapter.LeaderboardViewHolder, position: Int) {
        holder.bind(listLeaderboard[position], position)
    }

    override fun getItemCount(): Int {
        return listLeaderboard.size
    }

    inner class LeaderboardViewHolder(
        internal val binding: LeaderboardRowItemBinding,
        val parent: ViewGroup
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(leaderboardData: Leaderboard, position: Int) {
            with(binding) {
                (position + 1).toString().also { rank.text = it }
                playerName.text = leaderboardData.player
                topic.text = leaderboardData.topic
                score.text = buildString {
                    append(leaderboardData.score)
                    append("/100")
                }
                if (position == itemCount - 1) {
                    materialDivider.visibility = View.GONE
                } else {
                    materialDivider.visibility = View.VISIBLE
                }
            }
        }
    }
}