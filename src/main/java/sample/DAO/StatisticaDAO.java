package sample.DAO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import sample.Model.Statistica;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StatisticaDAO {

    String url = "http://ec2-18-196-42-56.eu-central-1.compute.amazonaws.com";

    public List<Statistica> prelevaStatistiche(){
        List<Statistica> list = new LinkedList<>();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaStatistiche.php");
        try {
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            list.add(new Statistica("n° utenti",jsonObject.getInt("numero utenti")));
            list.add(new Statistica("n° recensioni",jsonObject.getInt("numero recensioni")));
            list.add(new Statistica("n° segnalazioni",jsonObject.getInt("numero segnalazioni")));
            list.add(new Statistica("n° liste",jsonObject.getInt("numero liste")));
        } catch (Throwable e) {
            System.out.println("IMPOSSIBILE PRELEVARE STATISTICHE");
            return null;
        }
        return list;
    }

    public List<Statistica> prelevaStatisticheStringhe(){
        List<Statistica> list = new ArrayList<>();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaStatisticheStringhe.php");
        try {
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            if(!jsonObject.isNull("film piu apprezzato"))
                list.add(new Statistica("film piu apprezzato",jsonObject.getString("film piu apprezzato")));
            if(!jsonObject.isNull("film meno visto"))
                list.add(new Statistica("film meno visto",jsonObject.getString("film meno visto")));
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("IMPOSSIBILE PRELEVARE STATISTICHE STRINGHE");
            return null;
        }
        return list;
    }

}
