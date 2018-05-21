package app;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static app.Controller.LOGGER;

public class Autorization {
    public String Coding (String pass){
        String hash = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(pass.getBytes("utf-8"));
            hash = new BigInteger(1, crypt.digest()).toString(16);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return hash;
    }
}
