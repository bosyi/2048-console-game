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
        int value;
        value = scanner.nextInt();
        Game2048.Direction direction;
        switch (value) {
            case 4: direction = Game2048.Direction.LEFT; break;
            case 8: direction = Game2048.Direction.UP; break;
            case 6: direction = Game2048.Direction.RIGHT; break;
            case 2: direction = Game2048.Direction.DOWN;break;
            default: {
                System.out.println("You need to choose only between 4,8,6,2 numbers");
                return getDirection();
            }
        }
        return direction;
    }
}
