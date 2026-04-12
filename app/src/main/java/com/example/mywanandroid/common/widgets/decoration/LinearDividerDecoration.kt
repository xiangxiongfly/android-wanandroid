package com.example.mywanandroid.common.widgets.decoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mywanandroid.common.ext.dp

class LinearDividerDecoration(
    private val dividerSize: Float = 0.5F.dp,
    private val dividerColor: Int = Color.parseColor("#C0C0C0"),
    private val startMargin: Int = 0,
    private val endMargin: Int = 0
) : RecyclerView.ItemDecoration() {

    private val ignoreIndexList = mutableListOf<Int>()

    private val paint by lazy {
        Paint().apply {
            color = dividerColor
        }
    }

    // 设置第几条不显示
    fun setIgnorePosition(vararg indexs: Int) {
        ignoreIndexList.addAll(indexs.toList())
    }

    /**
     * 设置分割线大小
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager as LinearLayoutManager
        if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
            outRect.bottom = dividerSize.toInt()
        } else {
            outRect.right = dividerSize.toInt()
        }
    }

    /**
     * 绘制分割线
     */
    override fun onDraw(
        c: Canvas, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager as LinearLayoutManager
        if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
            drawVerticalDivider(c, parent)
        } else {
            drawHorizontalDivider(c, parent)
        }
    }

    /**
     * 垂直方向分割线绘制
     */
    private fun drawVerticalDivider(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft + startMargin
        val right = parent.measuredWidth - parent.paddingRight - endMargin
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) { //最后一条不显示
            if (i in ignoreIndexList) {
                continue
            }
            val childView = parent.getChildAt(i)
            val layoutParams = childView.layoutParams as RecyclerView.LayoutParams
            val top = childView.bottom + layoutParams.bottomMargin
            val bottom = top + dividerSize
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    /**
     * 水平方向分割线绘制
     */
    private fun drawHorizontalDivider(canvas: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop + startMargin
        val bottom = parent.measuredHeight - parent.paddingBottom - endMargin
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) { //最后一条不显示
            val childView = parent.getChildAt(i)
            val layoutParams = childView.layoutParams as RecyclerView.LayoutParams
            val left = childView.right + layoutParams.rightMargin
            val right = left + dividerSize
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }
}