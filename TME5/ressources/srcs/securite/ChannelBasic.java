package srcs.securite;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

//EX1 Q1
public class ChannelBasic implements Channel{
    private final Socket socket;

    public ChannelBasic(Socket socket){
        this.socket = socket;
    }

    @Override
    public void send(byte[] bytesArray) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(bytesArray.length);
        dos.write(bytesArray);
        dos.flush();
    }

    @Override
    public byte[] recv() throws IOException, ClassNotFoundException {
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        byte[] recv = new byte[dis.readInt()];
        for (int i = 0; i < recv.length; i++)
            recv[i] = dis.readByte();
        return recv;
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