package sample.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RecensioneSegnalata {

    private SimpleStringProperty idRecensione;
    private SimpleStringProperty idFilm;
    private SimpleStringProperty testo;
    private SimpleStringProperty autore;
    private SimpleIntegerProperty numeroSegnalazioni;

    public RecensioneSegnalata(String idRecensione, String idFilm, String testo, String autore, Integer numeroSegnalazioni){
        this.idRecensione = new SimpleStringProperty(idRecensione);
        this.idFilm = new SimpleStringProperty(idFilm);
        this.testo = new SimpleStringProperty(testo);
        this.autore = new SimpleStringProperty(autore);
        this.numeroSegnalazioni = new SimpleIntegerProperty(numeroSegnalazioni);
    }

    public SimpleStringProperty idRecensioneProperty() {
        return idRecensione;
    }

    public SimpleStringProperty idFilmProperty() {
        return idFilm;
    }

    public SimpleStringProperty testoProperty() {
        return testo;
    }

    public SimpleStringProperty autoreProperty() {
        return autore;
    }

    public SimpleIntegerProperty numeroSegnalazioniProperty() {
        return numeroSegnalazioni;
    }

    public String getIdRecensione(){
        return idRecensione.get();
    }

    public String getIdFilm(){
        return idFilm.get();
    }

    public String getTesto(){
        return testo.get();
    }

    public String getAutore(){
        return autore.get();
    }

    public Integer getNumeroSegnalazioni(){
        return numeroSegnalazioni.get();
    }

}
