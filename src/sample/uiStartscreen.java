package sample;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class uiStartscreen implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }

    // https://stackoverflow.com/questions/13567019/close-fxml-window-by-code-javafx
    public void StartscreenExit(ActionEvent event) {
        Platform.exit();
    }

    //Methode "StartscreenRules" ruft zweites Fenster (Stage) auf, in dem die Regeln abgebildet werden
    public void StartscreenRules(ActionEvent event) {
        final Stage dialog = new Stage();
        dialog.setTitle("Rules");
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("\n- Goal of the game ist to guess a secret word.\n- Each letter of this word is represented by an underscore.\n- In order to guess a letter which might be in the word press the according button.\n- If you are right the letter will replace an underscore at every position it appears in the word.\n- You are allowed a total number of 10 wrong guesses.\n- As soon as you guess incorrectly for the tenth time you have lost.\n- As soon as you have completed the secret word you have won.\n- If you feel confident that you know the right answer you can also give it a try by entering your guess in the provided field.\n\nGood luck and have fun ! :)\nBenedikt & Patrick"));
        Scene dialogScene = new Scene(dialogVbox, 680, 200);
        dialog.setScene(dialogScene);
        dialog.show();


    }

    public void StartscreenPlay(ActionEvent event) throws IOException {



        Parent gameplay = FXMLLoader.load(getClass().getResource("Variation.fxml"));
        Scene game = new Scene(gameplay);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(game);
        primaryStage.show();


    }


}