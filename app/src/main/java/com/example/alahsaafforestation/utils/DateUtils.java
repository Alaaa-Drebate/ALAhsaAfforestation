package com.example.alahsaafforestation.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DateUtils {

    static final List<Integer> months31 = Arrays.asList(1,3,5,7,8,10,12);
    static final List<Integer> month30 = Arrays.asList(4,6,9,11);

    public static boolean isLeapYear(int year){
        Calendar cal = Calendar.getInstance(); //gets Calendar based on local timezone and locale
        cal.set(Calendar.YEAR, year); //setting the calendar year
        int noOfDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR);

        if(noOfDays > 365){
            return true;
        }

        return false;
    }

    public static int getMonthDays(int month){

        if(months31.contains(month)){
            return 31;
        }if(month30.contains(month)){
            return 30;
        }

        return -1;

    }


}
