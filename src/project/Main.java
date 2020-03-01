package project;

public class Main {

    public static void main(String[] args) {
        try {
            String player = find(args, "-mode");
            String portName = find(args, "-port");
            String pathOfTheMap = find(args, "-map");
            String [] params = new String[] {player, portName, pathOfTheMap};
            if (player.equals("server")) {
                Server.main(params);
            } else {
                Client.main(params);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static String find ( String [] args, String param){
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(param)) {
                return args[i+1];
            }
        }
        return null;
    }
}

