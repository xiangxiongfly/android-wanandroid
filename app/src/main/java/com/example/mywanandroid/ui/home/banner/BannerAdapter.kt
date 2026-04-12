package com.example.mywanandroid.ui.home.banner

import android.widget.ImageView
import coil3.load
import com.example.mywanandroid.R
import com.example.mywanandroid.data.model.Banner
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class BannerAdapter : BaseBannerAdapter<Banner>() {

    override fun bindData(
        holder: BaseViewHolder<Banner>,
        data: Banner,
        position: Int,
        pageSize: Int
    ) {
        val imageView = holder.findViewById<ImageView>(R.id.banner_image)
        imageView.load(data.imagePath)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner;
    }
}
