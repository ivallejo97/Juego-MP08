package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bloque extends Actor {
    Rectangle bounds;
    boolean upsideDown;
    AssetManager manager;
    GameScreen gameScreen;

    Bloque() {
        setX(0);
        setY(0);
        setSize(64, 230);
        bounds = new Rectangle();
        setVisible(false);
    }



    @Override
    public void act(float delta) {
        //moveBy(-200 * delta, 0);
        bounds.set(getX(), getY(), getWidth(), getHeight());
        if(!isVisible())
            setVisible(true);
        if (getX() < -800)
            remove();
    }

    void setGameScreen(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw( manager.get( upsideDown ? "bloques.png" : "bloque_individual.png", Texture.class), getX() - gameScreen.scroll, getY() );
    }
    public Rectangle getBounds() {
        return bounds;
    }
    public boolean isUpsideDown() {
        return upsideDown;
    }
    public void setUpsideDown(boolean upsideDown) {
        this.upsideDown = upsideDown;
    }
    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
}
