package com.example.crinaed.ProgressBar.TestModel;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ListProgressBar {

    static private List<ProgressBarModel> progressBarModelList;

    private ListProgressBar(){
    }

    static public List<ProgressBarModel> getList(){
        if(progressBarModelList == null){
            progressBarModelList = createList();
        }
        return progressBarModelList;
    }

    static private List<ProgressBarModel> createList(){
        List<ProgressBarModel> progressBarModels = new ArrayList<>();
        ProgressBarModel progressBarModel0 = new ProgressBarModel(CategoryProgressBar.MENTAL,TypeProgressBar.PROGRESSIV,"Titolo0",null,null,2.0,0.2);
        ProgressBarModel progressBarModel1 = new ProgressBarModel(CategoryProgressBar.PHYSICAL,TypeProgressBar.PROGRESSIV,"Titolo1",null,null,2.0,0.2);
        ProgressBarModel progressBarModel2 = new ProgressBarModel(CategoryProgressBar.SOCIAL,TypeProgressBar.PROGRESSIV,"Titolo12",null,null,2.0,0.2);
        progressBarModels.add(progressBarModel0);
        progressBarModels.add(progressBarModel1);
        progressBarModels.add(progressBarModel2);
        return progressBarModels;
    }
}
