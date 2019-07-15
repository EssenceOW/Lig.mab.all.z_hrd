package com.lima.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

    private PlayerState state;
    private static final int MOVEMENT = 100;
    private Rectangle bounds;
    private Animation currentAnimation;

    private Animation playerWalking;
    private Animation playerJumping;
    private Animation playerCrouching;
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
                this.currentAnimation = playerWalking;
                break;
            case JUMPING:
                this.currentAnimation = playerJumping;
                break;
            case CROUCHING:
                this.currentAnimation = playerCrouching;
                break;
            default:
                return new TextureRegion(texture);
        }
        return this.currentAnimation.getFrame();
    }

    public Player(int x, int y){
        position = new Vector2(x,y);
        collided = false;
        velocity = new Vector2(0,0);
        state = PlayerState.IDLE;
        texture = new Texture("Player.png");
//        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
//        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        playerWalking = new Animation(new TextureRegion(new Texture("Player_walking.png")),4, 0.5f);
        playerJumping = new Animation(new TextureRegion(new Texture("Player_jumping.png")),16, 1f);
        playerCrouching = new Animation(new TextureRegion(new Texture("Player_crouching.png")),5, 0.4f);

    }

    public void run(){
        this.state = PlayerState.WALKING;
    }
    public void jump(){
        this.state = PlayerState.JUMPING;
    }
    public void crouch(){
        this.state = PlayerState.CROUCHING;
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
