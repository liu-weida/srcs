package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

public class client_EX1Q5 implements RequestProcessor{

    String url;

    public client_EX1Q5(String url){
        this.url = url;
    }

    @Override
    public void process(Socket connexion) throws IOException {
        connexionGoogle();

        InputStream is = connexion.getInputStream();
        bind(is,System.out);
    }

    public void connexionGoogle() throws IOException {
        URL newUrl = new URL(url);
        HttpURLConnection huc = (HttpURLConnection)newUrl.openConnection();
    }


    public static void bind(InputStream in, OutputStream out) throws IOException {
        int lu = 0;
        try {
            while ((lu = in.read()) != -1) {
                out.write(lu);
                out.flush();
            }
        }catch (IOException e){}
    }


}
