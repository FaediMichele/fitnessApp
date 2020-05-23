package com.example.crinaed.ProgressBar;

import com.example.crinaed.util.Pair;
import com.example.crinaed.ProgressBar.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SliderProgressBarModel {

    /**
     * Categorie
     */
    final public static String PHYSICAL = "PHYSICAL";
    final public static String SOCIAL =  "SOCIAL";
    final public static String MENTAL = "MENTAL";

    /**
     * tipo = fisico, mentale, sociale
     * titolo
     * descrizione
     * lista sotto obbiettivi(step) da cui si puo dedurre la percentuale progresso
     * mappa
     *      value : precentale minima a cui si deve visualizzare
     *      key   : un oggetto pair che contiene un immagine e un testo, possono essere null tutte e due
     */

    private String category;
    private String title;
    private String Description;
    private List<Step> stepList = new ArrayList<>();
    private Map<Double, Pair<String,String>> motivationalMap = new HashMap<>();

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Map<Double, Pair<String, String>> getMotivationalMap() {
        return motivationalMap;
    }

    public void setMotivationalMap(Map<Double, Pair<String, String>> motivationalMap) {
        this.motivationalMap = motivationalMap;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }
}