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
            mental.setTitle("Test title mental");
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
            physical.setTitle("Test title physical");
            Step stepPhysical1 = new Step();
            stepPhysical1.setIsChecklist(false);
            stepPhysical1.setProgressPercentage(30.0);
            stepPhysical1.setDescription("Test stepPhysical1");
            Step stepPhysical2 = new Step();
            stepPhysical2.setIsChecklist(false);
            stepPhysical2.setProgressPercentage(50.0);
            stepPhysical2.setDescription("Test stepPhysical2");
            Step stepPhysical3 = new Step();
            stepPhysical3.setIsChecklist(false);
            stepPhysical3.setProgressPercentage(50.0);
            stepPhysical3.setDescription("Test stepPhysical2");
            Step stepPhysical4 = new Step();
            stepPhysical4.setIsChecklist(false);
            stepPhysical4.setProgressPercentage(50.0);
            stepPhysical4.setDescription("Test stepPhysical2");
            Step stepPhysical5 = new Step();
            stepPhysical5.setIsChecklist(false);
            stepPhysical5.setProgressPercentage(50.0);
            stepPhysical5.setDescription("Test stepPhysical2");
            Step stepPhysical6 = new Step();
            stepPhysical6.setIsChecklist(false);
            stepPhysical6.setProgressPercentage(50.0);
            stepPhysical6.setDescription("Test stepPhysical2");
            Step stepPhysical7 = new Step();
            stepPhysical7.setIsChecklist(false);
            stepPhysical7.setProgressPercentage(50.0);
            stepPhysical7.setDescription("Test stepPhysical2");
            Step stepPhysical8 = new Step();
            stepPhysical8.setIsChecklist(false);
            stepPhysical8.setProgressPercentage(50.0);
            stepPhysical8.setDescription("Test stepPhysical2");
            Step stepPhysical9 = new Step();
            stepPhysical9.setIsChecklist(false);
            stepPhysical9.setProgressPercentage(50.0);
            stepPhysical9.setDescription("Test stepPhysical2");
            Step stepPhysical10 = new Step();
            stepPhysical10.setIsChecklist(false);
            stepPhysical10.setProgressPercentage(30.0);
            stepPhysical10.setDescription("Test stepPhysical1");
            Step stepPhysical11 = new Step();
            stepPhysical11.setIsChecklist(false);
            stepPhysical11.setProgressPercentage(50.0);
            stepPhysical11.setDescription("Test stepPhysical2");
            Step stepPhysical12 = new Step();
            stepPhysical12.setIsChecklist(false);
            stepPhysical12.setProgressPercentage(50.0);
            stepPhysical12.setDescription("Test stepPhysical2");
            Step stepPhysical13 = new Step();
            stepPhysical13.setIsChecklist(false);
            stepPhysical13.setProgressPercentage(50.0);
            stepPhysical13.setDescription("Test stepPhysical2");
            Step stepPhysical14 = new Step();
            stepPhysical14.setIsChecklist(false);
            stepPhysical14.setProgressPercentage(50.0);
            stepPhysical14.setDescription("Test stepPhysical2");
            Step stepPhysical15 = new Step();
            stepPhysical15.setIsChecklist(false);
            stepPhysical15.setProgressPercentage(50.0);
            stepPhysical15.setDescription("Test stepPhysical2");
            Step stepPhysical16 = new Step();
            stepPhysical16.setIsChecklist(false);
            stepPhysical16.setProgressPercentage(50.0);
            stepPhysical16.setDescription("Test stepPhysical2");
            Step stepPhysical17 = new Step();
            stepPhysical17.setIsChecklist(false);
            stepPhysical17.setProgressPercentage(50.0);
            stepPhysical17.setDescription("Test stepPhysical2");
            Step stepPhysical18 = new Step();
            stepPhysical18.setIsChecklist(false);
            stepPhysical18.setProgressPercentage(50.0);
            stepPhysical18.setDescription("Test stepPhysical2");
            Step stepPhysical19 = new Step();
            stepPhysical19.setIsChecklist(false);
            stepPhysical19.setProgressPercentage(50.0);
            stepPhysical19.setDescription("Test stepPhysical2");
            Step stepPhysical20 = new Step();
            stepPhysical20.setIsChecklist(false);
            stepPhysical20.setProgressPercentage(50.0);
            stepPhysical20.setDescription("Test stepPhysical2");
            Step stepPhysical21 = new Step();
            stepPhysical21.setIsChecklist(false);
            stepPhysical21.setProgressPercentage(50.0);
            stepPhysical21.setDescription("Test stepPhysical2");
            Step stepPhysical22 = new Step();
            stepPhysical22.setIsChecklist(false);
            stepPhysical22.setProgressPercentage(50.0);
            stepPhysical22.setDescription("Test stepPhysical2");
            Step stepPhysical23 = new Step();
            stepPhysical23.setIsChecklist(false);
            stepPhysical23.setProgressPercentage(50.0);
            stepPhysical23.setDescription("Test stepPhysical2");
            physical.getStepList().add(stepPhysical1);
            physical.getStepList().add(stepPhysical2);
            physical.getStepList().add(stepPhysical3);
            physical.getStepList().add(stepPhysical4);
            physical.getStepList().add(stepPhysical5);
            physical.getStepList().add(stepPhysical6);
            physical.getStepList().add(stepPhysical7);
            physical.getStepList().add(stepPhysical8);
            physical.getStepList().add(stepPhysical9);
            physical.getStepList().add(stepPhysical10);
            physical.getStepList().add(stepPhysical11);
            physical.getStepList().add(stepPhysical12);
            physical.getStepList().add(stepPhysical13);
            physical.getStepList().add(stepPhysical14);
            physical.getStepList().add(stepPhysical15);
            physical.getStepList().add(stepPhysical16);
            physical.getStepList().add(stepPhysical17);
            physical.getStepList().add(stepPhysical18);
            physical.getStepList().add(stepPhysical19);
            physical.getStepList().add(stepPhysical20);
            physical.getStepList().add(stepPhysical21);
            physical.getStepList().add(stepPhysical22);
            physical.getStepList().add(stepPhysical23);

            //SOCIAL
            SliderProgressBarModel social = new SliderProgressBarModel();
            social.setCategory(SliderProgressBarModel.SOCIAL);
            social.setDescription("Test desccrizione social");
            social.setTitle("Test title social");
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