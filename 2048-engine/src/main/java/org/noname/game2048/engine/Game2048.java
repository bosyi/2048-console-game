package org.noname.game2048.engine;

public interface Game2048 {
    enum Direction {LEFT, RIGHT, UP, DOWN}
    enum GameStatus {PLAYABLE, FINISHED}
    GameStatus getGameStatus();
    int getScore();
    void swipe(Direction direction);
    void printGameView();
}
