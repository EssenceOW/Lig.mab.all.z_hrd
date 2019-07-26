package com.lima.game.sprites.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class JumpButton{
    private Texture texture;
    private Vector2 position;
    private float resizew;
    private float resizeh;

    public JumpButton(int x, int y){
        texture = new Texture("Jump_Button.png");
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
    public boolean isPressed(float x, float y){
        if(x >= this.position.x + 110 && x < this.position.x + 170 && y <= this.position.y + 300 && y > this.position.y + 240) {
            return true;
        } else {
            return false;
        }
    }
    public void setPosition(float x){
        position.x = x - 170;
    }
    public void setresize(float w, float h){
        resizew = w;
        resizeh = h;
    }
}
