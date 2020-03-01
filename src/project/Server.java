package project;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    ServerSocket serverSocket;
    static String path;
    private Server(InetAddress address, int port) {
        try {
            serverSocket = new ServerSocket(port, 10000, address);
            System.out.println("Running server at address: " + address + ", port: " + port);
            path = "";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            InetAddress address = Utility.findAddress();
            Server server = new Server(address, Integer.parseInt(args[1]));
            path = args[2];
            new Thread(server, "Server").start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();
            Play play = new Play(socket, State.ENEMYTURN, path);
            new Thread(play, "Server").start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
