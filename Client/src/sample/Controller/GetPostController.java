package sample.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Allert;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetPostController.class);
    private static final String USER_AGENT = "Mozilla/5.0";

    public static StringBuffer getConnect (URL url){
        StringBuffer response = new StringBuffer();
        try {

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e){
            LOGGER.error(e.getMessage(),e);
            Allert allert = new Allert();
            allert.allerts("Ошибка 404, нет соединения");
        }
        return  response;
    }

    public static String postConnetct(String object, URL url){
        StringBuffer response = new StringBuffer();
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String urlParameters = object;

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e){
            Allert allert = new Allert();
            allert.allerts("Ошибка 404, нет соединения");
            LOGGER.error(e.getMessage(),e);
        }
        return String.valueOf(response) ;
    }
}

