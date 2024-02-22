package srcs.securite;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PasswordStore {
    private final String nomAlgoHash;
    static Map<String, String> passwordStore = new HashMap<>();


    public PasswordStore(String nomAlgoHash) {
        this.nomAlgoHash = nomAlgoHash;
    }

    public void storePassword(String user, String passwd) throws NoSuchAlgorithmException {
        String hashedPassword = toHash(passwd);
        passwordStore.put(user, hashedPassword);

    }

    public boolean checkPassword(String user, String passwd) throws NoSuchAlgorithmException {
        String hashedPassword = toHash(passwd);
        return hashedPassword.equals(passwordStore.get(user));
    }

    public boolean checkPassword2(String user, String passwd) throws NoSuchAlgorithmException {
        String password = passwordStore.get(user);

        System.out.println(password +"        3");

        return password.equals(passwd);
    }

    String toHash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(nomAlgoHash);
        byte[] hash = messageDigest.digest(password.getBytes());
        return hashToHex(hash);
    }

    String hashToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void printAllPasswords() {
        for (Map.Entry<String, String> entry : passwordStore.entrySet()) {
            System.out.println("User: " + entry.getKey() + ", Password Hash: " + entry.getValue());
        }
    }

}
