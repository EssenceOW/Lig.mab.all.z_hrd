package com.lima.game.sprites.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ShootButton{
    private Texture texture;
    private Vector2 position;

    public ShootButton(int x, int y){
        texture = new Texture("Shoot_Button.png");
        position = new Vector2(x, y);
    }
    public Texture getTexture(){
        return texture;
    }
    public Vector2 getPosition(){
        return position;
    }
    public void dispose(){
        texture.dispose();
    }
}
