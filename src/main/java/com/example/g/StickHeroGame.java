package com.example.g;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.paint.*;
import javafx.geometry.Insets;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;


public class StickHeroGame extends Application {
    private GameEngine gameEngine;
    private GameUI gameUi;
    private ScoreSystem scoreSystem;
    private Pane root;
    private Scene scene;
    private GameFacade gameFacade;










    public static void main(String[] args) {
        launch(args);
    }
    void resart(){
        gameFacade.restartGame();

    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        scoreSystem=new ScoreSystem();
        scoreSystem.loadScores();
//        FXMLLoader fxm =new FXMLLoader(StickHeroGame.class.getResource("hello-view.fxm"));
        root = new Pane();
        scene = new Scene(root, 1550, 800);
        primaryStage.setTitle("Stick Hero Game");
        gameUi=new GameUI(scene);
        gameFacade = new GameFacade(gameEngine, gameUi);

        gameEngine = new GameEngine(gameUi,scoreSystem);gameUi.setGameEngine(gameEngine);gameEngine.getGameUI().setGameEngine(gameEngine);
        Double pl1=350.0;
        Double pl2=150.0;
        Double pl3=200.0;
        Double pl4=75.0;
        Double pl5=100.0;
        Double pl6=135.0;
        Double pl7=45.0;
        gameEngine.getPlatform().getPillars().add(pl1);
        gameEngine.getPlatform().getPillars().add(pl2);
        gameEngine.getPlatform().getPillars().add(pl3);
        gameEngine.getPlatform().getPillars().add(pl4);
        gameEngine.getPlatform().getPillars().add(pl5);
        gameEngine.getPlatform().getPillars().add(pl6);
        gameEngine.getPlatform().getPillars().add(pl7);
        gameEngine.getPlatform().getWidthList().add(150.0);
        gameEngine.getPlatform().getWidthList().add(280.0);
        gameEngine.getPlatform().getWidthList().add(220.0);
        gameEngine.getPlatform().getWidthList().add(170.0);
        gameEngine.getPlatform().getWidthList().add(145.0);
        gameEngine.getPlatform().getWidthList().add(100.0);
        gameEngine.getPlatform().getWidthList().add(160.0);
        gameEngine.getPlatform().getWidthList().add(390.0);
        gameEngine.getPlatform().getWidthList().add(410.0);
        gameEngine.getPlatform().getWidthList().add(125.0);
        gameEngine.getPlatform().getWidthList().add(350.0);
        gameEngine.getPlatform().getWidthList().add(200.0);
        gameEngine.getPlatform().getWidthList().add(250.0);






        Stop[] stops = new Stop[] { new Stop(0, Color.WHITESMOKE), new Stop(1, Color.DEEPSKYBLUE) };
        RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops);
        BackgroundFill backgroundFill = new BackgroundFill(radialGradient, CornerRadii.EMPTY, Insets.EMPTY);

        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/backvipul.jpg")).toExternalForm());

        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        Background b = new Background(backgroundImage);

        root.setBackground(b);
        gameUi.setY(scene.getHeight()-300);
        gameUi.createPreliminaryUI(scene);
        root.getChildren().add(gameUi.getPane());
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}

class GameEngine implements Runnable  {
    int f=0;
    private StickHeroCharacter character;
    private static GameUI gameUI;
    private ScoreSystem scoreSystem;
    private Platform platform;
    private  boolean pressed;

    boolean resume=false;

    public GameEngine() {

    }


    void handleStartButtonClick() {
        int ind1=(int)(Math.random()*7);
        int ind2=(int)(Math.random()*13);
        gameUI.gameLoop(ind1,ind2);


    }

    public GameEngine(GameUI gameUI,ScoreSystem s) {
        this.gameUI = gameUI;
        this.character = StickHeroCharacter.instanceClass();
        this.scoreSystem=s;
        this.platform=new Platform();
        this.pressed=false;
    }
    public void handleKeyPress(KeyCode keyCode ,double len) {
        AtomicBoolean isover= new AtomicBoolean(false);
        gameUI.getScene().setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("SPACE")) {
                gameUI.startMovingLine();
            } if (event.isControlDown() && event.getCode() == KeyCode.S) {
                // Save data when Ctrl + S is presse
                this.resume=true;
                GameEngine.onClickHome();
            }

            if (event.getCode().toString().equals("UP")) {
               f= this.getCharacter().flip1(f);

            }

        });

        gameUI.getScene().setOnKeyReleased(event -> {
            if (event.getCode().toString().equals("SPACE")) {
                gameUI.stopMovingLine();
                double stickLen=gameUI.getVerticalLine().getStartY()-gameUI.getVerticalLine().getEndY();
                if ((gameUI.getVerticalLine().getStartX()+stickLen<len)||(gameUI.getVerticalLine().getStartX()+stickLen> len+getPlatform().getP2())){
                    try {
                        gameUI.moveChar();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }else {
                    ImageView i=character.getImage();
                    final int[] j={1,(int)(len+platform.getP2()-50-i.getLayoutX())};
                    Timeline t1 =new Timeline();
                    KeyFrame k1=new KeyFrame(Duration.millis(5),event1 ->{
                        i.setLayoutX(i.getLayoutX()+1);
                        j[0]=j[0]+1;
                        if (j[0]==j[1]){
                            scoreSystem.setCurrentScore(scoreSystem.getCurrentScore()+1);
                            pressed=true;
                            t1.stop();
                        }
                    });
                    t1.getKeyFrames().add(k1);
                    t1.setCycleCount(Timeline.INDEFINITE);
                    t1.play();
                }
            }
        });
    }

    public void onClickRestart(){
        scoreSystem.setCurrentScore(0);
        gameUI.stopMusic();
        gameUI.playMusic("/images/song.mp3");
        scoreSystem.update(resume);
        handleStartButtonClick();
    }
    public void onClickContinue(){
        gameUI.getPane().getChildren().clear();
        gameUI.stopMusic();
        if (scoreSystem.getNoOfCherry()>=4){
            scoreSystem.setNoOfCherry(scoreSystem.getNoOfCherry()-4);
            gameUI.playMusic("/images/song.mp3");
            handleStartButtonClick();
        }else {
            Rectangle r1=new Rectangle(500,350,Color.LIGHTSLATEGRAY);r1.setLayoutX(450);r1.setLayoutY(60);
            gameUI.getPane().getChildren().add(r1);
           // Rectangle r1=new Rectangle(600,400,Color.LIGHTSLATEGRAY);r1.setLayoutX(500);r1.setLayoutY(60);
            r1.setArcWidth(20); // Adjust the corner radius as needed
            r1.setArcHeight(20);

            Label title = new Label("         Insufficient \n            cherries");
            title.setLayoutX((450));
            title.setLayoutY((100));

            title.setStyle(
                    "-fx-font-size: 45px;" +      // Set font size
                            "-fx-font-family: 'Lobster';" + // Set font family
                            "-fx-font-weight: bold;" +    // Set font weight
                            "-fx-text-fill: #000000;"     // Set text color
            );
            gameUI.getPane().getChildren().add(title);
            Button restart = new Button("Restart");
            restart.setLayoutX(550);
            restart.setLayoutY(300);
            restart.setScaleX(3);
            restart.setScaleY(2.85);

            restart.setStyle("-fx-background-color: #000000;");
            restart.setTextFill(Color.WHITE);
            restart.setOnAction(event -> this.onClickRestart());
            gameUI.getPane().getChildren().add(restart);

            Button home = new Button("Home");
            home.setLayoutX(800);
            home.setLayoutY(300);
            home.setScaleX(3);
            home.setScaleY(2.85);

            home.setStyle("-fx-background-color: #000000;");
            home.setTextFill(Color.WHITE);

            home.setOnAction(event -> this.onClickHome());
            gameUI.getPane().getChildren().add(home);
        }
    }
    public static void onClickHome(){

        gameUI.getPane().getChildren().clear();
        gameUI.createPreliminaryUI(gameUI.getScene());
    }
    public void onClickPause(){
        gameUI.scoresystemUi();
    }
    @Override
    public void run() {

    }

    public Platform getPlatform() {
        return platform;
    }
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
    public StickHeroCharacter getCharacter() {
        return character;
    }
    public void setCharacter(StickHeroCharacter character) {
        this.character = character;
    }
    public GameUI getGameUI() {
        return gameUI;
    }
    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
    public boolean isPressed() {
        return pressed;
    }
    public ScoreSystem getScoreSystem() {
        return scoreSystem;
    }
    public void setScoreSystem(ScoreSystem scoreSystem) {
        this.scoreSystem = scoreSystem;
    }
}

class GameUI  extends GameEngine implements Runnable {

    private Button saveButton;
    private Pane pane;
    private Scene scene;
    private GameEngine gameEngine;
    private double y;
    private Timeline timeline;
    private Line verticalLine;


    private MediaPlayer mediaPlayer;

    public void playMusic(String file_path) {
        Media sound = new Media(getClass().getResource(file_path).toString());
        mediaPlayer = new MediaPlayer(sound);

        // Set the audio to loop indefinitely
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Set the initial volume (0.5 is 50% volume)
        mediaPlayer.setVolume(1);

        // Start playing the music
        mediaPlayer.play();
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void run() {

    }

    public void createPreliminaryUI(Scene s) {

        gameEngine.getScoreSystem().update(gameEngine.resume);
        stopMusic();
        playMusic("/images/song.mp3");
        // Load the image
        Image playImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/play3.png")));

// Create an ImageView with the image
        ImageView i = new ImageView(playImage);
        i.setFitWidth(80);
        i.setFitHeight(50);

        Button startButton = new Button();
        startButton.setGraphic(i);
        Label highScoreLabel = new Label("High Score: " + gameEngine.getScoreSystem().getBestScore());
        highScoreLabel.setLayoutX(10);
        highScoreLabel.setLayoutY(5);
        highScoreLabel.setStyle(
                "-fx-font-size: 30px;" +
                        "-fx-font-family: 'Lobster';" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #346688;"
        );
        pane.getChildren().add(highScoreLabel);
        // Set the size of the button to match the size of the image
        startButton.setMinSize(i.getFitWidth(), i.getFitHeight());
        startButton.setMaxSize(i.getFitWidth(), i.getFitHeight());

        startButton.setLayoutX((s.getWidth() - i.getFitWidth()) / 2);
        startButton.setLayoutY((s.getHeight() - i.getFitHeight()) / 2 + 100);
        startButton.setScaleX(3);
        startButton.setScaleY(2.85);

        startButton.setOnAction(event -> gameEngine.handleStartButtonClick());
        pane.getChildren().add(startButton);


        Button pause = new Button("STAT");
        pause.setStyle("-fx-background-color: #000000;");
        pause.setTextFill(Color.FLORALWHITE);
        Image playImagee = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/graphh.png")));

// Create an ImageView with the image
//        ImageView ii = new ImageView(playImagee);
//        ii.setFitWidth(60);
//        ii.setFitHeight(40);
//
//
//        pause.setGraphic(ii);
//
//        // Set the size of the button to match the size of the image
//        pause.setMinSize(ii.getFitWidth(), ii.getFitHeight());
//        pause.setMaxSize(ii.getFitWidth(), ii.getFitHeight());

        pause.setLayoutX(scene.getWidth() - 100);

        pause.setLayoutY(50);
        pause.setScaleX(3);
        pause.setScaleY(2.85);
        pause.setOnAction(event -> gameEngine.onClickPause());
        pane.getChildren().add(pause);

        Label title = new Label("Stick Hero Game");
        title.setFont(new Font(94));
        title.setLayoutX((s.getWidth() - startButton.getWidth()) / 2 - 200);
        title.setLayoutY((s.getHeight() - startButton.getHeight()) / 2 - 200);
        title.setStyle(
                "-fx-font-size: 54px;" +
                        "-fx-font-family: 'Lobster';" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #346688;"
        );

        pane.getChildren().add(title);
        Rectangle st = new Rectangle(350, 400, Color.BLACK);
        st.setLayoutY(y);
        st.setLayoutX(0);
        pane.getChildren().add(st);
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/hero_white.png")).toExternalForm());

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(75);
        gameEngine.getCharacter().setImage(imageView);
        gameEngine.getCharacter().getImage().setLayoutX(300);
        gameEngine.getCharacter().getImage().setLayoutY(y - 75);
        pane.getChildren().add(gameEngine.getCharacter().getImage());
        gameEngine.getPlatform().setP1(350);
        gameEngine.getPlatform().setY(y);

    }

    public GameUI(Scene root) {
        super();
        this.pane = new Pane();
        scene = root;



}
    public void gameLoop(int ind1, int ind2) {


        pane.getChildren().clear();
        Label title1 = new Label("Score");
        title1.setLayoutX((scene.getWidth() / 2 - 100));
        title1.setLayoutY((scene.getHeight() / 2 - 250));
        title1.setStyle(
                "-fx-font-size: 54px;" +
                        "-fx-font-family: 'Lobster';" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #346688;"
        );
        this.getPane().getChildren().add(title1);
//
//        saveButton = new Button("Save");
//        saveButton.setLayoutX(scene.getWidth() - 200);
//        saveButton.setLayoutY(50);
//        saveButton.setScaleX(3);
//        saveButton.setScaleY(2.85);
//        saveButton.setStyle("-fx-background-color: #000000;");
//        saveButton.setTextFill(Color.WHITE);
//        //saveButton.setOnAction(event -> gameEngine.onClickSave());
//        this.getPane().getChildren().add(saveButton);
//

        Label title2 = new Label("Cherries");
        title2.setLayoutX((50));
        title2.setLayoutY((20));
        title2.setStyle(
                "-fx-font-size: 30px;" +      // Set font size
                        "-fx-font-family: 'Lobster';" + // Set font family
                        "-fx-font-weight: bold;" +    // Set font weight
                        "-fx-text-fill: #346688;"     // Set text color
        );
        this.getPane().getChildren().add(title2);





        Label title3 = new Label(gameEngine.getScoreSystem().getCurrentScore() + "");
        title3.setLayoutX((title1.getLayoutX() + 65));
        title3.setLayoutY((title1.getLayoutY() + 65));
        title3.setStyle(
                "-fx-font-size: 40px;" +      // Set font size
                        "-fx-font-family: 'Lobster';" + // Set font family
                        "-fx-font-weight: bold;" +    // Set font weight
                        "-fx-text-fill: #346688;"     // Set text color
        );
        this.getPane().getChildren().add(title3);
        Label title4 = new Label("" + gameEngine.getScoreSystem().getNoOfCherry());
        title4.setLayoutX((100));
        title4.setLayoutY((50));
        title4.setStyle(
                "-fx-font-size: 30px;" +      // Set font size
                        "-fx-font-family: 'Lobster';" + // Set font family
                        "-fx-font-weight: bold;" +    // Set font weight
                        "-fx-text-fill: #346688;"     // Set text color
        );
        this.getPane().getChildren().add(title4);
        Platform p = gameEngine.getPlatform();
        ImageView i = gameEngine.getCharacter().getImage();
        i.setLayoutX(p.getP1() - 50);
        i.setLayoutY(y - 75);
        this.getPane().getChildren().add(i);
        p.setP2(p.getPillars().get(ind1));
        double len = p.getP1() + p.getWidthList().get(ind2);
        Rectangle rec1 = new Rectangle(p.getP1(), 400, Color.BLACK);
        rec1.setLayoutX(0);
        rec1.setLayoutY(this.y);
        Rectangle rec2 = new Rectangle(p.getP2(), 400, Color.BLACK);
        rec2.setLayoutX(len);
        rec2.setLayoutY(this.y);
        this.getPane().getChildren().add(rec1);
        this.getPane().getChildren().add(rec2);
        double b = Math.random() * (p.getWidthList().get(ind2));
        Cherry cherry = new Cherry();
        Random r = new Random();
        int a = r.nextInt(0, 2);
        System.out.println(a);
        if (a == 1) {
            cherry.getImage().setLayoutX(gameEngine.getCharacter().getImage().getLayoutX() + 60 + b);
            cherry.getImage().setFitWidth(20);
            cherry.getImage().setLayoutY(this.y - 30);
            cherry.getImage().setFitHeight(20);
            gameEngine.getPlatform().setCherry(cherry);
            pane.getChildren().add(gameEngine.getPlatform().getCherry().getImage());
            gameEngine.getScoreSystem().setNoOfCherry(gameEngine.getScoreSystem().getNoOfCherry() + 1);
        }
        timeline = new Timeline();
        verticalLine = new Line(gameEngine.getPlatform().getP1(), gameEngine.getPlatform().getY(), gameEngine.getPlatform().getP1(), gameEngine.getPlatform().getY());
        this.getPane().getChildren().add(verticalLine);
        gameEngine.handleKeyPress(KeyCode.SPACE, len);
        p.setP1(p.getP2());
        ind1 = (int) (Math.random() * 7);
        ind2 = (int) (Math.random() * 13);
        final int[] indices = new int[]{ind1, ind2};
        Timeline t2 = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), event -> {
            if (gameEngine.isPressed()) {
                t2.stop();
                gameLoop(indices[0], indices[1]);
                gameEngine.setPressed(false);
            }
        });

        t2.getKeyFrames().add(keyFrame);
        t2.setCycleCount(Timeline.INDEFINITE);
        t2.play();



    }

    public void startMovingLine() {
        verticalLine.setStrokeWidth(5);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(50), event -> {
            verticalLine.setEndY(verticalLine.getEndY() - 1);
        });

        // Configure the timeline to repeat indefinitely
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Start the timeline
        timeline.play();
    }


    public void stopMovingLine() {
        // Stop the timeline
        timeline.stop();
        KeyFrame k1 = new KeyFrame(Duration.millis(10), event1 -> {
            verticalLine.getTransforms().add(new Rotate(9, verticalLine.getStartX(), verticalLine.getStartY()));
            ;
        });
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(k1);
        timeline.setCycleCount(10);
        timeline.play();
//
//            verticalLine.getTransforms().add(new Rotate(90,verticalLine.getStartX(), verticalLine.getStartY()));
    }

    public void moveChar() throws InterruptedException {
        int len = (int) (-verticalLine.getEndY() + verticalLine.getStartY() + 50);
        ImageView i = gameEngine.getCharacter().getImage();
        KeyFrame k1 = new KeyFrame(Duration.millis(5), event1 -> {
            i.setLayoutX(i.getLayoutX() + 1);
        });
        Timeline t1 = new Timeline(k1);
        t1.setCycleCount(len - 50);
        // Add an event handler to be executed when the timeline finishes
        t1.play();
        t1.setOnFinished(event -> {

            KeyFrame k2 = new KeyFrame(Duration.millis(5), event1 -> {
                i.setLayoutY(i.getLayoutY() + 1);
            });
            Timeline t2 = new Timeline(k2);
            t2.setCycleCount(350);
            t2.play();


            // Check for game over after the character reaches the end of the line
            this.isOverUi();
        });


    }

    public void isOverUi() {
        stopMusic();
        playMusic("/images/pause.mp3");

        Rectangle r1 = new Rectangle(600, 400, Color.LIGHTSLATEGRAY);
        r1.setLayoutX(500);
        r1.setLayoutY(60);
        r1.setArcWidth(20); // Adjust the corner radius as needed
        r1.setArcHeight(20);
        pane.getChildren().add(r1);

        Label title = new Label("Game Over");
        title.setLayoutX((660));
        title.setLayoutY((70));
        title.setStyle(
                "-fx-font-size: 60px;" +      // Set font size
                        "-fx-font-family: 'Lobster';" + // Set font family
                        "-fx-font-weight: bold;" +    // Set font weight
                        "-fx-text-fill: #FFFFFF;"     // Set text color
        );
        this.getPane().getChildren().add(title);

        Button cont = new Button("Continue(4 Cheries)");
        cont.setLayoutX(750);
        cont.setLayoutY(250);
        cont.setScaleX(3);
        cont.setScaleY(2.85);

        cont.setStyle("-fx-background-color: #000000;");
        cont.setTextFill(Color.WHITE);


        cont.setOnAction(event -> gameEngine.onClickContinue());
        pane.getChildren().add(cont);

        Button restart = new Button("Restart");
        restart.setLayoutX(650);
        restart.setLayoutY(360);
        restart.setScaleX(3);
        restart.setScaleY(2.85);
        restart.setStyle("-fx-background-color: #000000;");
        restart.setTextFill(Color.WHITE);

        restart.setOnAction(event -> gameEngine.onClickRestart());
        pane.getChildren().add(restart);

        Button home = new Button("Home");
        home.setLayoutX(930);
        home.setLayoutY(360);
        home.setScaleX(3);
        home.setScaleY(2.85);
        home.setStyle("-fx-background-color: #000000;");
        home.setTextFill(Color.WHITE);
        home.setOnAction(event -> { gameEngine.getScoreSystem().setCurrentScore(0);
            gameEngine.onClickHome();});
        pane.getChildren().add(home);
    }

    public void scoresystemUi() {
        pane.getChildren().clear();
        Rectangle r1 = new Rectangle(570, 500, Color.LIGHTSLATEGRAY);
        r1.setLayoutX(400);
        r1.setLayoutY(200);

        r1.setArcWidth(20); // Adjust the corner radius as needed
        r1.setArcHeight(20);
        pane.getChildren().add(r1);

        Label title = new Label("Score Board");
        title.setLayoutX((600));
        title.setLayoutY((200));
        title.setStyle(
                "-fx-font-size: 40px;" +      // Set font size
                        "-fx-font-family: 'Lobster';" + // Set font family
                        "-fx-font-weight: bold;" +    // Set font weight
                        "-fx-text-fill: #346688;"     // Set text color
        );
        this.getPane().getChildren().add(title);

        Label title1 = new Label("  Highest Score  :     " + gameEngine.getScoreSystem().getBestScore());
        title1.setLayoutX((400));
        title1.setLayoutY((300));
        title1.setStyle(
                "-fx-font-size: 40px;" +      // Set font size
                        "-fx-font-family: 'Lobster';" + // Set font family
                        "-fx-font-weight: bold;" +    // Set font weight
                        "-fx-text-fill: #346688;"     // Set text color
        );
        this.getPane().getChildren().add(title1);

        Label title11 = new Label("  Last score  :     " + gameEngine.getScoreSystem().getLastscore());
        title11.setLayoutX((400));
        title11.setLayoutY((400));
        title11.setStyle(
                "-fx-font-size: 40px;" +      // Set font size
                        "-fx-font-family: 'Lobster';" + // Set font family
                        "-fx-font-weight: bold;" +    // Set font weight
                        "-fx-text-fill: #346688;"     // Set text color
        );
        this.getPane().getChildren().add(title11);

        Label title2 = new Label("  No Of cherries  :     " + gameEngine.getScoreSystem().getNoOfCherry());
        title2.setLayoutX((400));
        title2.setLayoutY((500));
        title2.setStyle(
                "-fx-font-size: 40px;" +      // Set font size
                        "-fx-font-family: 'Lobster';" + // Set font family
                        "-fx-font-weight: bold;" +    // Set font weight
                        "-fx-text-fill: #346688;"     // Set text color
        );
        this.getPane().getChildren().add(title2);


        Button home = new Button("Home");
        home.setStyle("-fx-background-color: #000000;");
        home.setTextFill(Color.FLORALWHITE);

        home.setLayoutX(650);
        home.setLayoutY(600);
        home.setScaleX(3);
        home.setScaleY(2.85);
        home.setOnAction(event -> gameEngine.onClickHome());
        pane.getChildren().add(home);
    }

    public Pane getPane() {
        return pane;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene rootPane) {
        this.scene = rootPane;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public Line getVerticalLine() {
        return verticalLine;
    }
}
interface gamplay{
    void start();
}

class StickHeroCharacter {
    private int flipp=0;
    private int x,y;
    private boolean isInverted;
    private ImageView image;

    private static StickHeroCharacter hero=null;

    //Singleton design pattern
    public static synchronized StickHeroCharacter instanceClass(){
        if (hero==null){
            hero= new StickHeroCharacter();
            return hero;
        }
        return hero;
    }


    private StickHeroCharacter() {
        isInverted=false;
        x=0;
    }

    public int flip1(int x) {
        int z=0;
        if(x==0) {

            // Invert the character's image vertically
            image.setScaleY(-1);
            image.setY(image.getY() + 60);
            z=1;
        }
        if(x==1) {


            // Invert the character's image vertically
            image.setScaleY(1);
            image.setY(image.getY() - 60);
            z=0;
        }
        return z;


    }
    public void flip2(int x) {
        if(x==1) {

            // Invert the character's image vertically
            image.setScaleY(1);
            image.setY(image.getY() - 60);
        }
    }
    public ImageView getImage() {
        return image;
    }
    public void setImage(ImageView image) {
        this.image = image;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public boolean isInverted() {
        return isInverted;
    }
    public void setInverted(boolean inverted) {
        isInverted = inverted;
    }

    public void flipUp() {
        image.setScaleY(-1);
    }

    public void resetFlip() {
        image.setScaleY(1);
    }
}
class ScoreSystem  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int bestScore;
    private int currentScore;
    private int noOfCherry;
    private int initCherry;
    private  int lastscore=0;

    public int getLastscore() {
        return lastscore;
    }

    public void setLastscore(int lastscore) {
        this.lastscore = lastscore;
    }

    public int getBestScore() {
        return bestScore;
    }
    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }
    public int getCurrentScore() {
        return currentScore;
    }
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }
    public int getNoOfCherry() {
        return noOfCherry;
    }
    public void setNoOfCherry(int noOfCherry) {
        this.noOfCherry = noOfCherry;
    }
    public int getInitCherry() {
        return initCherry;
    }
    public void setInitCherry(int initCherry) {
        this.initCherry = initCherry;
    }

    public void saveScores() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/java/com/example/g/scores.ser"))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadScores() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/java/com/example/g/scores.ser"))) {
            ScoreSystem savedScores = (ScoreSystem) in.readObject();
            this.bestScore = savedScores.bestScore;
            this.noOfCherry = savedScores.noOfCherry;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }}

    public ScoreSystem() {
        this.bestScore = 0;
        this.currentScore = 0;
        this.noOfCherry=0;
        this.initCherry=0;

    }

    public void update(boolean resume){
        if (currentScore>bestScore){
            bestScore=currentScore;

        }
        initCherry=noOfCherry;
        lastscore=currentScore;
        if(resume==false){

            currentScore=0;

        }


        this.saveScores();
    }

}
class Platform {
    private Cherry cherry;
    private double pillar1Width;
    private double pillar2Width;
    private ArrayList<Double> pillars = new ArrayList<>();
    private ArrayList<Double> widthList = new ArrayList<>();
    private double x, y;

    public Platform() {
    }
    public Cherry getCherry() {
        return cherry;
    }
    public void setCherry(Cherry cherry) {
        this.cherry = cherry;
    }
    public double getP1() {
        return pillar1Width;
    }
    public void setP1(double p1) {
        this.pillar1Width = p1;
    }
    public double getP2() {
        return pillar2Width;
    }
    public void setP2(double p2) {
        this.pillar2Width = p2;
    }
    public ArrayList<Double> getPillars() {
        return pillars;
    }
    public void setPillars(ArrayList<Double> pillars) {
        this.pillars = pillars;
    }
    public ArrayList<Double> getWidthList() {
        return widthList;
    }
    public void setWidthList(ArrayList<Double> widthList) {
        this.widthList = widthList;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
}

class Cherry extends Platform {
    private final ImageView image;

    public Cherry() {
        Image i = new Image(Objects.requireNonNull(getClass().getResource("/images/CHERRY Background Removed.png")).toExternalForm());
        image = new ImageView(i);
    }


    public ImageView getImage() {
        return image;
    }


}

