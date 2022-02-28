package br.com.dev.valber.medeiros.controleficancas.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static LocalDate stringToDate(String reference) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(reference + "-01", formatter);
    }

    public static String complementDate(String reference) {
        return reference + "-01";
    }

    public static LocalDate replaceDate(String date){
        return stringToDate(date.substring(0, date.length()-3));
    }

}
