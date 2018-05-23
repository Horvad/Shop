package sample.FormBuyOrEdit;

import javafx.scene.layout.GridPane;
import sample.Autorization.Autorization;

public class Good {
    public String name ;
    public int count ;
    public int price ;

    public Good(){}  ;

    public Good(String name, int count, int price) {
        this.name = name;
        this.count = count ;
        this.price = price ;
    }

    public static class ResetForms {
        private GridPane gridPane = new GridPane() ;

        public void restartFomrs (){
            Autorization autorization = new Autorization(gridPane) ;
        }
    }
}
