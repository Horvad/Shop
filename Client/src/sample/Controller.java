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
    public String request ;
    private final String USER_AGENT = "Mozilla/5.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);


    public ArrayList<Good> getAll(){
        try {
            request = "http://localhost:4567/getAll";
            URL url = new URL(request) ;
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
        try {
            String listToJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(goods);
            request = "http://localhost:4567/buy";
            URL url = new URL(request) ;
            String response = postConnetct(url,listToJson) ;
            if (!response.equals("Don't OK")){
                return response ;
            }
        } catch (IOException e) {
            LOGGER.debug(e.getMessage(),e);
        }
        return "NO" ;
    }

    public String addGood(ArrayList<Good> goodList) {
        try {
            String goodToJson = new ObjectMapper().writeValueAsString(goodList);

            String request = "http://localhost:4567/add";
            URL url = new URL(request) ;
            String response = postConnetct(url,goodToJson) ;
            if (!response.equals("Don't OK")){
                return response ;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return "Добавление товара НЕ получилось!";
    }

    public String addAccount(Account account){
        String responce = "";
        try {
            String addingAccount = new ObjectMapper().writeValueAsString(account);
            String request = "http://localhost:4567/addAccount";
            URL url = new URL(request) ;
            responce =postConnetct(url,addingAccount) ;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return String.valueOf(responce);
    }

    public String postAccount(Account account){
        try {
            String postAccount = new ObjectMapper().writeValueAsString(account);
            String request = "http://localhost:4567/login";
            URL url = new URL(request) ;
            String response = postConnetct(url,postAccount) ;
            if (!response.equals("")){
                return response ;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return "Нет соединения";
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
            Good.ResetForms resetForms = new Good.ResetForms() ;
            resetForms.restartFomrs();
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
            Good.ResetForms resetForms = new Good.ResetForms() ;
            LOGGER.error(e.getMessage(),e);
            resetForms.restartFomrs();
        }
        return String.valueOf(response) ;
    }

}