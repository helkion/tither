/**
 * 
 */
package nz.hmp.tither.utils;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author helcio
 *
 */
public interface CryptoUtils extends Serializable {
	
	public static String base64(String texto) {
        return Base64
        		.getEncoder()
        		.encodeToString(
        				texto.getBytes());
    }

    public static String md5(String texto) {
        try {
            return bytesToHex(MessageDigest.getInstance("MD5")
            		.digest(texto.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            return "";
        }
    }
    
    public static String bcrypt(String texto) {
        return new BCryptPasswordEncoder().encode(texto);
        
    }
    
    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
