package com.lima.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GoreFast {

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

    public GoreFast(int x, int y){
        position = new Vector2(x,y);
        collided = false;
        velocity = new Vector2(0,0);
        texture = new Texture("Gorefast.png");
        state = GoreState.IDLE;
//        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(position.x, position.y, texture.getWidth() / 3, texture.getHeight());
//        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float dt){
        currentAnimation.update(dt);
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
        texture.dispose();
    }
}
