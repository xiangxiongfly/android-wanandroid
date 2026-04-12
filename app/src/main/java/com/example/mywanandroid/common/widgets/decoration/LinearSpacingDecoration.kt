package com.example.mywanandroid.common.widgets.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LinearSpacingDecoration(
    private val spacing: Int = 0,
    private val includeEdge: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager as LinearLayoutManager
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return
        val count = parent.adapter?.itemCount ?: 0
        if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
            if (includeEdge) {
                if (position == 0) {
                    outRect.top = spacing
                }
                outRect.bottom = spacing
            } else {
                if (position == count - 1) {
                    outRect.bottom = 0
                } else {
                    outRect.bottom = spacing
                }
            }
        } else {
            if (includeEdge) {
                if (position == 0) {
                    outRect.left = spacing
                }
                outRect.right = spacing
            } else {
                if (position == count - 1) {
                    outRect.right = 0
                } else {
                    outRect.right = spacing
                }
            }
        }
    }
}