package com.um.snownote.model;

import java.util.ArrayList;
import java.util.List;

public class LabelSummary {

    private List<String> labels;
    private int totalLabels;


    public LabelSummary() {
        this.labels = new ArrayList<>();
        this.totalLabels = 0;
    }

    public LabelSummary(List<String> labels, int totalLabels) {
        this.labels = labels;
        this.totalLabels = totalLabels;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public int getTotalLabels() {
        return totalLabels;
    }

    public void setTotalLabels(int totalLabels) {
        this.totalLabels = totalLabels;
    }

    @Override
    public String toString() {
        return "LabelSummary{" +
                "labels=" + labels +
                ", totalLabels=" + totalLabels +
                '}';
    }



}
