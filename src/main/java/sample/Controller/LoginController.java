package sample.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.DAO.UtenteDAO;
import sample.Model.Utente;
import sample.View.Main;

import java.io.IOException;

public class LoginController {

    @FXML
    TextField tfUsernameLogin;

    @FXML
    TextField tfPasswordLogin;

    @FXML
    Label labelErrorLogin;

    @FXML
    Button buttonLogin;

    @FXML
    Button buttonRegistratiLgn;

    public void buttonLoginClick(ActionEvent event){
        String username = tfUsernameLogin.getText();
        String password = tfPasswordLogin.getText();
        new Thread(){
            public void run(){
                UtenteDAO utenteDAO = new UtenteDAO();
                boolean login = utenteDAO.loginAmministratore(username,password);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        loginResult(login);
                    }
                });
            }
        }.start();
    }

    public void buttonRegistratiLgnClick(ActionEvent event) {
        mostraRegistrazioneScene();
    }

    private void mostraRegistrazioneScene(){
        Stage stage = Main.getPrimaryStage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass()
                    .getResource("/fxml/registrazione.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 400, 300));
        stage.setResizable(false);
        stage.show();
    }

    private void mostraSchermataPrincipale(){
        Stage stage = Main.getPrimaryStage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass()
                    .getResource("/fxml/menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 800, 600));
        stage.setResizable(false);
        stage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth()-stage.getWidth())/2);
        stage.setY((screenBounds.getHeight()-stage.getHeight())/2);
    }

    private void loginResult(boolean login){
        if(login == false) {
            labelErrorLogin.setText("Username e/o Password errati");
            labelErrorLogin.setTextFill(Color.RED);
        }
        else {
            new Thread(){
                public void run(){
                    UtenteDAO utenteDAO = new UtenteDAO();
                    String username = tfUsernameLogin.getText();
                    Utente utenteLoggato = utenteDAO.prelevaUtente(username);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Utente.setUtenteLoggato(utenteLoggato);
                            mostraSchermataPrincipale();
                        }
                    });
                }
            }.start();
        }
    }

}
