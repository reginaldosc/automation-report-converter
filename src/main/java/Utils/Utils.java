package Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {


    public static String dateNowToString() {
        return new SimpleDateFormat("ddMMyyyy", Locale.US).format(getDate());
    }

    private static Date getDate() {
        Calendar c= Calendar.getInstance();
        c.add(Calendar.DATE, 0);
        return c.getTime();
    }

}
