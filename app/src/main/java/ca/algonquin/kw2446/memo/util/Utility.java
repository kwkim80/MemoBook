package ca.algonquin.kw2446.memo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String memoTitleDate(String strDate){

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat resultFormat = new SimpleDateFormat("MM yyyy");


        String result="";
        try {
            Date middleForm = transFormat.parse(strDate);
            result = resultFormat.format(middleForm);

        } catch (ParseException e) {
            e.printStackTrace();
            result="--- ----";
        }
        return  result;
    }
}
