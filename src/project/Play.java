package project;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Play implements Runnable {
    private BufferedWriter writer;
    private BufferedReader reader;
    private String message;
    private BattleshipMap myMap;
    private BattleshipMap enemyMap;
    private State state;

    public Play(Socket socket, State state, String path) throws IOException {
        this.state = state;
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        message = "";
        myMap = new BattleshipMap();
        enemyMap = new BattleshipMap();
        myMap.load(path);
    }

    public void run() {
        try {
            while (true) {
                if (state == State.MYTURN) {
                    myTurn();
                } else if (state == State.SEND) {
                    sendMap();
                } else if (state == State.ENEMYTURN) {
                    enemyTurn();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void win() {
        try {
            System.out.println("Wygrana");
            message = myMap.toString();
            sendMess(message);
            state = State.SEND;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void enemyTurn() {
        try {
            String input = readInput();
            System.out.println("response: " + input);
            String status = input;
            if (input.contains(";")) {
                status = input.substring(0, input.indexOf(';'));
            }
            if (status.equals("ostatni zatopiony")) {
                win();
            } else {
                input = input.substring(input.indexOf(';') + 1);
                int first = input.charAt(0) - 'A';
                int second = Integer.parseInt(input.substring(1)) - 1;
                char fieldValue = myMap.getBattleShipMap()[first][second];
                if (fieldValue == '#' || fieldValue == '@') {
                    hit(first, second);
                } else if (fieldValue == '.' || fieldValue == '~') {
                    miss(first, second);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void myTurn() {
        try {
            Scanner scanner = new Scanner(System.in);
            String field = scanner.nextLine();
            if (message.equals("")) {
                sendMess(field);
            } else {
                sendMess(message + ";" + field);
            }
            state = State.ENEMYTURN;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void sendMess(String message) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    private void sendMap() {
        try {
            String input = readInput();
            enemyMap = BattleshipMap.readBattleShipMap(input);
            System.out.println("enemy map: ");
            enemyMap.printBattleShipMap();
            System.out.println("my map: ");
            myMap.printBattleShipMap();
            sendMess(myMap.toString());
            state = State.END;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void miss(int firstIndex, int secondIndex) {
        message = "pudło";
        myMap.setMap('~', firstIndex, secondIndex);
        state = State.MYTURN;
    }

    private void hit(int firstIndex, int secondIndex) {
        try {
            myMap.setMap('@', firstIndex, secondIndex);
            if (myMap.isLast()) {
                message = "ostatni zatopiony";
                System.out.println("Przegrana");
                sendMess(message);
                state = State.SEND;
            } else if (myMap.wholeShipDestroyed(firstIndex, secondIndex)) {
                message = "trafiony zatopiony";
                state = State.MYTURN;
            } else {
                message = "trafiony";
                state = State.MYTURN;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String readInput() {
        String input = null;
        try {
            do {
                input = reader.readLine();
            } while (input == null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return input;
    }
}