package com.lima.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lima.game.blzhrd;
import com.lima.game.sprites.Gorefast;
import com.lima.game.sprites.Husk;
import com.lima.game.sprites.Patriarch;
import com.lima.game.sprites.Player;


public class PlayState extends State {
    private final World world;
    private final Box2DDebugRenderer b2dr;
    private Gorefast goreFast;
    private Husk husk;
    private Player player;
    private Patriarch patriarch;
    private int currentAction = 0;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    public PlayState(GameStateManager gsm) {
        super(gsm);


        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/killingfloor_lvl_1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/ blzhrd.PPM);

        world = new World(new Vector2(0,-110),true);
        b2dr = new Box2DDebugRenderer();

        player = new Player(40, 100, world);
        patriarch = new Patriarch(40, 300, world);
        goreFast = new Gorefast(120, 300, world);
        husk = new Husk(200, 300, world);
        loadGround();
        loadBossGround();
    }

    private void loadGround() {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.x + rect.width / 2, rect.y + rect.height / 2 );

            body = world.createBody(bdef);

            shape.setAsBox(rect.width/2, rect.height/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    private void loadBossGround() {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.x + rect.width / 2, rect.y + rect.height / 2 );

            body = world.createBody(bdef);

            shape.setAsBox(rect.width/2, rect.height/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.jump();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getB2body().getLinearVelocity().x <=2)
            player.moveRight();
            goreFast.moveRight();
            husk.moveRight();
            patriarch.moveRight();
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getB2body().getLinearVelocity().x >=-2)
            player.moveLeft();
            goreFast.moveLeft();
            husk.moveLeft();
            patriarch.moveLeft();

        if (Gdx.input.justTouched()) {
            switch (this.currentAction) {
                case 0:
                    goreFast.run();
                    husk.run();
                    patriarch.run();
                    player.run();
                    break;
                case 1:
                    goreFast.attack();
                    husk.attack();
                    patriarch.attack();
                    player.attackWithHandgun();
                    break;
                case 2:
                    goreFast.die();
                    husk.die();
                    patriarch.die();
                    player.attackWithLargeGun();
                    break;
                case 3:
                    player.attackWithRocketLauncher();
                    break;
            }
            this.currentAction += 1;
        }
    }

        @Override
        public void update(float dt){

            super.update(dt);

            world.step(1/60f,  6,2);
            goreFast.update(dt);
            husk.update(dt);
            player.updateSprite(dt);
            patriarch.update(dt);

            cam.update();
            mapRenderer.setView(this.cam);
        }

        @Override
        public void render(SpriteBatch sb){
            super.render(sb);
            Batch tileSB = this.mapRenderer.getBatch();


            mapRenderer.render();

            b2dr.render(world, cam.combined);

            tileSB.begin();

            tileSB.draw(husk.getTexture(), husk.getPosition().x, husk.getPosition().y, 100, 100);
            tileSB.draw(patriarch.getTexture(), patriarch.getPosition().x, patriarch.getPosition().y, 100, 100);
            tileSB.draw(player.getTexture(), player.getPosition().x, player.getPosition().y,100, 100);
            if(player.getWeaponTexture() != null){
                tileSB.draw(player.getWeaponTexture(), player.getWeaponPosition().x, player.getWeaponPosition().y, 100, 100);
            }
            tileSB.draw(goreFast.getTexture(), goreFast.getPosition().x, goreFast.getPosition().y, 100, 100);

            tileSB.end();
        }

        @Override
        public void dispose(){
            goreFast.dispose();
            husk.dispose();
            player.dispose();
            patriarch.dispose();
        }
    }
