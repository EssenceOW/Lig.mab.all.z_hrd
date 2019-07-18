package com.lima.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Player {
    private final World world;
    private Body b2body;

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
                return new Vector2(position.x + 21, position.y + 2);
            case ATTACK_WITH_LARGE_GUN:
                return new Vector2(position.x + 6, position.y - 1);
            case ATTACK_WITH_ROCKET_LAUNCHER:
                return new Vector2(position.x + 3, position.y + 3);
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

    public Player(int x, int y, World world){



        position = new Vector2(x,y);
        this.world = world;

        this.define(x, y);


        collided = false;
        state = PlayerState.IDLE;
        texture = new Texture("Player.png");
        handgun = new Texture("Pistol.png");
        largeGun = new Texture("SMG.png");
        rocketLauncher = new Texture("RPG.png");
        bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
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

    private void define(int x, int y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(27);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void update(float dt){
        if (currentAnimation!=null){
            currentAnimation.update(dt);
        }
        this.position = b2body.getPosition();
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
