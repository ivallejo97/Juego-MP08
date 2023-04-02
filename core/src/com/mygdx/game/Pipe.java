package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Pipe extends Actor {
    Rectangle bounds;
    boolean upsideDown;
    AssetManager manager;
    GameScreen gameScreen;


    Pipe() {
        setSize(40, 60);
        bounds = new Rectangle();
        setVisible(false);
    }
    @Override
    public void act(float delta) {
        //moveBy(-200 * delta, 0);
        bounds.set(getX(), getY(), getWidth(), getHeight());
        if(!isVisible())
            setVisible(true);
        if (getX() < -64)
            remove();
    }

    void setGameScreen(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw( manager.get( upsideDown ? "tuberia.png" :
                "pipe_down.png", Texture.class), getX() - gameScreen.scroll, getY() );
    }
    public Rectangle getBounds() {
        return bounds;
    }
    public boolean isUpsideDown() {return upsideDown;}
    public void setUpsideDown(boolean upsideDown) {
        this.upsideDown = upsideDown;
    }
    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
}

