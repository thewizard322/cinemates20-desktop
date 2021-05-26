package sample.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Model.Utente;
import sample.View.Main;

import java.io.IOException;

public class MenuController {

    @FXML
    BorderPane rootPane;

    @FXML
    Label labelUsernameMenu;

    @FXML
    JFXButton buttonStatisticheMenu;

    @FXML
    JFXButton buttonSegnalazioniMenu;

    @FXML
    JFXButton buttonLogoutMenu;

    @FXML
    public void initialize(){
        labelUsernameMenu.setText(Utente.getUtenteLoggato().getUsername());
        buttonLogoutMenu.setFocusTraversable(false);
        buttonStatisticheMenu.setFocusTraversable(false);
        buttonSegnalazioniMenu.setFocusTraversable(false);
        Image image = new Image(getClass().getResourceAsStream("/images/logout.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        imageView.setPreserveRatio(true);
        buttonLogoutMenu.setGraphic(imageView);
    }

    public void buttonStatisticheClick() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/statistiche.fxml"));
        } catch (IOException e) {
        }
        rootPane.setCenter(null);
        rootPane.setCenter(root);
        buttonStatisticheMenu.setDisable(true);
        buttonSegnalazioniMenu.setDisable(false);
    }

    public void buttonSegnalazioniClick() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/gestionesegnalazioni.fxml"));
        } catch (IOException e) {
        }
        rootPane.setCenter(null);
        rootPane.setCenter(root);
        buttonStatisticheMenu.setDisable(false);
        buttonSegnalazioniMenu.setDisable(true);
    }

    public void buttonLogoutClick(){
        Utente.finalizeUtenteLoggato();
        mostraLoginScene();
    }

    private void mostraLoginScene(){
        Stage stage = Main.getPrimaryStage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass()
                    .getResource("/fxml/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 400, 300));
        stage.setResizable(false);
        stage.show();
    }

}
