package com.gem.timekeeper.screen.main.bestsurvey.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gem.timekeeper.R;
import com.gem.timekeeper.data.dto.SurveyDTO;
import com.gem.timekeeper.screen.adapter.OnItemClickListener;
import com.gemvietnam.base.adapter.RecyclerBaseAdapter;
import com.gemvietnam.widget.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by BaVV on 03/20/2018.
 * BestSurveyAdapter
 */
public class BestSurveyAdapter extends RecyclerBaseAdapter<SurveyDTO, BestSurveyAdapter.ViewHolder> {

  private OnItemClickListener<SurveyDTO> mListener;

  public BestSurveyAdapter(Context context, OnItemClickListener<SurveyDTO> listener) {
    super(context);
    mListener = listener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.item_survey, parent, false);
    return new ViewHolder(view);
  }

  class ViewHolder extends BaseViewHolder<SurveyDTO> {

    @BindView(R.id.tv_item_survey_title)
    TextView mTitleTv;

    ViewHolder(View itemView) {
      super(itemView);
    }

    @Override
    public void bindView(final SurveyDTO data, int position) {
      mTitleTv.setText(data.getTitle());
      mTitleTv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mListener.onItemClick(data);
        }
      });
    }
  }
}

