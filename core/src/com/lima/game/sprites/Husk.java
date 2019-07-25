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

public class Husk {
    private HuskState state;
    private static final int MOVEMENT = 100;
    private int health;
    private int damage;
    private Rectangle bounds;
    private Animation currentAnimation;

    private Animation huskWalking;
    private Animation huskDeath;
    private Animation huskAttack;
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private boolean collided;
    private Body b2body;
    private World world;


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

    public Husk(int x, int y, World world){
        health = 600;
        damage = 20;
        position = new Vector2(x,y);
        this.world = world;
        this.define(x, y);

        collided = false;
        velocity = new Vector2(0,0);
        state = HuskState.IDLE;
        texture = new Texture("Husk.png");
//        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
//        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        huskWalking = new Animation(new TextureRegion(new Texture("Husk_walking.png")),4, 0.5f, false);
        huskDeath = new Animation(new TextureRegion(new Texture("Husk_death.png")),20, 1f, false);
        huskAttack = new Animation(new TextureRegion(new Texture("Husk_attack.png")),8, 1f, false);
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

    public void moveRight(){
        this.state = HuskState.WALKING;
        b2body.applyLinearImpulse(new Vector2(50f,0), b2body.getWorldCenter(), true);
    }
    public void moveLeft(){
        this.state = HuskState.WALKING;
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
        b2body.createFixture(fdef);
    }

    public void update(float dt){
        if (currentAnimation!=null){
            currentAnimation.update(dt);
        }
        this.position.x = b2body.getPosition().x - 49;
        this.position.y = b2body.getPosition().y - 51;
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
    public int getHealth(){
        return health;
    }
    public void takeDamage(int damage){
        health -= damage;
    }
    public void dispose() {
    }
}