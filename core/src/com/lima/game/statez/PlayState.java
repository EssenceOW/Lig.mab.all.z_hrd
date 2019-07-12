package com.lima.game.statez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lima.game.sprites.Gorefast;
import com.lima.game.sprites.Husk;
import com.lima.game.sprites.Patriarch;
import com.lima.game.sprites.Player;

public class PlayState extends State {
    private Gorefast goreFast;
    private Husk husk;
    private Player player;
    private Patriarch patriarch;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        goreFast = new Gorefast(-75, 0);
        husk = new Husk(-75, 150);
        player = new Player(75, 0);
        patriarch = new Patriarch(75, 150);

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            goreFast.run();
            husk.run();
            player.run();
            patriarch.run();
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        goreFast.update(dt);
        husk.update(dt);
        player.update(dt);
        patriarch.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        sb.begin();
        sb.draw(goreFast.getTexture(), goreFast.getPosition().x, goreFast.getPosition().y);
        sb.draw(husk.getTexture(), husk.getPosition().x, husk.getPosition().y);
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);
        sb.draw(patriarch.getTexture(), patriarch.getPosition().x, patriarch.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {
        goreFast.dispose();
        husk.dispose();
        player.dispose();
        patriarch.dispose();
    }
}
