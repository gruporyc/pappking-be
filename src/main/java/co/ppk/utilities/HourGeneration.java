package co.ppk.utilities;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class HourGeneration {
    Date datetime = new Date();
    DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");



    public String getHourFormat() {
        return hourFormat.format(datetime);
    }


    public String getDateFormat() {
        return dateFormat.format(datetime);
    }

}
