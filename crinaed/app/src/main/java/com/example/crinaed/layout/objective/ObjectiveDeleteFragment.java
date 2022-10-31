package com.example.crinaed.layout.objective;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.layout.learning.LearningBoughtFragment;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjectiveDeleteFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_objective_deletete, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final ObjectiveAdapter adapter = new ObjectiveAdapter(this);
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MyCommitment> toDelete = adapter.getSelected();
                long now =new Date().getTime();
                for(MyCommitment mc : toDelete){
                    mc.endDate=now;
                    mc.ended=true;
                }
                DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().updateCommitment(toDelete.toArray(new MyCommitment[0]));
                Snackbar.make(view, getResources().getQuantityString(R.plurals.commitment_ended, toDelete.size(), toDelete.size()), BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private class ObjectiveAdapter extends RecyclerView.Adapter<ObjectiveVH>{

        private List<CommitmentWithMyStep> newest;
        private List<ObjectiveVH> holders= new ArrayList<>();

        public ObjectiveAdapter(LifecycleOwner owner) {
            DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().getCommitmentNotArchived().observe(owner, new Observer<List<CommitmentWithMyStep>>() {
                @Override
                public void onChanged(List<CommitmentWithMyStep> myCommitments) {
                    newest=myCommitments;
                    notifyDataSetChanged();
                }
            });
        }

        public List<MyCommitment> getSelected(){
            List<MyCommitment> ret = new ArrayList<>();
            for(ObjectiveVH h : holders){
                if(h.selected.isChecked()){
                    ret.add(h.commitment);
                }
            }
            return ret;
        }

        @NonNull
        @Override
        public ObjectiveVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_objective, parent, false);
            holders.add(new ObjectiveVH(itemView));
            return holders.get(holders.size()-1);
        }

        @Override
        public void onBindViewHolder(@NonNull ObjectiveVH holder, int position) {
            holder.setCommitment(newest.get(position).commitment);
            switch (newest.get(position).steps.get(0).myStep.category){//---------------------------------------------------------------------------------------------------------------------da sisteamre non so come ricavare il tipo
                case SOCIAL:
                    holder.itemView.findViewById(R.id.objective_item).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_social));
                    break;
                case MENTAL:
                    holder.itemView.findViewById(R.id.objective_item).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_learning));
                    break;
                case SPORT:
                    holder.itemView.findViewById(R.id.objective_item).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_physical));
                    break;
            }
        }

        @Override
        public int getItemCount() {
            if(newest!=null){
                return newest.size();
            }
            return 0;
        }
    }

    private static class ObjectiveVH extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        CheckBox selected;
        MyCommitment commitment;

        public ObjectiveVH(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title_objective);
            this.description = itemView.findViewById(R.id.description_objective);
            this.selected = itemView.findViewById(R.id.check_selected);
        }
        private void setCommitment(MyCommitment commitment){
            description.setText(commitment.desc);
            title.setText(commitment.name);
            selected.setChecked(false);
            this.commitment=commitment;
        }
    }
}
