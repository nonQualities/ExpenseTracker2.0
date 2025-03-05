package org.exptrkr;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ronit
 */
public class UtilDate {
    public static Date StringToDate(String dateString, String format) throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.parse(dateString);
    }

    public static String DateToString(Date date, String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

}