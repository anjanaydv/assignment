package com.hilton.assignment.model;

/**
 * @Copyright Any portion of this assignment's code are not allowed to use in business or production.
 *
 * This class is used to generate custom error response message in controller advice.
 *
 * @author Anjana Yadav
 */
public class FieldValidationError {
    String field;
    String errorMsg;
    public FieldValidationError(String field, String errorMsg){
        this.field = field;
        this.errorMsg = errorMsg;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
