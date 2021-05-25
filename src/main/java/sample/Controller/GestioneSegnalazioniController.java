package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Model.RecensioneSegnalata;

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
    public void initialize(){
        //ObservableList<RecensioneSegnalata> observableList = FXCollections.emptyObservableList();
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
        RecensioneSegnalata recensioneSegnalata =
                new RecensioneSegnalata("5","50","prova","autore",500);
        //observableList.add(recensioneSegnalata);
        //tableViewSegnalazioni.setItems(observableList);
        //tableViewSegnalazioni.getItems().add(recensioneSegnalata);
    }

}
