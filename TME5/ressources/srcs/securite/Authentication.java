package srcs.securite;

import java.io.*;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class Authentication {
    private final Channel channel;
    private final Certif localCertif;
    private Certif distCert;
    private KeyPair keyPair;
    private PublicKey pubKey;

    private PublicKey publicKeyClient;
    private PasswordStore passwordStore;
    private String login;
    private String password;

    private static ConcurrentHashMap<String, Boolean> processedRequestsServer = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Boolean> processedRequestsClient = new ConcurrentHashMap<>();

    public Authentication(Channel channel, Certif certif, KeyPair keyPair, PasswordStore passwordStore, PublicKey authPubKey) throws IOException, GeneralSecurityException, ClassNotFoundException {
        this.channel = channel;
        this.localCertif = certif;
        this.keyPair = keyPair;
        this.passwordStore = passwordStore;
        this.publicKeyClient = authPubKey;

        authenticateServer();
    }


    public Authentication(Channel channel, Certif certif, KeyPair keyPair, String password, String login, PublicKey authPubKey) throws IOException, GeneralSecurityException, ClassNotFoundException {
        this.channel = channel;
        this.localCertif = certif;
        this.keyPair = keyPair;
        this.pubKey = authPubKey;
        this.login = login;
        this.password = password;

        authenticateClient();
    }

    public Authentication(Channel channel, Certif certif, KeyPair keyPair, PublicKey authPubKey,String password ,String login) throws IOException, GeneralSecurityException, ClassNotFoundException {
        this.channel = channel;
        this.localCertif = certif;
        this.keyPair = keyPair;
        this.pubKey = authPubKey;
        this.login = login;
        this.password = password;

        authenticateClient();
    }

    //server
    private void authenticateServer() throws IOException, ClassNotFoundException, GeneralSecurityException {
        channel.send(toBytes(localCertif));
        try {
            channel.send(generateCertificateHash(localCertif));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

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



            this.distCert = clientCert;



            byte[] encryptedLogin = channel.recv();
            byte[] encryptedPassword = channel.recv();


            String login = new String(encryptedLogin);

            String password = passwordStore.hashToHex(encryptedPassword);


            if (passwordStore.checkPassword2(login,password)) {
                throw new AuthenticationFailedException("Password verification failed.");
            }


    }


    //client
    private void authenticateClient() throws IOException, ClassNotFoundException, GeneralSecurityException {


            //System.out.println("send c:");
            channel.send(toBytes(localCertif));
        try {
            channel.send(generateCertificateHash(localCertif));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


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

        if (!serverCert.verify(pubKey)) {
                throw new AuthenticationFailedException("Server certificate verification failed.");
            }
            this.distCert = serverCert;



            PasswordStore passwordStore = new PasswordStore("SHA");

            //passwordStore.storePassword(login,password);


            byte[] encryptedLogin = login.getBytes();
            byte[] encryptedPassword = passwordStore.toHash(this.password).getBytes();


            channel.send(encryptedLogin);
            channel.send(encryptedPassword);

    }




    public Certif getLocalCertif() {
        return localCertif;
    }

    public Certif getDistCert() {
        return distCert;
    }

    public KeyPair getLocalKeys() {
        return keyPair;
    }



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
