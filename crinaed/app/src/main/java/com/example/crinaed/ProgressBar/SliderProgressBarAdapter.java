package com.example.crinaed.ProgressBar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.crinaed.DetailsProgressBarDialog;
import com.example.crinaed.R;
import com.example.crinaed.view.ProgressBarView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SliderProgressBarAdapter extends SliderViewAdapter<SliderProgressBarAdapter.SliderProgressBarVH> {

    public final static String TAG = "LAUNCH_DETAIL_FRAGMENT";
    private Context context;
    private List<SliderProgressBarModel> progressBarModelList = new ArrayList<>();

    public SliderProgressBarAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderProgressBarModel> sliderItems) {
        this.progressBarModelList = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.progressBarModelList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderProgressBarModel sliderItem) {
        this.progressBarModelList.add(sliderItem);
        notifyDataSetChanged();
    }

    public List<SliderProgressBarModel> getProgressBarModelList() {
        return Collections.unmodifiableList(progressBarModelList);
    }

    @Override
    public SliderProgressBarVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_item, null);
        return new SliderProgressBarVH(inflate);
    }


    @Override
    public void onBindViewHolder(final SliderProgressBarVH viewHolder, final int position) {
        SliderProgressBarModel progressBarModel = progressBarModelList.get(position);
        int primaryColor;
        int secondaryColor;
        switch(progressBarModel.getCategory()){
            case SliderProgressBarModel.SOCIAL:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.greenPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.greenSecondary);
                break;
            case SliderProgressBarModel.MENTAL:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.bluPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.bluSecondary);
                break;
            case SliderProgressBarModel.PHYSICAL:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.redPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.redSecondary);
                break;
            default:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.redPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.redSecondary);
                break;
        }
        viewHolder.progressBarView.setForegroundColor(primaryColor);
        viewHolder.progressBarView.setBackgroundColor(secondaryColor);
        viewHolder.progressBarView.setProgress(this.getPercentProgress(progressBarModel));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float progress = viewHolder.progressBarView.getProgress();
                progress = (progress+10)%100;
                viewHolder.progressBarView.setProgress(progress);
                new DetailsProgressBarDialog((AppCompatActivity) context).show();
            }
        });
    }

    private Float getPercentProgress (SliderProgressBarModel progressBarModel){
        Double result = 0.0;
        for (Step step: progressBarModel.getStepList()) {
            result = result + step.getProgressPercentage();
        }
        result = result / progressBarModel.getStepList().size();
        return result.floatValue();
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return progressBarModelList.size();
    }

    class SliderProgressBarVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ProgressBarView progressBarView;

        public SliderProgressBarVH(View itemView) {
            super(itemView);
            progressBarView = itemView.findViewById(R.id.progressBarItem);
            this.itemView = itemView;
        }
    }

}
