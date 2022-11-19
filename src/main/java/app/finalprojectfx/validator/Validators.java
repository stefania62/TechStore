package app.finalprojectfx.validator;

import app.finalprojectfx.exception.FormValidationException;

public class Validators {
    public static String validateRequiredField(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new FormValidationException(fieldName + " is required");
        }
        return value;
    }

    public static String validateFieldLength(String value, int minLength, int maxLength, String fieldName) {
        value = value.trim(); //remove trailing spaces
        if (value.length() < minLength || value.length() > maxLength) {
            throw new FormValidationException(fieldName + " length is not in the range of [" + minLength + ", " + maxLength + "]");
        }
        return value;
    }

    public static int validatePositiveInteger(String value, String fieldName) {
        try {
            int num = Integer.parseInt(value); //this might throw NumberFormatException, so we add this inside a try catch
            if (num <= 0) {
                throw new FormValidationException(fieldName + " should be positive");
            }
            return num;
        } catch (NumberFormatException ex) {

            //it is easier to catch only this type of exception on controllers
            throw new FormValidationException(fieldName + " should be a integer");
        }
    }

    public static double validatePositiveDouble(String value, String fieldName) {
        try {
            double num = Double.parseDouble(value); //this might throw NumberFormatException, so we add this inside a try catch
            if (num <= 0) {
                throw new FormValidationException(fieldName + " should be positive");
            }
            return num;
        } catch (NumberFormatException ex) {
            //it is easier to catch only this type of exception on controllers
            throw new FormValidationException(fieldName + " should be a double");
        }
    }
}
