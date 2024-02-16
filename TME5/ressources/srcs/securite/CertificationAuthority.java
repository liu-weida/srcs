package srcs.securite;

import java.security.*;
import java.util.HashMap;
import java.util.Map;

public class CertificationAuthority {

    private final String nomAlgoAsy;
    private final int tailleCle;
    private final String nomAlgoSign;
    private KeyPair keyPair;
    private Map<String, Certif> certifs = new HashMap<>();

    public CertificationAuthority(String encryptionAlgorithm, int keySize, String signatureAlgorithm) throws NoSuchAlgorithmException {
        this.nomAlgoAsy = encryptionAlgorithm;
        this.tailleCle = keySize;
        this.nomAlgoSign = signatureAlgorithm;
        this.keyPair = Util.generateNewKeyPair(encryptionAlgorithm, keySize);
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public Certif getCertificate(String identifier) {
        return certifs.get(identifier);
    }

    public Certif declarePublicKey(String identifier, PublicKey pubk) throws GeneralSecurityException {
        if (certifs.containsKey(identifier)) {
            throw new GeneralSecurityException("Already exists.");
        }

        Signature signature = Signature.getInstance(nomAlgoSign);
        signature.initSign(keyPair.getPrivate());
        signature.update(pubk.getEncoded());
        byte[] sign = signature.sign();

        Certif cert = new Certif(identifier, pubk, sign, nomAlgoSign);
        certifs.put(identifier, cert);
        return cert;
    }
}
