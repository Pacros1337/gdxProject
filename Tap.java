package com.mygdx.tap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.tap.Screens.About;
import com.mygdx.tap.Screens.HallOfFame;
import com.mygdx.tap.Screens.MainGame;
import com.mygdx.tap.Screens.Menu;
import com.mygdx.tap.Screens.Options;
import com.mygdx.tap.Utility.HighScore;
import com.mygdx.tap.Utility.MusicPlayer;
import com.mygdx.tap.Utility.OptionsConfig;
import com.mygdx.tap.Utility.TimePlayed;

public class Tap extends Game {
    public SpriteBatch batch;

    public final static int MENUSCREEN = 0;
    public final static int GAMESCREEN = 1;
    public static final int OPTIONSCREEN = 2;
    public static final int HALLOFFAMESCREEN = 3;
    public static final int ABOUTSCREEN = 4;

    OrthographicCamera camera;
    private Menu menu;
    private MainGame game;
    private Options options;
    private HallOfFame hallOfFame;
    private About about;
    public OptionsConfig optionsCfg;
    private Tap parent;
    //private final Stage stage;

    MusicPlayer arcade;
    HighScore score;
    TimePlayed time;
    boolean loader = true;

    public OptionsConfig getPreferences() {
        return this.optionsCfg;
    }

    @Override
    public void pause() {
        super.pause();
        getPreferences().saveTime();
        getPreferences().saveHighScore();

    }

    @Override
    public void create() {

        time = TimePlayed.returnInstance();
        score = HighScore.returnInstance();
        arcade = MusicPlayer.vratInstanci();
        menu = new Menu(this);
        game = new MainGame(this);
        options = new Options(this);
        optionsCfg = new OptionsConfig(this);
        hallOfFame = new HallOfFame(this);
        about = new About(this);
        batch = new SpriteBatch();


        setScreen(menu);

        arcade.muzika = Gdx.audio.newMusic(Gdx.files.internal("arcade.ogg"));

        if (optionsCfg.musicOn()) {
            arcade.muzika.setLooping(true);
            arcade.muzika.play();
            arcade.muzika.setVolume(optionsCfg.getMusicVolume());

        } else {
            arcade.muzika.stop();
        }

        if (loader == true) {
            optionsCfg.loadTime();
          optionsCfg.loadHighScore();
        }
    }

    @Override
    public void render() {
        super.render();
        time.update(Gdx.graphics.getDeltaTime());
        System.out.println(time.getTime());



    }
    @Override
    public void dispose() {
        super.dispose();
        getPreferences().saveTime();
        getPreferences().saveHighScore();
    }

    public void screenChanger(int screen) {
        switch (screen) {
            case MENUSCREEN:
                if (menu == null) menu = new Menu(this);
                this.setScreen(menu);
                break;
            case GAMESCREEN:
                if (game == null) game = new MainGame(this);
                this.setScreen(game);
                break;

            case OPTIONSCREEN:
                if (options == null) options = new Options(this);
                this.setScreen(options);
                break;

            case HALLOFFAMESCREEN:
                if (hallOfFame == null) hallOfFame = new HallOfFame(this);
                this.setScreen(hallOfFame);
                break;

            case ABOUTSCREEN:
                if (about == null) about = new About(this);
                this.setScreen(about);
                break;
        }
    }


}
