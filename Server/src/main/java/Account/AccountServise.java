package Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class AccountServise {
    private static boolean accountBoolean  = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServise.class);

    public String addAccount(Account account){
        AccountDao accountDao = new AccountDao() ;
        LOGGER.debug("add account "+account.getName());
        String addingAccount = accountDao.addAccount(account);
        return addingAccount ;
    }

    public String postAccount (Account account){
        accountBoolean = false ;
        AccountDao accountDao = new AccountDao() ;
        ArrayList<Account> accounts = accountDao.getAccount() ;
        Autorization autorization = new Autorization() ;
        String password = account.getPassword() ;
        password = autorization.coding(password) ;
        String returName = "false";
        account.setPassword(password);
        for (Account findAccount: accounts){
            if (findAccount.getPassword().equals(account.getPassword())&&findAccount.getName().equals(account.getName())){
                if (account.getName().equals("root")){
                    returName = "root" ;
                    break;
                } else {
                    returName = "true" ;
                    break;
                }
            } else {
                returName = "false" ;
            }
        }
        LOGGER.debug("post account"+account.getName()+"rigts="+returName);
        return returName ;
    }
}
