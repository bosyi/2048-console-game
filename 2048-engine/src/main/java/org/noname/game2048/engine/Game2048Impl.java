package org.noname.game2048.engine;

import java.util.ArrayList;
import java.util.Random;

class Game2048Impl implements Game2048 {
    private Random random = new Random();
    private GameStatus gameStatus;
    private int sideSize;
    private int score;
    private Block2048[][] horzLeftView;
    private Block2048[][] horzRightView;
    private Block2048[][] vertTopView;
    private Block2048[][] vertBottomView;
    private ArrayList<Block2048> allCellsView;
    private Block2048[][] copyOfView;

    Game2048Impl(int sideSize, Class clazz) throws IllegalAccessException, InstantiationException {
        this.sideSize = sideSize;
        horzLeftView = new Block2048[sideSize][sideSize];
        horzRightView = new Block2048[sideSize][sideSize];
        vertTopView = new Block2048[sideSize][sideSize];
        vertBottomView = new Block2048[sideSize][sideSize];
        allCellsView = new ArrayList<>(sideSize*sideSize);
        copyOfView = new Block2048[sideSize][sideSize];
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                Block2048 block2048 = (Block2048) clazz.newInstance();
                horzLeftView[i][j] = block2048;
                horzRightView[i][sideSize - 1 - j] = block2048;
                vertTopView[j][i] = block2048;
                vertBottomView[j][sideSize - 1 - i] = block2048;
                allCellsView.add(block2048);
            }
        }
        pushNewNumber();
        pushNewNumber();
        gameStatus = GameStatus.PLAYABLE;
        copyView();
    }

    @Override
    public void printGameView() {
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                System.out.printf("%5s", horzLeftView[i][j].getData() == null ? "*" : horzLeftView[i][j].getData());
            }
            System.out.println();
        }
    }

    private void process(Block2048[][] view) {
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

    private void moveZeroRight(Block2048[] array, int numberOfCellsWithNumbers) {
        Block2048[] transformed = new Block2048[numberOfCellsWithNumbers];
        for (int i = 0, counter = 0; i < sideSize; i++) {
            if (array[i].getData() != null) {
                Block2048 clonedBlock = array[i].clone();
                transformed[counter] = clonedBlock;
                counter++;
            }
        }
        for (int i = 0; i < sideSize; i++) {
            if (i < transformed.length && transformed[i] != null) {
                array[i].copy(transformed[i]);
            } else {
                array[i].clear();
            }
        }
    }
    private void joinDuplicates(Block2048[] array) {
        for (int i = 0; i < sideSize - 1 && array[i].getData() != null; i++) {
            int result = array[i].joinAndGetScore(array[i + 1]);
            if (result != -1) {
                score+=result;
                i++;
            }
        }
    }

    private int countCellsWithValuesInRow(Block2048[] row) {
        int counter = 0;
        for (Block2048 block2048:row) {
            if (block2048.getData() != null) {
                counter++;
            }
        }
        return counter;
    }

    private boolean ifWeHaveFreeCells() {
        for (Block2048 block2048:allCellsView) {
            if (block2048.getData() == null) {
                return true;
            }
        }
        return false;
    }

    private boolean ifWeHaveJoinableCells() {
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize - 1; j++) {
                if (horzLeftView[i][j].ifJoinable(horzLeftView[i][j + 1])
                        || vertTopView[i][j].ifJoinable(vertTopView[i][j + 1])) {
                    return true;
                }
            }
        }
        return false;
    }

    private void pushNewNumber() {
        Object[] tempView = allCellsView.stream().filter(block2048 -> block2048.getData() == null).toArray();
        Block2048 block2048 = (Block2048) tempView[random.nextInt(tempView.length)];
        block2048.generateData();
    }

    private void copyView() {
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                copyOfView[i][j] = horzLeftView[i][j].clone();
            }
        }
    }

    private boolean ifViewChanged() {
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                if (!copyOfView[i][j].equals(horzLeftView[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void swipe(Direction direction) {
        if (gameStatus == GameStatus.FINISHED) {
            throw new RuntimeException("Game already finished");
        }
        Block2048[][] view = null;
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

}