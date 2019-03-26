package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    private BorderPane root;

    private static MainPane mainPane;

    @Override
    public void start(Stage primaryStage) throws Exception{

        root = new BorderPane();
        Scene scene = new Scene(root, 368, 423, Color.WHITE);

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menuBar);
        mainPane = new MainPane();
        root.setCenter(mainPane);

        Menu fileMenu = new Menu("File");
        MenuItem newGameMenuItem = new MenuItem("New Game");
        MenuItem saveGameMenuItem = new MenuItem("Save");

        fileMenu.getItems().add(newGameMenuItem);
        fileMenu.getItems().add(saveGameMenuItem);

        newGameMenuItem.setOnAction(actionEvent -> {
            mainPane = new MainPane();
            root.setCenter(mainPane);
            mainPane.saveGame();
        } );
        saveGameMenuItem.setOnAction(actionEvent -> mainPane.saveGame() );

        menuBar.getMenus().addAll(fileMenu);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
