package com.gan.breakingbadcharacters.ui.utils

import android.content.Context
import android.graphics.*
import android.util.Log
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.gan.breakingbadcharacters.R

interface ImageLoader {
    fun load(url: String?, imageView: ImageView)

    fun loadCircled(
        url: String,
        imageView: ImageView,
        borderSize: Float = 0F,
        borderColor: Int = Color.WHITE
    )
}

class ImageLoaderImpl(private val context: Context) : ImageLoader {

    override fun load(url: String?, imageView: ImageView) {
        if (url.isNullOrBlank()) return

        try {
            Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView)
        } catch (ex: Exception) {
            Log.e("Image Loader", "Could not load resource from URL: $url")
        }
    }

    override fun loadCircled(
        url: String,
        imageView: ImageView,
        borderSize: Float,
        borderColor: Int
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .placeholder(R.mipmap.ic_launcher)
            .apply(RequestOptions.circleCropTransform())
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    imageView.setImageDrawable(
                        resource?.run {
                            RoundedBitmapDrawableFactory.create(
                                context.resources,
                                if (borderSize > 0) {
                                    createBitmapWithBorder(borderSize, borderColor)
                                } else {
                                    this
                                }
                            ).apply {
                                isCircular = true
                            }
                        }
                    )
                }
            })
    }

    /**
     * Create a new bordered bitmap with the specified borderSize and borderColor
     *
     * @param borderSize - The border size in pixel
     * @param borderColor - The border color
     * @return A new bordered bitmap with the specified borderSize and borderColor
     */
    fun Bitmap.createBitmapWithBorder(borderSize: Float, borderColor: Int = Color.WHITE): Bitmap {
        val borderOffset = (borderSize * 2).toInt()
        val halfWidth = width / 2
        val halfHeight = height / 2
        val circleRadius = Math.min(halfWidth, halfHeight).toFloat()
        val newBitmap = Bitmap.createBitmap(
            width + borderOffset,
            height + borderOffset,
            Bitmap.Config.ARGB_8888
        )

        // Center coordinates of the image
        val centerX = halfWidth + borderSize
        val centerY = halfHeight + borderSize

        val paint = Paint()
        val canvas = Canvas(newBitmap).apply {
            // Set transparent initial area
            drawARGB(0, 0, 0, 0)
        }

        // Draw the transparent initial area
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        canvas.drawCircle(centerX, centerY, circleRadius, paint)

        // Draw the image
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(this, borderSize, borderSize, paint)

        // Draw the createBitmapWithBorder
        paint.xfermode = null
        paint.style = Paint.Style.STROKE
        paint.color = borderColor
        paint.strokeWidth = borderSize
        canvas.drawCircle(centerX, centerY, circleRadius, paint)
        return newBitmap
    }
}
