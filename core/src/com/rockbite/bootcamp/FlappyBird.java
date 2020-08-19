package com.rockbite.bootcamp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class FlappyBird extends ApplicationAdapter {
    private Stage stage;
    private final int viewPortWidth = 1200;
    private Image bird;
    private final float step = 1f;
    private TextureAtlas textureAtlas;
    Image gameOverImage;
    Image startImage;

    Image firstPipeImage;
    Image firstReversedPipeImage;
    Image secondPipeImage;
    Image secondReversedPipeImage;
    Image thirdPipeImage;
    Image thirdReversedPipeImage;
    Image fourthPipeImage;
    Image fourthReversedPipeImage;

    Array<Image> cloudImages = new Array<>();

    SpriteBatch batch;
    Texture img;

    //internal path of the texture
    private final String textureInternalPath = "images/flappyBirdAtlas.png";
    //index of the regions in atlas
    private final int regionIndex = -1;

    public void create() {
        batch = new SpriteBatch();
        img = new Texture(textureInternalPath);
        img.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        textureAtlas = new TextureAtlas("images/flappyBirdAtlas.atlas");

        gameOverImage = new Image(textureAtlas.findRegion("game over", regionIndex));
        gameOverImage.setY(200);
        startImage = new Image(textureAtlas.findRegion("play", regionIndex));

        startImage.setWidth(startImage.getWidth() / 4);
        startImage.setHeight(startImage.getHeight() / 4);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (Input.Keys.SPACE == keycode) {
                    birdUp();
                }
                return super.keyDown(event, keycode);
            }
        });

        bird = new Image(textureAtlas.findRegion("bird", regionIndex));
        bird.setWidth(100);
        bird.setHeight(100);
        bird.setY(300);
        bird.setX(50);
        bird.setZIndex(9);
        stage.addActor(bird);

        firstPipeImage = new Image(textureAtlas.findRegion("pipe 101", regionIndex));
        firstPipeImage.setZIndex(9);
        firstPipeImage.setWidth(150);
        firstPipeImage.setX(200);
        stage.addActor(firstPipeImage);

        firstReversedPipeImage = new Image(textureAtlas.findRegion("pipe 003", regionIndex));
        firstReversedPipeImage.setZIndex(9);
        firstReversedPipeImage.setWidth(150);
        firstReversedPipeImage.setX(200);
        firstReversedPipeImage.setY(383);
        stage.addActor(firstReversedPipeImage);


        secondPipeImage = new Image(textureAtlas.findRegion("pipe 102", regionIndex));
        secondPipeImage.setZIndex(9);
        secondPipeImage.setWidth(150);
        secondPipeImage.setX(450);
        stage.addActor(secondPipeImage);

        secondReversedPipeImage = new Image(textureAtlas.findRegion("pipe 002", regionIndex));
        secondReversedPipeImage.setZIndex(9);
        secondReversedPipeImage.setWidth(150);
        secondReversedPipeImage.setX(450);
        secondReversedPipeImage.setY(483);
        stage.addActor(secondReversedPipeImage);

        thirdPipeImage = new Image(textureAtlas.findRegion("pipe 103", regionIndex));
        thirdPipeImage.setZIndex(9);
        thirdPipeImage.setWidth(150);
        thirdPipeImage.setX(700);
        stage.addActor(thirdPipeImage);

        thirdReversedPipeImage = new Image(textureAtlas.findRegion("pipe 001", regionIndex));
        thirdReversedPipeImage.setZIndex(9);
        thirdReversedPipeImage.setWidth(150);
        thirdReversedPipeImage.setX(700);
        thirdReversedPipeImage.setY(583);
        stage.addActor(thirdReversedPipeImage);

        fourthPipeImage = new Image(textureAtlas.findRegion("pipe 102", regionIndex));
        fourthPipeImage.setZIndex(9);
        fourthPipeImage.setWidth(150);
        fourthPipeImage.setX(950);
        stage.addActor(fourthPipeImage);

        fourthReversedPipeImage = new Image(textureAtlas.findRegion("pipe 002", regionIndex));
        fourthReversedPipeImage.setZIndex(9);
        fourthReversedPipeImage.setWidth(150);
        fourthReversedPipeImage.setX(950);
        fourthReversedPipeImage.setY(483);
        stage.addActor(fourthReversedPipeImage);

        Array<Image> cloudImages = new Array<>();
        for (int i = 0; i < 5; i++) {
            Image image = new Image(textureAtlas.findRegion("cloud", regionIndex));

            image.setWidth(200);
            image.setHeight(100);
            image.setX((i == 0) ? 50 : (cloudImages.get(i - 1).getX() + 250));
            image.setY(600);
            image.setZIndex(99);

            cloudImages.add(image);

            stage.addActor(cloudImages.get(i));
        }
    }

    private void restart() {
       stage.clear();
        create();
    }

    private void birdUp() {
        bird.setY(bird.getY() + 30);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.41015625f, 0.6015625f, 0.7578125f, 1);
        stage.act(delta);
        for (Actor actor : stage.getActors()) {
            actor.setX((actor.getX() < 0) ? viewPortWidth : actor.getX() - step);
        }
        bird.setX(bird.getX() + step);
        bird.setY(bird.getY() - 1);

        gameOverImage.setX(200);
        startImage.setX(350);

        //building Box2D rectangles from actors to check collision with the bird actor
        Rectangle birdRectangle = new Rectangle(bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());

        for (Actor actor : stage.getActors()) {
            if (actor.equals(bird))
                continue;
            Rectangle actorRectangle = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());

            if (birdRectangle.overlaps(actorRectangle) || bird.getY() < 0) {
                finish();
            }
        }

        stage.draw();
    }

    private void finish() {

        stage.clear();

        startImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                restart();
                super.clicked(event, x, y);
            }
        });
        stage.addActor(gameOverImage);
        stage.addActor(startImage);
    }


    public void dispose() {
        stage.dispose();
    }
}

