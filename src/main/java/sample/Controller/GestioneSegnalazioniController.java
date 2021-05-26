package sample.Controller;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import sample.DAO.RecensioneSegnalataDAO;
import sample.DAO.UtenteDAO;
import sample.Model.RecensioneSegnalata;
import sample.Model.Utente;

import java.util.List;

public class GestioneSegnalazioniController {

    @FXML
    TableView<RecensioneSegnalata> tableViewSegnalazioni;

    @FXML
    TableColumn<RecensioneSegnalata,String> tableColumnIdRecensione;

    @FXML
    TableColumn<RecensioneSegnalata,String> tableColumnIdFilm;

    @FXML
    TableColumn<RecensioneSegnalata,String> tableColumnTesto;

    @FXML
    TableColumn<RecensioneSegnalata,String> tableColumnAutore;

    @FXML
    TableColumn<RecensioneSegnalata,Integer> tableColumnNumeroSegnalazioni;

    @FXML
    Label labelTitoloFilm, labelIdFilm, labelIdRecensione, labelAutore, labelTesto, labelTextIdFilm
            , labelTextTitoloFilm, labelTextIdRecensione, labelTextIdAutore, labelTextTestoRecensione;

    @FXML
    Button buttonApprova, buttonRimuovi;

    @FXML
    Line lineSeparatore;

    RecensioneSegnalata recensioneCliccata;

    ObservableList<RecensioneSegnalata> observableListSegnalazioni = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        nascondiParteGestione();
        tableColumnIdRecensione.setCellValueFactory(
                new PropertyValueFactory<RecensioneSegnalata,String>("idRecensione")
        );
        tableColumnIdFilm.setCellValueFactory(
                new PropertyValueFactory<RecensioneSegnalata,String>("idFilm")
        );
        tableColumnTesto.setCellValueFactory(
                new PropertyValueFactory<RecensioneSegnalata,String>("testo")
        );
        tableColumnAutore.setCellValueFactory(
                new PropertyValueFactory<RecensioneSegnalata,String>("autore")
        );
        tableColumnNumeroSegnalazioni.setCellValueFactory(
                new PropertyValueFactory<RecensioneSegnalata,Integer>("numeroSegnalazioni")
        );
        tableViewSegnalazioni.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2)
                    if(tableViewSegnalazioni.getSelectionModel().getSelectedItem()!=null){
                        recensioneCliccata = tableViewSegnalazioni.getSelectionModel().getSelectedItem();
                        riempiCampiGestione(recensioneCliccata);
                        mostraParteGestione();
                    }
            }
        });
        prelevaSegnalazioni();
    }

    public void buttonApprovaClick(){
        approvaRecensione();
    }

    public void buttonRimuoviClick(){
        rimuoviRecensione();
    }

    private void approvaRecensione(){
        new Thread(){
            public void run(){
                UtenteDAO utenteDAO = new UtenteDAO();
                List<String> listSegnalatori =
                        utenteDAO.prelevaUsernameSegnalatori(recensioneCliccata.getIdRecensione());

                if(listSegnalatori!=null || !listSegnalatori.isEmpty()){
                    RecensioneSegnalataDAO recensioneSegnalataDAO = new RecensioneSegnalataDAO();
                    String usernameMittente = Utente.getUtenteLoggato().getUsername();
                    for(String usernameDestinatario : listSegnalatori) {
                        recensioneSegnalataDAO.inviaNotificaEsitoDecisioneAmministratore(
                                usernameMittente, usernameDestinatario, "ARSS");
                    }
                    recensioneSegnalataDAO.inviaNotificaEsitoDecisioneAmministratore(
                            usernameMittente,recensioneCliccata.getAutore(),"ARSP");
                    boolean checkSegnalazioniRimosse =
                            recensioneSegnalataDAO.eliminaSegnalazioniRecensione(recensioneCliccata.getIdRecensione());
                    if(checkSegnalazioniRimosse)
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                mostraAlertRecensioneApprovata();
                                nascondiParteGestione();
                                rimuoviRecensioneDaTabella();
                            }
                        });
                    else
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                mostraAlertOperazioneFallita();
                            }
                        });
                }
            }
        }.start();
    }

    private void rimuoviRecensione(){
        new Thread(){
            public void run(){
                UtenteDAO utenteDAO = new UtenteDAO();
                List<String> listSegnalatori =
                        utenteDAO.prelevaUsernameSegnalatori(recensioneCliccata.getIdRecensione());

                if(listSegnalatori!=null || !listSegnalatori.isEmpty()){
                    RecensioneSegnalataDAO recensioneSegnalataDAO = new RecensioneSegnalataDAO();
                    String usernameMittente = Utente.getUtenteLoggato().getUsername();
                    for(String usernameDestinatario : listSegnalatori) {
                        recensioneSegnalataDAO.inviaNotificaEsitoDecisioneAmministratore(
                                usernameMittente, usernameDestinatario, "AASS");
                    }
                    recensioneSegnalataDAO.inviaNotificaEsitoDecisioneAmministratore(
                            usernameMittente,recensioneCliccata.getAutore(),"AASP");
                    boolean checkRimozione = recensioneSegnalataDAO.eliminaRecensione(recensioneCliccata.getIdRecensione());
                    if(checkRimozione)
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                mostraAlertRecensioneRimossa();
                                nascondiParteGestione();
                                rimuoviRecensioneDaTabella();
                            }
                        });
                    else
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                mostraAlertOperazioneFallita();
                            }
                        });
                }
            }
        }.start();
    }

    private void rimuoviRecensioneDaTabella(){
        RecensioneSegnalata recensioneDaRimuovere = null;
        for(RecensioneSegnalata recensioneSegnalata : observableListSegnalazioni){
            if(recensioneSegnalata.getIdRecensione().equals(recensioneCliccata.getIdRecensione()))
                recensioneDaRimuovere = recensioneSegnalata;
        }
        observableListSegnalazioni.remove(recensioneDaRimuovere);
    }

    private void prelevaSegnalazioni(){
        new Thread(){
            public void run(){
                RecensioneSegnalataDAO recensioneSegnalataDAO = new RecensioneSegnalataDAO();
                List<RecensioneSegnalata> list = recensioneSegnalataDAO.prelevaSegnalazioni();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        riempiTabella(list);
                    }
                });
            }
        }.start();
    }

    private void riempiTabella(List<RecensioneSegnalata> list){
        observableListSegnalazioni.addAll(list);
        tableViewSegnalazioni.setItems(observableListSegnalazioni);
    }

    private void nascondiParteGestione(){
        labelAutore.setVisible(false);
        labelIdFilm.setVisible(false);
        labelTesto.setVisible(false);
        labelIdRecensione.setVisible(false);
        labelTitoloFilm.setVisible(false);
        labelTextIdAutore.setVisible(false);
        labelTextIdFilm.setVisible(false);
        labelTextIdRecensione.setVisible(false);
        labelTextTitoloFilm.setVisible(false);
        labelTextTestoRecensione.setVisible(false);
        buttonApprova.setVisible(false);
        buttonRimuovi.setVisible(false);
        lineSeparatore.setVisible(false);
    }

    private void mostraParteGestione(){
        labelAutore.setVisible(true);
        labelIdFilm.setVisible(true);
        labelTesto.setVisible(true);
        labelIdRecensione.setVisible(true);
        labelTitoloFilm.setVisible(true);
        labelTextIdAutore.setVisible(true);
        labelTextIdFilm.setVisible(true);
        labelTextIdRecensione.setVisible(true);
        labelTextTitoloFilm.setVisible(true);
        labelTextTestoRecensione.setVisible(true);
        buttonApprova.setVisible(true);
        buttonRimuovi.setVisible(true);
        lineSeparatore.setVisible(true);
    }

    private void riempiCampiGestione(RecensioneSegnalata recensioneSegnalata){
        labelIdFilm.setText(recensioneSegnalata.getIdFilm());
        labelAutore.setText(recensioneSegnalata.getAutore());
        labelTesto.setText(recensioneSegnalata.getTesto());
        labelIdRecensione.setText(recensioneSegnalata.getIdRecensione());
        new Thread(){
            public void run(){
                TmdbApi tmdbApi = new TmdbApi("2bc3bb8279aa7bcc7bd18d60857dc82a");
                MovieDb movieDb = tmdbApi.getMovies()
                        .getMovie(Integer.parseInt(recensioneSegnalata.getIdFilm()),"it");
                String titoloFilm = movieDb.getTitle();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        labelTitoloFilm.setText(titoloFilm);
                    }
                });
            }
        }.start();
    }

    private void mostraAlertRecensioneRimossa(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Operazione effettuata con successo");
        alert.setTitle("Recensione rimossa");
        alert.setHeaderText("");
        alert.show();
    }

    private void mostraAlertRecensioneApprovata(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Operazione effettuata con successo");
        alert.setTitle("Recensione approvata");
        alert.setHeaderText("");
        alert.show();
    }

    private void mostraAlertOperazioneFallita(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Operazione fallita");
        alert.setTitle("");
        alert.setHeaderText("");
        alert.show();
    }

}
