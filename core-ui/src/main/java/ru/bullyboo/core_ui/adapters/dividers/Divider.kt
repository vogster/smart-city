package ru.bullyboo.core_ui.adapters.dividers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class Divider(
    context: Context,
    color: Int? = null,
    private val drawLastDivider: Boolean = false
): ItemDecoration() {

    companion object {
        private val ATTRS = intArrayOf(
            android.R.attr.listDivider
        )
    }

    private val mDivider: Drawable?

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()

        if(mDivider != null && color != null){
            DrawableCompat.setTint(mDivider, color)
        }
    }

    override fun onDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        draw(c, parent)
    }

    private fun draw(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = if (drawLastDivider) {
            parent.childCount
        } else {
            parent.childCount - 1
        }
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val isNotLast = parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1

        parent.adapter?.let {
            if (isNotLast) {
                outRect[0, 0, 0] = mDivider!!.intrinsicHeight
            }
        }
    }
}