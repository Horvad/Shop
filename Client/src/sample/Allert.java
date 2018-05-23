package sample;

import javafx.scene.control.Alert;

public class Allert {
    private String title = "Ошибка" ;
    public Allert(){}
    public Allert(String title){
        this.title = title ;
    }
    public void allerts (String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.show();
    }
}
