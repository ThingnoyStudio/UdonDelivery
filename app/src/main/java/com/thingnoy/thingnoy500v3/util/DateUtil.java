package com.thingnoy.thingnoy500v3.util;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public String getCurrentLocalDateTimeStamp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }else {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
        }
    }
}
