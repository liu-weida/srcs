package srcs.securite;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Objects;

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



    public boolean verify(PublicKey publicKeyAuthority) throws GeneralSecurityException {

        System.out.println("pub  " + publicKeyAuthority.toString());
        System.out.println("cle  " + clePublic.toString());

        return Objects.equals(publicKeyAuthority.toString(), clePublic.toString());
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


    @Override
    public String toString() {
        return "Certif{" +
                "id='" + id + '\'' +
                ", clePublic=" + clePublic +
                ", signAutoCer=" + Arrays.toString(signAutoCer) +
                ", nomAlgoSign='" + nomAlgoSign + '\'' +
                '}';
    }
}
