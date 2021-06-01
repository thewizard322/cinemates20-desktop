import org.junit.Test;
import sample.Controller.RegistrazioneController;

import static org.junit.Assert.assertEquals;

public class CampiNonVuotiRegistrazioneControllerTestWhiteBox {

    RegistrazioneController registrazioneController;

    @Test
    public void campiNonVuotiRegistrazioneControllerTestWhiteBox_1_2(){
        String username = "";
        String password = "pass";
        String email = "email";
        registrazioneController = new RegistrazioneController();
        assertEquals(false,registrazioneController.campiNonVuoti(username,password,email));
    }

    @Test
    public void campiNonVuotiRegistrazioneControllerTestWhiteBox_1_3_4(){
        String username = "user";
        String password = "";
        String email = "email";
        registrazioneController = new RegistrazioneController();
        assertEquals(false,registrazioneController.campiNonVuoti(username,password,email));
    }

    @Test
    public void campiNonVuotiRegistrazioneControllerTestWhiteBox_1_3_5_6(){
        String username = "user";
        String password = "pass";
        String email = "";
        registrazioneController = new RegistrazioneController();
        assertEquals(false,registrazioneController.campiNonVuoti(username,password,email));
    }

    @Test
    public void campiNonVuotiRegistrazioneControllerTestWhiteBox_1_3_5_7(){
        String username = "user";
        String password = "pass";
        String email = "email";
        registrazioneController = new RegistrazioneController();
        assertEquals(true,registrazioneController.campiNonVuoti(username,password,email));
    }

}