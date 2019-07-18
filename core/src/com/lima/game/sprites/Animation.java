package com.lima.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;



public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    private boolean loop;
    private boolean loopCompleted;

    public Animation(TextureRegion region, int frameCount, float cycleTime, boolean loop) {
        this.loop = loop;
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime/frameCount;
        frame = 0;
    }

    public void update(float dt){
        if (frame < frameCount-1 && !loopCompleted){
            currentFrameTime += dt;
            if (currentFrameTime > maxFrameTime){
                frame++;
                currentFrameTime = 0;
            }
        } else {
            this.loopCompleted = true;
            if (loop){
                frame = 0;
            } else {
                frame = 0;
                loopCompleted = false;
            }
        }

    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}