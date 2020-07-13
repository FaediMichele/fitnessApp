package com.example.crinaed.ProgressBarDetails;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.database.entity.MyStepDone;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.util.Pair;
import com.example.crinaed.util.TypeOfStep;

import java.util.ArrayList;
import java.util.List;


/**
 * This is the Adapter for the collection of MyStepDone that is on going with one specific {@link com.example.crinaed.util.Category}
 * It allows the user to change the value for the steps then save it.
 */
public class ProgressBarDetailsAdapter extends RecyclerView.Adapter<ProgressBarDetailsAdapter.ProgressBarDetailsViewHolder> {

    private List<MyStepDoneWithMyStep> stepList;
    private List<ProgressBarDetailsViewHolder> holderList;
    private Context context;

    public ProgressBarDetailsAdapter(List<MyStepDoneWithMyStep> steps, Context context) {
        this.stepList = steps;
        this.holderList = new ArrayList<>(steps.size());
        this.context=context;
    }

    public void update(final List<MyStepDoneWithMyStep> newSteps){
        DiffUtil.DiffResult result =  DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return stepList.size();
            }

            @Override
            public int getNewListSize() {
                return newSteps.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                MyStepDoneWithMyStep old =stepList.get(oldItemPosition);
                MyStepDoneWithMyStep newI =stepList.get(newItemPosition);
                return old.stepDone.idMyStep==newI.stepDone.idMyStep && old.stepDone.dateStart == newI.stepDone.dateStart;
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                MyStepDoneWithMyStep old =stepList.get(oldItemPosition);
                MyStepDoneWithMyStep newI =stepList.get(newItemPosition);
                return old.equals(newI);
            }
        });
        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ProgressBarDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        TypeOfStep type=TypeOfStep.values()[viewType];
        // progression
        if (type == TypeOfStep.CHECKLIST) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checked_progress_bar, parent, false);
        } else { // Progression
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressive_progress_bar, parent, false);
        }
        ProgressBarDetailsViewHolder ret = new ProgressBarDetailsViewHolder(itemView, context);
        holderList.add(ret);
        return ret;
    }


    public MyStepDone[] getDataToSave(){
        MyStepDone[] ret = new MyStepDone[stepList.size()];
        for(int i=0; i< ret.length; i++){
            ret[i] = stepList.get(i).stepDone;
            if(stepList.get(i).step.getType() == TypeOfStep.CHECKLIST){
                ret[i].result = (int) (holderList.get(i).checkBox.isChecked()?stepList.get(i).step.max:0);
            } else{
                ret[i].result = holderList.get(i).seekBar.getProgress();
            }
        }
        return ret;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressBarDetailsViewHolder holder, int position) {
        MyStepDoneWithMyStep currentStep = this.stepList.get(position);
        holder.setDescription(currentStep.step.name);
        switch (currentStep.step.getType()){
            case CHECKLIST:
                holder.checkBox.setChecked(currentStep.stepDone.result == currentStep.step.max);
                holder.previousVal=holder.checkBox.isChecked()?1:0;
                holder.checkBox.setClickable(!holder.checkBox.isChecked());
                break;
            case PROGRESSION:
                holder.seekBar.setProgress(currentStep.stepDone.result);
                holder.seekBar.setMax((int) currentStep.step.max);
                holder.previousVal=currentStep.stepDone.result;
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return this.stepList.get(position).step.getType().ordinal();
    }

    @Override
    public int getItemCount() {
        return this.stepList.size();
    }

    static class ProgressBarDetailsViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView description;
        CheckBox checkBox;
        SeekBar seekBar;
        boolean checklist=false;

        int previousVal=0;

        public ProgressBarDetailsViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            this.description = itemView.findViewById(R.id.description_step);
            if (itemView.findViewById(R.id.seekBar) != null){
                this.seekBar = itemView.findViewById(R.id.seekBar);
                final TextView seekBarValue= itemView.findViewById(R.id.progress_text);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(progress < previousVal){
                            seekBar.setProgress(previousVal);
                        }else{
                            seekBarValue.setText(context.getString(R.string.progress_text, progress, seekBar.getMax()));
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }else{
                this.checkBox = itemView.findViewById(R.id.checkBox);
                checklist=true;
            }
            this.itemView = itemView;
        }

        public void setDescription(String text){
            if(text.length()>0) {
                text = text.substring(0, 1).toUpperCase();
                if (text.length() > 1) {
                    text += text.substring(1);
                }
            }

            description.setText(text);
        }

        public float getResult(){
            if(checklist){
                return checkBox.isChecked()?1:previousVal;
            } else{
                return seekBar.getProgress();
            }
        }
    }
}
