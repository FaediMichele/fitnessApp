package com.example.crinaed.ProgressBar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.crinaed.DetailsProgressBarDialog;
import com.example.crinaed.ProgressBarTest.TestModel.ProgressBarModel;
import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.database.repository.CommitmentRepository;
import com.example.crinaed.database.repository.RepositoryManager;
import com.example.crinaed.util.Category;
import com.example.crinaed.view.ProgressBarView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This class manage the categories and the relatives Dialog.
 */
public class SliderProgressBarAdapter extends SliderViewAdapter<SliderProgressBarAdapter.SliderProgressBarVH> {

    public final static String TAG = "LAUNCH_DETAIL_FRAGMENT";
    private Context context;
    private List<LiveData<List<MyStepDoneWithMyStep>>> stepsForEachCategory= new ArrayList<>();
    private List<List<MyStepDoneWithMyStep>> newestStep = new ArrayList<>();
    private LifecycleOwner owner;
    private int[] countForEachCategory;

    public SliderProgressBarAdapter(Context context, LifecycleOwner owner) {
        CommitmentRepository repo = DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository();
        this.owner=owner;
        this.context = context;
        repo.updateMyStepDone();
        countForEachCategory = new int[Category.values().length];
        for(final Category c : Category.values()){
            final LiveData<List<MyStepDoneWithMyStep>> stepForCategory = repo.getStepOnGoing(c);
            stepForCategory.observe(owner, new Observer<List<MyStepDoneWithMyStep>>() {
                @Override
                public void onChanged(List<MyStepDoneWithMyStep> steps) {
                    countForEachCategory[c.ordinal()]= steps.size();
                    newestStep.add(c.ordinal(), steps);
                    notifyDataSetChanged();
                }
            });
            stepsForEachCategory.add(stepForCategory);
            List<MyStepDoneWithMyStep> val = stepForCategory.getValue();
            if(val != null){
                countForEachCategory[c.ordinal()]= val.size();
                newestStep.add(c.ordinal(), val);
            } else{
                countForEachCategory[c.ordinal()]= 0;
                newestStep.add(c.ordinal(), new ArrayList<MyStepDoneWithMyStep>());
            }

        }

    }

    /*public void renewItems(List<SliderProgressBarModel> sliderItems) {
        /* TODO
        // this.s = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        /* TODO
        // this.progressBarModelList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderProgressBarModel sliderItem) {
        /* TODO
        // this.progressBarModelList.add(sliderItem);
        notifyDataSetChanged();
    }*/

    public Category getCategoryForPosition(int position){
        return Category.values()[position];
    }

    @Override
    public SliderProgressBarVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_item, null);
        return new SliderProgressBarVH(inflate);
    }


    @Override
    public void onBindViewHolder(final SliderProgressBarVH viewHolder, final int position) {
        final List<MyStepDoneWithMyStep> listForPosition = newestStep.get(position);
        int primaryColor;
        int secondaryColor;

        switch(Category.values()[position]){
            case SOCIAL:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.greenPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.greenSecondary);
                break;
            case MENTAL:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.bluPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.bluSecondary);
                break;
            case SPORT:
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

        viewHolder.progressBarView.setProgress(getPercentProgress(listForPosition));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Float progress = viewHolder.progressBarView.getProgress();
                // progress = (progress+10)%100;
                // viewHolder.progressBarView.setProgress(progress);
                //build data for dialog
                //TODO change the title of the dialog with a user-friendly one
                new DetailsProgressBarDialog((AppCompatActivity) context, getCategoryForPosition(position).name().toUpperCase(), stepsForEachCategory.get(position), owner).show();
                Log.d("naed","open category step to do");
            }
        });
    }

    private Float getPercentProgress (List<MyStepDoneWithMyStep> steps){
        if(steps.size()==0){
            return 1f;
        }
        Double result = 0.0;
        for (MyStepDoneWithMyStep step: steps) {
            result = result + ((double) step.stepDone.result)/step.step.max;
        }
        result = result / steps.size();
        return result.floatValue()*100;
    }

    @Override
    public int getCount() {
        return countForEachCategory.length;
    }

    static class SliderProgressBarVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ProgressBarView progressBarView;

        public SliderProgressBarVH(View itemView) {
            super(itemView);
            progressBarView = itemView.findViewById(R.id.progressBarItem);
            this.itemView = itemView;
        }
    }

}
