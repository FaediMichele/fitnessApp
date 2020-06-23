package com.example.crinaed.ProgressBarDetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.ProgressBar.Step;
import com.example.crinaed.R;
import com.example.crinaed.view.ProgressBarView;

import java.util.List;

public class ProgressBarDetailsAdapter extends RecyclerView.Adapter<ProgressBarDetailsAdapter.ProgressBarDetailsViewHolder> {

    public final static int CHECKED = 0;
    public final static int PROGRESS = 1;

    List<Step> stepList;


    @NonNull
    @Override
    public ProgressBarDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType){
            case CHECKED:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checked_progress_bar, parent, false);
                break;
            case PROGRESS:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressive_progress_bar, parent, false);
                break;
            default:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressive_progress_bar, parent, false);
                break;
        }
        return new ProgressBarDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressBarDetailsViewHolder holder, int position) {
        Step currentStep = this.stepList.get(position);
        holder.description.setText(currentStep.getDescription());
        if(currentStep.isChecklist()){
            holder.checkBox.setActivated(currentStep.isChecklist());
        }else {
            holder.editText.setText(currentStep.getProgressPercentage().toString());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(this.stepList.get(position).isChecklist()){
            return CHECKED;
        }
        return  PROGRESS;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ProgressBarDetailsViewHolder extends RecyclerView.ViewHolder {

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

    enum LayoutType{
        CHECKED,  PROGRESS
    }
}
