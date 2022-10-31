package com.example.crinaed.layout.learning;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.join.CourseSearchData;
import com.example.crinaed.util.Lambda;

import java.util.ArrayList;
import java.util.List;

public class LearningBuySearchFragment extends Fragment  {
    public static final String TAG_SOCIAL_ARCHIVE = "SOCIAL_FRAGMENT_TO_SOCIAL_ARCHIVE_FRAGMENT";
    public static final String TAG_DETAIL = "LEARNING_BUY_FRAGMENT_TO_LEARNING_BUY_DETAIL_FRAGMENT";
    public static final String TAG_LEARNING_PAGER = "LEARNING_ACTIVITY_TO_LEARNING_PAGER_FRAGMENT";
    public static final String KEY_ID_COURSE = "KEY_ID_COURSE";
    public static final String IS_BOUGHT = "IS_BOUGHT";
    final public static int TYPE_VIEW_ITEM_VIEW_ARCHIVE = 0;
    final public static int TYPE_VIEW_VIEW_NORMAL = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_buy_search, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final LearningBuySearchFragment.LearningAdapter adapter = new LearningBuySearchFragment.LearningAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);

        SearchView search = view.findViewById(R.id.course_name_search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    private class LearningAdapter extends RecyclerView.Adapter<LearningBuySearchFragment.LearningViewHolder>{

        private List<CourseSearchData> search;
        private LiveData<List<CourseSearchData>> liveData;

        LifecycleOwner owner;
        SearchHelper helper;

        Observer<List<CourseSearchData>> observer =new Observer<List<CourseSearchData>>() {
            @Override
            public void onChanged(List<CourseSearchData> courseSearchData) {
                search=courseSearchData;
                Log.d("naed", "search data: " + search.size());
                notifyDataSetChanged();
            }
        };

        public LearningAdapter(Context context, LifecycleOwner owner) {
            helper=new SearchHelper(context);
            this.owner=owner;
        }

        public void search(String text){
            if(liveData!=null){
                liveData.removeObserver(observer);
            }

            helper.search(text, new Lambda() {
                @Override
                public Object[] run(Object... paramether) {

                    liveData = DatabaseUtil.getInstance().getRepositoryManager().getSchoolRepository().getSearchData((long[]) paramether[0]);
                    liveData.observe(owner, observer);
                    return new Object[0];
                }
            });
        }

        @NonNull
        @Override
        public LearningBuySearchFragment.LearningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning, parent, false);
            return new LearningViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final LearningBuySearchFragment.LearningViewHolder holder, final int position) {
            if(this.search!= null){
                final CourseSearchData course = this.search.get(position);
                Log.d("naed", "review: " + course.reviews.size());
                switch (course.course.cat){
                    case SOCIAL:
                        holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.course_social));
                        break;
                    case MENTAL:
                        holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.course_learning));
                        break;
                    case SPORT:
                        holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.course_physical));
                        break;
                }
                if(course.course.imagesDownloaded && course.course.images.length>0){
                    holder.imageView.setImageURI(Uri.parse(course.course.images[0]));
                }
                holder.description.setText(course.course.desc);
                holder.title.setText(course.course.name);
                holder.setBought(course.course.isBought);
                holder.school.setText(course.school.name);
                holder.val.setText(getString(R.string.review_val, course.course.review));
            }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    LearningBuyDetailsFragment learningBuyDetailsFragment = new LearningBuyDetailsFragment();
//                    //learningBuyDetailsFragment.setArguments(dataForChatActivity);
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.container_learning, learningBuyDetailsFragment, TAG_DETAIL);
//                    //transaction.addToBackStack(TAG_BACK_STECK);
//                    transaction.commit();

                    LearningPagerFragment learningPagerFragment = new LearningPagerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(LearningBuySearchFragment.IS_BOUGHT, holder.isBought);
                    bundle.putLong(KEY_ID_COURSE, search.get(position).course.idCourse);
                    learningPagerFragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container_learning, learningPagerFragment, TAG_LEARNING_PAGER);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            if(search!=null){
                return search.size();
            }
            return 0;
        }
    }

    private static class LearningViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        TextView description;
        TextView school;
        TextView val;
        boolean isBought;

        public LearningViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_course);
            this.title = itemView.findViewById(R.id.title_course);
            this.description = itemView.findViewById(R.id.description_course);
            this.school=itemView.findViewById(R.id.school);
            this.val=itemView.findViewById(R.id.value_review);
        }

        public void setBought(boolean isBought) {
            this.isBought=isBought;
        }
    }
}
