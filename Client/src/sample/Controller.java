package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            StringBuffer response = getConnect(request) ;
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

    public boolean userLogin (String user,String password){
        boolean title = false ;
        if (user.equals("root")){
            title = true ;
        } else {
            title = false ;
        }
        LOGGER.debug("user root or don't root login "+String.valueOf(title));
        return title ;
    }

    public String buy(ArrayList<Good> goods){
        try {
            String listToJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(goods);
            request = "http://localhost:4567/buy";
            String response = postConnetct(request,listToJson) ;
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
            String response = postConnetct(request,goodToJson) ;
            if (!response.equals("Don't OK")){
                return response ;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return "Добавление товара НЕ получилось!";
    }

    public String addAccount(Account account){
        try {
            String addingAccount = new ObjectMapper().writeValueAsString(account);
            String request = "http://localhost:4567/addAccount";
            String response =postConnetct(request,addingAccount) ;
            if (!response.equals("Don't OK")){
                return response ;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return "false" ;
    }

    public String postAccount(Account account){
        try {
            String postAccount = new ObjectMapper().writeValueAsString(account);
            String request = "http://localhost:4567/login";
            String response = postConnetct(request,postAccount) ;
            if (!response.equals("Don't OK")){
                return response ;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return "Нет соединения";
    }

    public StringBuffer getConnect (String request){
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(request);

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
        }
        return  response;
    }

    public String postConnetct(String request, String object){
        try {
            URL url = new URL(request);

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
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return String.valueOf(response) ;
        } catch (IOException e){
            LOGGER.error(e.getMessage(),e);
        }
        return "Don't OK" ;
    }

}