package com.nashtech.minhtran.gearshop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    public static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    public static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    public static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    public static boolean checkNumberInRange (double min, double max, double number){
        return !(number < min) && !(number > max);
    }

    public static boolean checkNumberNegative(double number){
        return number < 0;
    }

    public static boolean checkValidPassword(String password){
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    public static boolean checkValidEmail(String email){
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
