package sample;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Autorization.Account;
import sample.FormBuyOrEdit.Good;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Controller {
    private static String request ;
    private final String USER_AGENT = "Mozilla/5.0";
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    public Controller(){} ;
    public Controller(String request){
        this.request = request ;
    }

    public ArrayList<Good> getAll(){
        try {
            URL url = new URL(request+"/getAll") ;
            StringBuffer response = getConnect(url) ;
            ArrayList<Good> goods = new ObjectMapper().readValue(response.toString(),
                    new TypeReference<ArrayList<Good>>() {
                    });
            LOGGER.debug("goods get server="+goods.toString());
            return goods;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
            return null;
        }
    }

    public String buy(ArrayList<Good> goods){
        String responce = "" ;
        try {
            String listToJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(goods);
            URL url = new URL(request+"/buy") ;
            String response = postConnetct(url,listToJson) ;
        } catch (IOException e) {
            LOGGER.debug(e.getMessage(),e);
        }
        return responce ;
    }

    public String addGood(ArrayList<Good> goodList) {
        String responce = "NO";
        try {
            String goodToJson = new ObjectMapper().writeValueAsString(goodList);

            URL url = new URL(request+"/add") ;
            String response = postConnetct(url,goodToJson) ;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return responce;
    }

    public String addAccount(Account account){
        String responce = "";
        try {
            String addingAccount = new ObjectMapper().writeValueAsString(account);
            URL url = new URL(request+"/addAccount") ;
            responce =postConnetct(url,addingAccount) ;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return String.valueOf(responce);
    }

    public String postAccount(Account account){
        try {
            String requestUrl = request+"/login" ;
            String postAccount = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(account);
            System.out.print(postAccount);
            URL url = new URL(requestUrl) ;
            String response = postConnetct(url,postAccount) ;
            if (!response.equals("")){
                return response ;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return "Нет соединения";
    }

    public String connect(){
        String response = "" ;
        try {
            String pingClient = "ping" ;
            URL url = new URL(request+"/connect") ;
            response = postConnetct(url,pingClient) ;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return response;
    }

    public StringBuffer getConnect (URL url){
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

    public String postConnetct(URL url, String object){
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