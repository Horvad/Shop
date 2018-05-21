package app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ShopDao {
    ObjectMapper mapper = new ObjectMapper() ;


    public ArrayList<Good> getAll() {
        ArrayList<Good> getAllGoods = new ArrayList<Good>() ;
        try {
            getAllGoods = mapper.readValue(new File("Goods.json"), new TypeReference<ArrayList<Good>>(){}) ;
        } catch (IOException e) {
           getAllGoods = new ArrayList<>() ;
        }
        return getAllGoods ;
    } ;

    //------------------------------------------------------------------------------------------------------------------

    public Good findBuyName(String name){
        ArrayList <Good> findBuyNameGoods = new ArrayList<Good>() ;
        findBuyNameGoods = getAll() ;
        Good good = null ;
        for(Good tmpgood: findBuyNameGoods){
            if (tmpgood.name.equals(name)){
                good = tmpgood;
            }
        }
        if (good==null){
            IllegalArgumentException errorFindBuyName = new IllegalArgumentException("Товар не найден") ;
            throw  errorFindBuyName;
        }
        return good ;
    } ;

    //------------------------------------------------------------------------------------------------------------------

    public void deleteByName (String name){
        ArrayList<Good> deleteByNameGoods = new ArrayList<Good>() ;
        deleteByNameGoods = getAll() ;
        boolean errFindDeleteName = true ;
        for (Good good: deleteByNameGoods){
            if (good.name.equals(name)){
                deleteByNameGoods.remove(good) ;
                errFindDeleteName = false ;
                break;
            }
        }
        saveAll(deleteByNameGoods);
        if (errFindDeleteName){
            IllegalArgumentException errorDeleteBuyName = new IllegalArgumentException("Товар для удаления не найден") ;
            throw errorDeleteBuyName ;
        }
        saveAll(deleteByNameGoods);
    } ;

    //------------------------------------------------------------------------------------------------------------------

    public void saveAll (ArrayList <Good> arraySaveAllGoods){
        try {
            mapper.writeValue(new File("Goods.json"), arraySaveAllGoods);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    public void updateAll (Good good) {
        ArrayList<Good> arrayUpdate = new ArrayList<Good>() ;
        arrayUpdate = getAll() ;
        for (Good updateGood : arrayUpdate){
            if (updateGood.name.equals(good.name)){
                updateGood.count = good.count ;
                updateGood.price = good.price ;
            }
        }
        saveAll(arrayUpdate);
    }

    public ArrayList<Account> getAccount (){
        ArrayList<Account> accounts = new ArrayList<>() ;
        try {
            accounts = mapper.readValue(new File("Account.json"),new TypeReference<ArrayList<Account>>(){}) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts ;
    }

    public void addAccount(Account account){
        Autorization autorization = new Autorization() ;
        String password = account.getPassword();
        password = autorization.Coding(password) ;
        account.setPassword(password);
        ArrayList<Account> accounts = new ArrayList<>() ;
        accounts = getAccount() ;
        System.out.print(accounts.get(0).getName());
        System.out.print(account.getName());
        boolean newAccount = true ;
        for (int i=0 ; i<accounts.size(); i++){
            if (accounts.get(i).getName().equals(account.getName())){
                newAccount = false ;
            }
        }
        if (accounts.size()==0||newAccount==true){
            accounts.add(account) ;
        }
        try {
            mapper.writeValue(new File("Account.json"),accounts) ;
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

}
