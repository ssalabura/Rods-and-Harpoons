package scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import util.GameInfo;

import static application.Program.MainApp.*;

public class MainMenu extends Scene {
    private final static StackPane root = new StackPane();
    private final static VBox rootBox = new VBox(50);
    private final static VBox buttonsBox = new VBox(20);
    private Button btnContinue;

    public void load() {
        ImageView logo = new ImageView(new Image("/logo.png"));

        Button btnNewGame = new Button("New Game");
        btnNewGame.setFont(Font.font(40));
        btnNewGame.setOnAction(event -> primaryStage.setScene(settings));

        btnContinue = new Button("Continue");
        btnContinue.setFont(Font.font(20));
        try {
            GameInfo savedGame = jsonSavefile.loadGame();
            btnContinue.setDisable(savedGame.isGameFinished());
            btnContinue.setOnAction(event -> {
                gameScene.load(savedGame);
                primaryStage.setScene(gameScene);
            });

        } catch (Exception e) {
            btnContinue.setDisable(true);
        }

        Button btnMatchHistory = new Button("Match History");
        btnMatchHistory.setFont(Font.font(20));
        btnMatchHistory.setOnAction(event -> primaryStage.setScene(matchHistory));

        Button btnHighScores = new Button("High Scores");
        btnHighScores.setFont(Font.font(20));
        btnHighScores.setOnAction(event -> {
            highScores.refresh();
            primaryStage.setScene(highScores);
        });

        Button btnExit = new Button("Exit");
        btnExit.setFont(Font.font(20));
        btnExit.setOnAction(event -> System.exit(0));

        buttonsBox.getChildren().addAll(btnNewGame, btnContinue, btnMatchHistory, btnHighScores, btnExit);
        buttonsBox.setAlignment(Pos.CENTER);
        rootBox.getChildren().addAll(logo, buttonsBox);
        rootBox.setAlignment(Pos.CENTER);
        root.getChildren().add(rootBox);
    }

    public void refresh() {
        try {
            GameInfo savedGame = jsonSavefile.loadGame();
            btnContinue.setDisable(savedGame.isGameFinished());
            btnContinue.setOnAction(event -> {
                gameScene.load(savedGame);
                primaryStage.setScene(gameScene);
            });

        } catch (Exception e) {
            btnContinue.setDisable(true);
        }
    }

    public MainMenu(int width, int height) {
        super(root,width,height);
    }
}
