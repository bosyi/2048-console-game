package game;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Game2048 game2048 = Game2048.getGame();
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
        Game2048.Direction direction = null;
        String line = scanner.nextLine();
        int value = getLegalValue(line);
        if (value == -1) {
            System.out.println("Please enter number 4,8,6, or 2");
            return getDirection();
        }
        switch (value) {
            case 4: direction = Game2048.Direction.LEFT; break;
            case 8: direction = Game2048.Direction.UP; break;
            case 6: direction = Game2048.Direction.RIGHT; break;
            case 2: direction = Game2048.Direction.DOWN;break;
        }
        return direction;
    }

    private static int getLegalValue(String line) {
        int value;
        try {
            value = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
        if (value == 4 || value == 8
                || value == 6 || value == 2) {
            return value;
        } else {
            return -1;
        }
    }
}
