package srcs.securite;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

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
    public Authentication(Channel channel, Certif certif, KeyPair keyPair, String login, String password, PublicKey authorityPublicKey) throws IOException, GeneralSecurityException, ClassNotFoundException {
        this.channel = channel;
        this.localCertif = certif;
        this.keyPair = keyPair;
        this.publicKeyServer = authorityPublicKey;
        this.login = login;
        this.password = password;

        authenticateClient();
    }

    public Authentication(Channel channel, Certif certif, KeyPair keyPair, PublicKey authorityPublicKey, String login, String password) throws IOException, GeneralSecurityException, ClassNotFoundException {
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
        try {
            // 发送本地证书
            channel.send(toBytes(localCertif));
            // 接收客户端证书并验证
            byte[] clientCertBytes = channel.recv();
            Certif clientCert = bytesTo(clientCertBytes);

            //System.out.println(localCertif.toString());
            System.out.println("aaa" + publicKeyClient);

            if (!clientCert.verify(publicKeyClient)) {
                throw new CertificateCorruptedException("Client certificate verification failed.");
            }

            this.remoteCertif = clientCert;
            // 等待接收登录信息并验证
            byte[] loginInfo = channel.recv();
            // 此处应有解密登录信息的逻辑
            if (!passwordStore.checkPassword(login, new String(loginInfo))) {
                throw new AuthenticationFailedException("Password verification failed.");
            }
        } catch (EOFException e) {
            System.err.println("Connection closed unexpectedly: " + e.getMessage());
            throw new IOException("Connection closed unexpectedly.", e);
        }
    }


    //client
    private void authenticateClient() throws IOException, ClassNotFoundException, GeneralSecurityException {
        try {
            // 发送本地证书
            //System.out.println("send c:");
            channel.send(toBytes(localCertif));

            // 接收服务器端证书并验证
            byte[] serverCertBytes = channel.recv();

            Certif serverCert = bytesTo(serverCertBytes);

            //System.out.println(localCertif.toString());
            System.out.println("aaa"+publicKeyServer);

            if (!serverCert.verify(publicKeyServer)) {
                throw new CertificateCorruptedException("Server certificate verification failed.");
            }
            this.remoteCertif = serverCert;
            // 发送加密的登录信息
            channel.send(encryptLoginInfo(login, password));
        } catch (EOFException e) {
            System.err.println("Connection closed unexpectedly: " + e.getMessage());
            throw new IOException("Connection closed unexpectedly.", e);
        }
    }

    // 示例加密登录信息方法，需要根据实际情况实现
    private byte[] encryptLoginInfo(String login, String password) {
        // 实现加密逻辑
        return new byte[0]; // 示例返回值
    }

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

}
