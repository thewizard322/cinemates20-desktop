package sample.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.DAO.UtenteDAO;
import sample.View.Main;

import java.io.IOException;

public class RegistrazioneController {

    @FXML
    TextField tfUsernameReg;

    @FXML
    TextField tfPasswordReg;

    @FXML
    TextField tfEmailReg;

    @FXML
    Button buttonRegistratiReg;

    @FXML
    Button buttonIndietroReg;

    @FXML
    Label labelErrorReg;

    @FXML
    public void initialize(){
        Image image = new Image(getClass().getResourceAsStream("/images/frecciaindietro.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        imageView.setPreserveRatio(true);
        buttonIndietroReg.setGraphic(imageView);
    }

    public void buttonRegistratiClick(){
        String username = tfUsernameReg.getText();
        String password = tfPasswordReg.getText();
        String email = tfEmailReg.getText();
        boolean campiNonVuoti = campiNonVuoti(username,password,email);
        if(campiNonVuoti){
            registraUtente(username,password,email);
        }
        else{
            labelErrorReg.setText("Compilare tutti i campi");
            labelErrorReg.setTextFill(Color.RED);
        }
;    }

    public void buttonIndietroRegClick(ActionEvent event){
        mostraLoginScene();
    }

    private boolean campiNonVuoti(String username, String password, String email){
        if(username.equals("") || password.equals("") || email.equals(""))
            return false;
        return true;
    }

    private void registraUtente(String username, String password, String email){
        new Thread(){
            public void run(){
                UtenteDAO utenteDAO = new UtenteDAO();
                boolean checkUsername = utenteDAO.checkUser(username);
                boolean checkEmail = utenteDAO.checkEmail(email);
                boolean checkRegistrazione = false;
                if(checkUsername && checkEmail)
                    checkRegistrazione = utenteDAO.registraAmministratore(username,password,email);
                verificaRegistrazioneAvvenuta(checkUsername,checkEmail,checkRegistrazione);
            }
        }.start();
    }

    private void verificaRegistrazioneAvvenuta(boolean checkUsername, boolean checkEmail, boolean checkRegistrazione){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(!checkUsername) {
                    labelErrorReg.setText("Username già utilizzato");
                    labelErrorReg.setTextFill(Color.RED);
                }
                else if (!checkEmail){
                    labelErrorReg.setText("Email già utilizzata");
                    labelErrorReg.setTextFill(Color.RED);
                }
                else if(!checkRegistrazione){
                    labelErrorReg.setText("Impossibile registrare l'utente");
                    labelErrorReg.setTextFill(Color.RED);
                }
                else{
                    mostraAlertRegistrazioneSuccesso();
                    mostraLoginScene();
                }
            }
        });
    }

    private void mostraAlertRegistrazioneSuccesso(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Registrazione avvenuta con successo");
        alert.setTitle("Registrazione");
        alert.setHeaderText("");
        alert.show();
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
