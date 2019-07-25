package com.lima.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Observable;
import java.util.Observer;

public class Player implements Observer {
    private final World world;
    private Body b2body;

    private final Texture playerAttackingWithHandgun;
    private final Texture playerAttackingWithLargeGun;
    private final Texture playerAttackingWithShotgun;
    private PlayerState state;
    private static final int MOVEMENT = 100;
    private int health;
    private int damage;
    private Rectangle bounds;
    private Animation currentAnimation;
    private Animation playerWalking;
    private Animation playerCrouching;
    private Texture texture;
    private Texture handgun;
    private Texture largeGun;
    private Texture shotgun;
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
            case ATTACK_WITH_SHOTGUN:
                return new Vector2(position.x + 7, position.y + -4);
            default:
                return null;
        }
    }

    public TextureRegion getTexture() {
        switch(state){
            case WALKING:
                this.currentAnimation = playerWalking;
                break;
            case CROUCHING:
                this.currentAnimation = playerCrouching;
                break;
            case ATTACK_WITH_HANDGUN:
                return new TextureRegion(playerAttackingWithHandgun);
            case ATTACK_WITH_LARGE_GUN:
                return new TextureRegion(playerAttackingWithLargeGun);
            case ATTACK_WITH_SHOTGUN:
                return new TextureRegion(playerAttackingWithShotgun);
            default:
                return new TextureRegion(texture);
        }
        return this.currentAnimation.getFrame();
    }

    public TextureRegion getWeaponTexture() {
        switch(state){
            case ATTACK_WITH_HANDGUN:
                damage = 20;
                return new TextureRegion(handgun);
            case ATTACK_WITH_LARGE_GUN:
                damage = 10;
                return new TextureRegion(largeGun);
            case ATTACK_WITH_SHOTGUN:
                damage = 100;
                return new TextureRegion(shotgun);
            default:
                return null;
        }
    }
    public Body getB2body() {
        return b2body;
    }

    public Player(int x, int y, World world){
        health = 100;
        damage = 0;
        position = new Vector2(x,y);
        this.world = world;

        this.define(x, y);

        collided = false;
        state = PlayerState.IDLE;
        texture = new Texture("Player.png");
        handgun = new Texture("Pistol.png");
        largeGun = new Texture("SMG.png");
        shotgun = new Texture("Shotgun.png");

        bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());

        playerWalking = new Animation(new TextureRegion(new Texture("Player_walking.png")),4, 0.5f, false);
        playerWalking.addObserver(this);
        playerCrouching = new Animation(new TextureRegion(new Texture("Player_crouching.png")),5, 0.4f, false);
        playerAttackingWithHandgun = new Texture("Player_holding_handgun.png");
        playerAttackingWithLargeGun = new Texture("Player_holding_large_gun.png");
        playerAttackingWithShotgun = new Texture("Player_holding_large_gun.png");
    }

    public void run(){
        this.state = PlayerState.WALKING;
    }
    public void stand(){
        this.state = PlayerState.IDLE;
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
    public void attackWithShotgun(){
        this.state = PlayerState.ATTACK_WITH_SHOTGUN;
    }

    public void jump(){
        this.state = PlayerState.JUMPING;
        b2body.applyLinearImpulse(new Vector2(0,90f), b2body.getWorldCenter(), true);
    }
    public void moveRight(){
        this.state = PlayerState.WALKING;
        b2body.applyLinearImpulse(new Vector2(50f,0), b2body.getWorldCenter(), true);
    }
    public void moveLeft(){
        this.state = PlayerState.WALKING;
        b2body.applyLinearImpulse(new Vector2(-50f,0), b2body.getWorldCenter(), true);
    }

    private void define(int x, int y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(11, 27);

        fdef.shape = shape;
        getB2body().createFixture(fdef);
    }

    public void updateSprite(float dt){
        if (currentAnimation!=null){
            currentAnimation.update(dt);
        }
        this.position.x = getB2body().getPosition().x - 48;
        this.position.y = getB2body().getPosition().y - 54;
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

    @Override
    public void update(Observable observable, Object o) {
        Animation a = (Animation) observable;
        if (a.isLoopComplete()){
            a.resetFrame();
            this.state = PlayerState.IDLE;
        }
    }
    public int getHealth(){
        return health;
    }
    public PlayerState getWeapon(){
        return state;
    }

    public int getDamage() {
        return damage;
    }
}
