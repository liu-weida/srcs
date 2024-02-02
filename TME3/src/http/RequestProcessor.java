package http;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface RequestProcessor {
    public void process(Socket connexion) throws IOException;

}
