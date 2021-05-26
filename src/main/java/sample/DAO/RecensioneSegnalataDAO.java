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
import sample.Model.RecensioneSegnalata;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecensioneSegnalataDAO {

     String url = "http://ec2-18-196-42-56.eu-central-1.compute.amazonaws.com";

     public List<RecensioneSegnalata> prelevaSegnalazioni(){
         List<RecensioneSegnalata> list = new LinkedList<>();
         HttpClient client = new DefaultHttpClient();
         HttpPost httpPost = new HttpPost(url+"/prelevaSegnalazioni.php");
         try {
             HttpResponse response = client.execute(httpPost);
             String responseString = EntityUtils.toString(response.getEntity());
             JSONArray jsonArray = new JSONArray(responseString);
             int n = jsonArray.length();
             for(int i=0; i<n; i++){
                 JSONObject jsonObject = jsonArray.getJSONObject(i);
                 String idRecensione = jsonObject.getString("id_recensione");
                 String idFilm = jsonObject.getString("id_film");
                 String testo = jsonObject.getString("testo");
                 String autore = jsonObject.getString("autore");
                 Integer numeroSegnalazioni = jsonObject.getInt("num_segnalazioni");
                 list.add(new RecensioneSegnalata(idRecensione,idFilm,testo,autore,numeroSegnalazioni));
             }
         } catch (Throwable e) {
             System.out.println("IMPOSSIBILE PRELEVARE SEGNALAZIONI");
             return null;
         }
         return list;
     }

     public boolean inviaNotificaEsitoDecisioneAmministratore(String usernameMittente
             , String usernameDestinatario, String tipo){
         List<NameValuePair> params = new ArrayList<NameValuePair>();
         params.add(new BasicNameValuePair("username_mittente", usernameMittente));
         params.add(new BasicNameValuePair("username_destinatario", usernameDestinatario));
         params.add(new BasicNameValuePair("tipo", tipo));
         HttpClient client = new DefaultHttpClient();
         HttpPost httpPost = new HttpPost(url+"/inviaNotificaEsitoDecisioneAmministratore.php");
         try {
             httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
             HttpResponse response = client.execute(httpPost);
             String responseString = EntityUtils.toString(response.getEntity());
             JSONObject jsonObject = new JSONObject(responseString);
             int check = jsonObject.getInt("response");
             if(check != 0)
                 return false;
         } catch (Throwable e) {
             e.printStackTrace();
             return false;
         }
         return true;
     }

     public boolean eliminaRecensione(String idRecensione){
         List<NameValuePair> params = new ArrayList<NameValuePair>();
         params.add(new BasicNameValuePair("id_recensione", idRecensione));
         HttpClient client = new DefaultHttpClient();
         HttpPost httpPost = new HttpPost(url+"/eliminaRecensione.php");
         try {
             httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
             HttpResponse response = client.execute(httpPost);
             String responseString = EntityUtils.toString(response.getEntity());
             JSONObject jsonObject = new JSONObject(responseString);
             int check = jsonObject.getInt("response");
             if(check != 0)
                 return false;
         } catch (Throwable e) {
             e.printStackTrace();
             return false;
         }
         return true;
     }

     public boolean eliminaSegnalazioniRecensione(String idRecensione){
         List<NameValuePair> params = new ArrayList<NameValuePair>();
         params.add(new BasicNameValuePair("id_recensione", idRecensione));
         HttpClient client = new DefaultHttpClient();
         HttpPost httpPost = new HttpPost(url+"/eliminaSegnalazioniRecensione.php");
         try {
             httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
             HttpResponse response = client.execute(httpPost);
             String responseString = EntityUtils.toString(response.getEntity());
             JSONObject jsonObject = new JSONObject(responseString);
             int check = jsonObject.getInt("response");
             if(check != 0)
                 return false;
         } catch (Throwable e) {
             e.printStackTrace();
             return false;
         }
         return true;
     }

}
