package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Bird extends Game {

	SpriteBatch batch;
	BitmapFont smallFont, bigFont;
	AssetManager manager;
	int topScore;
	int lastScore;
	int monedasCogidas;
	int numGoombas;
	int numGoombasMuertos;


	public void create() {
		batch = new SpriteBatch();
		// Create bitmap fonts from TrueType font
		FreeTypeFontGenerator generator = new
		FreeTypeFontGenerator(Gdx.files.internal("8bitOperatorPlus-Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new
		FreeTypeFontGenerator.FreeTypeFontParameter();
		params.size = 22;
		params.borderWidth = 2;
		params.borderColor = Color.BLACK;
		params.color = Color.WHITE;
		smallFont = generator.generateFont(params); // font size 22 pixels
		params.size = 50;
		params.borderWidth = 5;
		bigFont = generator.generateFont(params); // font size 50 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		this.setScreen(new MainMenuScreen(this));

		manager = new AssetManager();
		manager.load("nacho.png", Texture.class);
		manager.load("pipe_up.png", Texture.class);
		manager.load("pipe_down.png", Texture.class);
		manager.load("fondo_mario_bros.jpg", Texture.class);
		manager.load("fondo_menu.jpg", Texture.class);
		manager.load("plataforma.png", Texture.class);
		manager.load("goomba.png", Texture.class);
		manager.load("goomba_gigante.png", Texture.class);
		manager.load("victoria.png", Texture.class);
		manager.load("bandera_victoria.png", Texture.class);
		manager.load("nube.png", Texture.class);
		manager.load("arbusto.png", Texture.class);
		manager.load("montaña.png", Texture.class);
		manager.load("tuberia.png", Texture.class);
		manager.load("moneda.png", Texture.class);
		manager.load("mario1.png", Texture.class);
		manager.load("mario2.png", Texture.class);
		manager.load("mario3.png", Texture.class);
		manager.load("game_over.png", Texture.class);
		manager.load("bloques.png", Texture.class);
		manager.load("bloque_individual.png", Texture.class);
		manager.load("fondo_bowser.jpg", Texture.class);
		manager.load("bowser.png", Texture.class);
		topScore = 0;
		lastScore = 0;
		monedasCogidas = 0;
		numGoombas = 0;
		numGoombasMuertos = 0;





		manager.load("flap.wav", Sound.class);
		manager.load("fail.wav", Sound.class);
		manager.load("musica_fondo.mp3", Sound.class);
		manager.load("salto.mp3", Sound.class);
		manager.load("moneda.mp3", Sound.class);
		manager.load("game_over.mp3", Sound.class);
		manager.load("dead_goomba.mp3", Sound.class);


		manager.finishLoading();



	}
	public void render() {
		super.render(); // important!
	}
	public void dispose() {
	}


}
