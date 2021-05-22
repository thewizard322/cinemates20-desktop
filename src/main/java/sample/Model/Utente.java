package sample.Model;

public class Utente {

    private Utente utenteLoggato;
    private String username;
    private String email;
    private int privilegio;

    public Utente(String username, String email, int privilegio){
        this.username = username;
        this.email = email;
        this.privilegio = privilegio;
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
