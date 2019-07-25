package com.lima.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Vector2 position;
    private Vector2 velocity;
    private Texture texture;
    private Rectangle bounds;
    private boolean hit;

    public Bullet(float x, float y){
        hit = false;
        position = new Vector2(x, y);
        texture = new Texture("Bullet.png");
        velocity = new Vector2(20, 0);
        bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }
    public void update(float dt){
        position.add(velocity);
    }
    public Texture getTexture(){
        return texture;
    }
    public Vector2 getPosition(){
        return position;
    }
    public void setPosition(float x, float y){
        position.x = x;
        position.y = y;
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public boolean getHit(){
        return hit;
    }
    public void setHit(){
        hit = true;
    }
}
