package com.um.snownote.model;

import java.util.ArrayList;
import java.util.List;

public class Row {

    private List<String> valuesRow;

    public Row(){
        valuesRow = new ArrayList<>();
    }

    public Row(List<String> valuesRow) {
        this.valuesRow = valuesRow;
    }

    public List<String> getValuesRow() {
        return valuesRow;
    }

    public void setValuesRow(List<String> valuesRow) {
        this.valuesRow = valuesRow;
    }
}
