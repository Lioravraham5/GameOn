package com.example.gameon.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Field implements Serializable {
    public enum FieldType{soccer, basketball, tennis, general};
    private String fieldId;
    private String fieldName;
    private Address address;
    private FieldType fieldType;
    private CompareOrdersByDateTimeFromOldestToNewest compareOrdersByDateTime = new CompareOrdersByDateTimeFromOldestToNewest();

    public Field(){

    }

    public String getFieldId() {
        return fieldId;
    }

    public Field setFieldId(String fieldId) {
        this.fieldId = fieldId;
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Field setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Field setAddress(Address address) {
        this.address = address;
        return this;
    }


    public FieldType getFieldType() {
        return fieldType;
    }

    public Field setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    @Override
    public String toString() {
        return "Field Details:\n" +
                "Name: " + fieldName + "\n" +
                "Type: " + fieldType.toString() + "\n" +
                "Address: " + address.toString();
    }
}
