package com.barnettwong.lovedoudou.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cgb on 2017/3/19.
 */

public class TextUtils {

    public static String handleEmptyText(String text){
        String result = "——";
        if (text!=null && !text.isEmpty()){
            result = text;
        }
        return result;
    }

    public static String handleListText(List<String> texts){
        StringBuilder builder = new StringBuilder();
        if (texts!=null && texts.size()>0){
            for (int i = 0; i < texts.size(); i++) {
                if (i>0){
                    builder.append("/");
                }
                builder.append(texts.get(i));
            }
        }
        return handleEmptyText(builder.toString());
    }

    public static String getBirthDay(int year, int month, int day) {
        StringBuilder birthday = new StringBuilder();
        if (year!=0){
            birthday.append(year).append("年");
        }
        if (month!=0){
            birthday.append(month).append("月");
            if (day != 0) {
                birthday.append(day).append("日");
            }
        }
        return handleEmptyText(birthday.toString());
    }

    public static String getBirthDay(int year, String date){
        StringBuilder birthday = new StringBuilder();
        if (year!=0){
            birthday.append(year).append("年").append(date);
        }else {
            birthday.append(date);
        }
        return handleEmptyText(birthday.toString());
    }

    /**
     * 将格式为 yyyyMMdd 的日期 格式化为 yyyy年MM月dd日；
     * @param date
     * @return
     */
    public static String formatDate(String date, String parse, String format){
        String formatDate = "";
        SimpleDateFormat parseFormat = new SimpleDateFormat(parse);
        try {
            Date parseDate = parseFormat.parse(date);
            SimpleDateFormat formater = new SimpleDateFormat(format);
            formatDate = formater.format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            return handleEmptyText(formatDate);
        }
    }

    public static String formatDate(long mills, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String formatData = dateFormat.format(mills);
        return handleEmptyText(formatData);
    }

    public static String handleSpace(String text) {
        String result = text;
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(text);
        if (m.find()){
            result = m.replaceAll("");
        }
        return handleEmptyText(result);
    }
}
