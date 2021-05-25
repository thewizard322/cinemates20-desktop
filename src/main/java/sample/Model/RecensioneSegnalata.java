package sample.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RecensioneSegnalata {

    private final SimpleStringProperty idRecensione;
    private final SimpleStringProperty idFilm;
    private final SimpleStringProperty testo;
    private final SimpleStringProperty autore;
    private final SimpleIntegerProperty numeroSegnalazioni;

    public RecensioneSegnalata(String idRecensione, String idFilm, String testo, String autore, Integer numeroSegnalazioni){
        this.idRecensione = new SimpleStringProperty(idRecensione);
        this.idFilm = new SimpleStringProperty(idFilm);
        this.testo = new SimpleStringProperty(testo);
        this.autore = new SimpleStringProperty(autore);
        this.numeroSegnalazioni = new SimpleIntegerProperty(numeroSegnalazioni);
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
