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
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.StackPane;
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
    private Button btnInstructions;
    private Button btnExit;
    private Stage window;
    private Scene menu, instructions, game;

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
        Image img = new Image("images/jozsi.png");
        ImageView imgView = new ImageView(img);
        imgView.setTranslateY(600 - 39);
        imgView.setTranslateX(400 - 38);

        return imgView;
    }

    private Node spawnBugs() {
        Image img = new Image("images/bugicon.png");
        ImageView imgView = new ImageView(img);
        imgView.setTranslateY((int)(Math.random() * 14) * 40);

        root.getChildren().add(imgView);

        return imgView;
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
                codecooler.setTranslateX(400 - 38);
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("T&T Studios Alert");
        alert.setHeaderText("Do you really want to run that file? Are you sure?");
        alert.setContentText("You are insane if you click this button:");

        alert.showAndWait();

        //Scene menu
        Label label1 = new Label("Welcome codecooler!");
        Label label2 = new Label("\"The best gaming experience since Super Mario\" - Washington Post");

        Button btnPlay = new Button("Play");
        btnPlay.setOnAction(e -> window.setScene(game));

        Button btnInstructions = new Button(("Instructions"));
        btnInstructions.setOnAction(e -> window.setScene(instructions));

        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e -> System.exit(0));

        VBox layout1 = new VBox(20);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(label1, btnPlay, btnInstructions, btnExit, label2);

        menu = new Scene(layout1, 800, 600);

        menu.getStylesheets().add(CodecoolerVsBugs.class.getResource("images/menu.css").toExternalForm());

        menu.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE:
                    System.exit(0);
                    break;
                case I:
                    window.setScene(instructions);
                    break;
                case P:
                    window.setScene(game);
                    break;
            }
        });

        //Scene instructions
        Label label3 = new Label("You will control our fictional character, just name him for example : \"Józsi\". \n\n" + "He is a very important person, he got the code of life. Help him to deliver it!" +
                "\n\nPress \"A\", \"S\", \"D\", \"W\" to move.\n\n" +
                "Press \"ESC\" for the main menu.\n\n" +
                "Good luck!");
        
        VBox layout2 = new VBox(20);
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().addAll(label3);

        instructions = new Scene(layout2, 800, 600);

        instructions.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE:
                    window.setScene(menu);
                    break;
            }
        });

        instructions.getStylesheets().add(CodecoolerVsBugs.class.getResource("images/menu.css").toExternalForm());

        // Scene game
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
