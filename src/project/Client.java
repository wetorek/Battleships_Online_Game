package project;

import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            String address = Utility.findAddress().toString();
            Socket socket = new Socket(address.substring(1), Integer.parseInt(args[1]));
            Play play = new Play(socket, State.MYTURN, args[2]);
            new Thread(play, "Client").start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("client is starting");
    }
}
