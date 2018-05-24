package Good;

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
            if (tmpgood.getName().equals(name)){
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
            if (good.getName().equals(name)){
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
            if (updateGood.getName().equals(good.getName())){
                updateGood.setCount(good.getCount()) ;
                updateGood.setPrice(good.getPrice()) ;
            }
        }
        LOGGER.debug("ArrayList saves="+arrayUpdate.toString());
        saveAll(arrayUpdate);
    }



}
