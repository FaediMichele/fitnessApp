package com.example.crinaed.ProgressBarDetails;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    public ProgressBarDetailsAdapter(List<MyStepDoneWithMyStep> steps) {
        this.stepList = steps;
        this.holderList = new ArrayList<>(steps.size());
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
        View itemView;
        TypeOfStep type=TypeOfStep.values()[viewType];
        switch (type){
            case CHECKLIST:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checked_progress_bar, parent, false);
                break;
            case PROGRESSION:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressive_progress_bar, parent, false);
                break;
            default:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressive_progress_bar, parent, false);
                break;
        }
        ProgressBarDetailsViewHolder ret = new ProgressBarDetailsViewHolder(itemView);
        holderList.add(ret);
        return ret;
    }

    // is optimized to use return only the data that has been changed.
    public Pair<Boolean, MyStepDone[]> getDataToSave(){
        MyStepDone[] ret = new MyStepDone[stepList.size()];
        for(int i=0; i< ret.length; i++){
            ret[i] = stepList.get(i).stepDone;
            if(stepList.get(i).step.getType() == TypeOfStep.CHECKLIST){
                if(ret[i].result == (int) (holderList.get(i).checkBox.isChecked()?stepList.get(i).step.max:0)){
                    ret[i]=null;
                    continue;
                }
                ret[i].result = (int) (holderList.get(i).checkBox.isChecked()?stepList.get(i).step.max:0);
            } else{
                if(holderList.get(i).editText.getText().toString().equals(Integer.valueOf(ret[i].result).toString())){
                    ret[i]=null;
                    continue;
                }
                try{
                    ret[i].result = Integer.parseInt(holderList.get(i).editText.getText().toString());
                } catch (NumberFormatException e) {
                    return new Pair<>(false, null);
                }
            }
        }
        return new Pair<>(true, ret);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressBarDetailsViewHolder holder, int position) {
        MyStepDoneWithMyStep currentStep = this.stepList.get(position);
        holder.description.setText(currentStep.step.name);
        switch (currentStep.step.getType()){
            case CHECKLIST:
                holder.checkBox.setChecked(currentStep.stepDone.result == currentStep.step.max);
                break;
            case PROGRESSION:
                holder.editText.setText(String.valueOf(currentStep.stepDone.result), TextView.BufferType.EDITABLE);
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
        EditText editText;

        public ProgressBarDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.description = itemView.findViewById(R.id.description_step);
            if (itemView.findViewById(R.id.value_step) != null){
                this.editText = itemView.findViewById(R.id.value_step);
            }else{
                this.checkBox = itemView.findViewById(R.id.value_step_checked);
            }
            this.itemView = itemView;;
        }
    }
}
