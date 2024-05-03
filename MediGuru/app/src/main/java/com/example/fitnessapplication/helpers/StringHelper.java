package com.example.fitnessapplication.helpers;

public class StringHelper {

    public static boolean regexEmailValidationPattern(String email){
        String regex = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})";

        if(email.matches(regex)){
            return true;
        }

        return false;
    }
}
