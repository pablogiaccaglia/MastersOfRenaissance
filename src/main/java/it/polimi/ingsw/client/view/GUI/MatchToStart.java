package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.CommonData;
import it.polimi.ingsw.client.simplemodel.State;
import it.polimi.ingsw.client.view.CLI.CLI;
import it.polimi.ingsw.client.view.CLI.layout.drawables.Canvas;
import it.polimi.ingsw.client.view.GUI.util.ResourceGUI;
import it.polimi.ingsw.client.view.abstractview.CreateJoinLoadMatchViewBuilder;
import it.polimi.ingsw.client.view.abstractview.IDLEViewBuilder;
import it.polimi.ingsw.client.view.abstractview.ResourceMarketViewBuilder;
import it.polimi.ingsw.client.view.abstractview.SetupPhaseViewBuilder;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Sphere;
import javafx.util.Duration;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.client.simplemodel.State.*;

/**
 * Waiting screen
 */
public class MatchToStart extends CreateJoinLoadMatchViewBuilder implements GUIView {

    @FXML
    private AnchorPane createPane;

    @Override
    public void run() {

        Node root=getRoot();
        root.setId("WAITING");
        GUI.getRealPane().getChildren().remove(0);
        GUI.getRealPane().getChildren().add(root);

        System.out.println(GUI.getRealPane().getChildren());

    }

    public SubScene getRoot() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MatchToStart.fxml"));
        Parent root = null;
        try {
            root = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new SubScene(root,1000,700);

    }

    /**
     * Basic loading animation
     * @param url is ignored
     * @param resourceBundle is ignored
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Sphere circle1=new Sphere();
        circle1.setRadius(10);
        circle1.setLayoutX(500);
        circle1.setLayoutY(100);
        Group group=new Group();

        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5),circle1);
        transition.setToX(0);
        transition.setToY(500);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.play();

        group.getChildren().add(circle1);


        Sphere circle2=new Sphere();
        circle2.setRadius(10);
        circle2.setLayoutX(500);
        circle2.setLayoutY(600);


        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1.5),circle2);
        transition2.setToX(0);
        transition2.setToY(-500);
        transition2.setAutoReverse(true);
        transition2.setCycleCount(Animation.INDEFINITE);
        transition2.play();



        group.getChildren().add(circle2);


        Sphere circle3=new Sphere();
        circle3.setRadius(10);
        circle3.setLayoutX(100);
        circle3.setLayoutY(350);


        TranslateTransition transition3 = new TranslateTransition(Duration.seconds(1.5),circle3);
        transition3.setToX(800);
        transition3.setToY(0);
        transition3.setAutoReverse(true);
        transition3.setCycleCount(Animation.INDEFINITE);
        transition3.play();

        group.getChildren().add(circle3);

        Sphere circle4=new Sphere();
        circle4.setRadius(10);
        circle4.setLayoutX(900);
        circle4.setLayoutY(350);

        TranslateTransition transition4 = new TranslateTransition(Duration.seconds(1.5),circle4);
        transition4.setToX(-800);
        transition4.setToY(0);
        transition4.setAutoReverse(true);
        transition4.setCycleCount(Animation.INDEFINITE);
        transition4.play();

        group.getChildren().add(circle4);

        RotateTransition rotateTransition=  new RotateTransition(Duration.seconds(3),group);
        rotateTransition.setByAngle(720);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.play();
        createPane.getChildren().add(group);
        getClient().getStage().show();


    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {


    }
}