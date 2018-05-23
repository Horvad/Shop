package sample.FormBuyOrEdit;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Controller;

import java.util.ArrayList;

public class FormBuy {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormBuy.class);

    private Button buttonAddToCard = new Button("Добавить в корзину") ;
    private Button buttonRemoveGood = new Button("Удалить") ;
    private Button buttonBuy = new Button("Купить") ;
    private ArrayList<Good>goodsBuy = new ArrayList<>() ;

    private static Controller controller = new Controller() ;
    private static ArrayList<Good> goods = controller.getAll() ;
    private static boolean newCardForm = true;
    private static ArrayList<Spinner> spinnersArrayList = new ArrayList<>() ; // спиннеры товаров
    private static ArrayList<Spinner> spinnersCard = new ArrayList<>(); //спинеры корзины
    private static ArrayList<SpinnerValueFactory> spinnerValueFactoriesArrayList = new ArrayList<>() ;

    public GridPane formGoodsWithoutSpinners (GridPane gridPane, ArrayList<Good> goodsForm){

        int countString = 0 ;
        gridPane.getChildren().clear();

        Label labelName = new Label("Название продукта") ;
        gridPane.add(labelName,0,countString,1,1);

        Label labelCount = new Label("Колличество") ;
        gridPane.add(labelCount,1,countString,1,1);

        Label labelPrice = new Label("Цена продукта") ;
        gridPane.add(labelPrice,2,countString,1,1);
        form(goodsForm,gridPane,countString+1);
        return gridPane;
    }

    public void form (ArrayList<Good> goodsForm, GridPane gridPane, int countString){
        for (int i=0 ; i<goodsForm.size(); i++){
            goods = controller.getAll() ;
            LOGGER.debug("goods whith server: "+goods.toString());
            gridPane.add(new Label(String.valueOf(goodsForm.get(i).name)),0,countString,1,1);
            gridPane.add(new Label(String.valueOf(goodsForm.get(i).count)),1,countString,1,1);
            gridPane.add(new Label(String.valueOf(goodsForm.get(i).price)),2,countString,1,1);
            countString++ ;
        }
    }

    public GridPane formGoods(GridPane gridPane){
        if (spinnersArrayList.size()!=0) {
            spinnersArrayList.clear();
            spinnerValueFactoriesArrayList.clear();
        }
        gridPane.getChildren().clear();
        gridPane = formGoodsWithoutSpinners(gridPane, goods) ;
        for (int i = 0; i<goods.size(); i++){
            boolean newSpinner = true ;
            if (goodsBuy.size()!=0){
                for (Good good : goodsBuy){
                    if (goods.get(i).name.equals(good.name)){
                      spinnerValueFactoriesArrayList.add(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,goods.get(i).count-good.count,1)) ;
                      newSpinner = false ;
                      break;
                    }
                }
            }
            if (newSpinner){
                spinnerValueFactoriesArrayList.add(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,goods.get(i).count,1)) ;
            }
            spinnerValueFactoriesArrayList.get(i).setValue(0);
            spinnersArrayList.add(new Spinner(spinnerValueFactoriesArrayList.get(i))) ;
            gridPane.add(spinnersArrayList.get(i),3,i+1,1,1);
        }
        if (newCardForm) {
            gridPane.add(buttonAddToCard,0,goods.size()+2,1,1);
            formGoodsAndButtooAdd(gridPane) ;
        }
        return gridPane ;
    }

    public GridPane formGoodsAndButtooAdd(GridPane gridPane){
        buttonAddToCard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                formCard(gridPane) ;
            }
        });

        buttonRemoveGood.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i=0; i<goodsBuy.size(); i++){
                    if ((int)spinnersCard.get(i).getValue()!=0){
                        boolean newGood = true ;
                        for (int j=0; j<goods.size(); j++){
                            if (goods.get(j).name.equals(goodsBuy.get(i).name)){
                                newGood = false ;
                                goods.get(j).count=goods.get(j).count+(int)spinnersCard.get(i).getValue() ;
                                break;
                            }
                        }
                        if (newGood){
                            goods.add(new Good(goodsBuy.get(i).name,(int)spinnersCard.get(i).getValue(),goodsBuy.get(i).price));
                        } ;
                        goodsBuy.get(i).count = goodsBuy.get(i).count-(int)spinnersCard.get(i).getValue() ;
                        if (goodsBuy.get(i).count==0){goodsBuy.remove(i);}
                    }

                }
                gridPane.getChildren().clear();
                if (goodsBuy.size()==0){newCardForm=true ; formGoods(gridPane);} else {formCard(gridPane);}
                LOGGER.debug("goods buy:"+goodsBuy.toString());
            }
        });

        buttonBuy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newCardForm = true ;
                String title = controller.buy(goodsBuy);
                if (title.equals("OK")) {
                    goodsBuy.clear();
                    formGoods(gridPane) ;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Покупка");
                    alert.setHeaderText(null);
                    alert.setContentText("Товары переданны на покупку в магазин, с вами свяжутся");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText("Покупка не удалась");
                    alert.show();
                }
            }
        });
        return gridPane ;
    }

    public GridPane formCard (GridPane gridPane){
        newCardForm = false ;
        gridPane.getChildren().clear();
        int countString = goods.size()+1 ;
        spinnersCard.clear();
        boolean spinner0 = true ;
        for (int i = 0; i<goods.size(); i++){
            boolean newGood = true ;
            int count = (int) spinnersArrayList.get(i).getValue() ;
            if (count!=0){
                spinner0 = false ;
                for (Good good : goodsBuy){
                    if (goods.get(i).name.equals(good.name)){
                        newGood = false ;
                        good.count = good.count+(Integer)spinnersArrayList.get(i).getValue() ;
                        break;
                    }
                }
                if (newGood) {goodsBuy.add(new Good(goods.get(i).name,(int)(spinnersArrayList.get(i).getValue()),goods.get(i).price)) ;}
                goods.get(i).count = goods.get(i).count-(Integer)spinnersArrayList.get(i).getValue() ;
                if (goods.get(i).count==0){goods.remove(i);} ;
            }
        }
        formGoods(gridPane) ;
        gridPane.add(buttonAddToCard,0,countString,1,1);
        if (!spinner0||goodsBuy.size()!=0){
            gridPane.add(buttonRemoveGood,1,countString,1,1);
            countString++ ;
            form(goodsBuy,gridPane,countString);
            countString = countString+goodsBuy.size() ;
            gridPane.add(buttonBuy,0,countString,1,1);
        } else {
            gridPane.add(buttonAddToCard,0,countString,1,1); ;
        }
        if (spinnersCard.size()!=0){
            spinnersCard.clear();
        }
        ArrayList<SpinnerValueFactory> spinnerValueFactoryCart = new ArrayList<>() ;
        countString=countString-goodsBuy.size() ;
        for (int i = 0; i<goodsBuy.size(); i++){
            spinnerValueFactoryCart.add(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,goodsBuy.get(i).count,1)) ;
            spinnerValueFactoryCart.get(i).setValue(0);
            spinnersCard.add(new Spinner(spinnerValueFactoryCart.get(i))) ;
            gridPane.add(spinnersCard.get(i),3,countString,1,1);
            countString++;
        }
        return gridPane;
    }
}
