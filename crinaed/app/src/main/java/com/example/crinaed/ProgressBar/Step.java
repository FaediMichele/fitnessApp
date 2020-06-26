package com.example.crinaed.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;

public class Step {

    /**
     * descrizione
     * tipo (checklist, incrementale)
     * stato progresso (che è uguale a 100 se lo step e di tipo checklist e è stato completato)
     * (data inizio) ?? non so se ci va
     *
     */

    private String description;
    private Boolean isChecklist;
    private Double progressPercentage;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isChecklist() {
        return isChecklist;
    }

    public void setIsChecklist(Boolean checklist) {
        isChecklist = checklist;
    }

    public Double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    @Override
    public String toString() {
        return "Step{" +
                "description='" + description + '\'' +
                ", isChecklist=" + isChecklist +
                ", progressPercentage=" + progressPercentage +
                '}';
    }
}
