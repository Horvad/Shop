package sample.FormAndBuyGoodsOrEditGoods;

import javafx.scene.layout.GridPane;

public class CreaterForm {
    public GridPane gridPane = new GridPane() ;
    private String title ;

    public CreaterForm(GridPane gridPane){
        this.gridPane = gridPane ;
    }

    private void create(){
        Autorization autorization = new Autorization(gridPane) ;
        autorization.formAutorization() ;
        gridPane = autorization.getGridPane() ;
        title = autorization.formAutorization() ;
        System.out.print(title);

    }
    public GridPane getGridPane() {
        create();
        return gridPane;
    }
}
