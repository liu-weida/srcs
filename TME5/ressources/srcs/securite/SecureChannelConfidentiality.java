package srcs.securite;

public class SecureChannelConfidentiality extends ChannelDecorator {
    private Channel channel;
    private Authentication authentication;
    private String nomAlgo;
    private int tailleCle;

    public SecureChannelConfidentiality(Channel channel, Authentication authentication, String nomAlgo, int tailleCle) {
        super(channel);
        this.authentication = authentication;
        this.nomAlgo = nomAlgo;
        this.tailleCle = tailleCle;
    }

}
