package com.example.crinaed.ProgressBar;

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

    public Boolean getChecklist() {
        return isChecklist;
    }

    public void setChecklist(Boolean checklist) {
        isChecklist = checklist;
    }

    public Double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}
