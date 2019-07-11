package com.lima.game.statez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lima.game.sprites.Gorefast;
import com.lima.game.sprites.Husk;

public class PlayState extends State {
    private Gorefast goreFast;
    private Husk husk;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        goreFast = new Gorefast(0, 0);
        husk = new Husk(0, 150);

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            goreFast.run();
            husk.run();
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        goreFast.update(dt);
        husk.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        sb.begin();
        sb.draw(goreFast.getTexture(), goreFast.getPosition().x, goreFast.getPosition().y);
        sb.draw(husk.getTexture(), husk.getPosition().x, husk.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {
        goreFast.dispose();
        husk.dispose();
    }
}
