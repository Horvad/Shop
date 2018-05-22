import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ShopDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(ShopDao.class);

    ObjectMapper mapper = new ObjectMapper() ;


    public ArrayList<Good> getAll() {
        ArrayList<Good> getAllGoods = new ArrayList<Good>() ;
        try {
            getAllGoods = mapper.readValue(new File("Goods.json"), new TypeReference<ArrayList<Good>>(){}) ;
        } catch (IOException e) {
           getAllGoods = new ArrayList<>() ;
           LOGGER.error(e.getMessage(),e);
        }
        LOGGER.debug("get goods:"+getAllGoods.toString());
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
            LOGGER.error(errorFindBuyName.getMessage());
            throw  errorFindBuyName;
        }
        LOGGER.debug("find name users="+name+" finding object of ArrayList="+String.valueOf(good));
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
            LOGGER.error(errorDeleteBuyName.getMessage());
            throw errorDeleteBuyName ;
        }
        LOGGER.debug("delete names="+deleteByNameGoods.toString());
        saveAll(deleteByNameGoods);
    } ;

    //------------------------------------------------------------------------------------------------------------------

    public void saveAll (ArrayList <Good> arraySaveAllGoods){
        try {
            mapper.writeValue(new File("Goods.json"), arraySaveAllGoods);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(),e);
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
        LOGGER.debug("ArrayList saves="+arrayUpdate.toString());
        saveAll(arrayUpdate);
    }

    public ArrayList<Account> getAccount (){
        ArrayList<Account> accounts = new ArrayList<>() ;
        try {
            accounts = mapper.readValue(new File("Account.json"),new TypeReference<ArrayList<Account>>(){}) ;
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(),e);
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
            LOGGER.debug("add add account="+String.valueOf(account));
        } else {
            IllegalArgumentException er = new IllegalArgumentException("Аккаунт существует") ;
            LOGGER.error(er.getMessage());
            throw er ;
        }
        try {
            mapper.writeValue(new File("Account.json"),accounts) ;
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(),e);
        };
    }

}
