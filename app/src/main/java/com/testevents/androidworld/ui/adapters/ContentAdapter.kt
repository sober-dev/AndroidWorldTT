package com.testevents.androidworld.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.testevents.androidworld.R
import com.testevents.androidworld.models.ContentData
import kotlinx.android.synthetic.main.content_item.view.*

class ContentAdapter(private val posts: List<ContentData>, private val clickListener: (ContentData) -> Unit) :
    RecyclerView.Adapter<ContentAdapter.PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.content_item, parent, false)
        )

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) =
        holder.bind(posts, position, clickListener)

    override fun getItemCount() = posts.size

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(result: List<ContentData>, position: Int, clickListener: (ContentData) -> Unit) {
            val contentItem: ContentData = result[position]
            itemView.tv_title.text = contentItem.title
            itemView.tv_short_description.text = contentItem.shortDescription
            if (contentItem.type == "event") {
                itemView.tv_short_description.textSize = 14f
                itemView.tv_short_description.setTextColor(itemView.context.getColor(R.color.red))
            } else if (contentItem.type == "shop") {
                itemView.tv_short_description.textSize = 10f
                itemView.tv_short_description.setTextColor(itemView.context.getColor(R.color.gray))
            }
            Picasso.get().load(contentItem.smallImage).into(itemView.iv_small_image)
            itemView.setOnClickListener { clickListener(contentItem) }
        }
    }
}
