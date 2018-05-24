package sample.FormBuyOrEdit.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Allert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private URL url ;
    private final String USER_AGENT = "Mozilla/5.0";

    public GetController(URL url){
        this.url = url ;
    }

    public StringBuffer getConnect (){
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
}
