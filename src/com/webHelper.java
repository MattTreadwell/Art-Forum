package com;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

// Functions for assisting with jsp pages

public class webHelper {
    public static String textPreview(String content) {
        // shortens a text post's content to be viewable on the main page

        return content.substring(0, Math.min(content.length(), 140));
    }

    public static String commentNumber(int count) {
        // https://stackoverflow.com/questions/9769554/how-to-convert-number-into-k-thousands-m-million-and-b-billion-suffix-in-jsp
        // example: converts 1278 to 1.2k
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f %c",
                count / Math.pow(1000, exp),
                "kMGTPE".charAt(exp-1));

    }

    public static String relativeTime(Date date) {
        PrettyTime p = new PrettyTime();

        return p.format(date);
    }

    public static void main(String[] args) {
        System.out.println(relativeTime(new Date()));
    }
}
