package org.noname.game2048.engine;

interface Block2048 {
    String getData();
    int joinAndGetScore(Block2048 block);
    boolean ifJoinable(Block2048 block);
    void generateData();
    Block2048 clone();
    void copy(Block2048 block);
    void clear();
}
