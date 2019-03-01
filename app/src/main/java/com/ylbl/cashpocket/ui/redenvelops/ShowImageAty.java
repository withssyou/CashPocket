package com.ylbl.cashpocket.ui.redenvelops;

import android.content.Intent;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;

import butterknife.BindView;

public class ShowImageAty extends BaseToolBarAty {
    @BindView(R.id.aty_image_show_iv)
    ImageView imageView;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_image_show;
    }

    @Override
    protected String iniTitle() {
        return "图片预览";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
        Intent intent = getIntent();
        String url = intent.getStringExtra("img");
        Picasso.with(context).load(url).into(imageView);
    }
}
