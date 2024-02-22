package srcs.securite;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;



public class Authentication {

    private Channel channel;
    private Certif localCertif;
    private Certif remoteCertif;
    private KeyPair keyPair;
    private PublicKey publicKeyServer;

    private PublicKey publicKeyClient;
    private PasswordStore passwordStore;
    private String login;
    private String password;

    private static ConcurrentHashMap<String, Boolean> processedRequestsServer = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Boolean> processedRequestsClient = new ConcurrentHashMap<>();


    // 服务器构造函数
    public Authentication(Channel channel, Certif certif, KeyPair keyPair, PasswordStore passwordStore, PublicKey authorityPublicKey) throws IOException, GeneralSecurityException, ClassNotFoundException {
        this.channel = channel;
        this.localCertif = certif;
        this.keyPair = keyPair;
        this.passwordStore = passwordStore;
        this.publicKeyClient = authorityPublicKey;

        authenticateServer();
    }

    // 客户端构造函数
    public Authentication(Channel channel, Certif certif, KeyPair keyPair, String password, String login, PublicKey authorityPublicKey) throws IOException, GeneralSecurityException, ClassNotFoundException {
        this.channel = channel;
        this.localCertif = certif;
        this.keyPair = keyPair;
        this.publicKeyServer = authorityPublicKey;
        this.login = login;
        this.password = password;

        authenticateClient();
    }

    public Authentication(Channel channel, Certif certif, KeyPair keyPair, PublicKey authorityPublicKey,String password ,String login) throws IOException, GeneralSecurityException, ClassNotFoundException {
        this.channel = channel;
        this.localCertif = certif;
        this.keyPair = keyPair;
        this.publicKeyServer = authorityPublicKey;
        this.login = login;
        this.password = password;

        authenticateClient();
    }

    //server
    private void authenticateServer() throws IOException, ClassNotFoundException, GeneralSecurityException {

            // 发送本地证书
            channel.send(toBytes(localCertif));

        try {
            channel.send(generateCertificateHash(localCertif));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        // 接收客户端证书并验证
            byte[] clientCertBytes = channel.recv();
            byte[] clientCertBytesHash = channel.recv();

        String requestIdentifier = Arrays.toString(clientCertBytesHash);
        if (processedRequestsServer.putIfAbsent(requestIdentifier, true) != null) {
            throw new AuthenticationFailedException("Detected replay attack to server.");
        }

            Certif clientCert = bytesTo(clientCertBytes);


        try {
            if (!compareCertificateHash(clientCertBytesHash,generateCertificateHash(clientCert))){
                throw new CertificateCorruptedException("The certificate is damaged.");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        try {
                if (!clientCert.verify(publicKeyClient)) {
                    throw new CertificateCorruptedException("Client certificate verification failed.");
                }
            }catch (CertificateCorruptedException e){
                throw new CertificateCorruptedException("Client certificate is corrupted.", e);
            }



            this.remoteCertif = clientCert;


//            // 接收加密的登录信息
            byte[] encryptedLogin = channel.recv();
            byte[] encryptedPassword = channel.recv();


            String login = new String(encryptedLogin);

            String password = passwordStore.hashToHex(encryptedPassword);

            passwordStore.printAllPasswords();

            if (passwordStore.checkPassword2(login,password)) {
                throw new AuthenticationFailedException("Password verification failed.");
            }


    }


    //client
    private void authenticateClient() throws IOException, ClassNotFoundException, GeneralSecurityException {

            // 发送本地证书
            //System.out.println("send c:");
            channel.send(toBytes(localCertif));
        try {
            channel.send(generateCertificateHash(localCertif));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 接收服务器端证书并验证
            byte[] serverCertBytes = channel.recv();
            byte[] serverCertBytesHash = channel.recv();

        String requestIdentifier = Arrays.toString(serverCertBytesHash);
        if (processedRequestsClient.putIfAbsent(requestIdentifier, true) != null) {
            throw new AuthenticationFailedException("Detected replay attack to server.");
        }


            Certif serverCert = bytesTo(serverCertBytes);


        try {
            if (!compareCertificateHash(serverCertBytesHash,generateCertificateHash(serverCert))){
                throw new CertificateCorruptedException("The certificate is damaged.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!serverCert.verify(publicKeyServer)) {
                throw new AuthenticationFailedException("Server certificate verification failed.");
            }
            this.remoteCertif = serverCert;



            // 使用PasswordStore加密登录信息
            PasswordStore passwordStore = new PasswordStore("SHA");

            //passwordStore.storePassword(login,password);

            passwordStore.printAllPasswords();

            byte[] encryptedLogin = login.getBytes();
            byte[] encryptedPassword = passwordStore.toHash(this.password).getBytes();

            // 发送加密的登录信息
            channel.send(encryptedLogin);
            channel.send(encryptedPassword);

    }

    // 示例加密登录信息方法，需要根据实际情况实现
//    private byte[] encryptLoginInfo(String login, String password) {
//        // 实现加密逻辑
//        return new byte[0]; // 示例返回值
//    }

    // Getter 方法
    public Certif getLocalCertif() {
        return localCertif;
    }

    public Certif getRemoteCertif() {
        return remoteCertif;
    }

    public KeyPair getLocalKeys() {
        return keyPair;
    }

    // 序列化和反序列化方法
// 序列化方法
    public static byte[] toBytes(Certif certif) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(certif.getIdentifier());
        byte[] publicKeyBytes = certif.getPublicKey().getEncoded();
        dos.writeInt(publicKeyBytes.length); // 明确记录公钥的长度
        dos.write(publicKeyBytes);
        dos.writeInt(certif.getAuthoritySignature().length);
        dos.write(certif.getAuthoritySignature());
        dos.writeUTF(certif.getNomAlgoSign());

        dos.flush();

        return baos.toByteArray();
    }

    // 反序列化方法
    public static Certif bytesTo(byte[] data) throws IOException, GeneralSecurityException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);

        String id = dis.readUTF();
        int publicKeyLength = dis.readInt(); // 读取公钥的长度
        byte[] publicKeyBytes = new byte[publicKeyLength];
        dis.readFully(publicKeyBytes); // 根据记录的长度读取公钥
        int signLength = dis.readInt();
        byte[] signAutoCer = new byte[signLength];
        dis.readFully(signAutoCer);
        String nomAlgoSign = dis.readUTF();

        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        Certif cer = new Certif(id, publicKey, signAutoCer, nomAlgoSign);

       // System.out.println(cer.toString());

        return cer;
    }

    public static byte[] generateCertificateHash(Certif certif) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] certBytes = toBytes(certif);
        return digest.digest(certBytes);
    }

    public static boolean compareCertificateHash(byte[] hash1, byte[] hash2) {
        return Arrays.equals(hash1, hash2);
    }


}
