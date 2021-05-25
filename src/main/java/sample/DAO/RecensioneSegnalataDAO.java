package sample.DAO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.Model.RecensioneSegnalata;

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

}
