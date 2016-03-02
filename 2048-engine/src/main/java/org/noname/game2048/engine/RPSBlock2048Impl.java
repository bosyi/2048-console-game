package org.noname.game2048.engine;

import java.util.Objects;
import java.util.Random;

class RPSBlock2048Impl implements Block2048 {
    private int multiplier;
    private String rps;

    RPSBlock2048Impl() {

    }

    @Override
    public String getData() {
        if (multiplier == 0) {
            return null;
        } else {
            return "" + multiplier + rps;
        }
    }

    @Override
    public int joinAndGetScore(Block2048 block) {
        if (block == null || block.getData() == null) {
            return -1;
        }
        RPSBlock2048Impl rpsBlock2048 = (RPSBlock2048Impl) block;
        int score = 0;
        if (rps.equals("W") && rpsBlock2048.rps.equals("W")) {
            rpsBlock2048.multiplier = 0;
            rpsBlock2048.rps = null;
            return 0;
        }
        if (rps.equals("W") || rpsBlock2048.rps.equals("W")) {
            return -1;
        }
        if (rps.equals(rpsBlock2048.rps)) {
            multiplier = multiplier + rpsBlock2048.multiplier;
            score = multiplier;
        } else if (rps.equals("R") && rpsBlock2048.rps.equals("P")) {
            rps = "P";
            multiplier = rpsBlock2048.multiplier;
            score = 0;
        } else if (rps.equals("P") && rpsBlock2048.rps.equals("S")) {
            rps = "S";
            multiplier = rpsBlock2048.multiplier;
            score = 0;
        } else if (rps.equals("S") && rpsBlock2048.rps.equals("R")) {
            rps = "R";
            multiplier = rpsBlock2048.multiplier;
            score = 0;
        }
        rpsBlock2048.multiplier = 0;
        rpsBlock2048.rps = null;
        return score;
    }

    @Override
    public boolean ifJoinable(Block2048 block) {
        RPSBlock2048Impl rpsBlock2048 = (RPSBlock2048Impl) block;
        if (rps.equals("W") || rpsBlock2048.rps.equals("W")) {
            return false;
        }
        return true;
    }

    @Override
    public void generateData() {
        multiplier = 1;
        Random random = new Random();
        double value = random.nextDouble();
        if (value < 0.25) {
            rps = "R";
        } else if (value < 0.5) {
            rps = "P";
        } else if (value < 0.75) {
            rps = "S";
        } else {
            rps = "W";
        }
    }

    @Override
    public Block2048 clone() {
        RPSBlock2048Impl rpsBlock2048 = new RPSBlock2048Impl();
        rpsBlock2048.multiplier = this.multiplier;
        rpsBlock2048.rps = this.rps;
        return rpsBlock2048;
    }

    @Override
    public void copy(Block2048 block) {
        RPSBlock2048Impl rpsBlock2048 = (RPSBlock2048Impl) block;
        this.multiplier = rpsBlock2048.multiplier;
        this.rps = rpsBlock2048.rps;
    }

    @Override
    public void clear() {
        this.multiplier = 0;
        this.rps = null;
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
        RPSBlock2048Impl rpsBlock2048 = (RPSBlock2048Impl) obj;
        if (rps == null ? rpsBlock2048.rps == null : rps.equals(rpsBlock2048.rps)
            && multiplier == rpsBlock2048.multiplier) {
            return true;
        } else {
            return false;
        }
    }
}
