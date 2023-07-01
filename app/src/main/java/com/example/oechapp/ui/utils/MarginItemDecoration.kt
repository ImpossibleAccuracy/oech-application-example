package com.example.oechapp.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (val manager = parent.layoutManager) {
            is GridLayoutManager -> makeOffsetsGrid(view, manager, parent, outRect)
            is LinearLayoutManager -> makeOffsetsLinear(view, manager, parent, outRect)
        }
    }

    private fun makeOffsetsLinear(
        view: View,
        manager: LinearLayoutManager,
        parent: RecyclerView,
        outRect: Rect
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (manager.orientation == RecyclerView.VERTICAL) {
            if (position > 0) {
                outRect.top = spacing
            }
        } else {
            if (position > 0) {
                outRect.left = spacing
            }
        }
    }

    private fun makeOffsetsGrid(
        view: View,
        manager: GridLayoutManager,
        parent: RecyclerView,
        outRect: Rect
    ) {
        val position = parent.getChildAdapterPosition(view)

        val viewLayoutParams = view.layoutParams
        if (viewLayoutParams !is GridLayoutManager.LayoutParams) return

        val column = viewLayoutParams.spanIndex
        val columnCount = manager.spanCount
        val isFirstLine = position < columnCount

        if (manager.orientation == RecyclerView.VERTICAL) {
            if (!isFirstLine) {
                outRect.top = spacing
            }

            if (column > 0) {
                outRect.left = spacing
            }
        } else {
            if (!isFirstLine) {
                outRect.left = spacing
            }

            if (column > 0) {
                outRect.top = spacing
            }
        }
    }
}