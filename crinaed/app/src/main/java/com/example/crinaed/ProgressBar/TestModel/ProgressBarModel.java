package com.example.crinaed.ProgressBar.TestModel;

import android.graphics.Color;

public class ProgressBarModel {

    public CategoryProgressBar category;
    public TypeProgressBar type;
    public Integer sizeCheckList;
    public Integer checkDid;
    public Double sizeProgressiv;
    public Double progressivDid;
    public String titolo;


    public ProgressBarModel(CategoryProgressBar category, TypeProgressBar type,String titolo, Integer sizeCheckList, Integer checkDid, Double sizeProgressiv, Double progressivDid) {
        this.category = category;
        this.type = type;
        this.titolo = titolo;
        this.sizeCheckList = sizeCheckList;
        this.checkDid = checkDid;
        this.sizeProgressiv = sizeProgressiv;
        this.progressivDid = progressivDid;
    }
}
