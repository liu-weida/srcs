package srcs.securite;

import javax.crypto.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SecureChannelConfidentiality extends ChannelDecorator {
    private static SecretKey secretKey = null;
    private String nomAlgo;
    private int tailleCle;
    private Authentication authentication;

    private Channel channel;

    SecretKey newnewSecreKey;

    //这部分应该没啥问题
    public SecureChannelConfidentiality(Channel channel, Authentication authentication, String nomAlgo, int tailleCle) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, IOException, ClassNotFoundException {
        super(channel);
        this.nomAlgo = nomAlgo;
        this.tailleCle = tailleCle;
        this.authentication = authentication;

        // 生成对称密钥
        KeyGenerator keyGen = KeyGenerator.getInstance(nomAlgo);
        keyGen.init(tailleCle);
        SecretKey newSecretKey = keyGen.generateKey();

        //用非对称密钥加密密钥
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.WRAP_MODE, authentication.getRemoteCertif().getPublicKey());
        byte[] encryptedKey = cipher.wrap(newSecretKey);

        //发送
        channel.send(encryptedKey);

        //接受
        encryptedKey = channel.recv();

        //用非对称密钥解密密钥
        cipher.init(Cipher.UNWRAP_MODE, authentication.getLocalKeys().getPrivate());

        newnewSecreKey = (SecretKey) cipher.unwrap(encryptedKey, nomAlgo, Cipher.SECRET_KEY);
        
        setSercreKey();
    }

    public synchronized void setSercreKey(){
        if (secretKey == null) {
            System.out.println("123123");
            this.secretKey = newnewSecreKey;
        }
    }

    public SecretKey getSecretKey(){
        return secretKey;
    }


    //加密解密部分发送接收应该没有问题（我测过发送接收前后，加密解密前后是一样的）
    @Override
    public void send(byte[] data) throws IOException {

//        System.out.println(data + "123" );
//        System.out.println(secretKey + " 123333");
//        System.out.println(nomAlgo);

        //System.out.println(Arrays.toString(data) + "   123");


        try {
            Cipher cipher = Cipher.getInstance(nomAlgo);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data);



//            Cipher cipher1 = Cipher.getInstance(nomAlgo);
//            cipher1.init(Cipher.DECRYPT_MODE, secretKey);
//
//
//            System.out.println(Arrays.toString(cipher1.doFinal(encryptedData)) + "   456");
            Thread.sleep(1);
            super.send(encryptedData);
        } catch (GeneralSecurityException | InterruptedException e) {
            throw new IOException("", e);
        }
    }

    @Override
    public byte[] recv() throws IOException, ClassNotFoundException {

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        byte[] encryptedData = super.recv();
        //System.out.println(Arbrays.toString(encryptedData) + "456");
//
        System.out.println(secretKey + " 456666");
//
//        System.out.println(nomAlgo);
        try {
            Cipher cipher = Cipher.getInstance(nomAlgo);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            //System.out.println(Arrays.toString(cipher.doFinal(encryptedData)) + "456");

            return cipher.doFinal(encryptedData);
        } catch (GeneralSecurityException e) {
            throw new IOException("", e);
        }
    }
}
