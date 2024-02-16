//package srcs.securite;
//
//import java.security.KeyPair;
//import java.security.PublicKey;
//
//public class Authentication {
//
//    private Channel channel;
//    private Certif certif;
//    private KeyPair keyPair;
//    private PublicKey publicKey;
//
//    private PasswordStore passwordStore;
//
//    private String login;
//    private String password;
//
//
//    public Authentication(Channel channel, Certif certif, KeyPair keyPair, PasswordStore passwordStore, PublicKey publicKey) {
//        this.channel = channel;
//        this.certif = certif;
//        this.keyPair = keyPair;
//        this.passwordStore = passwordStore;
//        this.publicKey = publicKey;
//    }
//
//    public Authentication(Channel channel, Certif certif, KeyPair keyPair, PublicKey publicKey, String login, String password) {
//        this.channel = channel;
//        this.certif = certif;
//        this.keyPair = keyPair;
//        this.publicKey = publicKey;
//        this.login = login;
//        this.password = password;
//    }
//}
