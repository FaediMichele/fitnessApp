package com.example.crinaed.layout.learning;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.database.entity.Review;
import com.example.crinaed.database.entity.join.CourseWithExercise;
import com.example.crinaed.database.entity.join.ReviewWithUser;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.Objects;

public class LearningBoughtDetailsFragment extends Fragment {

    private CourseWithExercise course;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_learning_bought_details, container, false);
        Bundle dataLearning = getArguments();
        if(dataLearning == null){
            Snackbar.make(view, R.string.unknown_error, BaseTransientBottomBar.LENGTH_LONG).show();
            return view;
        }
        long idCourse = dataLearning.getLong(LearningBuySearchFragment.KEY_ID_COURSE);


        final SliderCourseAdapter adapter = new SliderCourseAdapter(getActivity());
        final AdapterReview adapterReview = new AdapterReview();
        final TextView title = view.findViewById(R.id.title_course);
        final TextView reviewTopCard = view.findViewById(R.id.review_top_card);
        final TextView description_course = view.findViewById(R.id.description_course);
        final Button archive= view.findViewById(R.id.button_archive);


        DatabaseUtil.getInstance().getRepositoryManager().getSchoolRepository().getCourseById(idCourse).observe(this, new Observer<CourseWithExercise>() {
            @Override
            public void onChanged(CourseWithExercise courseWithExercise) {
                title.setText(courseWithExercise.course.name);
                reviewTopCard.setText(getString(R.string.review_val, courseWithExercise.course.review));
                description_course.setText(courseWithExercise.course.desc);
                adapter.setCourse(courseWithExercise);
                adapterReview.setData(courseWithExercise);
                course=courseWithExercise;
                archive.setText(course.course.isArchived?R.string.remove_archive:R.string.add_archive);
            }
        });

        final SliderView sliderView = view.findViewById(R.id.slider_course);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setIndicatorSelectedColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()),R.color.redPrimary));
        //sliderView.setScrollTimeInSec(3);
        //sliderView.setAutoCycle(false);
        //sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });

        //manager RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterReview);

        final EditText review_text = view.findViewById(R.id.text_send);
        final EditText review_val = view.findViewById(R.id.value_review);
        review_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()<=0 || Integer.parseInt(review_val.getText().toString())>10 ||Integer.parseInt(review_val.getText().toString())<=0){
                    review_val.setError(getString(R.string.review_val_error));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        final View send = view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(review_val.getText().length()<=0 || Integer.parseInt(review_val.getText().toString())>10 ||Integer.parseInt(review_val.getText().toString())<0){
                    Snackbar.make(view, R.string.review_val_error, BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }
                ServerManager.getInstance(getContext()).addReview(course.course.idCourse, review_text.getText().toString(), Integer.parseInt(review_val.getText().toString()), new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        Snackbar.make(view, R.string.review_added, BaseTransientBottomBar.LENGTH_SHORT).show();
                        return new Object[0];
                    }
                }, new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        try {
                            VolleyError error = (VolleyError) paramether[0];
                            if (error.networkResponse.statusCode == 401) {
                                Snackbar.make(view, R.string.review_exist, BaseTransientBottomBar.LENGTH_LONG).show();
                            }
                        }catch (Exception ignore) {
                            Snackbar.make(view, R.string.unknown_error, BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                        return new Object[0];
                    }
                });
            }
        });
        archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerManager.getInstance(getContext()).archiveCourse(course.course, new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        if((Boolean) paramether[0]){
                            Snackbar.make(view, R.string.archived, BaseTransientBottomBar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(view, R.string.not_archived, BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                        return new Object[0];
                    }
                }, new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        Snackbar.make(view, R.string.unknown_error, BaseTransientBottomBar.LENGTH_LONG).show();
                        return new Object[0];
                    }
                });
            }
        });
        return view;
    }

    private class AdapterReview extends RecyclerView.Adapter<ReviewVH>{

        private CourseWithExercise course;
        public AdapterReview(){
        }

        public void setData(CourseWithExercise course){
            this.course=course;
            notifyDataSetChanged();
        }
        @NonNull
        @Override
        public ReviewVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review,parent,false);
            return new ReviewVH(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewVH holder, int position) {
            if(course!=null){
                ReviewWithUser review = course.reviews.get(position);
                if(review.user.imageDownloaded){
                    holder.imageProfile.setImageURI(Uri.parse(review.user.image));
                }

                holder.nameLastName.setText(getString(R.string.name_surname, review.user.firstname, review.user.surname));
                holder.value.setText(getString(R.string.review_val, review.review.val));
                holder.date.setText(Util.timestampToIsoPrintable(review.review.date));
                holder.review.setText(review.review.comment);
            }

        }

        @Override
        public int getItemCount() {
            if(course!=null){
                return course.reviews.size();
            }
            return 0;
        }
    }

    private static class ReviewVH extends RecyclerView.ViewHolder {

        ImageView imageProfile;
        TextView nameLastName;
        TextView value;
        TextView date;
        TextView review;

        public ReviewVH(@NonNull View itemView) {
            super(itemView);
            this.imageProfile = itemView.findViewById(R.id.image_profile);
            this.nameLastName = itemView.findViewById(R.id.name_and_last_name);
            this.value = itemView.findViewById(R.id.stars_review);
            this.date = itemView.findViewById(R.id.date_review);
            this.review = itemView.findViewById(R.id.review);
        }
    }
}
