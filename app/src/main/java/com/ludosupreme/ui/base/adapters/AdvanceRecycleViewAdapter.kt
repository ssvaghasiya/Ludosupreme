package com.ludosupreme.ui.base.adapters

import android.R
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView


abstract class AdvanceRecycleViewAdapter<H : BaseHolder<E>, E> :
    RecyclerView.Adapter<BaseHolder<*>>,
    HasItem<E> {

    var items: MutableList<E>? = null
        set(value) {
            if (field == null)
                field = mutableListOf()
            else
                field!!.clear()

            if (field != null)
                field!!.addAll(value!!)

            isLoading = false
            notifyDataSetChanged()
        }

    protected var isNoData = false
    protected var isLoading = true
    var loaderVisibility = View.GONE

    protected var errorMessage = "No Data Available"
        set(value) {
            field = value
            isLoading = false
            isNoData = true
            notifyDataSetChanged()
        }


    constructor()

    constructor(items: MutableList<E>) : this() {
        this.items = items
    }

    fun updateItems(items: List<E>?) {

        isLoading = true

        if (this.items == null) {
            this.items = mutableListOf()
            this.items!!.addAll(items!!)

            if (this.items!!.size < MAX_ITEM_PER_PAGE)
                isLoading = false
        } else {

            this.items!!.retainAll(items!!)

            isLoading = false

            notifyDataSetChanged()
        }
    }

    /**
     * Update each item in the items childList
     */
    fun updateEachItem(items: List<E>?) {

        isLoading = true

        if (this.items == null) {
            this.items = mutableListOf()
            this.items!!.addAll(items!!)

            if (this.items!!.size < MAX_ITEM_PER_PAGE)
                isLoading = false
        } else {

            items?.forEach {
                updateItem(it)
            }

            isLoading = false
        }
    }


    fun setItems(items: List<E>?, page: Int) {
        isLoading = true

        if (page == 1) {
            if (this.items == null)
                this.items = mutableListOf()
            else
                this.items!!.clear()
            this.items!!.addAll(items!!)

            if (this.items!!.size < MAX_ITEM_PER_PAGE)
                isLoading = false

            notifyDataSetChanged()
        } else {

            if (this.items == null)
                this.items = mutableListOf()

            if (this.items != null) {
                if (items != null && !items.isEmpty())
                    this.items!!.addAll(items)
                else
                    isLoading = false

                notifyDataSetChanged()
            }
        }
    }

    fun setItemsWithPage(items: List<E>?, page: Int) {

        isLoading = true

        if (page == 1) {
            if (this.items == null)
                this.items = mutableListOf()
            else
                this.items!!.clear()
            this.items!!.addAll(items!!)

            // item is less then per page items
            if (this.items!!.size < MAX_ITEM_PER_PAGE)
                isLoading = false

            notifyDataSetChanged()
        } else {

            if (this.items != null) {

                if (items != null && !items.isEmpty()) {

                    if (this.items!!.size >= page * MAX_ITEM_PER_PAGE) {

                        val top = (page - 1) * MAX_ITEM_PER_PAGE
                        val bottom = items.size
                        var i = top
                        var j = 0
                        while (i < bottom) {
                            this.items!![i] = items[j]
                            i++
                            j++
                        }

                    } else
                        this.items!!.addAll(items)

                } else
                    isLoading = false

                notifyDataSetChanged()
            }
        }
    }

    fun removeItem(e: E) {
        val i = items!!.indexOf(e)
        if (i > -1 && i < items!!.size) {
            items!!.removeAt(i)
            notifyItemRemoved(i)
        }
    }

    fun removeItemAt(postition: Int) {
        if (postition > -1 && postition < items!!.size) {
            items!!.removeAt(postition)
            notifyItemRemoved(postition)
        }
    }

    fun addItem(e: E) {
        if (items == null) {
            items = mutableListOf()
            isLoading = false
        }

        items!!.add(e)
        notifyItemInserted(items!!.size)
    }

    fun addItem(e: E, position: Int) {

        if (items == null) {
            items = mutableListOf()
            isLoading = false
            items!!.add(e)
            notifyDataSetChanged()
        } else {
            items!!.add(position, e)
            if (items!!.size == 1)
                notifyDataSetChanged()
            else
                notifyItemInserted(position)
        }

    }

    fun itemChanged(e: E) {
        if (items != null) {
            val index = items!!.indexOf(e)
            if (index > -1) {
                items!![index] = e
                notifyItemChanged(index)
            }
        }
    }

    fun updateItem(e: E) {
        if (items != null) {
            val index = items!!.indexOf(e)
            if (index > -1) {
                items!![index] = e
                notifyDataSetChanged()
            }
        }
    }

    fun clearAllItem() {
        if (items != null) {
            items!!.clear()
            isLoading = true
            notifyDataSetChanged()
        }
    }

/*fun getItems(): List<E>? {
    return items
}*/

/*fun setItems(items: List<E>?) {

    if (this.items == null)
        this.items = ArrayList()
    else
        this.items!!.clear()

    if (items != null)
        this.items!!.addAll(items)

    isLoading = false
    notifyDataSetChanged()
}*/

/*fun getErrorMessage(): String {
    return errorMessage
}*/

/* fun setErrorMessage(errorMessage: String) {
     this.errorMessage = errorMessage
     isLoading = false
     isNoData = true
     notifyDataSetChanged()
 }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<E> {

        val baseHolder: BaseHolder<E> = when (viewType) {
            333 -> createLoadingHolder(parent, viewType)
            111 -> createNoDataHolder(parent)
            else -> createDataHolder(parent, viewType)
        }

        baseHolder.hasItem = this

        return baseHolder
    }

    fun createNoDataHolder(parent: ViewGroup): NoDataHolder<E> {
        val textView = TextView(parent.context)
        textView.gravity = Gravity.CENTER
        val dimensionPixelSize = 200// parent.getResources().getDimensionPixelSize(R.dimen._20dp);
        textView.setPaddingRelative(
            dimensionPixelSize,
            dimensionPixelSize,
            dimensionPixelSize,
            dimensionPixelSize
        )
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        textView.layoutParams = layoutParams

        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_no_data, parent, false);
        return NoDataHolder(textView)
    }

    fun createLoadingHolder(parent: ViewGroup, viewType: Int): LoadingHolder<E> {
        val progressBar = ProgressBar(parent.context, null, R.attr.progressBarStyleSmall)

        val layoutParams = parent.layoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        progressBar.layoutParams = layoutParams

        if (layoutParams is LinearLayout.LayoutParams) {
            layoutParams.gravity = Gravity.CENTER
        }

        //Hide progress bar for now
        progressBar.visibility = loaderVisibility

        return LoadingHolder(progressBar)
    }

    abstract fun createDataHolder(parent: ViewGroup, viewType: Int): H

    override fun getItemViewType(position: Int): Int {
        if (isNoData && isLoading)
            return 333
        else if (!isLoading && isNoData)
            return 111
        else if (isLoading && items?.size == position)
            return 333

        return getViewType(position)
    }


    override fun onBindViewHolder(
        holder: BaseHolder<*>,
        position: Int,
        payloads: MutableList<Any>
    ) {
//        Log.e("Called", "WithPayload___$position")
        val itemViewType = getItemViewType(position)
        if (itemViewType != 111 && itemViewType != 333) {
            val item = getItem(position)
            // actual view
            if (payloads.size != 0) {
                onBindDataHolder(holder as H, position, item, payloads)
            } else {
                onBindDataHolder(holder as H, position, item)
            }
        }

        if (itemViewType == 111 && holder is NoDataHolder) {
            holder.setErrorText(errorMessage)
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    open fun getViewType(position: Int): Int {
        return 222
    }

    override fun onBindViewHolder(holder: BaseHolder<*>, position: Int) {

        /*val itemViewType = getItemViewType(position)

        if (itemViewType != 111 && itemViewType != 333) {
            val item = getItem(position)
            // actual view
            onBindDataHolder(holder as H, position, item)
        }

        if (itemViewType == 111 && holder is NoDataHolder) {
            holder.setErrorText(errorMessage)
        }*/
    }

    abstract fun onBindDataHolder(holder: H, position: Int, item: E)
    open fun onBindDataHolder(holder: H, position: Int, item: E, payloads: MutableList<Any>) {

    }

    override fun getItem(index: Int): E {
        if (isLoading && items!!.size == index) {
            var i = index - 1
            i = if (i < 0) 0 else i
            return items!![i]
        }

        if (index == items?.size) {
            return items!![0]
        }

        if (index == items?.size!! + 1) {
            return items!![0]
        }
        return items!![index]
    }

    override fun getItemCount(): Int {
        isNoData = items == null || items!!.isEmpty()
        return when {
            isNoData -> 1
            isLoading -> items!!.size + 1
            else -> items!!.size
        }
    }

    protected fun makeView(parent: ViewGroup, @LayoutRes layout: Int): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }

    companion object {
        protected var MAX_ITEM_PER_PAGE = 10
    }


}