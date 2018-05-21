package app;

public class Account {
    private String name ;
    private String password ;

    Account (String name, String password){
        this.name = name ;
        this.password = password ;
    }

    Account (){}


    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password ;
    }

    public String getName(){
        return name ;
    }
}
