package com.lima.game.statez;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {




    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render (SpriteBatch sb);
    public abstract void dispose();

}
