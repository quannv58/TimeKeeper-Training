package com.gem.timekeeper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gem.timekeeper.R;

/**
 * Created by BaVV on 21/03/2018.
 * HomeItemView
 */
public class HomeItemView extends LinearLayout {

  private ImageView mIcon;
  private TextView mContentTv;

  public HomeItemView(Context context) {
    super(context);
    initLayout(null, 0);
  }

  public HomeItemView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initLayout(attrs, 0);
  }

  public HomeItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initLayout(attrs, defStyleAttr);
  }

  private void initLayout(AttributeSet attrs, int defStyle) {
    inflate(getContext(), R.layout.view_home_item, this);

    mIcon = findViewById(R.id.iv_view_home_item_icon);
    mContentTv = findViewById(R.id.btn_view_home_item_content);

    TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.HomeItemView);
    mIcon.setImageDrawable(ta.getDrawable(R.styleable.HomeItemView_iconSrc));
    mContentTv.setBackground(ta.getDrawable(R.styleable.HomeItemView_contentBackground));
    mContentTv.setText(ta.getString(R.styleable.HomeItemView_contentText));
    ta.recycle();
  }

  @Override
  public void setOnClickListener(@Nullable OnClickListener l) {
    super.setOnClickListener(l);
    mContentTv.setOnClickListener(l);
  }
}
