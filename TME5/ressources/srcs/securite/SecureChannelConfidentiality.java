package srcs.securite;

import javax.crypto.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SecureChannelConfidentiality extends ChannelDecorator {
    private static SecretKey secretKey = null;
    private String nomAlgo;
    private int tailleCle;
    private Authentication authentication;

    public SecureChannelConfidentiality(Channel channel, Authentication authentication, String nomAlgo, int tailleCle) throws NoSuchAlgorithmException {
        super(channel);
        this.nomAlgo = nomAlgo;
        this.tailleCle = tailleCle;
        this.authentication = authentication;

        KeyGenerator keyGen = KeyGenerator.getInstance(nomAlgo);
        keyGen.init(tailleCle);
        secretKey = keyGen.generateKey();

    }

    public SecretKey getSecretKey(){
        return secretKey;
    }
    @Override
    public void send(byte[] data) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance(nomAlgo);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data);
            super.send(encryptedData);
        } catch (GeneralSecurityException e) {
            throw new IOException("", e);
        }
    }

    @Override
    public byte[] recv() throws IOException, ClassNotFoundException {
        byte[] encryptedData = super.recv();
        try {
            Cipher cipher = Cipher.getInstance(nomAlgo);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(encryptedData);
        } catch (GeneralSecurityException e) {
            throw new IOException("", e);
        }
    }
}
