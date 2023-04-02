package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bowser extends Actor {

    Rectangle bounds;
    AssetManager manager;

    private float startX, endX;

    float speed,time;


    Bowser(float startX, float endX) {
        setX(550);
        setY(30);
        setSize(64, 230);
        bounds = new Rectangle();
        speed = 1.5f;
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


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw( manager.get( "bowser.png" ,Texture.class), getX() , getY() );


    }
    public Rectangle getBounds() {
        return bounds;
    }
    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
}
