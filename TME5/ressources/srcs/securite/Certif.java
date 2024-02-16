package srcs.securite;

import java.security.GeneralSecurityException;
import java.security.PublicKey;

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




    public String getId() {
        return id;
    }

    public PublicKey getClePublic() {
        return clePublic;
    }

    public byte[] getSignAutoCer() {
        return signAutoCer;
    }

}
