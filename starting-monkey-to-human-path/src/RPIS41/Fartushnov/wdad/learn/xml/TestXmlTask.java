package RPIS41.Fartushnov.wdad.learn.xml;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by никита on 07.10.2016.
 */
public class TestXmlTask {
    public static void main(String[] args) {
        XmlTask test = new XmlTask();
        try{
            test.changeOfficiantName("garik","sidorov","vova","petrov"); //смена имени официанта
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            calendar.setTime(sdf.parse("7-5-2016"));
            //test.removeDay(calendar);
            System.out.println(test.earningsTotal("petrov",calendar));
            test.sortByDate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
