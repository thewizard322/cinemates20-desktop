import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CampiNonVuotiRegistrazioneControllerTestWhiteBox {

    private boolean campiNonVuoti(String username, String password, String email){
        if(username.equals(""))
            return false;
        else if(password.equals(""))
            return false;
        else if(email.equals(""))
            return false;
        return true;
    }

    @Test
    public void campiNonVuotiRegistrazioneControllerTestWhiteBox_1_2(){
        String username = "";
        String password = "pass";
        String email = "email";
        assertEquals(false,campiNonVuoti(username,password,email));
    }

    @Test
    public void campiNonVuotiRegistrazioneControllerTestWhiteBox_1_3_4(){
        String username = "user";
        String password = "";
        String email = "email";
        assertEquals(false,campiNonVuoti(username,password,email));
    }

    @Test
    public void campiNonVuotiRegistrazioneControllerTestWhiteBox_1_3_5_6(){
        String username = "user";
        String password = "pass";
        String email = "";
        assertEquals(false,campiNonVuoti(username,password,email));
    }

    @Test
    public void campiNonVuotiRegistrazioneControllerTestWhiteBox_1_3_5_7(){
        String username = "user";
        String password = "pass";
        String email = "email";
        assertEquals(true,campiNonVuoti(username,password,email));
    }

}