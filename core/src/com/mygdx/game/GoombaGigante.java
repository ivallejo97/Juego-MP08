package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GoombaGigante extends Actor {

    Rectangle bounds;
    boolean upsideDown;
    AssetManager manager;

    private float startX, endX;

    float speed,time;
    GameScreen gameScreen;


    GoombaGigante(float startX, float endX) {
        setX(550);
        setY(30);
        setSize(64, 230);
        bounds = new Rectangle();
        speed = 1.0f;
        time = 0;

        this.startX = startX;
        this.endX = endX;
        setPosition(startX, getY());
    }
    @Override
    public void act(float delta) {
        super.act(delta);

        time += delta;

        float newx = startX + (endX - startX) * MathUtils.sin(time*speed);

        setPosition(newx, getY());

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
        batch.draw( manager.get( "goomba_gigante.png" ,Texture.class), getX() - gameScreen.scroll, getY() );


    }
    public Rectangle getBounds() {
        return bounds;
    }
    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
}
