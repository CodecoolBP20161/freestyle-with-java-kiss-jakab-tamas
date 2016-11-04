        import javafx.animation.AnimationTimer;
        import javafx.animation.FadeTransition;
        import javafx.application.Application;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.geometry.Pos;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.Pane;
        import javafx.scene.layout.VBox;
        import javafx.scene.paint.Color;
        import javafx.scene.shape.Rectangle;
        import javafx.scene.text.Font;
        import javafx.scene.text.FontWeight;
        import javafx.scene.text.Text;
        import javafx.stage.Stage;
        import javafx.util.Duration;
        import java.util.ArrayList;
        import java.util.List;


public class CodecoolerVsBugs extends Application implements EventHandler<ActionEvent>{

    private Button btnPlay;
    private Button btnExit;
    private Stage window;
    private Scene menu, game;

    private AnimationTimer timer;

    private Pane root;

    private List<Node> bugs = new ArrayList<>();
    private Node codecooler;

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(800, 600);

        codecooler = initCodecooler();

        root.getChildren().add(codecooler);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return root;
    }

    private Node initCodecooler() {
        Rectangle rect = new Rectangle(38, 38, Color.BLUE);
        rect.setTranslateY(600 - 39);

        return rect;
    }

    private Node spawnBugs() {
        Rectangle rect = new Rectangle(40, 40, Color.BLACK);
        rect.setTranslateY((int)(Math.random() * 14) * 40);

        root.getChildren().add(rect);
        return rect;
    }

    private void onUpdate() {
        for (Node bug : bugs)
            bug.setTranslateX(bug.getTranslateX() + Math.random() * 15);

        if (Math.random() < 0.075) {
            bugs.add(spawnBugs());
            bugs.add(spawnBugs());
        }

        checkState();
    }

    private void checkState() {
        for (Node bug : bugs) {
            if (bug.getBoundsInParent().intersects(codecooler.getBoundsInParent())) {
                codecooler.setTranslateX(0);
                codecooler.setTranslateY(600 - 39);
                return;
            }
        }

        if (codecooler.getTranslateY() <= 0) {
            timer.stop();
            String win = "YOU WIN codecooler!";

            HBox hBox = new HBox();
            hBox.setTranslateX(120);
            hBox.setTranslateY(250);
            root.getChildren().add(hBox);

            for (int i = 0; i < win.toCharArray().length; i++) {
                char letter = win.charAt(i);

                Text codecoolerWinsText = new Text(String.valueOf(letter));
                codecoolerWinsText.setFont(Font.font ("Verdana", FontWeight.BOLD, 50));
                codecoolerWinsText.setFill(Color.BLANCHEDALMOND);
                codecoolerWinsText.setOpacity(0);

                hBox.getChildren().add(codecoolerWinsText);
                hBox.setStyle("-fx-background-color: #336699;");

                FadeTransition ft = new FadeTransition(Duration.seconds(0.66), codecoolerWinsText);
                ft.setToValue(1);
                ft.setDelay(Duration.seconds(i * 0.15));
                ft.play();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        Label label0 = new Label("Welcome!");
        Label label1 = new Label("This is a codecooler vs bugs game, have fun!");
        Label label2 = new Label("\"The best gaming experience since Super Mario\" - Washington Post");

        Button btnPlay = new Button("Play");
        btnPlay.setOnAction(e -> window.setScene(game));

        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e -> System.exit(0));

        VBox layout1 = new VBox(20);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(label0, label1, btnPlay, btnExit, label2);
        menu = new Scene(layout1,800, 600);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("T&T Studios Alert");
        alert.setHeaderText("Do you really want to run that file? Are you sure?");
        alert.setContentText("You are insane if you click this button:");

        alert.showAndWait();

        menu.getStylesheets().add(CodecoolerVsBugs.class.getResource("images/menu.css").toExternalForm());

        menu.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE:
                    System.exit(0);
                    break;
                case P:
                    window.setScene(game);
                    break;
            }
        });

        game = new Scene(createContent());

        game.getStylesheets().add(CodecoolerVsBugs.class.getResource("images/game.css").toExternalForm());

        game.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    codecooler.setTranslateY(codecooler.getTranslateY() - 40);
                    break;
                case S:
                    codecooler.setTranslateY(codecooler.getTranslateY() + 40);
                    break;
                case A:
                    codecooler.setTranslateX(codecooler.getTranslateX() - 40);
                    break;
                case D:
                    codecooler.setTranslateX(codecooler.getTranslateX() + 40);
                    break;
                case ESCAPE: window.setScene(menu);
                default:
                    break;
            }
        });

        window.setScene(menu);
        window.setTitle("T&T Studios represents");
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {
    }
}
