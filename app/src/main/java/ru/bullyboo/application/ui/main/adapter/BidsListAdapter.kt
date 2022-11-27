package ru.bullyboo.application.ui.main.adapter

import ru.bullyboo.application.databinding.ItemMainBidsBinding
import ru.bullyboo.application.databinding.ItemMainViewpagerBinding
import ru.bullyboo.core_ui.adapters.FastAdapter
import ru.bullyboo.core_ui.binding.Inflate

class BidsListAdapter: FastAdapter<BidsListAdapter.MainBidsList>() {

    override fun getBindingInflater(viewType: Int): Inflate {
        return ItemMainBidsBinding::inflate
    }

    override fun onBind(holder: ViewHolder, model: MainBidsList, viewType: Int) {
        with(holder.binding as ItemMainBidsBinding) {
            titleText.text = model.title
            photoImage.setImageResource(model.avatar)
            deadlineText.text = model.deadline
            likeText.text = model.likes
        }
    }

    data class MainBidsList(
        val title: String,
        val avatar: Int,
        val deadline: String,
        val likes: String
    )
}