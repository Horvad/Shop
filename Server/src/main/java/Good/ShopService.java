package Good;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ShopService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);

    ShopDao shopDao = new ShopDao();
    ObjectMapper mapper = new ObjectMapper() ;
    //------------------------------------------------------------------------------------------------------------------

    public String getAll() throws JsonProcessingException {
        ArrayList<Good> getAllGoodsArrayListServise = new ArrayList<Good>();
        getAllGoodsArrayListServise = shopDao.getAll();
        String returnList = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getAllGoodsArrayListServise) ;
        LOGGER.debug("Result getAll="+returnList);
        return returnList ;
    };

    //------------------------------------------------------------------------------------------------------------------

    public void addGoods(List<Good> goods) {
        ArrayList<Good> saveGoodsArray = new ArrayList<Good>();
        synchronized (saveGoodsArray) {
            saveGoodsArray = shopDao.getAll();
            for (Good newGoods : goods) {
                boolean newGoodBoolean = true;
                for (Good saveGood : saveGoodsArray) {
                    if (saveGood.getName().equals(newGoods.getName())) {
                        if (newGoods.getCount()!=0){
                            saveGood.setCount(newGoods.getCount()) ;
                        } else {
                            saveGoodsArray.remove(saveGood) ;
                        }
                        saveGood.setPrice(newGoods.getPrice());
                        newGoodBoolean = false;

                    }
                }
                if (newGoodBoolean) {
                    saveGoodsArray.add(newGoods);
                }
            }
            shopDao.saveAll(saveGoodsArray);
            LOGGER.debug("result add Googs="+saveGoodsArray.toString());
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    public void buyGoods(List<Good> arrayGoods) {
        int userBuyMoney = 0 ;
        Good findGood = null;
       // synchronized (findGood) {
            for (Good buyGood : arrayGoods) {
                findGood = shopDao.findBuyName(buyGood.getName());
                if (findGood == null) {
                    NullPointerException errorFindGood = new NullPointerException("Такого товара нет в магазине");
                    LOGGER.error(errorFindGood.getMessage());
                    throw errorFindGood;
                } else {
                    if (findGood.getCount() == buyGood.getCount()) {
                        userBuyMoney = userBuyMoney + buyGood.getCount() * findGood.getPrice();
                        shopDao.deleteByName(findGood.getName());
                    }
                    if (findGood.getCount() > buyGood.getCount()) {
                        findGood.setCount(findGood.getCount() - buyGood.getCount());
                        userBuyMoney = userBuyMoney + findGood.getPrice() * buyGood.getCount();
                        shopDao.updateAll(findGood);
                    } else {
                        IllegalArgumentException errorUserBuyCount = new IllegalArgumentException("Такого колличества товара нет в магазине");
                        LOGGER.error(errorUserBuyCount.getMessage());
                        throw errorUserBuyCount;
                    }
                }
                LOGGER.debug("resul buy=" + buyGood.getName() + " " + buyGood.getCount()+ " result goods to shop=" + findGood.getName() + " " + findGood.getCount());          //  }
            }
        }
    }