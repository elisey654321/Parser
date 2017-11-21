import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



public class Parser {

    private static Document getPage () throws IOException {
        String url = "https://yandex.ru/pogoda/salavat";
        Document page = Jsoup.parse(new URL(url),3000);
        return page;
    }

    private static ArrayList<String[]> data = new ArrayList();

    private static String[] printInfoWht (Element str) {
        String answerDate = str.select ("time[class=time forecast-briefly__date]").text ();
        String answerTempDay = str.select ("div[class=temp forecast-briefly__temp forecast-briefly__temp_day]").text ();
        String answerTempNight = str.select ("div[class=temp forecast-briefly__temp forecast-briefly__temp_night]").text ();
        String answerWth = str.select ("div[class=forecast-briefly__condition]").text ();
        return new String[]{answerDate, answerTempDay, answerTempNight, answerWth};
    }

    public static void main(String[] args) throws IOException {
        MainWindow app = new MainWindow();
        app.setVisible(true);

        String[] tableWth = {
                "Дата",
                "Температура днем",
                "Температура ночью",
                "Состояние погоды"
        };
        Document page= getPage();
        //css query language
        Element tableWthFirst = page.select("div[class=forecast-briefly__day forecast-briefly__day_weekstart_0 day-anchor i-bem]").first ();
        Elements tableWthRest = page.select("div[class=forecast-briefly__day day-anchor i-bem]");
        String[] first = printInfoWht(tableWthFirst);

        data.add (first) ;
        System.out.println (data.get(0));
        int index = 1;
        for (Element stringRest  : tableWthRest) {
            String[] rest = printInfoWht (stringRest);
            System.out.println (rest);
            data.add ( index, rest );
            index++;
        }

        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("Дата                   Температурв(Днем)                   Температура(ночью)                   Состаяние погоды");
        for (int i = 0; i < data.size(); i++) {
            String str = "";
            for (int j = 0; j < data.get(i).length; j++) {
                str += data.get (i)[j] + "                   ";
            }
            listModel.addElement (str);
        }


        JList list = new JList(listModel);
        app.add (list);
        app.pack();
    }
}
