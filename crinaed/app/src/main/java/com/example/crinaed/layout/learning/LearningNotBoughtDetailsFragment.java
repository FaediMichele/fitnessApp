package com.example.crinaed.layout.learning;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LearningNotBoughtDetailsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_learning_not_bought_details, container, false);
        final TextView title = view.findViewById(R.id.title_course);
        final TextView reviewTopCard = view.findViewById(R.id.review_top_card);
        final TextView priceTopCard = view.findViewById(R.id.price_top_card);
        final TextView description_course = view.findViewById(R.id.description_course);
        final Button buttonBuy = view.findViewById(R.id.text_congratulation);
        final Bundle dataLearning = getArguments();
        final Activity activity = getActivity();
        if(activity == null || dataLearning == null){
            Snackbar.make(view, R.string.unknown_error, BaseTransientBottomBar.LENGTH_LONG).show();
            return view;
        }
        final long idCourse = dataLearning.getLong(LearningBuySearchFragment.KEY_ID_COURSE);



        final SliderView sliderView = view.findViewById(R.id.slider_course);
        final SliderCourseAdapter adapter = new SliderCourseAdapter(activity);
        final LearningNotBoughtDetailsFragment.AdapterReview adapterReview = new LearningNotBoughtDetailsFragment.AdapterReview();

        DatabaseUtil.getInstance().getRepositoryManager().getSchoolRepository().getCourseById(idCourse).observe(this, new Observer<CourseWithExercise>() {
            @Override
            public void onChanged(CourseWithExercise course) {
                adapter.setCourse(course);
                adapterReview.setData(course);



                title.setText(course.course.name);
                reviewTopCard.setText(activity.getString(R.string.review_val, course.course.review));
                priceTopCard.setText(activity.getString(R.string.price_val, String.valueOf(course.course.price)));
                buttonBuy.setText(activity.getString(R.string.buy_for, String.valueOf(course.course.price)));
                description_course.setText(course.course.desc);
                buttonBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ServerManager.getInstance(getContext()).buyCourse(idCourse, new Lambda() {
                            @Override
                            public Object[] run(Object... paramether) {
                                Snackbar.make(view, R.string.course_bought, BaseTransientBottomBar.LENGTH_LONG).show();
                                Objects.requireNonNull(getActivity()).onBackPressed();
                                return new Object[0];
                            }
                        }, new Lambda() {
                            @Override
                            public Object[] run(Object... paramether) {
                                Snackbar.make(view, R.string.course_not_bought, BaseTransientBottomBar.LENGTH_LONG).show();
                                return new Object[0];
                            }
                        });
                    }
                });

            }
        });


        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setIndicatorSelectedColor(ContextCompat.getColor(activity,R.color.redPrimary));
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

        return view;
    }

    private class AdapterReview extends RecyclerView.Adapter<LearningNotBoughtDetailsFragment.ReviewVH>{
        private CourseWithExercise course;
        private List<ReviewVH> holders = new ArrayList<>();
        @NonNull
        @Override
        public LearningNotBoughtDetailsFragment.ReviewVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
            holders.add(new ReviewVH(itemView));
            return holders.get(holders.size()-1);
        }

        @Override
        public void onBindViewHolder(@NonNull LearningNotBoughtDetailsFragment.ReviewVH holder, int position) {
            if(course!= null && course.reviews!=null){
                ReviewWithUser review = course.reviews.get(position);
                if(review.user.imageDownloaded){
                    holder.imageProfile.setImageURI(Uri.parse(review.user.image));
                }

                holder.nameLastName.setText(Objects.requireNonNull(getActivity()).getString(R.string.name_surname, review.user.firstname, review.user.surname));
                holder.value.setText(getActivity().getString(R.string.review_val, review.review.val));
                holder.date.setText(Util.timestampToIsoPrintable(review.review.date));
                holder.review.setText(review.review.comment);
            }

        }

        public void setData(CourseWithExercise course){
            this.course=course;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if(course!=null){
                Log.d("naed", "review real count: " + course.reviews.size());
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

