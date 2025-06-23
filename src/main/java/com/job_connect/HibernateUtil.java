package com.job_connect;

public class HibernateUtil {

    public static String startWith(String value) {
        return value.toLowerCase() + "%";
    }

    public static String betweenWith(String value) {
        return "%" + value.toLowerCase() + "%";
    }

    public static String endWith(String value) {
        return "%" + value.toLowerCase();
    }
}
