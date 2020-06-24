package com.example.crinaed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class PagerFragment extends Fragment {

    private static final int NUM_PAGES = 3;
    private static final int OBJECTIVE_FRAGMENT = 0;
    private static final int SOCIAL_FRAGMENT = 1;
    private static final int LEARNING_FRAGMENT = 2;



    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        viewPager = view.findViewById(R.id.container_page);
        pagerAdapter = new HomePagerAdapter(getChildFragmentManager(),getLifecycle());
        viewPager.setAdapter(pagerAdapter);
        return view;
    }




    private class HomePagerAdapter extends FragmentStateAdapter{


        public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public HomePagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

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
