package sample.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Autorization.Account;
import sample.FormBuyOrEdit.Good;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Controller {
    private static String request ;
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    public Controller(){} ;
    public Controller(String request){
        this.request = request ;
    }


    public ArrayList<Good> getAll(){
        try {
            URL url = new URL(request+"/getAll") ;
            StringBuffer response = GetPostController.getConnect(url) ;
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
            responce = GetPostController.postConnetct(listToJson,url) ;
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
            responce = GetPostController.postConnetct(goodToJson,url) ;
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
            responce =GetPostController.postConnetct(addingAccount,url) ;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return String.valueOf(responce);
    }

    public String postAccount(Account account){
        try {
            String requestUrl = request+"/login" ;
            String postAccount = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(account);
            URL url = new URL(requestUrl) ;
            String response = GetPostController.postConnetct(postAccount,url) ;
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
            response = GetPostController.postConnetct(pingClient,url) ;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return response;
    }
}
