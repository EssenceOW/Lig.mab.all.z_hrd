package com.lima.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.lima.game.sprites.Bullet;
import com.lima.game.sprites.Crawler;
import com.lima.game.sprites.Gorefast;
import com.lima.game.sprites.Husk;
import com.lima.game.sprites.Patriarch;
import com.lima.game.sprites.Player;

import java.util.ArrayList;
import java.util.List;


public class PlayState extends State {
    private final World world;
    private final Box2DDebugRenderer b2dr;
    private Gorefast gorefast;
    private Husk husk;
    private Player player;
    private Patriarch patriarch;
    private Crawler crawler;
    private int currentAction = 0;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private BitmapFont playerHealth;
    private BitmapFont gorefastHealth;
    private BitmapFont huskHealth;
    private BitmapFont crawlerHealth;
    private BitmapFont patriarchHealth;
    private List<Bullet> bullets;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/killingfloor_lvl_1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/ blzhrd.PPM);

        world = new World(new Vector2(0,-110),true);
        b2dr = new Box2DDebugRenderer();

        player = new Player(120, 100, world);
        playerHealth = new BitmapFont();
        patriarch = new Patriarch(40, 300, world);
        patriarchHealth = new BitmapFont();
        gorefast = new Gorefast(200, 100, world);
        gorefastHealth = new BitmapFont();
        husk = new Husk(200, 300, world);
        huskHealth = new BitmapFont();
        crawler = new Crawler(120, 300, world);
        crawlerHealth = new BitmapFont();
        bullets = new ArrayList<Bullet>();
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player.jump();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.jump();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && player.getB2body().getLinearVelocity().x <=2) {
            player.moveRight();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getB2body().getLinearVelocity().x <=2) {
            player.moveRight();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && player.getB2body().getLinearVelocity().x >=-2) {
            player.moveLeft();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getB2body().getLinearVelocity().x >=-2) {
            player.moveLeft();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            player.attackWithHandgun();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            player.attackWithShotgun();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            player.attackWithLargeGun();
        }
        if(Gdx.input.justTouched()){
            bullets.add(new Bullet(player.getPosition().x + 33, player.getPosition().y + 7));
        }
    }

        @Override
        public void update(float dt){

            super.update(dt);

            world.step(1/60f,  6,2);
            cam.position.x = player.getB2body().getPosition().x;
            gorefast.update(dt);
            husk.update(dt);
            player.updateSprite(dt);
            patriarch.update(dt);
            crawler.update(dt);
            for(int i = 0; i < bullets.size(); i++){
                bullets.get(i).update(dt);
                if(bullets.get(i).getBounds().overlaps(gorefast.getBounds()) && bullets.get(i).getHit() == false){
                    bullets.get(i).setHit();
                    gorefast.takeDamage(player.getDamage());
                }
                if(bullets.get(i).getBounds().overlaps(husk.getBounds()) && bullets.get(i).getHit() == false){
                    bullets.get(i).setHit();
                    husk.takeDamage(player.getDamage());
                }
                if(bullets.get(i).getBounds().overlaps(crawler.getBounds()) && bullets.get(i).getHit() == false){
                    bullets.get(i).setHit();
                    crawler.takeDamage(player.getDamage());
                }
                if(bullets.get(i).getBounds().overlaps(patriarch.getBounds()) && bullets.get(i).getHit() == false){
                    bullets.get(i).setHit();
                    patriarch.takeDamage(player.getDamage());
                }
                if(bullets.get(i).getHit()){
                }
            }

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
            huskHealth.draw(tileSB, String.valueOf(husk.getHealth()), husk.getPosition().x + 35, husk.getPosition().y + 95);
            tileSB.draw(patriarch.getTexture(), patriarch.getPosition().x, patriarch.getPosition().y, 100, 100);
            patriarchHealth.draw(tileSB, String.valueOf(patriarch.getHealth()), patriarch.getPosition().x + 35, patriarch.getPosition().y + 95);
            tileSB.draw(player.getTexture(), player.getPosition().x, player.getPosition().y,100, 100);
            if(player.getWeaponTexture() != null){
                tileSB.draw(player.getWeaponTexture(), player.getWeaponPosition().x, player.getWeaponPosition().y, 100, 100);
            }
            playerHealth.draw(tileSB, String.valueOf(player.getHealth()), player.getPosition().x + 35, player.getPosition().y + 95);
            tileSB.draw(gorefast.getTexture(), gorefast.getPosition().x, gorefast.getPosition().y, 100, 100);
            gorefastHealth.draw(tileSB, String.valueOf(gorefast.getHealth()), gorefast.getPosition().x + 35, gorefast.getPosition().y + 95);
            tileSB.draw(crawler.getTexture(), crawler.getPosition().x + 35, crawler.getPosition().y + 95, 100, 100);
            crawlerHealth.draw(tileSB, String.valueOf(crawler.getHealth()), crawler.getPosition().x + 84, crawler.getPosition().y + 170);
            for(int i = 0; i < bullets.size(); i++){
                tileSB.draw(bullets.get(i).getTexture(), bullets.get(i).getPosition().x, bullets.get(i).getPosition().y, 100, 100);
            }
            tileSB.end();
        }

        @Override
        public void dispose(){
            gorefast.dispose();
            husk.dispose();
            player.dispose();
            patriarch.dispose();
            crawler.dispose();
        }
    }
