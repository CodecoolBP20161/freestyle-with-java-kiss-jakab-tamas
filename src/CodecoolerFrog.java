        import javafx.animation.AnimationTimer;
        import javafx.animation.FadeTransition;
        import javafx.application.Application;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.geometry.Pos;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.Pane;
        import javafx.scene.layout.VBox;
        import javafx.scene.paint.Color;
        import javafx.scene.shape.Rectangle;
        import javafx.scene.text.Font;
        import javafx.scene.text.Text;
        import javafx.stage.Stage;
        import javafx.util.Duration;
        import java.util.ArrayList;
        import java.util.List;


public class CodecoolerFrog extends Application implements EventHandler<ActionEvent>{

    private Button btnPlay;
    private Button btnExit;
    private Stage window;
    private Scene menu, game;

    private AnimationTimer timer;

    private Pane root;

    private List<Node> cars = new ArrayList<>();
    private Node frog;

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(800, 600);

        frog = initFrog();

        root.getChildren().add(frog);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return root;
    }

    private Node initFrog() {
        Rectangle rect = new Rectangle(38, 38, Color.GREEN);
        rect.setTranslateY(600 - 39);

        return rect;
    }

    private Node spawnCar() {
        Rectangle rect = new Rectangle(40, 40, Color.RED);
        rect.setTranslateY((int)(Math.random() * 14) * 40);

        root.getChildren().add(rect);
        return rect;
    }

    private void onUpdate() {
        for (Node car : cars)
            car.setTranslateX(car.getTranslateX() + Math.random() * 10);

        if (Math.random() < 0.075) {
            cars.add(spawnCar());
        }

        checkState();
    }

    private void checkState() {
        for (Node car : cars) {
            if (car.getBoundsInParent().intersects(frog.getBoundsInParent())) {
                frog.setTranslateX(0);
                frog.setTranslateY(600 - 39);
                return;
            }
        }

        if (frog.getTranslateY() <= 0) {
            timer.stop();
            String win = "YOU WIN codecooler!";

            HBox hBox = new HBox();
            hBox.setTranslateX(120);
            hBox.setTranslateY(250);
            root.getChildren().add(hBox);

            for (int i = 0; i < win.toCharArray().length; i++) {
                char letter = win.charAt(i);

                Text text = new Text(String.valueOf(letter));
                text.setFont(Font.font(50));
                text.setOpacity(0);

                hBox.getChildren().add(text);

                FadeTransition ft = new FadeTransition(Duration.seconds(0.66), text);
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
        Label label1 = new Label("This is a frog vs cars game, have fun!");
        Label label2 = new Label("\"The best gaming experience since Super Mario\" - Washington Post");

        Button btnPlay = new Button("Play");
        btnPlay.setOnAction(e -> window.setScene(game));

        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e -> System.exit(0));

        VBox layout1 = new VBox(20);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(label0, label1, btnPlay, btnExit, label2);
        menu = new Scene(layout1,800, 600);

        menu.getStylesheets().add(CodecoolerFrog.class.getResource("images/menu.css").toExternalForm());

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

        game.getStylesheets().add(CodecoolerFrog.class.getResource("images/game.css").toExternalForm());

        game.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    frog.setTranslateY(frog.getTranslateY() - 40);
                    break;
                case S:
                    frog.setTranslateY(frog.getTranslateY() + 40);
                    break;
                case A:
                    frog.setTranslateX(frog.getTranslateX() - 40);
                    break;
                case D:
                    frog.setTranslateX(frog.getTranslateX() + 40);
                    break;
                case ESCAPE: window.setScene(menu);
                default:
                    break;
            }
        });

        window.setScene(menu);
        window.setTitle("T&T Studios represents, The amazing frog game");
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {
    }
}
