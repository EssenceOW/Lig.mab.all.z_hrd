package com.lima.game.sprites.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class LeftButton{
    private Texture texture;
    private Vector2 position;

    public LeftButton(int x, int y){
        texture = new Texture("Left_Button.png");
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
    public boolean isPressed(){
        if(Gdx.input.getX() >= this.position.x && Gdx.input.getX() < this.position.x + texture.getWidth() && Gdx.input.getY() >= this.position.y && Gdx.input.getY() < this.position.y + texture.getHeight()) {
            return true;
        } else {
            return false;
        }
    }
    public void setPosition(float x){
        position.x = x - 170;
    }
}
