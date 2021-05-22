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

import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    String url = "http://ec2-18-196-42-56.eu-central-1.compute.amazonaws.com";

    public boolean loginAmministratore(String username, String password){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/loginAmministratore.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
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

}
