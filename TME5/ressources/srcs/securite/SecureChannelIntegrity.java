package srcs.securite;

import java.io.IOException;
import java.security.*;

public class SecureChannelIntegrity extends ChannelDecorator{
    private String nomAlgo;
    private Authentication authentication;

    public SecureChannelIntegrity(Channel channel, Authentication authentication, String nomAlgo) {
        super(channel);
        this.authentication = authentication;
        this.nomAlgo = nomAlgo;
    }

    @Override
    public void send(byte[] bytesArray) throws IOException {
        try {
            Signature signature = Signature.getInstance(nomAlgo);
            signature.initSign(authentication.getLocalKeys().getPrivate());
            signature.update(bytesArray);
            byte[] res = signature.sign();
            super.send(bytesArray);
            super.send(res);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] recv() throws IOException, ClassNotFoundException{
        byte[] bytesArray = super.recv();
        byte[] res = super.recv();

        try {
            Signature sig = Signature.getInstance(nomAlgo);
            sig.initVerify(authentication.getDistCert().getPublicKey());
            sig.update(bytesArray);
            if (! sig.verify(res)) {
                throw new CorruptedMessageException("Signature verification failed.");
            }
            return bytesArray;
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new CorruptedMessageException("error");
        }
    }
}
