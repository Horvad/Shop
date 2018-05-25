package sample.FormBuyOrEdit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Autorization.Autorization;
import sample.Controller.Controller;
import sample.Allert ;

import java.util.ArrayList;

public class FormAddNewGood {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormAddNewGood.class);

    private Controller controller = new Controller() ;
    private ArrayList<Spinner> spinners = new ArrayList<>() ;
    private Button buttonAdd = new Button("Добавить товар") ;
    private Button buttonRemove = new Button("Удалить товар") ;
    private Button buttonAddNewGood = new Button("Добавить новый товар") ;
    private Button buttonClose = new Button("К авторизации") ;
    private Button buttonConnect = new Button("К подключению") ;

    private TextField textFieldName = new TextField() ;
    private TextField textFieldCount = new TextField() ;
    private TextField textFieldPrice = new TextField() ;


    public GridPane formBuy(GridPane gridPane){
        FormBuy formBuy = new FormBuy() ;
        ArrayList<SpinnerValueFactory> spinnerValueFactories = new ArrayList<>() ;
        ArrayList<Good> goods = controller.getAll() ;
        gridPane = formBuy.formGoodsWithoutSpinners(gridPane,goods) ;
        for (int i=0 ; i<goods.size(); i++){
            spinnerValueFactories.add(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000000,1)) ;
            spinnerValueFactories.get(i).setValue(0);
            spinners.add(new Spinner(spinnerValueFactories.get(i))) ;
            gridPane.add(spinners.get(i),3,i+1,1,1);
        }
        int countString = goods.size()+1 ;
        gridPane.add(buttonAdd,0,countString,1,1);
        gridPane.add(buttonRemove,1,countString,1,1);
        countString++ ;

        gridPane.add(new Label("Название товара"),0,countString,1,1);
        gridPane.add(new Label("Колличество"),1,countString,1,1);
        gridPane.add(new Label("Цена"),2,countString,1,1);
        countString++ ;

        gridPane.add(textFieldName,0,countString,1,1);
        gridPane.add(textFieldCount,1,countString,1,1);
        gridPane.add(textFieldPrice,2,countString,1,1);
        countString++ ;

        gridPane.add(buttonAddNewGood,0,countString,1,1);
        gridPane.add(buttonClose,1,countString,1,1);
        gridPane = buttonsOnClick(gridPane) ;
        return gridPane ;
    }

    private GridPane buttonsOnClick(GridPane gridPane){
        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Good> goods = controller.getAll() ;
                for (int i=0; i<goods.size(); i++){
                    if ((int)spinners.get(i).getValue()!=0){
                        goods.get(i).setCount(goods.get(i).getCount()+(int)spinners.get(i).getValue()) ;
                    }
                }
                String title = controller.addGood(goods) ;
                if (!title.equals("OK")){
                    Allert alert = new Allert() ;
                    alert.allerts("Удаление не произведено");
                }
                LOGGER.debug("goods+goodsadd:"+goods.toString());
                gridPane.getChildren().clear();
                formBuy(gridPane) ;
            }
        });

        buttonRemove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Good> goods  = addGoods() ;
                String title = controller.addGood(goods) ;
                if (goods.size()!=0) {
                    if (!title.equals("OK")) {
                        Allert alert = new Allert() ;
                        alert.allerts("Удаление не произведено");
                    }
                }
                gridPane.getChildren().clear();
                LOGGER.debug("goods-goodsremove:"+goods.toString());
                formBuy(gridPane) ;
            }
        });

        buttonAddNewGood.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Good> goods = addGoods() ;
                Good good = new Good(textFieldName.getText(),Integer.valueOf(textFieldCount.getText()),Integer.valueOf(textFieldPrice.getText())) ;
                goods.add(good) ;
                String title = controller.addGood(goods) ;
                if (!title.equals("OK")){
                    Allert alert = new Allert() ;
                    alert.allerts("Добовление не произведено");
                }
                gridPane.getChildren().clear();
                LOGGER.debug("addGood:"+String.valueOf(good));
                formBuy(gridPane) ;
            }
        });
        buttonClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gridPane.getChildren().clear();
                Autorization autorization = new Autorization(gridPane) ;
                autorization.formAutorization() ;
            }
        });
        buttonConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gridPane.getChildren().clear();
                Autorization autorization = new Autorization(gridPane) ;
                autorization.formConnetct();
            }
        });
        return gridPane ;
    }

    private ArrayList<Good> addGoods (){
        ArrayList<Good> goods = controller.getAll() ;
        for (int i=0; i<goods.size(); i++){
            if ((int)spinners.get(i).getValue()!=0){
                if ((int)spinners.get(i).getValue()<=goods.get(i).getCount()){
                    goods.get(i).setCount(goods.get(i).getCount()-(int)spinners.get(i).getValue()) ;
                }else {
                    Allert allert = new Allert() ;
                    allert.allerts("Колличество удаляемых товаров не может быть меньше сущусвующих");
                    goods.clear();
                }
            }
        }
        return goods ;
    }
}
