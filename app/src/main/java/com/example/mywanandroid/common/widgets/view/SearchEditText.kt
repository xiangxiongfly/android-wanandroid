package com.example.mywanandroid.common.widgets.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.example.mywanandroid.R
import com.example.mywanandroid.common.ext.dp
import com.example.mywanandroid.common.ext.sp

class SearchEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseEditText(context, attrs) {

    private val labelPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = searchLabelColor
            textSize = searchLabelSize.toFloat()
        }
    }

    private val centerX: Float
        get() = width / 2f
    private val centerY: Float
        get() = height / 2f

    private val bgDrawable: Drawable?
    private val searchIcon: Drawable?
    private val searchIconSize: Int
    private val searchLabel: String?
    private val searchLabelSize: Int
    private val searchLabelColor: Int

    init {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchEditText)
        a.let {
            bgDrawable = it.getDrawable(R.styleable.SearchEditText_set_background)
            searchIcon = it.getDrawable(R.styleable.SearchEditText_set_searchIcon)
            searchIconSize =
                it.getDimensionPixelSize(R.styleable.SearchEditText_set_searchIconSize, 0)
            searchLabel = it.getString(R.styleable.SearchEditText_set_searchLabel)
            searchLabelSize =
                it.getDimensionPixelSize(R.styleable.SearchEditText_set_searchLabelSize, 10.sp)
            searchLabelColor =
                it.getColor(R.styleable.SearchEditText_set_searchLabelColor, 0x666666)
        }
        a.recycle()
        setup()
    }

    private fun setup() {
        bgDrawable?.let { background = it }
        searchIcon?.let {
            val size = if (searchIconSize > 0) searchIconSize else it.intrinsicWidth
            it.setBounds(0, 0, size, size)
        }
        imeOptions = EditorInfo.IME_ACTION_SEARCH
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        doDraw(canvas)
    }

    private fun doDraw(canvas: Canvas) {
        // 只有无文字时才绘制居中提示（图标+文字）
        if (text.isNullOrEmpty()) {
            doDrawCenterHint(canvas)
        }
    }

    private fun doDrawCenterHint(canvas: Canvas) {
        when {
            searchIcon != null && searchLabel != null -> drawIconAndText(canvas)
            searchIcon != null -> drawCenterIcon(canvas)
            searchLabel != null -> drawCenterText(canvas)
        }
    }

    /**
     * 绘制：图标 + 文字（整体居中）
     */
    private fun drawIconAndText(canvas: Canvas) {
        val icon = searchIcon ?: return
        val label = searchLabel ?: return
        val iconSize = searchIconSize.takeIf { it > 0 } ?: icon.intrinsicWidth
        val textWidth = labelPaint.measureText(label)

        // 组合总宽度 = 图标宽度 + 文字宽度 + 间距
        val space = 8.dp // 图标和文字之间的间距
        val totalWidth = iconSize + space + textWidth

        // 绘制起点（让整体水平居中）
        val startX = centerX - totalWidth / 2

        // 1. 绘制图标（垂直居中）
        val iconTop = centerY - iconSize / 2
        canvas.save()
        canvas.translate(startX, iconTop)
        icon.draw(canvas)
        canvas.restore()

        // 2. 绘制文字（垂直居中）
        val textX = startX + iconSize + space
        val textY = centerY - (labelPaint.descent() + labelPaint.ascent()) / 2
        canvas.drawText(label, textX, textY, labelPaint)
    }

    /**
     * 绘制：仅图标（完美垂直居中）
     */
    private fun drawCenterIcon(canvas: Canvas) {
        val icon = searchIcon ?: return
        val iconSize = searchIconSize.takeIf { it > 0 } ?: icon.intrinsicWidth

        // 核心：垂直居中计算公式
        val left = centerX - iconSize / 2
        val top = centerY - iconSize / 2

        canvas.save()
        canvas.translate(left, top)
        icon.draw(canvas)
        canvas.restore()
    }

    /**
     * 绘制：仅文字（完美垂直居中）
     */
    private fun drawCenterText(canvas: Canvas) {
        val label = searchLabel ?: return
        val textX = centerX - labelPaint.measureText(label) / 2
        val textY = centerY - (labelPaint.descent() + labelPaint.ascent()) / 2
        canvas.drawText(label, textX, textY, labelPaint)
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams) {
        if (params.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
        }
        super.setLayoutParams(params)
    }
}