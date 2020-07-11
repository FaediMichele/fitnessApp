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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.util.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObjectiveCreateFragment extends Fragment {

    public static final String TAG_STEP = "OBJECTIVE_CREATE_FRAGMENT_TO_OBJECTIVE_STEP_CREATE_FRAGMENT";

    private StepAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_objective_create, container, false);

        view.findViewById(R.id.add_step).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectiveStepCreateFragment objectiveStepCreateFragment = new ObjectiveStepCreateFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, objectiveStepCreateFragment, TAG_STEP);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

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

        private final List<MyStep> steps;
        private final Context context;

        public StepAdapter(Context context){
            this.context=context;
            this.steps = ObjectiveCreateFragment.getModel();//----------------------------------------------------------------------------------------da modificare
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
            switch (step.type) {
                case SOCIAL:
                    holder.itemView.findViewById(R.id.step_item).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_social));
                    break;
                case MENTAL:
                    holder.itemView.findViewById(R.id.step_item).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_learning));
                    break;
                case PHYSICAL:
                    holder.itemView.findViewById(R.id.step_item).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_physical));
                    break;
            }

            holder.title.setText(step.name);
            switch (step.repetitionDay) {
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

            boolean isProgressive = (position % 2) == 0;//---------------------------------------------------------------------------------------------------------------------------------------------da cambiare
            if (isProgressive) {
                holder.description.setText("Progressive : " + step.max + step.unitMeasure);
            }else {
                holder.description.setText("Single");
            }//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------fine pezzo da cambiare
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
             this.description = itemView.findViewById(R.id.description_step);
         }
     }

    static public List<MyStep> getModel(){
        List<MyStep> modelList = new ArrayList<>();
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.MONTH, StepAdapter.MENTAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.WEEK, StepAdapter.MENTAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.MONTH, StepAdapter.PHYSICAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.DAY, StepAdapter.MENTAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.DAY, StepAdapter.SOCIAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.MONTH, StepAdapter.PHYSICAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.WEEK, StepAdapter.SOCIAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.DAY, StepAdapter.MENTAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.MONTH, StepAdapter.SOCIAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.DAY, StepAdapter.PHYSICAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.YEAR, StepAdapter.MENTAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.DAY, StepAdapter.SOCIAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.WEEK, StepAdapter.MENTAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.DAY, StepAdapter.PHYSICAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.MONTH, StepAdapter.SOCIAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.WEEK, StepAdapter.PHYSICAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.YEAR, StepAdapter.PHYSICAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.DAY, StepAdapter.SOCIAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.MONTH, StepAdapter.MENTAL, null));
        modelList.add(new MyStep(-1, -1, "title step", "m", 5000, StepAdapter.DAY, StepAdapter.SOCIAL, null));
        return modelList;
    }


}
