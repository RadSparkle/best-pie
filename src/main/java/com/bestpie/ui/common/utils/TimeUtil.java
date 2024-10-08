package com.bestpie.ui.common.utils;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@Component
public class TimeUtil {

    private final String YYYYMMDDHHMMSS_FORMAT_A = "yyyy-MM-dd HH:mm:ss";

    private final String YYYYMMDDHHMMSS_FORMAT_B = "yyyy.MM.dd HH:mm";

    public LocalDateTime getLocalDateTime(String date) {
        if (!date.isEmpty() && date!=null) {
            try {
                // 우선 들어온 문자열이 "yyyy-MM-dd HH:mm:ss" 형식인지 확인
                return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS_FORMAT_A));
            } catch (DateTimeParseException e) {
                // "yyyy-MM-dd HH:mm:ss" 형식이 아닌 경우, 새로운 형식으로 파싱
                return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS_FORMAT_B));
            }
        }
        return null;
    }


    public static String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    public static String getCurrentTimeYYYYMMDD() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDateTime.now().format(formatter);
    }

    public static String getCurrentTimeRoundDown() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).format(formatter);
    }
}
