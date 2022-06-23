package com.giphy.gifdemo.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * @Author: Amit Patoliya
 * @Date: 23/06/22
 */
class GifImageView(context: Context, attributeSet: AttributeSet) : AppCompatImageView(context, attributeSet) {

    private var aspectRatio: Float = 1f

    fun setDimensions(height: Int, width: Int) {
        this.aspectRatio = height / width.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val newHeight = (measuredWidth * aspectRatio).toInt()
        setMeasuredDimension(measuredWidth, newHeight)
    }

}