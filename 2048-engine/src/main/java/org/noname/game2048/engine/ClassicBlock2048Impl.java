package org.noname.game2048.engine;

import java.util.Random;

class ClassicBlock2048Impl implements Block2048 {

    private int value;

    ClassicBlock2048Impl() {
    }

    @Override
    public String getData() {
        if (value == 0) {
            return null;
        } else {
            return "" + value;
        }
    }

    @Override
    public int joinAndGetScore(Block2048 block) {
        ClassicBlock2048Impl classicBlock2048 = (ClassicBlock2048Impl) block;
        if (value == classicBlock2048.value) {
            value = value * 2;
            classicBlock2048.value = 0;
            return value;
        }
        return -1;
    }

    @Override
    public boolean ifJoinable(Block2048 block) {
        ClassicBlock2048Impl classicBlock2048 = (ClassicBlock2048Impl) block;
        if (value == classicBlock2048.value) {
            return true;
        }
        return false;
    }

    @Override
    public void generateData() {
        Random random = new Random();
        double rndValue = random.nextDouble();
        if (rndValue < 0.9) {
            value = 2;
        } else {
            value = 4;
        }
    }

    @Override
    public Block2048 clone() {
        ClassicBlock2048Impl classicBlock2048 = new ClassicBlock2048Impl();
        classicBlock2048.value = this.value;
        return classicBlock2048;
    }

    @Override
    public void copy(Block2048 block) {
        ClassicBlock2048Impl classicBlock2048 = (ClassicBlock2048Impl) block;
        this.value = classicBlock2048.value;
    }

    @Override
    public void clear() {
        this.value = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ClassicBlock2048Impl classicBlock2048 = (ClassicBlock2048Impl) obj;
        if (value == classicBlock2048.value) {
            return true;
        } else {
            return false;
        }
    }
}
