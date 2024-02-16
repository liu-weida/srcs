package srcs.securite;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class Authentication {

    private Channel channel;
    private Certif localCertif;
    private Certif remoteCertif;
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PasswordStore passwordStore;
    private String login;
    private String password;

    //server
    public Authentication(Channel channel, Certif certif, KeyPair keyPair, PasswordStore passwordStore, PublicKey authorityPublicKey) throws IOException, GeneralSecurityException, ClassNotFoundException {
        this.channel = channel;
        this.localCertif = certif;
        this.keyPair = keyPair;
        this.passwordStore = passwordStore;
        this.publicKey = authorityPublicKey;

        authenticateServer();
    }

    //client
    public Authentication(Channel channel, Certif certif, KeyPair keyPair,String login, String password,  PublicKey authorityPublicKey) throws IOException, GeneralSecurityException, ClassNotFoundException {
        this.channel = channel;
        this.localCertif = certif;
        this.keyPair = keyPair;
        this.publicKey = authorityPublicKey;
        this.login = login;
        this.password = password;

        authenticateClient();
    }

    public Authentication(Channel channel, Certif certif, KeyPair keyPair,  PublicKey authorityPublicKey, String login, String password) throws IOException, GeneralSecurityException, ClassNotFoundException {
        this.channel = channel;
        this.localCertif = certif;
        this.keyPair = keyPair;
        this.publicKey = authorityPublicKey;
        this.login = login;
        this.password = password;

        authenticateClient();
    }

    private void authenticateServer() throws IOException, ClassNotFoundException, GeneralSecurityException {
        // 发送本地证书
        channel.send(toBytes(localCertif));
        // 接收客户端证书并验证
        byte[] clientCertBytes = channel.recv();
        Certif clientCert = bytesTo(clientCertBytes);
        if (!clientCert.verify(publicKey)) {
            throw new CertificateCorruptedException("Client certificate verification failed.");
        }
        this.remoteCertif = clientCert;
        // 等待接收登录信息并验证
        byte[] loginInfo = channel.recv();
        // 解密登录信息并验证（此处省略解密步骤）
        if (!passwordStore.checkPassword(login, new String(loginInfo))) {
            throw new AuthenticationFailedException("Password verification failed.");
        }
    }

    private void authenticateClient() throws IOException, ClassNotFoundException, GeneralSecurityException {
        // 发送本地证书
        channel.send(toBytes(localCertif));
        // 接收服务器端证书并验证
        byte[] serverCertBytes = channel.recv();
        Certif serverCert = bytesTo(serverCertBytes);
        if (!serverCert.verify(publicKey)) {
            throw new CertificateCorruptedException("Server certificate verification failed.");
        }
        this.remoteCertif = serverCert;
        // 发送加密的登录信息（此处省略加密步骤）
        channel.send(encryptLoginInfo(login, password));
    }

    // 加密登录信息的方法（示例，需要实现）
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

    // 序列化方法
    public static byte[] toBytes(Certif certif) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(certif.getIdentifier()); // id
        dos.write(certif.getPublicKey().getEncoded()); // 公钥
        dos.writeInt(certif.getAuthoritySignature().length); // 签名长度
        dos.write(certif.getAuthoritySignature()); // 签名
        dos.writeUTF(certif.getNomAlgoSign()); // 签名算法名称

        dos.flush();
        return baos.toByteArray();
    }

    // 反序列化方法
    public static Certif bytesTo(byte[] data) throws IOException, GeneralSecurityException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);

        String id = dis.readUTF();
        byte[] publicKeyBytes = new byte[dis.available() - Integer.BYTES];
        dis.readFully(publicKeyBytes);
        int signLength = dis.readInt();
        byte[] signAutoCer = new byte[signLength];
        dis.readFully(signAutoCer);
        String nomAlgoSign = dis.readUTF();

        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        return new Certif(id, publicKey, signAutoCer, nomAlgoSign);
    }

}
