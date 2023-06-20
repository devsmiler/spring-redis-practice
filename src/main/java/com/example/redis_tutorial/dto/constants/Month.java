package com.example.redis_tutorial.dto.constants;

import lombok.Builder;

public enum Month {

    JAN("Jan", 1),
    FEB("Feb",2),
    MAR("Mar",3),
    APR("Apr", 4),
    MAY("May", 5),
    JUN("Jun", 6),
    JUL("Jul", 7),
    AUG("Aug", 8),
    SEP("Sep", 9),
    OCT("Oct",10),
    NOV("Nov", 11),
    DEC("Dec",12);

    private String s;
    private int num;

    Month(String s, int num) {
        this.s = s;
        this.num = num;
    }

    public static int strToNumber(String s) {
        for(Month month : Month.values()){
            if(month.s.equals(s)){
                return month.num;
            }
        }
        return -1;
    }
}
