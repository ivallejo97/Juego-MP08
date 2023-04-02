package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class MarioVSBowser implements Screen {
    final Bird game;
    OrthographicCamera camera;
    Stage stage;
    PlayerBowser player;

    GameScreen gameScreen;

    boolean dead;
    boolean victoria;
    float score;
    float vidas;
    int vidasBowser;
    float scroll;

    private Texture leftButtonTexture;
    private Texture rightButtonTexture;
    private Texture upButtonTexture;
    private Sprite leftButtonSprite;
    private Sprite rightButtonSprite;
    private Sprite upButtonSprite;
    private Button leftButton;
    private Button rightButton;
    private Button upButton;

    public MarioVSBowser(final Bird gam){
        this.game = gam;
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        player = new PlayerBowser();
        player.setManager(game.manager);
        player.setWidth(40);
        player.setHeight(40);


        stage = new Stage();
        stage.getViewport().setCamera(camera);
        stage.addActor(player);


        // Carga las imágenes de los botones en la memoria
        leftButtonTexture = new Texture(Gdx.files.internal("boton_izquierda.png"));
        rightButtonTexture = new Texture(Gdx.files.internal("boton_derecha.png"));
        upButtonTexture = new Texture(Gdx.files.internal("flecha_salto.png"));

        // Crea los sprites de los botones
        leftButtonSprite = new Sprite(leftButtonTexture);
        rightButtonSprite = new Sprite(rightButtonTexture);
        upButtonSprite = new Sprite(upButtonTexture);

        // Crea los botones de movimiento
        leftButton = new Button(new SpriteDrawable(leftButtonSprite));
        rightButton = new Button(new SpriteDrawable(rightButtonSprite));
        upButton = new Button(new SpriteDrawable(upButtonSprite));

        // Establece las propiedades de los botones
        leftButton.setPosition(50, 45);
        rightButton.setPosition(130, 45);
        upButton.setPosition(690, 45);
        leftButton.setSize(60, 60);
        rightButton.setSize(60, 60);
        upButton.setSize(60, 60);

        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(upButton);

        // Agrega Listeners a los botones
        // Agrega un evento de click para el botón de la izquierda
        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Actualiza la posición del actor hacia la izquierda
            }
        });

        // Agrega un evento de click para el botón de la derecha
        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Actualiza la posición del actor hacia la derecha
            }
        });

        // Agrega un evento de click para el botón de la derecha
        upButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Actualiza la posición del actor hacia la derecha
                player.impulso();
            }
        });
    }



    @Override
    public void render(float delta) {
        // clear the screen with a color
        ScreenUtils.clear(0.3f, 0.8f, 0.8f, 1);
        // tell the camera to update its matrices.
        camera.update();
        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
        // begin a new batch
        game.batch.begin();
        game.batch.draw(game.manager.get("fondo_bowser.jpg", Texture.class), 0 , 0);
        game.batch.end();

        // Stage batch: Actors
        stage.getBatch().setProjectionMatrix(camera.combined);
        stage.draw();

        // Botones para mover al jugador
        if (leftButton.isPressed()){
            player.setPosition(player.getX() - 2, player.getY());
        }
        if (rightButton.isPressed()){
            player.setPosition(player.getX() + 2, player.getY());
        }
        if (upButton.isPressed()){
            player.impulso();
        }

        stage.act(delta);

        // Comprova que el jugador no es surt de la pantalla.
        // Si surt per la part inferior, game over
        if (player.getBounds().y > 480 - 45)
            player.setY( 480 - 45 );
        if (player.getBounds().y < 0 - 45) {
            //dead = true;
        }
        /*if (player.getBounds().x < scroll){
            player.setX(scroll);
            scroll = player.getX() - 200;
        }
        if (player.bounds.x < 5){
            player.setX(5);
        }*/


        // Comprobar muerte
        /*if(dead) {
            game.setScreen(new GameOverScreen(game));
            dispose();

            game.lastScore = (int)score;
            if(game.lastScore > game.topScore)
                game.topScore = game.lastScore;

            game.manager.get("game_over.mp3", Sound.class).play();
        }*/

        // Comprobar si se ha pasado el nivel
        if(victoria) {
            game.setScreen(new WinsScreen(game));
            dispose();

            game.lastScore = (int)score;
            if(game.lastScore > game.topScore)
                game.topScore = game.lastScore;

            game.manager.get("fail.wav", Sound.class).play();
        }


        if (vidas == 0){
            dead = true;
        }

        // Dibuja los botones y el personaje en la pantalla
        game.batch.begin();
        leftButtonSprite.setPosition(leftButton.getX(), leftButton.getY());
        leftButtonSprite.draw(game.batch);
        rightButtonSprite.setPosition(rightButton.getX(), rightButton.getY());
        rightButtonSprite.draw(game.batch);
        upButtonSprite.setPosition(upButton.getX(), upButton.getY());
        upButtonSprite.draw(game.batch);
        game.batch.end();

        // Puntuacion del Jugador
        game.batch.begin();
        game.smallFont.draw(game.batch, "Score: " + (int)score, 10, 470);
        game.smallFont.draw(game.batch, "Vidas: " + (int)vidas, 10, 430);
        game.batch.end();

        //La puntuació augmenta amb el temps de joc
        score += Gdx.graphics.getDeltaTime();


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addActor(player);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
