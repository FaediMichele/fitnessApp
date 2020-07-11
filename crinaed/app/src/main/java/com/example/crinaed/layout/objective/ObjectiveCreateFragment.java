package com.example.crinaed.layout.objective;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.database.entity.MyStep;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveCreateFragment extends Fragment {
    private StepAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_objective_create, container, false);

        RecyclerView recyclerSteps = view.findViewById(R.id.recycler_steps);
        recyclerSteps.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new ObjectiveCreateFragment.StepAdapter(getContext());
        recyclerSteps.setAdapter(adapter);
        return view;
    }


    private class StepAdapter extends RecyclerView.Adapter<ObjectiveCreateFragment.StepVH> {
        static public final String SOCIAL  ="SOCIAL";
        static public final String MENTAL  ="MENTAL";
        static public final String PHYSICAL="PHYSICAL";
        static public final int DAY = 1;
        static public final int WEEK = 7;
        static public final int MONTH = 30;
        static public final int YEAR = 365;

        private final List<MyStep> steps = new ArrayList<>();
        private final Context context;

        public StepAdapter(Context context){
            this.context=context;
        }

        public void addStep(MyStep step){
            steps.add(step);
        }

        @NonNull
        @Override
        public StepVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_objective_step, parent, false);
            return new StepVH(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ObjectiveCreateFragment.StepVH holder, int position) {
            MyStep step = this.steps.get(position);
            switch (step.type){
                case SOCIAL:
                    holder.itemView.findViewById(R.id.step_item).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.course_social));
                    break;
                case MENTAL:
                    holder.itemView.findViewById(R.id.step_item).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.course_learning));
                    break;
                case PHYSICAL:
                    holder.itemView.findViewById(R.id.step_item).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.course_physical));
                    break;
            }

            holder.title.setText(step.name);
            switch (step.repetitionDay){
                case DAY:
                    holder.repetition.setText("day");
                    break;
                case WEEK:
                    holder.repetition.setText("week");
                    break;
                case MONTH:
                    holder.repetition.setText("month");
                    break;
                case YEAR:
                    holder.repetition.setText("year");
                    break;
            }
            holder.description.setText("Progressive : "+ step.max + step.unitMeasure);//---------------------------------------------------------------------------------------------------------------------------------------------da cambiare
        }

        @Override
        public int getItemCount() {
            return steps.size();
        }
    }

     private class StepVH extends RecyclerView.ViewHolder{

        TextView title;
        TextView repetition;
        TextView description;

         public StepVH(@NonNull View itemView) {
             super(itemView);
             this.title = itemView.findViewById(R.id.title_step);
             this.repetition = itemView.findViewById(R.id.repetition_step);
             this.description = itemView.findViewById(R.id.description_objective);
         }
     }

}
