package srcs.securite;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class CertificationAuthority {

    private String nomAlgoAsy;
    private int tailleCle;
    private String nomAlgoSign;

    private KeyPair keyPair;

    private Map<String, Certif> certifs = new HashMap<>();

    public CertificationAuthority(String nomAlgoAsy, int tailleCle, String nomAlgoSign) throws NoSuchAlgorithmException {
        this.nomAlgoAsy = nomAlgoAsy;
        this.tailleCle = tailleCle;
        this.nomAlgoSign = nomAlgoSign;

        keyPair = Util.generateNewKeyPair(nomAlgoAsy,tailleCle);
    }

    public PublicKey getPublicKey(){
        return keyPair.getPublic();
    }

    public Certif getCertificate(String idenfifier){

        return null;
    }

    public Certif declarePublicKey(String identifier, PublicKey pubk)
            throws GeneralSecurityException{

        return null;
    }

}
