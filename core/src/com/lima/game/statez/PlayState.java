package com.lima.game.statez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.lima.game.sprites.Gorefast;
import com.lima.game.sprites.Husk;
import com.lima.game.sprites.Patriarch;
import com.lima.game.sprites.Player;



public class PlayState extends State {
    private Gorefast goreFast;
    private Husk husk;
    private Player player;
    private Patriarch patriarch;
    private int currentAction = 0;

    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        goreFast = new Gorefast(155, 55);
        husk = new Husk(10, 105);
        player = new Player(0, 5);
        patriarch = new Patriarch(0, 200);

        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("killingfloor.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    @Override
    public void handleInput() {

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
            }
            this.currentAction += 1;
        }
    }

        @Override
        public void update(float dt){
            super.update(dt);
            goreFast.update(dt);
            husk.update(dt);
            player.update(dt);
            patriarch.update(dt);
        }

        @Override
        public void render(SpriteBatch sb){
            super.render(sb);
            Batch tileSB = this.mapRenderer.getBatch();

            mapRenderer.setView(this.cam);
            mapRenderer.render();

            tileSB.begin();

            tileSB.draw(husk.getTexture(), husk.getPosition().x, husk.getPosition().y, 100, 100);
            tileSB.draw(patriarch.getTexture(), patriarch.getPosition().x, patriarch.getPosition().y, 100, 100);
            tileSB.draw(player.getTexture(), player.getPosition().x, player.getPosition().y,100, 100);
            if(player.getWeaponTexture() != null){
                tileSB.draw(player.getWeaponTexture(), player.getWeaponPosition().x, player.getWeaponPosition().y);
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
