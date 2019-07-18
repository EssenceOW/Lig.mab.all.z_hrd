package com.lima.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class PlayerSprite extends Sprite {
    private final World world;
    private Body b2body;

    public PlayerSprite(World world){
        super();
        this.world = world;
        define();
    }

    private void define() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(48, 100);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(27);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
