package it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.simplemodel.State;
import it.polimi.ingsw.client.view.CLI.ProductionViewCLI;
import it.polimi.ingsw.client.view.abstractview.MiddlePhaseViewBuilder;
import it.polimi.ingsw.client.view.abstractview.ProductionViewBuilder;
import it.polimi.ingsw.network.simplemodel.SimpleCardShop;
import it.polimi.ingsw.network.simplemodel.SimplePlayerLeaders;
import it.polimi.ingsw.network.simplemodel.SimpleProductions;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MiddlePhaseGUI extends MiddlePhaseViewBuilder implements GUIView {
    public Button cardButton;
    public Button productionButton;
    public Button resourceMarketButton;
    public AnchorPane middleAnchor;

    /**
     * This runnable appends the scene to the current view
     */
    @Override
    public void run() {
        SubScene root=getRoot();
        root.setId("MIDDLE");
        GUI.getRealPane().getChildren().add(root);

        System.out.println(GUI.getRealPane().getChildren());

    }

    public SubScene getRoot() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MiddlePhase.fxml"));
        Parent root = null;
        try {
            root = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new SubScene(root,500,400);

    }



    public void sendChoice(Choice choice) {

        sendMessage(choice);
        GUI.removeLast();

    }

    /**
     * Scene buttons get bound to controller
     * @param url is ignored
     * @param resourceBundle is ignored
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //todo remove runlater
        cardButton.setOnAction( p ->
        {
            sendChoice(Choice.CARD_SHOP);
            SetupPhase.getBoard().refreshCardShop();
        });
        SimpleCardShop simpleCardShop=getSimpleModel().getElem(SimpleCardShop.class).orElseThrow();
        if(!simpleCardShop.getIsAnyCardPurchasable())
            cardButton.setDisable(true);

        productionButton.setOnAction( e ->
                sendChoice(Choice.PRODUCTION));

        resourceMarketButton.setOnAction( e ->
        {
            sendChoice(Choice.RESOURCE_MARKET);
            SetupPhase.getBoard().refreshMarket();

        });

        middleAnchor.setId("pane");
    }
}
