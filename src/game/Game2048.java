package game;

import java.util.ArrayList;
import java.util.Random;

public class Game2048 {
    public enum Direction {LEFT, RIGHT, UP, DOWN}
    public enum GameStatus {PLAYABLE, FINISHED}
    private Random random = new Random();
    private GameStatus gameStatus;
    private int sideSize;
    private int score;
    private Cell2048[][] horzLeftView;
    private Cell2048[][] horzRightView;
    private Cell2048[][] vertTopView;
    private Cell2048[][] vertBottomView;
    private ArrayList<Cell2048> allCellsView;
    private int[][] integerView;

    private Game2048(int sideSize) {
        this.sideSize = sideSize;
        horzLeftView = new Cell2048[sideSize][sideSize];
        horzRightView = new Cell2048[sideSize][sideSize];
        vertTopView = new Cell2048[sideSize][sideSize];
        vertBottomView = new Cell2048[sideSize][sideSize];
        allCellsView = new ArrayList<>(sideSize*sideSize);
        integerView = new int[sideSize][sideSize];
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                Cell2048 cell = new Cell2048();
                horzLeftView[i][j] = cell;
                horzRightView[i][sideSize - 1 - j] = cell;
                vertTopView[j][i] = cell;
                vertBottomView[j][sideSize - 1 - i] = cell;
                allCellsView.add(cell);
            }
        }
        pushNewNumber();
        pushNewNumber();
        gameStatus = GameStatus.PLAYABLE;
        copyView();
    }

    public static Game2048 getGame() {
        return getGame(4);
    }

    public static Game2048 getGame(int sideSize) {
        if (sideSize < 2) {
            throw new RuntimeException("Side size can be 2 or more");
        }
        return new Game2048(sideSize);
    }

    public void printGameView() {
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                System.out.printf("%5d", horzLeftView[i][j].value);
            }
            System.out.println();
        }
    }

    public void swipe(Direction direction) {
        if (gameStatus == GameStatus.FINISHED) {
            throw new RuntimeException("Game already finished");
        }
        Cell2048[][] view = null;
        switch (direction) {
            case LEFT: view = horzLeftView; break;
            case RIGHT: view = horzRightView; break;
            case UP: view = vertTopView; break;
            case DOWN: view = vertBottomView; break;
        }
        process(view);
        if (!ifViewChanged()) {
            return;
        }
        pushNewNumber();
        copyView();
        if (!ifWeHaveFreeCells() && !ifWeHaveJoinableCells()) {
            gameStatus = GameStatus.FINISHED;
        }
    }

    private void process(Cell2048[][] view) {
        for (int i = 0; i < sideSize; i++) {
            int numbersInRow = countCellsWithValuesInRow(view[i]);
            if (numbersInRow == 0) {
                continue;
            }
            if (numbersInRow == 1) {
                moveZeroRight(view[i],numbersInRow);
            } else if (numbersInRow == 2) {
                moveZeroRight(view[i],numbersInRow);
                joinDuplicates(view[i]);
            } else if (numbersInRow == sideSize) {
                joinDuplicates(view[i]);
                moveZeroRight(view[i],numbersInRow);
            } else {
                moveZeroRight(view[i],numbersInRow);
                joinDuplicates(view[i]);
                moveZeroRight(view[i],numbersInRow);
            }
        }
    }

    private void moveZeroRight(Cell2048[] array, int numberOfCellsWithNumbers) {
        int[] transformed = new int[numberOfCellsWithNumbers];
        for (int i = 0, counter = 0; i < sideSize; i++) {
            if (array[i].value != 0) {
                transformed[counter] = array[i].value;
                counter++;
            }
        }
        for (int i = 0; i < sideSize; i++) {
            if (i < transformed.length) {
                array[i].value = transformed[i];
            } else {
                array[i].value = 0;
            }
        }
    }
    private void joinDuplicates(Cell2048[] array) {
        for (int i = 0; i < sideSize - 1 && array[i].value != 0; i++) {
            if (array[i].value == array[i + 1].value) {
                array[i].value = array[i].value * 2;
                array[i + 1].value = 0;
                score+=array[i].value;
                i++;
            }
        }
    }

    private int countCellsWithValuesInRow(Cell2048[] row) {
        int counter = 0;
        for (Cell2048 cell:row) {
            if (cell.value != 0) {
                counter++;
            }
        }
        return counter;
    }

    private boolean ifWeHaveFreeCells() {
        for (Cell2048 cell:allCellsView) {
            if (cell.value == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean ifWeHaveJoinableCells() {
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize - 1; j++) {
                if (horzLeftView[i][j].value == horzLeftView[i][j + 1].value
                        || vertTopView[i][j].value == vertTopView[i][j + 1].value) {
                    return true;
                }
            }
        }
        return false;
    }

    private void pushNewNumber() {
        Object[] tempView = allCellsView.stream().filter(cell -> cell.value == 0).toArray();
        Cell2048 cell = (Cell2048) tempView[random.nextInt(tempView.length)];
        cell.value = 2;
    }

    private void copyView() {
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                integerView[i][j] = horzLeftView[i][j].value;
            }
        }
    }

    private boolean ifViewChanged() {
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                if (integerView[i][j] != horzLeftView[i][j].value) {
                    return true;
                }
            }
        }
        return false;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public int getScore() {
        return score;
    }

    public int[][] getIntegerView() {
        return integerView;
    }

    private static class Cell2048{
        int value;
    }

}