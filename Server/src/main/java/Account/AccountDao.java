package Account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AccountDao {
    private ObjectMapper mapper = new ObjectMapper() ;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDao.class);

    public ArrayList<Account> getAccount (){
        ArrayList<Account> accounts = new ArrayList<>() ;
        try {
            accounts = mapper.readValue(new File("Account.json"),new TypeReference<ArrayList<Account>>(){}) ;
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(),e);
        }
        return accounts ;
    }

    public String addAccount(Account account){
        Autorization autorization = new Autorization() ;
        String password = account.getPassword();
        password = autorization.coding(password) ;
        account.setPassword(password);
        ArrayList<Account> accounts = new ArrayList<>() ;
        accounts = getAccount() ;
        System.out.print(accounts.get(0).getName());
        System.out.print(account.getName());
        boolean newAccount = true ;
        String addingAcount = "Don't OK" ;
        for (int i=0 ; i<accounts.size(); i++){
            if (accounts.get(i).getName().equals(account.getName())){
                newAccount = false ;
            }
        }
        if (accounts.size()==0||newAccount==true){
            accounts.add(account) ;
            LOGGER.debug("add account="+String.valueOf(account));
            addingAcount = "OK" ;
        } else {
            addingAcount = "Account is been" ;
            IllegalArgumentException er = new IllegalArgumentException("Аккаунт существует") ;
            LOGGER.error(er.getMessage());
        }
        try {
            mapper.writeValue(new File("Account.json"),accounts) ;
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(),e);
        };
        return addingAcount ;
    }
}
