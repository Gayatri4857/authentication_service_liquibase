package org.dnyanyog.encryption;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

  private static final String SECRET_KEY_BASE64 = "KHX2ZOeq44A2skh3lvsVn/FmOXc6Ysh5gz4GMQTz5II=";
  private static final String ALGORITHM = "AES";

  private static SecretKey secretKey;
  private static Cipher cipher;

  static {
    try {
      byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY_BASE64);
      secretKey = new SecretKeySpec(decodedKey, ALGORITHM);

      cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
      e.printStackTrace();
    }
  }

  public String encrypt(String data) throws Exception {
    byte[] encryptedData = cipher.doFinal(data.getBytes());
    return Base64.getEncoder().encodeToString(encryptedData);
  }

  public static String decrypt(String encryptedData) throws Exception {
    byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
    return new String(decryptedData, StandardCharsets.UTF_8);
  }

  public static SecretKey generateAesKey() {
    try {
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(256);
      return keyGenerator.generateKey();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error generating AES key", e);
    }
  }
}
