package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.FormAndBuyGoodsOrEditGoods.CreaterForm;


public class Main extends Application {

    private GridPane gridPane = new GridPane() ;

    @Override
    public void start(Stage firstStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        firstStage.setTitle("Shop");

        CreaterForm createrForm =new CreaterForm(gridPane) ;
        gridPane = createrForm.getGridPane() ;

        Scene scene = new Scene(gridPane,500,500) ;
        firstStage.setScene(scene);
        firstStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
