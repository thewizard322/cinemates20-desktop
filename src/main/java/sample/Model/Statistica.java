package sample.Model;

public class Statistica {

    private String nome;
    private int valore;
    private String valoreStringa;

    public Statistica(String nome, int valore){
        this.nome = nome;
        this.valore = valore;
    }

    public Statistica(String nome, String valoreStringa){
        this.nome = nome;
        this.valoreStringa = valoreStringa;
    }

    public String getNome(){
        return nome;
    }

    public int getValore(){
        return valore;
    }

    public String getValoreStringa(){
        return valoreStringa;
    }

}
