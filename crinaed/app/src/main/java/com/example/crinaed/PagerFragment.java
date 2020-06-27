package com.example.crinaed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class PagerFragment extends Fragment {

    private static final int NUM_PAGES = 3;
    private static final int OBJECTIVE_FRAGMENT = 0;
    private static final int SOCIAL_FRAGMENT = 1;
    private static final int LEARNING_FRAGMENT = 2;

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pager, container, false);

        //configure viewPagger2
        viewPager = view.findViewById(R.id.container_page);
        pagerAdapter = new HomePagerAdapter(getChildFragmentManager(),getLifecycle());
        viewPager.setAdapter(pagerAdapter);
        viewPager.registerOnPageChangeCallback(new PageChangeListener());

        //configure tab layout
        TabLayout tabs = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabs, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)   {
                     case OBJECTIVE_FRAGMENT:
                         tab.setText(R.string.tab_objective);
                         break;
                     case SOCIAL_FRAGMENT:
                         tab.setText(R.string.tab_social);
                         break;
                     case LEARNING_FRAGMENT:
                         tab.setText(R.string.tab_learning);
                         break;
                     default:
                         tab.setText("undefine");
                         break;
                 }
            }
        }).attach();



        return view;
    }

    private class PageChangeListener extends ViewPager2.OnPageChangeCallback{

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            FloatingActionButton fab = getActivity().findViewById(R.id.floating_action_button);
            switch (position) {
                case OBJECTIVE_FRAGMENT:
                    fabAnimation(R.drawable.setting,R.color.bluPrimary);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent objectiveIntent = new Intent(getContext(),ObjectiveActivity.class);
                            startActivity(objectiveIntent);
                        }
                    });
                    break;
                case SOCIAL_FRAGMENT:
                    fabAnimation(R.drawable.ic_baseline_search_24,R.color.greenPrimary);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent socialIntent = new Intent(getContext(),SocialActivity.class);
                            startActivity(socialIntent);
                        }
                    });
                    break;
                case LEARNING_FRAGMENT:
                    fabAnimation(R.drawable.ic_baseline_search_24,R.color.redPrimary);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent learningIntent = new Intent(getContext(),LearningActivity.class);
                            startActivity(learningIntent);
                        }
                    });
                    break;
            }
        }

        private void fabAnimation(final int drawable, final int color){
            final FloatingActionButton fab = getActivity().findViewById(R.id.floating_action_button);
            fab.clearAnimation();
            // Scale down animation
            ScaleAnimation shrink =  new ScaleAnimation(1f, 0.0f, 1f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            shrink.setDuration(150);     // animation duration in milliseconds
            shrink.setInterpolator(new DecelerateInterpolator());
            shrink.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Change FAB color and icon
                    fab.setBackgroundTintList(getResources().getColorStateList(color));
                    fab.setImageDrawable(getResources().getDrawable(drawable, null));

                    // Scale up animation
                    ScaleAnimation expand =  new ScaleAnimation(0.0f, 1f, 0.0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    expand.setDuration(100);     // animation duration in milliseconds
                    expand.setInterpolator(new AccelerateInterpolator());
                    fab.startAnimation(expand);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            fab.startAnimation(shrink);
        }

    }

    private class HomePagerAdapter extends FragmentStateAdapter{

        public HomePagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;
            switch (position) {
                case OBJECTIVE_FRAGMENT:
                    fragment = new ObjectiveFragment();
                    break;
                case SOCIAL_FRAGMENT:
                    fragment = new SocialFragment();
                    break;
                case LEARNING_FRAGMENT:
                    fragment = new LearningFragment();
                    break;
                default:
                    fragment = new ObjectiveFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}
