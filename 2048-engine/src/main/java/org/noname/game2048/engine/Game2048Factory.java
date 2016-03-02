package org.noname.game2048.engine;

public class Game2048Factory {
    public enum GameType {CLASSIC, RPS}
    private static final GameType defGameType = GameType.CLASSIC;
    private Game2048Factory() {

    }

    public static Game2048 getGame() {
        return getGame(4, defGameType);
    }

    public static Game2048 getGame(GameType gameType) {
        return getGame(4, gameType);
    }

    public static Game2048 getGame(int sideSize) {
        return getGame(sideSize, defGameType);
    }

    public static Game2048 getGame(int sideSize, GameType gameType) {
        if (sideSize < 2) {
            throw new RuntimeException("Side size can be 2 or more");
        }
        Game2048 game2048 = null;
        try {
            switch (gameType) {
                case CLASSIC: game2048 = new Game2048Impl(sideSize, ClassicBlock2048Impl.class); break;
                case RPS: game2048 = new Game2048Impl(sideSize, RPSBlock2048Impl.class); break;
            }
        } catch (Exception e) {
            throw new RuntimeException("Something go wrong");
        }
        return game2048;
    }
}
