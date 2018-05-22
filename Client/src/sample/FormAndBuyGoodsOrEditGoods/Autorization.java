package sample.FormAndBuyGoodsOrEditGoods;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert ;
import sample.Account;
import sample.Controller;


public class Autorization {
    private GridPane gridPane = new GridPane() ;
    private static String title ;
    private TextField textFieldAccout = new TextField() ;
    private PasswordField passwordField = new PasswordField() ;
    public Button buttonLogin = new Button("Войти") ;
    public Button buttonAddUser = new Button("Добавить") ;

    Autorization(GridPane gridPane){
        this.gridPane = gridPane ;
    }

    public String formAutorization(){
        title = "Not connect" ;
        gridPane.getChildren().clear();
        Label labelAccount = new Label("Аккаунт") ;
        gridPane.add(labelAccount,0,0,1,1);
        gridPane.add(textFieldAccout,0,1,1,1);

        Label labelPassword = new Label("Пароль") ;
        gridPane.add(labelPassword,1,0,1,1);
        gridPane.add(passwordField,1,1,1,1);

        gridPane.add(buttonLogin,0,2,1,1);
        gridPane.add(buttonAddUser,1,2,1,1);
        buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                title = autorization(title) ;
                if (title.equals("Нет соединения")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText(title);
                    alert.show();
                }
                if (title.equals("true")) {
                    FormBuy formBuy = new FormBuy();
                    gridPane = formBuy.formGoods(gridPane);
                }
                if (title.equals("false")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText("Не верно введаны данные");
                    alert.show();
                }
                if (title.equals("root")){
                    FormAddNewGood formAddNewGood = new FormAddNewGood() ;
                    formAddNewGood.formBuy(gridPane) ;
                }
            }
        });

        buttonAddUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                title = addUser(title) ;
                Account account = new Account(String.valueOf(textFieldAccout.getText()),String.valueOf(passwordField.getText())) ;
                Controller controller = new Controller() ;
                title = controller.addAccount(account) ;
                if (title.equals("false")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText("Пользователь не добавлен");
                    alert.show();
                }
                if (title.equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText("Пользователь добавлен");
                    alert.show();
                }
            }
        });
        return title ;
    }

    public String autorization(String title){
        Controller controller = new Controller() ;
        Account account = new Account(String.valueOf(textFieldAccout.getText()),String.valueOf(passwordField.getText())) ;
        title = controller.postAccount(account) ;
        return title ;
    }

    public String addUser(String title){
        Controller controller = new Controller() ;
        Account account = new Account(String.valueOf(textFieldAccout.getText()),String.valueOf(passwordField.getText())) ;
        title = controller.addAccount(account) ;
        title = "fsdada" ;
        return title ;
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
