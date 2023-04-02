package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
import java.util.Random;

public class GameScreen implements Screen {
    final Bird game;
    OrthographicCamera camera;
    Stage stage;
    Player player;
    BanderaVictoria banderaVictoria;
    //EnemigoGoomba goomba;
    boolean dead;
    boolean victoria;
    float score;
    float vidas;

    private Texture leftButtonTexture;
    private Texture rightButtonTexture;
    private Texture upButtonTexture;
    private Sprite leftButtonSprite;
    private Sprite rightButtonSprite;
    private Sprite upButtonSprite;
    private Button leftButton;
    private Button rightButton;
    private Button upButton;

    Array<Pipe> tuberias;
    Array<Plataforma> plataformas;
    Array<EnemigoGoomba> goombas;
    Array<GoombaGigante> goombaGigantes;
    Array<Nube> nubes;
    Array<Moneda> monedas;
    Array<Bloque> bloques;
    GoombaGigante goombaGigante;
    long lastObstacleTime;
    public float scroll;

    Random rand = new Random();
    int min = 8;
    int max = 12;
    int randomGoombas = rand.nextInt(max - min + 1) + min;

    int numTuberias = 4;
    int numGoombas = randomGoombas;
    int numMonedas = 12;
    int numGoombaGigante = 1;
    int vidasGoombaGigante = 3;
    int monedasCogidas = 0;
    int numGoombasMuertos = 0;



    public GameScreen(final Bird gam) {
        this.game = gam;
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        player = new Player();
        player.setGameScreen(this);
        player.setManager(game.manager);
        player.setWidth(40);
        player.setHeight(40);
        //Bandera Victoria
        banderaVictoria = new BanderaVictoria();
        banderaVictoria.setGameScreen(this);
        banderaVictoria.setManager(game.manager);

        stage = new Stage();
        stage.getViewport().setCamera(camera);
        stage.addActor(player);
        stage.addActor(banderaVictoria);

        dead = false;
        score = 0;
        vidas = 3;
        scroll = 0;

        // create the obstacles array and spawn the first obstacle
        tuberias = new Array<Pipe>();
        plataformas = new Array<Plataforma>();
        goombas = new Array<EnemigoGoomba>();
        goombaGigantes = new Array<GoombaGigante>();
        nubes = new Array<Nube>();
        monedas = new Array<Moneda>();
        bloques = new Array<Bloque>();

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


        for (int i = 0; i < 12; i++) {
            Plataforma plataforma = new Plataforma();
            plataforma.setX(i * 600); // establece la posición en el eje x
            plataforma.setY(0); // establece la posición en el eje y
            plataforma.setWidth(600); // establece el ancho de la plataforma
            plataforma.setHeight(40); // establece la altura de la plataforma
            plataforma.bounds.set(plataforma.getX(), plataforma.getY(), plataforma.getWidth(), plataforma.getHeight()); // establece los límites de colisión de la plataforma
            plataforma.setGameScreen(this); // establece la pantalla del juego en la que se encuentra la plataforma
            plataforma.setUpsideDown(false); // establece la orientación de la plataforma
            plataforma.setManager(game.manager); // establece el gestor de recursos del juego
            plataformas.add(plataforma); // agrega la plataforma al array de plataformas
            stage.addActor(plataforma); // agrega la plataforma a la escena
        }

        int posXBloques = 1;
        for (int i = 0; i < 3; i++) {
            Random rand = new Random();
            int min = 120;
            int max = 170;
            int randomY = rand.nextInt(max - min + 1) + min;
            Bloque bloque = new Bloque();
            bloque.setX(posXBloques * 1600);
            bloque.setY(randomY);
            bloque.setWidth(210);
            bloque.setHeight(40);
            bloque.setBounds(bloque.getX(), bloque.getY(), bloque.getWidth(), bloque.getHeight());
            bloque.setGameScreen(this);
            bloque.setUpsideDown(true);
            bloque.setManager(game.manager);
            bloques.add(bloque);
            stage.addActor(bloque);
            posXBloques++;
        }

        int posXBloquesIndividuales = 1;
        for (int i = 0; i < 3; i++) {
            Random rand = new Random();
            int min = 280;
            int max = 310;
            int randomY = rand.nextInt(max - min + 1) + min;
            Bloque bloque = new Bloque();
            bloque.setX(posXBloquesIndividuales * 1650);
            bloque.setY(randomY);
            bloque.setWidth(40);
            bloque.setHeight(40);
            bloque.setBounds(bloque.getX(), bloque.getY(), bloque.getWidth(), bloque.getHeight());
            bloque.setGameScreen(this);
            bloque.setManager(game.manager);
            bloques.add(bloque);
            stage.addActor(bloque);

            Moneda moneda = new Moneda();
            moneda.setX(bloque.getX() + 5);
            moneda.setY(bloque.getY() + 50);
            moneda.setGameScreen(this);
            moneda.setManager(game.manager);
            monedas.add(moneda);
            stage.addActor(moneda);

            posXBloquesIndividuales++;
        }

        // Crear el Goomba Gigante
        goombaGigante = new GoombaGigante(6000,5700);
        goombaGigante.setManager(game.manager);
        goombaGigante.setGameScreen(this);
        goombaGigante.setWidth(100);
        goombaGigante.setHeight(100);
        goombaGigantes.add(goombaGigante);
        stage.addActor(goombaGigante);
        numGoombaGigante--;

        game.numGoombas = numGoombas;

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
        game.batch.draw(game.manager.get("fondo_mario_bros.jpg", Texture.class), 0 - scroll, 0);
        game.batch.draw(game.manager.get("arbusto.png", Texture.class), 20 - scroll, 30);
        game.batch.draw(game.manager.get("arbusto.png", Texture.class), 240 - scroll, 30);
        game.batch.draw(game.manager.get("arbusto.png", Texture.class), 1500 - scroll, 30);
        game.batch.draw(game.manager.get("arbusto.png", Texture.class), 3400 - scroll, 30);
        game.batch.draw(game.manager.get("arbusto.png", Texture.class), 3900 - scroll, 30);
        game.batch.draw(game.manager.get("arbusto.png", Texture.class), 6100 - scroll, 30);
        game.batch.draw(game.manager.get("montaña.png", Texture.class), 1000 - scroll, 30);
        game.batch.draw(game.manager.get("montaña.png", Texture.class), 1200 - scroll, 30);
        game.batch.draw(game.manager.get("montaña.png", Texture.class), 2000 - scroll, 30);
        game.batch.draw(game.manager.get("montaña.png", Texture.class), 2200 - scroll, 30);
        game.batch.draw(game.manager.get("montaña.png", Texture.class), 3000 - scroll, 30);
        game.batch.draw(game.manager.get("montaña.png", Texture.class), 4200 - scroll, 30);
        game.batch.draw(game.manager.get("montaña.png", Texture.class), 5000 - scroll, 30);
        game.batch.draw(game.manager.get("montaña.png", Texture.class), 6400 - scroll, 30);
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
            dead = true;
        }
        if (player.getBounds().x < scroll){
            player.setX(scroll);
            scroll = player.getX() - 200;
        }
        if (player.bounds.x < 5){
            player.setX(5);
        }

        // Actualizacion de Scroll
        if(player.getX() > scroll+400) {
            scroll = player.getX() - 400;
        }



        // Comprobar muerte
        if(dead) {
            game.setScreen(new GameOverScreen(game));
            dispose();

            game.lastScore = (int)score;
            if(game.lastScore > game.topScore)
                game.topScore = game.lastScore;

            game.manager.get("game_over.mp3", Sound.class).play();
        }

        // Comprobar si se ha pasado el nivel
        if(victoria) {

            game.setScreen(new MarioVSBowser(game));
            dispose();

            game.lastScore = (int)score;
            if(game.lastScore > game.topScore)
                game.topScore = game.lastScore;

            game.manager.get("fail.wav", Sound.class).play();
        }



        // GENERA TODOS LOS ACTORES DE LA PARTIDA
        if (numTuberias > 0){
            spawnObstacle();
        }
        if (numGoombas > 0){
            spawnGoomba();
        }
        if (numMonedas > 0){
            spawnMonedas();
        }
        if (TimeUtils.nanoTime() - lastObstacleTime > 2100000000){
            spawnNubes();
        }


        // COLISIONES CON LAS PLATAFORMAS
        boolean colision = false;
        Iterator<Pipe> iter = tuberias.iterator();
        while (iter.hasNext()) {
            Pipe pipe = iter.next();
            if (player.speedy < 0){
                if (pipe.getBounds().overlaps(player.getBounds())) {
                    player.setY(pipe.getY() + 150);
                    player.colisionando = true;
                    colision = true;
                }
                if (pipe.getBounds().overlaps(player.getBounds()) && player.getX() < pipe.getX() && player.getY() < pipe.getY()){
                    player.setX(pipe.getX() - 40);
                }
            }
        }

        Iterator<Plataforma> plat = plataformas.iterator();
        while (plat.hasNext()) {
            Plataforma plataforma = plat.next();
            if (player.speedy < 0){
                if (plataforma.getBounds().overlaps(player.getBounds())) {
                    player.setY(plataforma.getY() + 33);
                    player.colisionando = true;
                    colision = true;
                }
            }
        }

        Iterator<Bloque> bloqueI = bloques.iterator();
        while (bloqueI.hasNext()) {
            Bloque bloque = bloqueI.next();
            if (player.speedy < 0){
                if (bloque.getBounds().overlaps(player.getBounds())) {
                    player.setY(bloque.getY() + 35);
                    player.colisionando = true;
                    colision = true;
                }
            }
        }

        if (!colision){
            player.colisionando = false;
        }


        // COLISIONES CON LAS MONEDAS
        Iterator<Moneda> monedaIT = monedas.iterator();
        while (monedaIT.hasNext()) {
            Moneda moneda = monedaIT.next();
            if (moneda.getBounds().overlaps(player.getBounds())) {
                monedas.removeValue(moneda,true);
                moneda.remove();
                monedasCogidas++;
                game.monedasCogidas = monedasCogidas;
                game.manager.get("moneda.mp3", Sound.class).play();
            }
        }

        // SI COLISIONA CON LA BANDERA GANA
        if (banderaVictoria.getBounds().overlaps(player.getBounds())) {
            victoria = true;
        }

        // COLISIONES CON EL GOOMBA
        Iterator<EnemigoGoomba> goomba = goombas.iterator();
        while (goomba.hasNext()) {
            EnemigoGoomba enemigoGoomba = goomba.next();
            if (player.speedy < 0 && player.colisionando == false){
                if (enemigoGoomba.getBounds().overlaps(player.getBounds()) && player.getY() > enemigoGoomba.getY()) {
                    game.manager.get("dead_goomba.mp3", Sound.class).play();
                    enemigoGoomba.remove();
                    goombas.removeValue(enemigoGoomba,true);
                    player.colisionando = true;
                    player.impulso();
                    numGoombasMuertos++;
                    game.numGoombasMuertos = numGoombasMuertos;
                }
            }

            if (player.colisionando == true) {
                if (enemigoGoomba.getBounds().overlaps(player.getBounds())) {
                    game.manager.get("dead_goomba.mp3", Sound.class).play();
                    enemigoGoomba.remove();
                    goombas.removeValue(enemigoGoomba, true);
                    vidas--;
                }
            }
        }

        //Colisiones con el goomba gigante
        Iterator<GoombaGigante> goombaGig = goombaGigantes.iterator();
        while (goombaGig.hasNext()) {
            GoombaGigante goombaGigante = goombaGig.next();
            if (player.speedy < 0 && player.colisionando == false){
                if (goombaGigante.getBounds().overlaps(player.getBounds()) && player.getY() > goombaGigante.getY()) {
                    player.colisionando = true;
                    player.impulso();
                    vidasGoombaGigante--;
                }

            }

            if (player.colisionando == true) {
                if (goombaGigante.getBounds().overlaps(player.getBounds())) {
                    vidasGoombaGigante--;
                    goombaGigante.remove();
                    goombaGigantes.removeValue(goombaGigante, true);
                    vidas-=2;
                }
            }

            /*if (vidasGoombaGigante == 2){
                goombaGigante.speed = 1.2f;
                goombaGigante.setX(goombaGigante.getX());
            }
            if (vidasGoombaGigante == 1){
                goombaGigante.speed = 1.5f;
                goombaGigante.setX(goombaGigante.getX());
            }*/

            if (vidasGoombaGigante == 0){
                goombaGigante.remove();
                goombaGigantes.removeValue(goombaGigante, true);
            }
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
    public void resize(int width, int height) {
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addActor(player);
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

    int posX = 1;

    private void spawnObstacle() {
        // Calcula la alçada de l'obstacle aleatòriament
        float holey = MathUtils.random(50, 180);
        // Crea dos obstacles: Una tubería superior i una inferior
        Pipe pipe1 = new Pipe();
        pipe1.setX(1350 * posX);
        pipe1.setY(30);
        pipe1.setWidth(40);
        pipe1.setHeight(150);
        pipe1.setUpsideDown(true);
        pipe1.setManager(game.manager);
        pipe1.setGameScreen(this);
        tuberias.add(pipe1);
        stage.addActor(pipe1);

        numTuberias--;
        posX++;
        lastObstacleTime = TimeUtils.nanoTime();
    }

    private void spawnGoomba(){
        //Goomba
        Random rand = new Random();
        int min = 500;
        int max = 6000;
        int randomNumber = rand.nextInt(max - min + 1) + min;

        EnemigoGoomba goomba = new EnemigoGoomba(randomNumber,randomNumber-150);
        //goomba = new EnemigoGoomba(550,400);
        goomba.setManager(game.manager);
        goomba.setGameScreen(this);
        goomba.setWidth(40);
        goomba.setHeight(40);
        goombas.add(goomba);
        stage.addActor(goomba);

        numGoombas--;
        lastObstacleTime = TimeUtils.nanoTime();

    }


    private void spawnNubes(){
        Random rand = new Random();
        int min = 220;
        int max = 450;
        int randomNumber = rand.nextInt(max - min + 1) + min;
        Nube nube = new Nube();
        nube.setX(6700);
        nube.setY(randomNumber);
        nube.setWidth(136);
        nube.setHeight(136);
        nube.setUpsideDown(false);
        nube.setGameScreen(this);
        nube.setManager(game.manager);
        nubes.add(nube);
        stage.addActor(nube);

        lastObstacleTime = TimeUtils.nanoTime();
    }

    private void spawnMonedas(){
        Random rand = new Random();
        int min = 500;
        int max = 6000;
        int min2 = 30;
        int max2 = 200;
        int randomNumber = rand.nextInt(max - min + 1) + min;
        int randomNumber2 = rand.nextInt(max2 - min2 + 1) + min2;
        Moneda moneda = new Moneda();
        moneda.setX(randomNumber);
        moneda.setY(randomNumber2);
        moneda.setGameScreen(this);
        moneda.setManager(game.manager);
        monedas.add(moneda);
        stage.addActor(moneda);

        numMonedas--;
    }


}
