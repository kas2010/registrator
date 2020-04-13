package com.alikhansfamily.registrator.to;

public enum UserRequestOrder {
    DBTYPE("dbType"),
    IIN("iin"),
    FIRSTNAME("firstName"),
    MIDDLENAME("middleName"),
    LASTNAME("lastName"),
    DEPARTMENT("department"),
    MANAGEMENT("management"),
    DEGREE("degree"),
    JOB("job"),
    PHONE("phone"),
    STATE("state");

    private String fieldName;

    UserRequestOrder(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
