package com.example.crinaed.ProgressBar;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.layout.home.DetailsProgressBarDialog;
import com.example.crinaed.util.Pair;
import com.example.crinaed.util.Period;
import com.example.crinaed.view.MySliderAdapter;
import com.example.crinaed.view.ProgressBarView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderProgressBarAdapterNew extends SliderViewAdapter<SliderProgressBarAdapterNew.SliderProgressBarVH> implements MySliderAdapter {

    public final static String TAG = "LAUNCH_DETAIL_FRAGMENT";
    private Context context;
    private List<CommitmentWithMyStep> myCommitments;
    private LifecycleOwner owner;

    public SliderProgressBarAdapterNew(Context context, LifecycleOwner owner) {
        this.context = context;
        this.owner=owner;
        DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().getAllCommitmentOnGoing(false).observe(owner, new Observer<List<CommitmentWithMyStep>>() {
            @Override
            public void onChanged(List<CommitmentWithMyStep> commitmentWithMySteps) {
                if(myCommitments== null || myCommitments.size()!=commitmentWithMySteps.size()) {
                    myCommitments = commitmentWithMySteps;
                    Log.d("naed", "update of my commitment");
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public SliderProgressBarVH onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar_new, null);
        return new SliderProgressBarVH(itemView, owner);
    }

    @Override
    public void onBindViewHolder(final SliderProgressBarVH viewHolder, final int position) {
        final CommitmentWithMyStep myCommitment = myCommitments.get(position);
        Pair<Integer,Integer> color = getColorForPage(position);
        viewHolder.setColor(color);
        viewHolder.setCommitment(myCommitment.commitment);
        viewHolder.progressBarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsProgressBarDialog dialog = new DetailsProgressBarDialog(context, myCommitment.commitment.name, myCommitment.commitment.idCommitment, owner);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        viewHolder.status = false;
                    }
                });
                viewHolder.status=true;
                dialog.show();
            }
        });
    }

    @Override
    public int getCount() {
        if(myCommitments!=null) {
            return myCommitments.size();
        }
        return 0;
    }

    @Override
    public Pair<Integer,Integer> getColorForPage(int position){
        return myCommitments.get(position).steps.get(0).myStep.category.toColor(context);
    }

    static class SliderProgressBarVH extends SliderViewAdapter.ViewHolder {
        boolean status; // if the dialog is open(to avoid open it multiple times
        TextView title;
        ProgressBarView progressBarView;
        TextView description;
        LiveData<List<MyStepDoneWithMyStep>> liveDataDay;
        Observer<List<MyStepDoneWithMyStep>> observerDay = new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                newestDay=steps;
                updateData();
            }
        };
        LiveData<List<MyStepDoneWithMyStep>> liveDataWeek;
        Observer<List<MyStepDoneWithMyStep>> observerWeek = new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                newestWeek=steps;
                updateData();
            }
        };
        List<MyStepDoneWithMyStep> newestDay;
        List<MyStepDoneWithMyStep> newestWeek;
        LifecycleOwner owner;

        public SliderProgressBarVH(View itemView, LifecycleOwner owner) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.progressBarView = itemView.findViewById(R.id.progressBarItem);
            this.description =  itemView.findViewById(R.id.description_objective);
            this.owner=owner;
        }

        public void setColor(Pair<Integer, Integer> color) {
            progressBarView.setForegroundColor(color.getX());
            progressBarView.setBackgroundColor(color.getY());
            title.setTextColor(color.getX());
        }

        public void setCommitment(MyCommitment commitment) {
            description.setText(commitment.desc);
            title.setText(commitment.name);
            if(liveDataDay!=null){
                liveDataDay.removeObserver(observerDay);
            }
            if(liveDataWeek!=null){
                liveDataWeek.removeObserver(observerWeek);
            }
            liveDataDay=DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().getStepOnGoingByIdCommitment(commitment.idCommitment, Period.DAY);
            liveDataDay.observe(owner, observerDay);
            liveDataWeek=DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().getStepOnGoingByIdCommitment(commitment.idCommitment, Period.WEEK);
            liveDataWeek.observe(owner, observerWeek);
        }
        private void updateData(){
            List<MyStepDoneWithMyStep> both= new ArrayList<>();
            if(newestDay!=null){
                both.addAll(newestDay);
            }
            if(newestWeek!=null){
                both.addAll(newestWeek);
            }
            updatePercentage(both);
        }

        private void updatePercentage(List<MyStepDoneWithMyStep> results){
            float sum=0;
            float weight=0;
            for(MyStepDoneWithMyStep step : results){
                sum+=step.stepDone.result;
                weight+=step.step.max;
            }
            progressBarView.setProgress(100*sum/weight);
        }
    }
}
