package cz.janvrska.fg;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {
    DatagramSocket datagramSocket;
    InetAddress ip;
    int port;


    public UDPClient(InetAddress ip, int port) throws SocketException {
        datagramSocket = new DatagramSocket();
        this.ip = ip;
        this.port = port;
    }

    public void sendMessage(String message) throws IOException {
        byte[] buf = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, ip, port);
        datagramSocket.send(datagramPacket);
    }

}
