package Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Autorization {
    public static final Logger LOGGER = LoggerFactory.getLogger(Autorization.class);

    public static String coding (String pass){
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
