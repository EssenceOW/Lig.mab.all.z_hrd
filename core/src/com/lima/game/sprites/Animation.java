package com.lima.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class Animation extends Observable {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    private boolean canLoop;
    private boolean loopComplete;

    public boolean isLoopComplete() {
        return this.loopComplete;
    }

    public Animation(TextureRegion region, int frameCount, float cycleTime, boolean canLoop) {
        super();

        this.canLoop = canLoop;
        this.frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;

        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }

        this.frameCount = frameCount;
        maxFrameTime = cycleTime/frameCount;
        frame = 0;
    }

    private boolean canIncreaseFrame(){
        if (frame < frameCount -1){
            return true;
        } else {
            return false;
        }
    }

    public void update(float dt){
        if (canIncreaseFrame()){
            currentFrameTime += dt;
            if (currentFrameTime > maxFrameTime) {
                frame++;
                currentFrameTime = 0;
            }
        } else if (canLoop) {
            loopComplete = true;
            resetFrame();
        } else {
            loopComplete = true;
            this.setChanged();
            this.notifyObservers();
        }

    }

    public void resetFrame(){
        frame = 0;
        this.loopComplete = false;
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}