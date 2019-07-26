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
import com.lima.game.sprites.Buttons.JumpButton;
import com.lima.game.sprites.Buttons.LeftButton;
import com.lima.game.sprites.Buttons.RightButton;
import com.lima.game.sprites.Buttons.ShootButton;
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
    private LeftButton leftButton;
    private RightButton rightButton;
    private JumpButton jumpButton;
    private ShootButton shootButton;
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

        leftButton = new LeftButton(-50, 10);
        rightButton = new RightButton(250, 10);
        jumpButton = new JumpButton(-50, 60);
        shootButton = new ShootButton(250, 60);
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
        if(jumpButton.isPressed(Gdx.input.getX(), Gdx.input.getY())){
            player.jump();
        }
        if(rightButton.isPressed() && player.getB2body().getLinearVelocity().x <=2) {
            player.moveRight();
        }
        if(leftButton.isPressed() && player.getB2body().getLinearVelocity().x >=-2) {
            player.moveLeft();
        }
//        if(Gdx.input.justTouched()) {
//            player.attackWithHandgun();
//        }
//        if(Gdx.input.justTouched()) {
//            player.attackWithShotgun();
//        }
//        if(Gdx.input.justTouched()) {
//            player.attackWithLargeGun();
//        }
//        if(Gdx.input.justTouched()){
//            bullets.add(new Bullet(player.getPosition().x + 33, player.getPosition().y + 7));
//        }
    }

        @Override
        public void update(float dt){

            super.update(dt);

            world.step(1/60f,  6,2);
            cam.position.x = player.getB2body().getPosition().x;
            jumpButton.setPosition(cam.position.x);
            leftButton.setPosition(cam.position.x);
            shootButton.setPosition(cam.position.x);
            rightButton.setPosition(cam.position.x);
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
            tileSB.draw(leftButton.getTexture(), leftButton.getPosition().x, leftButton.getPosition().y, 30, 30);
            tileSB.draw(rightButton.getTexture(), rightButton.getPosition().x, rightButton.getPosition().y, 30, 30);
            jumpButton.setresize(30, 30);
            tileSB.draw(jumpButton.getTexture(), jumpButton.getPosition().x, jumpButton.getPosition().y, 30, 30);
            tileSB.draw(shootButton.getTexture(), shootButton.getPosition().x, shootButton.getPosition().y, 30, 30);
            tileSB.draw(husk.getTexture(), husk.getPosition().x, husk.getPosition().y, 80/2.5f, 143/2.5f);
            huskHealth.draw(tileSB, String.valueOf(husk.getHealth()), husk.getPosition().x + 3, husk.getPosition().y + 75);
            tileSB.draw(patriarch.getTexture(), patriarch.getPosition().x, patriarch.getPosition().y, 87/2.5f, 139/2.5f);
            patriarchHealth.draw(tileSB, String.valueOf(patriarch.getHealth()), patriarch.getPosition().x + 3, patriarch.getPosition().y + 75);
            tileSB.draw(player.getTexture(), player.getPosition().x, player.getPosition().y,81/2.5f, 144/2.5f);
            if(player.getWeaponTexture() != null){
                tileSB.draw(player.getWeaponTexture(), player.getWeaponPosition().x, player.getWeaponPosition().y, 100, 100);
            }
            playerHealth.draw(tileSB, String.valueOf(player.getHealth()), player.getPosition().x + 3, player.getPosition().y + 75);
            tileSB.draw(gorefast.getTexture(), gorefast.getPosition().x, gorefast.getPosition().y, 83/2.5f, 136/2.5f);
            gorefastHealth.draw(tileSB, String.valueOf(gorefast.getHealth()), gorefast.getPosition().x + 3, gorefast.getPosition().y + 75);
            tileSB.draw(crawler.getTexture(), crawler.getPosition().x + 35, crawler.getPosition().y + 95, 149/2.5f, 85/2.5f);
            crawlerHealth.draw(tileSB, String.valueOf(crawler.getHealth()), crawler.getPosition().x + 5, crawler.getPosition().y + 170);
            for(int i = 0; i < bullets.size(); i++){
                tileSB.draw(bullets.get(i).getTexture(), bullets.get(i).getPosition().x, bullets.get(i).getPosition().y, 30, 30);
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
            leftButton.dispose();
            rightButton.dispose();
            jumpButton.dispose();
            shootButton.dispose();
        }
    }
