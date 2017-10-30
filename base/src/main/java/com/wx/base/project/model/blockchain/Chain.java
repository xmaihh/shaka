package com.wx.base.project.model.blockchain;

/**
 * Created by alex on 16-11-30.
 */

public class Chain {

    int id;
    int pos;
    Block block;

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
