package com.example.lorempicsum

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_layout.view.*

/**
 * ImageAdapter for recyclerview.
 */
class ImageAdapter(private val listImageData: List<ImageData>) : RecyclerView.Adapter<ViewHolder>() {

    /**
     * Holds reference of the tag value to show adapter log.
     */
    private val TAG: String = ImageAdapter::class.java.name

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.v(TAG, "onCreateViewHolder()")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.v(TAG, String.format("getItemCount() :: listImageData.size = %d", listImageData.size))
        return listImageData.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.v(TAG, String.format("onBindViewHolder() :: ImageData id = %d, author = %s", listImageData[position].id, listImageData[position].author))
        return viewHolder.bindView(listImageData[position])
    }
}

/**
 * ViewHolder for image layout.
 */
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     * Binds view to the adapter.
     */
    fun bindView(imageData: ImageData) {
        if (imageData.id != null) {
            Log.v(
                "ImageAdapter ViewHolder",
                String.format("bindView() :: imageData.id = %d", imageData.id)
            )
            Glide.with(itemView.context)
                .load(ServiceBuilder.IMAGE_URL + imageData.id)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .override(300, 300)
                .centerCrop()
                .into(itemView.imgvImage)
        }

        if (imageData.author != null && imageData.author.isNotEmpty()) {
            Log.v(
                "ImageAdapter ViewHolder",
                String.format("bindView() :: imageData.author = %s", imageData.author)
            )
            itemView.txtvAuthor.text = imageData.author
        }
    }
}