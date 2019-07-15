package com.lima.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Husk {

    private HuskState state;
    private static final int MOVEMENT = 100;
    private Rectangle bounds;
    private Animation currentAnimation;

    private Animation huskWalking;
    private Animation huskDeath;
    private Animation huskAttack;
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private boolean collided;


    public void setCollided(boolean collided) {
        this.collided = collided;
    }
    public Rectangle getBounds() {
        return bounds;
    }
    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        switch(state){
            case WALKING:
                this.currentAnimation = huskWalking;
                break;
            default:
                return new TextureRegion(texture);
            case DIE:
                this.currentAnimation = huskDeath;
                break;
            case ATTACKING:
                this.currentAnimation = huskAttack;
        }
        return this.currentAnimation.getFrame();
    }

    public Husk(int x, int y){
        position = new Vector2(x,y);
        collided = false;
        velocity = new Vector2(0,0);
        state = HuskState.IDLE;
        texture = new Texture("Husk.png");
//        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
//        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        huskWalking = new Animation(new TextureRegion(new Texture("Husk_walking.png")),4, 0.5f);
        huskDeath = new Animation(new TextureRegion(new Texture("Husk_death.png")),20, 1f);
        huskAttack = new Animation(new TextureRegion(new Texture("Husk_attack.png")),8, 1f);
    }

    public void run(){
        this.state = HuskState.WALKING;
    }
    public void attack(){
        this.state = HuskState.ATTACKING;
    }
    public void die(){
        this.state = HuskState.DIE;
    }

    public void update(float dt){
        if (currentAnimation!=null){
            currentAnimation.update(dt);
        }
//        if (position.y > 0) {
//            velocity.add(0, GRAVITY);
//        }
//
//        velocity.scl(dt);
//        if (!this.collided){
//            position.add(MOVEMENT * dt, velocity.y);
//            if (position.y < 0){
//                position.y = 0;
//            }
//        } else {
//            position.add(BOUNCE * dt, velocity.y);
//            if (position.y < 0){
//                position.y = 0;
//            }
//        }

//        velocity.scl(1/dt);

        bounds.setPosition(position.x, position.y);
    }

    public void dispose() {
    }
}