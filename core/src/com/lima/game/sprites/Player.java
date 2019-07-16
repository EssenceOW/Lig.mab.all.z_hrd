package com.lima.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

    private final Texture playerAttackingWithHandgun;
    private final Texture playerAttackingWithLargeGun;
    private final Texture playerAttackingWithRocketLauncher;
    private PlayerState state;
    private static final int MOVEMENT = 100;
    private Rectangle bounds;
    private Animation currentAnimation;

    private Animation playerWalking;
    private Animation playerJumping;
    private Animation playerCrouching;
    private Texture texture;
    private Texture handgun;
    private Texture largeGun;
    private Texture rocketLauncher;
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
    public Vector2 getWeaponPosition(){
        switch(state){
            case ATTACK_WITH_HANDGUN:
                return new Vector2(position.x + 50, position.y + 5);
            case ATTACK_WITH_LARGE_GUN:
                return new Vector2(position.x + 15, position.y - 5);
            case ATTACK_WITH_ROCKET_LAUNCHER:
                return new Vector2(position.x, position.y);
            default:
                return null;
        }
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
            case ATTACK_WITH_HANDGUN:
                return new TextureRegion(playerAttackingWithHandgun);
            case ATTACK_WITH_LARGE_GUN:
                return new TextureRegion(playerAttackingWithLargeGun);
            case ATTACK_WITH_ROCKET_LAUNCHER:
                return new TextureRegion(playerAttackingWithRocketLauncher);
            default:
                return new TextureRegion(texture);
        }
        return this.currentAnimation.getFrame();
    }

    public TextureRegion getWeaponTexture() {
        switch(state){
            case ATTACK_WITH_HANDGUN:
                return new TextureRegion(handgun);
            case ATTACK_WITH_LARGE_GUN:
                return new TextureRegion(largeGun);
            case ATTACK_WITH_ROCKET_LAUNCHER:
                return new TextureRegion(rocketLauncher);
            default:
                return null;
        }
    }

    public Player(int x, int y){
        position = new Vector2(x,y);
        collided = false;
        velocity = new Vector2(0,0);
        state = PlayerState.IDLE;
        texture = new Texture("Player.png");
        handgun = new Texture("Pistol.png");
        largeGun = new Texture("SMG.png");
        rocketLauncher = new Texture("RPG.png");
//        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
//        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        playerWalking = new Animation(new TextureRegion(new Texture("Player_walking.png")),4, 0.5f);
        playerJumping = new Animation(new TextureRegion(new Texture("Player_jumping.png")),16, 1f);
        playerCrouching = new Animation(new TextureRegion(new Texture("Player_crouching.png")),5, 0.4f);
        playerAttackingWithHandgun = new Texture("Player_holding_handgun.png");
        playerAttackingWithLargeGun = new Texture("Player_holding_large_gun.png");
        playerAttackingWithRocketLauncher = new Texture("Player_holding_large_gun.png");
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
    public void attackWithHandgun(){
        this.state = PlayerState.ATTACK_WITH_HANDGUN;
    }
    public void attackWithLargeGun(){
        this.state = PlayerState.ATTACK_WITH_LARGE_GUN;
    }
    public void attackWithRocketLauncher(){
        this.state = PlayerState.ATTACK_WITH_ROCKET_LAUNCHER;
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
