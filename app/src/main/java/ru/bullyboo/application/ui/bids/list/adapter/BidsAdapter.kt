package ru.bullyboo.application.ui.bids.list.adapter

import ru.bullyboo.application.databinding.ItemBidsBinding
import ru.bullyboo.core_ui.adapters.FastAdapter
import ru.bullyboo.core_ui.binding.Inflate
import ru.bullyboo.domain.entity.BidsData

class BidsAdapter: FastAdapter<BidsData>() {

    override fun getBindingInflater(viewType: Int): Inflate {
        return ItemBidsBinding::inflate
    }

    override fun onBind(holder: ViewHolder, model: BidsData, viewType: Int) {
        with(holder.binding as ItemBidsBinding){
            titleText.text = model.title
            subtitleText.text = model.description
        }
    }
}