package api.ceroxe.time;

import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;

public class Time {
    public static Calendar calendar = Calendar.getInstance();

    private Time() {
    }

    public static Integer getCurrentYear() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static Integer getCurrentMonth() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static Integer getCurrentDay() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getCurrentHour() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static Integer getCurrentMinutes() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    public static String getCurrentSecondAsString() {
        calendar = Calendar.getInstance();
        int c = calendar.get(Calendar.SECOND);
        if (String.valueOf(c).length() == 1) {
            StringBuilder s = new StringBuilder("0");
            return s.append(c).toString();
        } else {
            return String.valueOf(c);
        }
    }

    public static Integer getCurrentSecond() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.SECOND);
    }

    public static Integer getCurrentMicroseconds() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.MILLISECOND);
    }

    public static Long getCurrentTimeAsLong() {
        StringBuilder year = new StringBuilder(Time.getCurrentYear().toString());
        String month = Time.getCurrentMonth().toString();
        String day = Time.getCurrentDay().toString();
        String hour = Time.getCurrentHour().toString();
        String minutes = Time.getCurrentMinutes().toString();
        String second = Time.getCurrentSecondAsString();
        String micros = Time.getCurrentMicroseconds().toString();
        return Long.valueOf(year.append(month).append(day).append(hour).append(minutes).append(second).append(micros).toString());
    }

    public static String getCurrentTimeAsString() {
        return String.valueOf(Time.getCurrentTimeAsLong());
    }

    public static CopyOnWriteArrayList<String> getCurrentTimeAsStringListDivided() {
        CopyOnWriteArrayList<String> c = new CopyOnWriteArrayList<>();
        c.add(String.valueOf(Time.getCurrentYear()));
        c.add(String.valueOf(Time.getCurrentMonth()));
        c.add(String.valueOf(Time.getCurrentDay()));
        c.add(String.valueOf(Time.getCurrentHour()));
        c.add(String.valueOf(Time.getCurrentMinutes()));
        c.add(String.valueOf(Time.getCurrentSecond()));
        c.add(String.valueOf(Time.getCurrentMicroseconds()));
        return c;
    }

    public static CopyOnWriteArrayList<String> getCurrentTimeAsStringList() {
        CopyOnWriteArrayList<String> c = new CopyOnWriteArrayList<>();
        StringBuilder s = new StringBuilder();
        StringBuilder b = new StringBuilder();
        c.add(s.append(Time.getCurrentYear()).append(".").append(Time.getCurrentMonth()).append(".").append(Time.getCurrentDay()).toString());
        c.add(b.append(Time.getCurrentHour()).append(":").append(Time.getCurrentMinutes()).append(":").append(Time.getCurrentSecond()).toString());
        c.add(String.valueOf(Time.getCurrentMicroseconds()));
        return c;
    }

    public static String getCurrentTimeAsFileName(boolean includeMicrosecond) {
        StringBuilder year = new StringBuilder(Time.getCurrentYear().toString());
        String month = Time.getCurrentMonth().toString();
        String day = Time.getCurrentDay().toString();
        String hour = Time.getCurrentHour().toString();
        String minutes = Time.getCurrentMinutes().toString();
        String second = Time.getCurrentSecondAsString();
        String micros = Time.getCurrentMicroseconds().toString();
        if (includeMicrosecond) {
            return year.append("-").append(month).append("-").append(day).append("-").append(hour).append("-").append(minutes).append("-").append(second).append("-").append(micros).toString();
        } else {
            return year.append("-").append(month).append("-").append(day).append("-").append(hour).append("-").append(minutes).append("-").append(second).toString();
        }
    }

    public static String getCurrentTimeAsFileName() {
        return Time.getCurrentTimeAsFileName(false);
    }
//    public static int standardTimeFileNameToIntTime(){
//
//    }

}
