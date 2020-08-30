package scenes;

import game.controllers.*;
import game.controllers.strategies.GreedyStrategy;
import game.controllers.strategies.MixedStrategy;
import game.controllers.strategies.RandomMoveStrategy;
import game.threads.JavaFXThreadRunner;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import util.sleeper.RealTimeSleeper;

import java.util.ArrayList;

import static application.Program.MainApp.gameScene;
import static application.Program.MainApp.primaryStage;

public class Settings extends Scene {
    private final static StackPane root = new StackPane();
    private final static VBox rootBox = new VBox(50);
    private final static VBox playerBoxes = new VBox(20);
    private Button btnNewPlayer;

    private final int MIN_PLAYERS = 2;
    private final int MAX_PLAYERS = 4;

    public void load() {
        ImageView logo = new ImageView(new Image("/logo.png"));

        Button btnPlay = new Button();
        btnPlay.setFont(Font.font(50));
        btnPlay.setText("Play");
        btnPlay.setOnAction(event -> {
            ArrayList<String> nicknames = new ArrayList<>();
            ArrayList<PlayerController> controllers = new ArrayList<>();

            for(int i=0; i<playerBoxes.getChildren().size()-1; i++) {
                nicknames.add(((TextField)((HBox)playerBoxes.getChildren().get(i)).getChildren().get(0)).getText());
                switch(((ChoiceBox<String>)((HBox)playerBoxes.getChildren().get(i)).getChildren().get(1)).getValue()) {
                    case "Human":
                        controllers.add(new HumanController());
                        break;
                    case "Easy Bot":
                        controllers.add(new BotController(new RandomMoveStrategy(), new RealTimeSleeper(),new JavaFXThreadRunner()));
                        break;
                    case "Medium Bot":
                        controllers.add(new BotController(new GreedyStrategy(), new RealTimeSleeper(),new JavaFXThreadRunner()));
                        break;
                    case "Hard Bot":
                        controllers.add(new BotController(new MixedStrategy(), new RealTimeSleeper(),new JavaFXThreadRunner()));
                        break;
                }
            }

            gameScene.load(nicknames, controllers);
            primaryStage.setScene(gameScene);
        });

        btnNewPlayer = new Button("+");
        btnNewPlayer.setOnAction(event -> addPlayerBox());
        playerBoxes.getChildren().add(btnNewPlayer);
        playerBoxes.setAlignment(Pos.CENTER);
        // adding 3 and removing 1 to trigger disabling buttons
        for(int i=0; i<3; i++) addPlayerBox();
        removePlayerBox((HBox)playerBoxes.getChildren().get(2));

        rootBox.getChildren().add(logo);
        rootBox.getChildren().addAll(playerBoxes);
        rootBox.getChildren().addAll(btnPlay);
        rootBox.setAlignment(Pos.CENTER);
        root.getChildren().add(rootBox);
    }

    private void addPlayerBox() {
        HBox hBox = new HBox(20);
        TextField textField = new TextField("Player " + playerBoxes.getChildren().size());
        ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Human", "Easy Bot", "Medium Bot","Hard Bot"));
        choiceBox.setValue("Human");
        Button btnRemove = new Button("-");
        btnRemove.setOnAction(event -> removePlayerBox(hBox));
        hBox.getChildren().addAll(textField, choiceBox, btnRemove);
        hBox.setAlignment(Pos.CENTER);

        playerBoxes.getChildren().add(playerBoxes.getChildren().size()-1,hBox);
        if(playerBoxes.getChildren().size() == MAX_PLAYERS+1) btnNewPlayer.setDisable(true);
        for(int i=0; i<playerBoxes.getChildren().size()-1; i++) {
            ((HBox)playerBoxes.getChildren().get(i)).getChildren().get(2).setDisable(false);
        }
    }

    private void removePlayerBox(HBox hBox) {
        playerBoxes.getChildren().remove(hBox);
        if(playerBoxes.getChildren().size() == MIN_PLAYERS+1) {
            for(int i=0; i<MIN_PLAYERS; i++) {
                ((HBox)playerBoxes.getChildren().get(i)).getChildren().get(2).setDisable(true);
            }
        }
        btnNewPlayer.setDisable(false);
    }

    public Settings(int width, int height) {
        super(root,width,height);
    }
}
