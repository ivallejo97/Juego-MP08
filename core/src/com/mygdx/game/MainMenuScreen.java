package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final Bird game;
    OrthographicCamera camera;
    Stage stage;

    private Texture botonJugarTexture;
    private Sprite botonJugarSprite;
    private Button botonJugar;


    private float blinkInterval = 0.5f;

    public MainMenuScreen(final Bird gam) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage();
        stage.getViewport().setCamera(camera);

        botonJugarTexture = new Texture(Gdx.files.internal("boton_jugar.png"));
        botonJugarSprite = new Sprite(botonJugarTexture);
        botonJugar = new Button(new SpriteDrawable(botonJugarSprite));

        botonJugar.setPosition(290,120);
        botonJugar.setSize(250,60);

        botonJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Actualiza la posición del actor hacia la izquierda
                game.setScreen(new GameScreen(game));
                dispose();
                game.manager.get("musica_fondo.mp3", Sound.class).play();
            }
        });

        // Agregar efecto de parpadeo al botón
        SequenceAction blink = new SequenceAction();
        blink.addAction(Actions.fadeOut(blinkInterval));
        blink.addAction(Actions.fadeIn(blinkInterval));
        botonJugar.addAction(Actions.forever(blink));

        stage.addActor(botonJugar);
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.manager.get("fondo_menu.jpg", Texture.class), 0, 0);

        //game.bigFont.draw(game.batch, "Welcome to Nacho Bird!!! ", 30, 300);
        //game.bigFont.draw(game.batch, "Tap anywhere to begin!", 20, 180);
        game.batch.end();


        stage.act(delta);
        stage.draw();


        /*if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }*/
    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void hide() {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void dispose() {
    }
}
