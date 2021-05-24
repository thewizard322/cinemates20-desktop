package sample.Model;

public class Utente {

    private static Utente utenteLoggato;
    private String username;
    private String email;
    private int privilegio;

    public Utente(String username, String email, int privilegio){
        this.username = username;
        this.email = email;
        this.privilegio = privilegio;
    }

    public Utente(String username, String email){
        this.username = username;
        this.email = email;
    }

    public static void setUtenteLoggato(Utente uL){
        if(utenteLoggato == null)
            utenteLoggato = uL;
    }

    public static void finalizeUtenteLoggato(){
        utenteLoggato = null;
    }

    public static Utente getUtenteLoggato(){
        return utenteLoggato;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getPrivilegio() {
        return privilegio;
    }
}
