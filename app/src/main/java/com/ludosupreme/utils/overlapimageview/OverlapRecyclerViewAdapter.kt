package com.ludosupreme.utils.overlapimageview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ludosupreme.R
import kotlinx.android.synthetic.main.row_image.view.*

class OverlapRecyclerViewAdapter(
    private var mContext: Context,
    private var recyclerViewClickListener: OverlapRecyclerViewClickListener,
    private val overlapLimit: Int
) : RecyclerView.Adapter<OverlapRecyclerViewAdapter.CustomViewHolder>() {

    private val TAG = OverlapRecyclerViewAdapter::class.java.simpleName

    //----array list to be shown
    private var mImageList = ArrayList<String>()

    //----array list to be shown after expansion
    private var mImageExpandedList = ArrayList<String>()

    //----flag to indicate recyclerview is expanded or not
    private var isExpanded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_image, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val mCurrentImageModel = mImageList[position]

        //----bind data to view
        holder.bind(mCurrentImageModel)
    }

    /**
     * set array list over adapter
     */
    fun setImageList(mImageList: ArrayList<String>) {
        if (mImageList.size > overlapLimit) {
            for (mImageModel in mImageList) {
                if (this.mImageList.size <= overlapLimit) {
                    this.mImageList.add(mImageModel)
                } else {
                    this.mImageExpandedList.add(mImageModel)
                }
            }
        } else {
            this.mImageList = mImageList
        }
        notifyDataSetChanged()
    }

    /**
     * add items to array list
     */
    fun addItems(mImageList: ArrayList<String>) {
        this.mImageList.addAll(mImageList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mImageList.size
    }

    /**
     * get item by its position
     */
    fun getItem(pos: Int): String {
        return mImageList[pos]
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var requestOptions: RequestOptions? = null

        /**
         * init request option for glide
         */
        private fun getGlideRequestOptions(): RequestOptions {
            if (requestOptions == null) {
                requestOptions = RequestOptions()
                requestOptions?.error(R.mipmap.ic_launcher)
                requestOptions?.placeholder(R.mipmap.ic_launcher)
            }
            return requestOptions!!
        }

        /**
         * bind model data to item
         */
        fun bind(mImageModel: String) {

            if (adapterPosition == overlapLimit && !isExpanded) {

                //----set text drawable to show count on last imageview
                val text = mImageExpandedList.size.toString()
                val drawable = TextDrawable.builder()
                    .beginConfig()
                    .textColor(Color.WHITE)
                    .width(90)
                    .height(90)
                    .endConfig()
                    .buildRound(text, Color.parseColor("#8FAE5D"))
                itemView.imageView.setImageDrawable(drawable)
            } else {

                //----load image
                Glide.with(mContext)
                    .load(mImageModel)
                    .apply(getGlideRequestOptions())
                    .into(itemView.imageView)
            }

            //----handle item click
            itemView.imageView.setOnClickListener {
                if (adapterPosition == overlapLimit && !isExpanded) {
                    recyclerViewClickListener.onNumberedItemClick(adapterPosition)
                } else {
                    recyclerViewClickListener.onNormalItemClicked(adapterPosition)
                }
            }
        }
    }
}