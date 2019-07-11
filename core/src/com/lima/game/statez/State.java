package com.lima.game.statez;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.lima.game.blzhrd;

public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;

    protected  GameStateManager gsm;



    protected State(GameStateManager value){
        gsm = value;
        cam = new OrthographicCamera();
        mouse = new Vector3();
        cam.setToOrtho(
                false,
                blzhrd.WIDTH / 2,
                blzhrd.HEIGHT / 2);
    }

    public abstract void handleInput();
    public void update(float dt) {
        handleInput();
    }
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
    }
    public abstract void dispose();
}
