package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;


public class Backend {

    public static String word() {
        //wählt zufälliges Wort aus File "words.json" aus, das erraten werden muss

        JSONParser parser = new JSONParser();

        //https://www.educative.io/edpresso/how-to-generate-random-numbers-in-java
        Random rand = new Random(); //zum Erzeugen einer Zufallszahl
        int upperbound = 100;
        int choice = rand.nextInt(upperbound); //gibt zufällige Zahl aus zwischen 0 und [upperbound - 1]
        System.out.println(choice); //ausgabe aktuell nur für testzwecke

        //https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library
        try {
            JSONObject a = (JSONObject) parser.parse(new FileReader("src/sample/words.json"));
            JSONArray b = (JSONArray) a.get("words"); //"words"enthält array mit 100 Worten
            String word = (String) b.get(choice); //Wort an der Stelle [Zufallszahl] wird aus dem Array ausgewählt
            System.out.println(word.toUpperCase()); //Ausgabe aktuell nur für testzwecke
            return word.toUpperCase(); //Spiel wird nur mit Großbuchstaben gespielt, um Problem mit Casesensitiveness zu umgehen
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
