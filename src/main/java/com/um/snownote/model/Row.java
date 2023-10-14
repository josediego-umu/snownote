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

    public void addValue(String value){
        valuesRow.add(value);
    }

    public void removeValue(String value){
        valuesRow.remove(value);
    }

    public void removeValue(int index){
        valuesRow.remove(index);
    }

    public String getValue(int index){
        return valuesRow.get(index);
    }

    public void setValue(int index, String value){
        valuesRow.set(index, value);
    }

}
