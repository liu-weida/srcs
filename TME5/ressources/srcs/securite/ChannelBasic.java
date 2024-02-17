package srcs.securite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

//EX1 Q1

public class ChannelBasic implements Channel{

    Socket socket;

    public ChannelBasic(Socket socket){
        this.socket = socket;
    }

    @Override
    public void send(byte[] bytesArray) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(bytesArray);
        oos.flush();

        //System.out.println("Sending bytes: " + Arrays.toString(bytesArray));
    }

    @Override
    public byte[] recv() throws IOException, ClassNotFoundException {

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            byte[] bytesArray = (byte[]) ois.readObject();

        //System.out.println("Received bytes: " + Arrays.toString(bytesArray));

            return bytesArray;

    }

    @Override
    public InetAddress getRemoteHost() {
        return socket.getInetAddress();
    }

    @Override
    public int getRemotePort() {
        return socket.getPort();
    }

    @Override
    public InetAddress getLocalHost() {
        return socket.getLocalAddress();
    }

    @Override
    public int getLocalPort() {
        return socket.getLocalPort();
    }
}
