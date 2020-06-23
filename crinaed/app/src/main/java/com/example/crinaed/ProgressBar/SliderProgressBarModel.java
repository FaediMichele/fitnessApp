package com.example.crinaed.ProgressBar;

import android.view.View;

import com.example.crinaed.util.Pair;
import com.example.crinaed.ProgressBar.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SliderProgressBarModel {

    public static List<SliderProgressBarModel> EXAMPLE_MODEL= SliderProgressBarModel.createSimpleModel();

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
     *      value : percentuale minima a cui si deve visualizzare
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

    static private List<SliderProgressBarModel> createSimpleModel(){
            List<SliderProgressBarModel> sliderItemList = new ArrayList<>();

            //MENTAL
            SliderProgressBarModel mental = new SliderProgressBarModel();
            mental.setCategory(SliderProgressBarModel.MENTAL);
            mental.setDescription("Test desccrizione mentale");
            mental.setTitle("Test title");
            Step stepMental1 = new Step();
            stepMental1.setIsChecklist(false);
            stepMental1.setProgressPercentage(30.0);
            stepMental1.setDescription("Test stepMental1");
            Step stepMental2 = new Step();
            stepMental2.setIsChecklist(false);
            stepMental2.setProgressPercentage(50.0);
            stepMental2.setDescription("Test stepMental2");
            mental.getStepList().add(stepMental1);
            mental.getStepList().add(stepMental2);

            //PHYSICAL
            SliderProgressBarModel physical = new SliderProgressBarModel();
            physical.setCategory(SliderProgressBarModel.PHYSICAL);
            physical.setDescription("Test desccrizione physical");
            physical.setTitle("Test title");
            Step stepPhysical1 = new Step();
            stepPhysical1.setIsChecklist(false);
            stepPhysical1.setProgressPercentage(30.0);
            stepPhysical1.setDescription("Test stepPhysical1");
            Step stepPhysical2 = new Step();
            stepPhysical2.setIsChecklist(false);
            stepPhysical2.setProgressPercentage(50.0);
            stepPhysical2.setDescription("Test stepPhysical2");
            physical.getStepList().add(stepPhysical1);
            physical.getStepList().add(stepPhysical2);

            //SOCIAL
            SliderProgressBarModel social = new SliderProgressBarModel();
            social.setCategory(SliderProgressBarModel.SOCIAL);
            social.setDescription("Test desccrizione social");
            social.setTitle("Test title");
            Step stepSocial1 = new Step();
            stepSocial1.setIsChecklist(false);
            stepSocial1.setProgressPercentage(30.0);
            stepSocial1.setDescription("Test stepSocial1");
            Step stepSocial2 = new Step();
            stepSocial2.setIsChecklist(false);
            stepSocial2.setProgressPercentage(80.0);
            stepSocial2.setDescription("Test stepSocial2");
            social.getStepList().add(stepSocial1);
            social.getStepList().add(stepSocial2);

            //add
            sliderItemList.add(mental);
            sliderItemList.add(social);
            sliderItemList.add(physical);
            return  sliderItemList;
        }

    @Override
    public String toString() {
        return "SliderProgressBarModel{" +
                "category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", Description='" + Description + '\'' +
                ", stepList=" + stepList +
                ", motivationalMap=" + motivationalMap +
                '}';
    }
}