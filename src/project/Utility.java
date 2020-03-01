package project;

import java.net.*;

public class Utility {
    static InetAddress findAddress() throws SocketException, UnknownHostException {
        var wlp3s0 = NetworkInterface.getByName("lo");
        return wlp3s0.inetAddresses()
                .filter(a -> a instanceof Inet4Address)
                .findFirst()
                .orElse(InetAddress.getLocalHost());
    }
}

