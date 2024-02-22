package srcs.securite;

import java.io.Serializable;
import java.security.*;

public class Certif implements Serializable {
    private final String id;
    private final PublicKey clePublic;
    private final byte[] signAutoCer;
    private final String nomAlgoSign;

    protected Certif(String id, PublicKey clePublic, byte[] signAutoCer, String nomAlgoSign){
        this.id = id;
        this.signAutoCer = signAutoCer;
        this.clePublic = clePublic;
        this.nomAlgoSign = nomAlgoSign;
    }

    public boolean verify(PublicKey publicKeyAuthority) {
        try {
            Signature signature = Signature.getInstance(nomAlgoSign);
            signature.initVerify(publicKeyAuthority);
            signature.update(clePublic.getEncoded());
            return signature.verify(signAutoCer);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public String getIdentifier() {
        return id;
    }

    public PublicKey getPublicKey() {
        return clePublic;
    }

    public byte[] getAuthoritySignature() {
        return signAutoCer;
    }

    public String getNomAlgoSign() {
        return nomAlgoSign;
    }
}
