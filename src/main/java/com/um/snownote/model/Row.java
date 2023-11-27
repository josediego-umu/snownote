package com.um.snownote.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Row {

    private List<String> valuesRow;
    private List<String> labeledRow;

    public Row() {
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

    public void addValue(String value) {
        valuesRow.add(value);
    }

    public void removeValue(String value) {
        valuesRow.remove(value);
    }

    public void removeValue(int index) {
        valuesRow.remove(index);
    }

    public String getValue(int index) {
        return valuesRow.get(index);
    }

    public void setValue(int index, String value) {
        valuesRow.set(index, value);
    }

    public List<String> getLabeledRow() {
        return labeledRow;
    }

    public void setLabeledRow(List<String> labeledRow) {
        this.labeledRow = labeledRow;
    }

    public List<String> getValueLabel() {

        List<String> valueLabeled = new ArrayList<>();

        for (int i = 0; i < valuesRow.size(); i++) {


            if (labeledRow != null && !labeledRow.get(i).isEmpty())
                valueLabeled.add(valuesRow.get(i) + " | " + labeledRow.get(i));
            else
                valueLabeled.add(valuesRow.get(i));

        }

        return valueLabeled;
    }

    @Override
    public String toString() {
        return "Row{" +
                "valuesRow=" + valuesRow +
                ", LabeledRow=" + labeledRow +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Row row)) return false;
        return Objects.equals(getValuesRow(), row.getValuesRow()) && Objects.equals(getLabeledRow(), row.getLabeledRow());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValuesRow(), getLabeledRow());
    }
}
