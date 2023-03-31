package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Player extends Actor {
    Rectangle bounds;
    AssetManager manager;
    float speedy, gravity;
    GameScreen gameScreen;
    boolean colisionando;


    Player()
    {
        setX(400);
        setY(58);
        //setY(280 / 2 - 64 / 2);
        setSize(40,40);
        bounds = new Rectangle();
        speedy = 0;
        gravity = 850f;
    }

    void setGameScreen(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }

    @Override
    public void act(float delta)
    {
        //Actualitza la posició del jugador amb la velocitat vertical
        moveBy(0, speedy*delta);
        //Actualitza la velocitat vertical amb la gravetat
        if (colisionando == false){
            speedy -= gravity * delta;
        }
        else {
            speedy = 0;
        }

        //if(getY() < 58) setY(58);


        bounds.set(getX(), getY(), getWidth(), getHeight());

    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(manager.get("nacho.png", Texture.class), getX() - gameScreen.scroll, getY());

    }
    public Rectangle getBounds() {
        return bounds;
    }
    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
    void impulso()
    {
        if( colisionando == true){
            speedy = 500f;
            colisionando = false;
        }
    }



}
