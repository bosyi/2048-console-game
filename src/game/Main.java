package game;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int sideSize;
        System.out.println("Enter side size of game board. 4 is good, 2 is minimal");
        try {
            sideSize = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("You entered bad value");
            return;
        }
        Game2048 game2048 = Game2048.getGame(sideSize);
        System.out.println("Score: " + game2048.getScore());
        game2048.printGameView();

        while (game2048.getGameStatus() == Game2048.GameStatus.PLAYABLE) {
            System.out.print("L: 4, U: 8, R:6, D:8\n...");
            game2048.swipe(getDirection());
            System.out.println("Score: " + game2048.getScore());
            game2048.printGameView();
        }
    }

    private static Game2048.Direction getDirection() {
        String msg = "Please enter number 4,8,6, or 2";
        Game2048.Direction direction;
        String line = scanner.nextLine();
        int value;
        if (isValueInteger(line)) {
            value = Integer.parseInt(line);
        } else {
            System.out.println(msg);
            return getDirection();
        }
        switch (value) {
            case 4: direction = Game2048.Direction.LEFT; break;
            case 8: direction = Game2048.Direction.UP; break;
            case 6: direction = Game2048.Direction.RIGHT; break;
            case 2: direction = Game2048.Direction.DOWN;break;
            default: {
                System.out.println(msg);
                return getDirection();
            }
        }
        return direction;
    }

    private static boolean isValueInteger(String line) {
        try {
            Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
