package sample.Autorization;

import javafx.scene.control.TextField;

public class Account {
    private String name ;
    private String password ;

    public Account(String name, String password) {
        this.name = name ;
        this.password = password ;
    }

    public Account(){} ;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
