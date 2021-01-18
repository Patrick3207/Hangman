package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class VariationController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private Rectangle floor;

    @FXML
    private Line lineOne, lineTwo, lineThree, lineFour, arms, leftLeg, rightLeg, body;

    @FXML
    private Circle head;

    @FXML
    private Text triesLeft, left, display;

    private int lives = 10;
    private String word;
    private char[] visual;
    private char[] compare;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Setzt alle shapes, aus denen der Galgen gebaut ist, transperent --> werden später bei Fehlversuch sichtbar
        floor.setStroke(Color.TRANSPARENT);
        floor.setFill(Color.TRANSPARENT);
        lineOne.setStroke(Color.TRANSPARENT);
        lineTwo.setStroke(Color.TRANSPARENT);
        lineThree.setStroke(Color.TRANSPARENT);
        lineFour.setStroke(Color.TRANSPARENT);
        head.setFill(Color.TRANSPARENT);
        head.setStroke(Color.TRANSPARENT);
        body.setStroke(Color.TRANSPARENT);
        arms.setStroke(Color.TRANSPARENT);
        leftLeg.setStroke(Color.TRANSPARENT);
        rightLeg.setStroke(Color.TRANSPARENT);

        word = Backend.word();
        compare = word.toCharArray();
        visual = new char[word.length()];

        //Setz alle Buchstaben des Stringarrays, der zu erratendes Wort abbildet, zu Spielbeginn auf "_"
        for (int i = 0; i < visual.length; i++) {
            visual[i] = '_';
        }

        //Setzt Lebensanzeige zu Spielbeginn
        left.setText(String.valueOf(lives)); //tries left
        display.setText(String.valueOf(visual));//shows word as combination of right guesses and "_" (yet to guess)

    }

    //Methode "rules" ruft zweites Fenster (Stage) auf, in dem die Regeln abgebildet werden
    public void rules(ActionEvent actionEvent) {

        //https://stackoverflow.com/questions/22166610/how-to-create-a-popup-windows-in-javafx
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Rules");
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("- Goal of the game ist to guess a secret word.\n- Each letter of this word is represented by an underscore.\n- In order to guess a letter which might be in the word press the according button.\n- If you are right the letter will replace an underscore at every position it appears in the word.\n- You are allowed a total number of nine wrong guesses.\n- As soon as you guess incorrectly for the tenth time you have lost.\n- As soon as you have completed the secret word you have won."));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();

    }

    public void guess(ActionEvent actionEvent) {

        //Liest aus, welcher Button gedrückt wurde (wird als vorletztes Element des Strings, der durch "getSource" entsteht, übermittlet
        String button = actionEvent.getSource().toString();
        char[] help = button.toCharArray();
        int length = help.length;
        char inputChar = help[length - 2];
        String input = inputChar + "";

        //Abzug bei Fehlversuch
        int substraction = 1;

        //deaktviert Button, nachdem er gedrückt wurde
        Button deactivate = (Button) actionEvent.getSource();
        deactivate.setDisable(true);

        //Wenn der Versuch im zu erratenden Wort enthalten ist, wird/werden im StringArray "visual" die entsprechende(n) Position(en) durch den Buchstaben ersetzt & visual neu angezeigt
        if (word.contains(input + "")) {
            for (int i = 0; i < word.length(); i++) {
                if ( inputChar == compare[i]) {
                    visual[i] = inputChar;
                    display.setText(String.valueOf(visual));
                }
            }
            //Wenn visual komplett mit Buchstaben befüllt wurde (==compare) hat der Spieler gewonnen --> statt "TRIES LEFT" wird "YOU WON!" angezeigt
            if (Arrays.equals(visual, compare)) {
                triesLeft.setText("YOU WON!");
                //Das Spiel ist vorbei --> alle Buttons werden deaktiviert
                //https://stackoverflow.com/questions/35119290/javafx-extracting-all-children-that-are-buttons-from-root-getchildren
                for(int i=0; i<pane.getChildren().size(); i++)
                    if(pane.getChildren().get(i) instanceof Button) {
                        Button loop = (Button) pane.getChildren().get(i);
                        loop.setDisable(true);
                    }
            }

        //Bei einem Fehlversuch wird ein Leben abgezogen --> abhängig davon, wie wenig Leben noch über sind, werden mehr oder weniger Teile des Galgens angezeigt
        } else {
            lives = lives - substraction;

            switch (lives){
                case 9: floor.setFill(Color.BLACK); floor.setStroke(Color.BLACK); break;
                case 8: lineOne.setStroke(Color.BLACK); break;
                case 7: lineTwo.setStroke(Color.BLACK); break;
                case 6: lineThree.setStroke(Color.BLACK); break;
                case 5: lineFour.setStroke(Color.BLACK); break;
                case 4: head.setFill(Color.YELLOW); head.setStroke(Color.YELLOW); break;
                case 3: body.setStroke(Color.BLACK); break;
                case 2: arms.setStroke(Color.BLACK); break;
                case 1: leftLeg.setStroke(Color.BLACK); break;
                case 0: rightLeg.setStroke(Color.BLACK); break;
                default: System.out.println("Error @ switchcase!");
            }

            //aktualisierte Anzahl, der Leben wird angezeigt
            left.setText(String.valueOf(lives));
            //Wenn 0 Leben erreicht sind hat, wurde das Spiel verloren --> statt "TRIES LEFT" wird "YOU LOST!" angezeigt
            if(lives == 0){
                triesLeft.setText("YOU LOST!");
                //Lösung, die korrekt gewesen wäre, wird angezeigt (compare)
                display.setText(String.valueOf(compare));
                //Das Spiel ist vorbei --> alle Buttons werden deaktiviert
                for(int i=0; i<pane.getChildren().size(); i++)
                    if(pane.getChildren().get(i) instanceof Button) {
                        Button loop = (Button) pane.getChildren().get(i);
                        loop.setDisable(true);
                    }
            }
        }

    }
}