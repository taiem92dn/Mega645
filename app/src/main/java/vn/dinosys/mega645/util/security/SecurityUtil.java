package vn.dinosys.mega645.util.security;

import java.security.MessageDigest;

/**
 * Created by htsi.
 * Since: 2/18/16 on 2:26 PM
 * Project: DinoHR_Android
 */
public class SecurityUtil {
    /**
     * generate a sha256 string from base string
     *
     * @param base input string
     * @return sha256 generated string
     */
    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
