package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.abstractview.ViewBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The GUIStarter will call this method. The first stage will be replaced each view transition, and all the buttons and containers
 * already defined in the FXML files will be given a function accordingly. Each time the stage is replaced, the client will be
 * referenced through static method.
 */
public class GUI extends Application {

    public static double GUIwidth=1800;
    public static double GUIlen=1000;
    public static Node appendedScene;

    static String[] arguments;
    public static void main(String[] args) {
        arguments = args;
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Client client = ViewBuilder.getClient();
        client.setStage(stage);
        stage.setTitle("Maestri");
        stage.setResizable(false);
        stage.centerOnScreen();

        //INITIAL SCREEN BOOTSTRAP, THEN UPDATE IS LEFT TO ChangeViewBuilder
        Scene scene = new Scene(getRealPane());

        new StartingScreen().run();

        client.getStage().setScene(scene);
        client.getStage().getScene().getStylesheets().add("assets/application.css");
        client.getStage().show();
        client.run();

    }

    private static StackPane realPane = null;

    public static StackPane getRealPane()
    {
        if (realPane == null)
        {
            realPane = new StackPane();
            realPane.setPrefHeight(GUIlen);
            realPane.setPrefWidth(GUIwidth);

        }

        return realPane;
    }

    public static void removeLast()
    {
        Runnable run = new Runnable() {
            public void run() {
                realPane.getChildren().remove(appendedScene);
            };
        };
        Platform.runLater(run);
    }

    public static void addLast(Node scene)
    {
        if(realPane.getChildren().size()<3)
        {
            appendedScene=scene;
            realPane.getChildren().add(appendedScene);
        }

    }


}
