package srcs.securite;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class Certif {

    private String id;
    private PublicKey clePublic;
    private byte[] signAutoCer;

    private String nomAlgoSign;

    protected Certif(String id, PublicKey clePublic, byte[] signAutoCer, String nomAlgoSign){
        this.id = id;
        this.clePublic = clePublic;
        this.signAutoCer = signAutoCer;
        this.nomAlgoSign = nomAlgoSign;
    }

    public boolean verify(PublicKey publickeyauthority)throws GeneralSecurityException {
        return publickeyauthority == clePublic;
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
