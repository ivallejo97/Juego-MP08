package com.mygdx.game;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ScreenUtils;


public class WinsScreen implements Screen {
    final Bird game;
    OrthographicCamera camera;
    Stage stage;


    private Texture botonMenuPrincipalTexture;
    private Sprite botonMenuPrincipalSprite;
    private Button botonMenuPrincipal;


    public WinsScreen(final Bird gam) {
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage();
        stage.getViewport().setCamera(camera);


        botonMenuPrincipalTexture = new Texture(Gdx.files.internal("boton-volver.png"));
        botonMenuPrincipalSprite = new Sprite(botonMenuPrincipalTexture);
        botonMenuPrincipal = new Button(new SpriteDrawable(botonMenuPrincipalSprite));

        botonMenuPrincipal.setPosition(300,80);
        botonMenuPrincipal.setSize(250,60);

        botonMenuPrincipal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Actualiza la posición del actor hacia la izquierda
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });


        stage.addActor(botonMenuPrincipal);


    }




    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.manager.get("victoria.png",
                Texture.class), 0, 0);
        game.bigFont.draw(game.batch, "VICTORIA ", 250, 450);
        game.smallFont.draw(game.batch, "Final Score: " + game.lastScore, 40, 80);
        game.smallFont.draw(game.batch, "Top Score: " + game.topScore, 40, 50);
        game.batch.end();

        stage.act(delta);
        stage.draw();

        /*if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }*/

        game.batch.begin();
            botonMenuPrincipalSprite.setPosition(botonMenuPrincipal.getX(),botonMenuPrincipal.getY());
            botonMenuPrincipalSprite.draw(game.batch);
        game.batch.end();


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
