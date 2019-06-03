package com.androidisland.easypermission.sample.view.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.androidisland.easypermission.sample.R
import kotlinx.android.synthetic.main.item_empty.view.*
import kotlinx.android.synthetic.main.item_result.view.*

class PermissionAdapter(private val clickListener: (viewId: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<PermissionModel>()

    companion object {
        private const val TYPE_EMPTY = 1
        private const val TYPE_RESULT = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.isEmpty()) TYPE_EMPTY else TYPE_RESULT
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EMPTY -> {
                val root = LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false)
                EmptyHolder(root)
            }
            else -> {
                val root = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
                ResultHolder(root)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) 1 else items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ResultHolder) {
            holder.bind(items[position])
        }
    }

    fun clear() {
        val size = items.size
        if (size > 0) {
            items.clear()
            notifyItemRangeRemoved(0, size)
        }
    }

    fun add(vararg items: PermissionModel) {
        val index = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(index, items.size)
    }

    private inner class EmptyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.goBtn.setOnClickListener {
                clickListener.invoke(it.id)
            }
        }
    }

    private inner class ResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.settings_txt.setOnClickListener {
                clickListener.invoke(it.id)
            }
        }

        fun bind(data: PermissionModel) {
            itemView.permission_txt.text = data.permission
            itemView.result_txt.text =
                when {
                    data.isGranted() -> "granted"
                    data.isPermanentlyDenied() -> "permanently denied"
                    else -> "denied"
                }
            val color =
                ContextCompat.getColor(itemView.context, if (data.isGranted()) R.color.granted else R.color.denied)
            itemView.result_txt.setTextColor(color)
            itemView.settings_txt.visibility = if (data.isPermanentlyDenied()) View.VISIBLE else View.GONE
        }
    }
}