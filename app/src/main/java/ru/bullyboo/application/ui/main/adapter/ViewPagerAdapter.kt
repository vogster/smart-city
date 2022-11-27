package ru.bullyboo.application.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.bullyboo.application.R
import ru.bullyboo.application.databinding.ItemBidsBinding
import ru.bullyboo.application.databinding.ItemMainViewpagerBinding
import ru.bullyboo.core_ui.adapters.FastAdapter
import ru.bullyboo.core_ui.binding.Inflate
import java.util.*

class ViewPagerAdapter : FastAdapter<Int>(){

    override fun getBindingInflater(viewType: Int): Inflate {
        return ItemMainViewpagerBinding::inflate
    }

    override fun onBind(holder: ViewHolder, model: Int, viewType: Int) {
        with(holder.binding as ItemMainViewpagerBinding){
            imageViewMain.setImageResource(model)
        }
    }
}