package sample.Autorization;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Allert;
import sample.Controller;
import sample.FormBuyOrEdit.FormAddNewGood;
import sample.FormBuyOrEdit.FormBuy;


public class Autorization {
    private GridPane gridPane = new GridPane() ;
    private static String title ;
    private TextField textFieldAccout = new TextField() ;
    private PasswordField passwordField = new PasswordField() ;
    private Button buttonLogin = new Button("Войти") ;
    private Button buttonAddUser = new Button("Добавить") ;
    private static final Logger LOGGER = LoggerFactory.getLogger(Autorization.class);


    public Autorization(GridPane gridPane){
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
                Allert allert = new Allert() ;
                if (title.equals("true")) {
                    FormBuy formBuy = new FormBuy();
                    gridPane = formBuy.formGoods(gridPane);
                }
                if (title.equals("false")){
                    allert.allerts("Не верно введаны данные");
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
                Allert allert = new Allert() ;
                if (title.equals("Account is been")){
                    allert.allerts("Аккаунт существует");
                }
                if (title.equals("OK")){
                    Allert allerts = new Allert("OK") ;
                    allerts.allerts("Пользователь добавлен");
                }
            }
        });
        return title ;
    }

    private String autorization(String title){
        Controller controller = new Controller() ;
        Account account = new Account(String.valueOf(textFieldAccout.getText()),String.valueOf(passwordField.getText())) ;
        title = controller.postAccount(account) ;
        LOGGER.debug("autorization="+String.valueOf(account)+" title"+title);
        return title ;
    }

    private String addUser(String title){
        Controller controller = new Controller() ;
        Account account = new Account(String.valueOf(textFieldAccout.getText()),String.valueOf(passwordField.getText())) ;
        title = controller.addAccount(account) ;
        LOGGER.debug("Add account="+String.valueOf(account)+" title"+title);
        return title ;
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
