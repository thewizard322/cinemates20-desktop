package sample.DAO;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.Model.Utente;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UtenteDAO {

    String url = "http://ec2-18-196-42-56.eu-central-1.compute.amazonaws.com";

    public boolean registraAmministratore(String username, String pwd, String email){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", pwd));
        params.add(new BasicNameValuePair("email", email));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/registrazioneAmministratore.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return false;
        } catch (Throwable e) {
            System.out.println("IMPOSSIBILE REGISTRARE UTENTE");
            return false;
        }
        return true;
    }

    public boolean loginAmministratore(String username, String password){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/loginAmministratore.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            int count = 0;
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                count = jsonObject.getInt("count");
            }
            if(count==0)
                return false;
        } catch (Throwable e) {
            System.out.println("IMPOSSIBILE LOGGARE UTENTE");
            return false;
        }
        return true;
    }

    public boolean checkUser(String username){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/checkUser.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            int count = 0;
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                count = jsonObject.getInt("count");
            }
            if(count!=0)
                return false;
        } catch (Throwable e) {
            System.out.println("ERRORE NEL CHECK USERNAME");
            return false;
        }
        return true;
    }

    public boolean checkEmail(String email){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/checkEmail.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            int count = 0;
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                count = jsonObject.getInt("count");
            }
            if(count!=0)
                return false;
        } catch (Throwable e) {
            System.out.println("ERRORE NEL CHECK DELLA MAIL");
            return false;
        }
        return true;
    }

    public Utente prelevaUtente(String username){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaUtente.php");
        Utente utente;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            String email = null;
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                email = jsonObject.getString("email");
            }
            utente = new Utente(username,email);
        } catch (Throwable e) {
            return null;
        }
        return utente;
    }

    public List<String> prelevaUsernameSegnalatori(String idRecensione){
        List<String> usernameList = new LinkedList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id_recensione", idRecensione));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaUsernameSegnalatori.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String username = jsonObject.getString("username");
                usernameList.add(username);
            }
        } catch (Throwable e) {
            return null;
        }
        return usernameList;
    }

}
