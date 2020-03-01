package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class BattleshipMap {

    private char[][] map;

    public BattleshipMap() {
        map = new char[10][10];
        for (char[] row : map)
            Arrays.fill(row, '0');
    }

    public BattleshipMap(char[][] list) {
        map = list;
    }

    public static BattleshipMap readBattleShipMap(String input) { //czytywanie z pliku
        char[][] result = new char[10][10];
        input = input.replaceAll("\n", "");
        int k = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                result[i][j] = input.charAt(k++);
            }
        }
        return new BattleshipMap(result);
    }

    @Override
    public String toString() {
        final var builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                builder.append(map[i][j]);
            }
        }
        return builder.toString();
    }

    public void setMap(char value, int firstIndex, int secondIndex) {
        map[firstIndex][secondIndex] = value;
    }

    public void load(String path) { // wypisuję mape do pliku
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < 10; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < 10; j++) {
                    map[i][j] = line.charAt(j);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void printBattleShipMap() {
        final var builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                builder.append(map[i][j]);
            }
            builder.append('\n');
        }
        System.out.println(builder.toString());
        System.out.println();
    }

    public boolean isLast() {       // sprawdzam czy zostało coś niezatopionego
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                if (map[i][j] == '#')
                    return false;
        return true;
    }

    public boolean wholeShipDestroyed(int first, int second) { // idę w lewo, prawo, góre i dół i sprawdzam czy jest niezatopuony segment
        for (int j = second + 1; j < 10; j++) {
            if (map[first][j] == '#') {
                return false;
            } else if (map[first][j] != '@')
                break;
        }
        for (int j = second - 1; j >= 0; j--) {
            if (map[first][j] == '#') {
                return false;
            } else if (map[first][j] != '@')
                break;
        }
        for (int j = first + 1; j < 10; j++) {
            if (map[j][second] == '#') {
                return false;
            } else if (map[j][second] != '@')
                break;
        }
        for (int j = first - 1; j >= 0; j--) {
            if (map[j][second] == '#') {
                return false;
            } else if (map[j][second] != '@')
                break;
        }
        return true;
    }

    public char[][] getBattleShipMap() {
        return map;
    }
}