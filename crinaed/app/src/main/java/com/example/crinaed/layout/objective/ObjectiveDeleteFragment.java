package com.example.crinaed.layout.objective;

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
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.layout.learning.LearningBoughtFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveDeleteFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_objective_deletete, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ObjectiveAdapter adapter = new ObjectiveAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }

    private class ObjectiveAdapter extends RecyclerView.Adapter<ObjectiveVH>{

        static final int SOCIAL = 0;
        static final int MENTAL = 1;
        static final int PHYSICAL = 2;

        private final List<MyCommitment> modelList;

        public ObjectiveAdapter() {
            this.modelList = ObjectiveDeleteFragment.getModel();
        }

        @NonNull
        @Override
        public ObjectiveVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_objective, parent, false);
            return new ObjectiveVH(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ObjectiveVH holder, int position) {
            holder.description.setText(modelList.get(position).desc);
            holder.title.setText(modelList.get(position).name);

            switch (position % 3){//---------------------------------------------------------------------------------------------------------------------da sisteamre non so come ricavare il tipo
                case SOCIAL:
                    holder.itemView.findViewById(R.id.objective_item).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_social));
                    break;
                case MENTAL:
                    holder.itemView.findViewById(R.id.objective_item).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_learning));
                    break;
                case PHYSICAL:
                    holder.itemView.findViewById(R.id.objective_item).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_physical));
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return modelList.size();
        }
    }

    private class ObjectiveVH extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;

        public ObjectiveVH(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title_objective);
            this.description = itemView.findViewById(R.id.description_objective);
        }
    }


    static public List<MyCommitment> getModel(){
        List<MyCommitment> myCommitments = new ArrayList<>();
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        myCommitments.add(new MyCommitment(-1, "Titolo obbiettivo", "voglio andare a letto sono stanchissimo e mi sta ritornando il malditesta nonostante ho gian preso il moment", 1, -1));
        return myCommitments;
    }
}
