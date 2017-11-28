import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    private static final String CSS_DATE_SELECTOR = "time[class=time forecast-briefly__date]";

    private static final String CSS_DAY_TEMPERATURE_SELECTOR = "div[class=temp forecast-briefly__temp forecast-briefly__temp_day]";

    private static final String CSS_NIGHT_TEMPERATURE_SELECTOR = "div[class=temp forecast-briefly__temp forecast-briefly__temp_night]";

    private static final String CSS_BRIEFLY_CONDITION_SELECTOR = "div[class=forecast-briefly__condition]";

    private static final String CSS_TODAY_WEATHER_BLOCK_SELECTOR = "div[class=forecast-briefly__day forecast-briefly__day_weekstart_0 day-anchor i-bem]";

    private static final String CSS_DAYS_WEATHER_BLOCKS_SELECTOR = "div[class=forecast-briefly__day day-anchor i-bem]";

    private static Document getPage () throws IOException {
        String url = "https://yandex.ru/pogoda/salavat";
        return Jsoup.parse(new URL(url),3000);
    }

    private static ArrayList<String[]> data = new ArrayList();

    private static String[] printInfoWht (Element str) {
        String answerDate = str.select (CSS_DATE_SELECTOR).text ();
        String answerTempDay = str.select (CSS_DAY_TEMPERATURE_SELECTOR).text ();
        String answerTempNight = str.select (CSS_NIGHT_TEMPERATURE_SELECTOR).text ();
        String answerWth = str.select (CSS_BRIEFLY_CONDITION_SELECTOR).text ();
        return new String[]{answerDate, answerTempDay, answerTempNight, answerWth};
    }

    public static void main(String[] args) throws IOException {
        MainWindow app = new MainWindow();
        app.setVisible(true);

        Document page= getPage();

        Element tableWthFirst = page.select(CSS_TODAY_WEATHER_BLOCK_SELECTOR).first ();
        Elements tableWthRest = page.select(CSS_DAYS_WEATHER_BLOCKS_SELECTOR);
        String[] first = printInfoWht(tableWthFirst);

        data.add (first) ;
        System.out.println ( Arrays.toString ( data.get ( 0 ) ) );
        int index = 1;
        for (Element stringRest  : tableWthRest) {
            String[] rest = printInfoWht (stringRest);
            System.out.println ( Arrays.toString ( rest ) );
            data.add ( index, rest );
            index++;
        }

        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("Дата                   Температурв(Днем)                   Температура(ночью)                   Состаяние погоды");
        for (String[] aData : data) {
            String str = "";
            for (int j = 0; j < aData.length; j++) {
                str += aData[j] + "                   ";
            }
            listModel.addElement ( str );
        }

        JList list = new JList(listModel);
        app.add (list);
        app.pack();
    }
}
