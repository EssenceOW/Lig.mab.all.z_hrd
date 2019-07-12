package com.lima.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Gorefast {

    private GoreState state;
    private static final int MOVEMENT = 100;
    private Rectangle bounds;
    private Animation currentAnimation;

    private Animation gorefastRunning;
    private Animation gorefastDeath;
    private Animation gorefastAttack;
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
            case DIE:
                this.currentAnimation = gorefastDeath;
                break;
            case RUNNING:
                this.currentAnimation = gorefastRunning;
                break;
            case ATTACKING:
                this.currentAnimation = gorefastAttack;
                break;
            default:
                return new TextureRegion(texture);
        }
        return this.currentAnimation.getFrame();
    }



    public Gorefast(int x, int y){
        position = new Vector2(x,y);
        collided = false;
        velocity = new Vector2(0,0);
        state = GoreState.IDLE;
        texture = new Texture("Gorefast.png");
//        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
//        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        gorefastAttack = new Animation(new TextureRegion(new Texture("Gorefast_attack.png")),16, 0.6f);
        gorefastRunning = new Animation(new TextureRegion(new Texture("Gorefast_running.png")),4, 0.4f);
        gorefastDeath = new Animation(new TextureRegion(new Texture("Gorefast_death.png")),20, 1.5f);

    }

    public void run(){
        this.state = GoreState.RUNNING;
    }
    public void attack(){
        this.state = GoreState.ATTACKING;
    }
    public void die(){
        this.state = GoreState.DIE;
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