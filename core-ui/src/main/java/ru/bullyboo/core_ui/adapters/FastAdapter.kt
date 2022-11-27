package ru.bullyboo.core_ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.bullyboo.core_ui.binding.Inflate

@Suppress("unused")
abstract class FastAdapter<M>(
    protected var list: ArrayList<M> = arrayListOf()
): RecyclerView.Adapter<FastAdapter.ViewHolder>() {

    private val diffUtilCallback by lazy { FastDiffUtilCallback() }

    abstract fun getBindingInflater(viewType: Int): Inflate

    abstract fun onBind(holder: ViewHolder, model: M, viewType: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = getBindingInflater(viewType).invoke(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBind(holder, list[position], getItemViewType(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getType(list[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    open fun getType(model: M): Int {
        return -1
    }

    open fun isDiffUtilsEnable(): Boolean {
        return false
    }

    fun submitList(model: List<M>) {
        if (isDiffUtilsEnable()) {
            diffUtilCallback.oldList = list
            diffUtilCallback.newList = model

            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)

            list.clear()
            list.addAll(model)

            diffResult.dispatchUpdatesTo(this)
        } else {
            list.clear()
            list.addAll(model)
            notifyDataSetChanged()
        }
    }

    fun add(model: M){
        list.add(model)
        notifyItemInserted(list.size - 1)
    }

    fun add(model: M, position: Int){
        list.add(position, model)
        notifyItemInserted(position)
    }

    fun remove(model: M){
        val item = list.firstOrNull { it?.equals(model) ?: false }

        item?.let {
            val position = list.indexOf(it)
            list.remove(it)
            notifyItemRemoved(position)
        }
    }

    fun getList(): MutableList<M> {
        return list.toMutableList()
    }

    fun getItem(position: Int): M {
        return list[position]
    }

    open class ViewHolder(
        val binding: ViewBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    )

    inner class FastDiffUtilCallback : DiffUtil.Callback() {

        var oldList: List<M> = listOf()
        var newList: List<M> = listOf()

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}