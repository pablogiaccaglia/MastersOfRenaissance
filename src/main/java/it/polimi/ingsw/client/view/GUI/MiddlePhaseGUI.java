package it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.simplemodel.State;
import it.polimi.ingsw.client.view.abstractview.MiddlePhaseViewBuilder;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MiddlePhaseGUI extends MiddlePhaseViewBuilder implements GUIView {
    public Button cardButton;
    public Button productionButton;
    public Button resourceMarketButton;

    /**
     * This runnable appends the scene to the current view
     */
    @Override
    public void run() {
        SubScene toadd=getRoot();
        toadd.setId("MIDDLE");
        ((Pane)getClient().getStage().getScene().getRoot()).getChildren().add(toadd);
        System.out.println(((Pane)getClient().getStage().getScene().getRoot()).getChildren());

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

    /**
     * When market line choice is performed, Discard Box gets initialized
     * @param evt is a fired support property
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(State.CHOOSING_POSITION_FOR_RESOURCES.name()))
        {
            DiscardBoxInitializer disc=new DiscardBoxInitializer();
            Platform.runLater(disc);
            ChooseResourceForMarket chooseResourceForMarket=new ChooseResourceForMarket();
            Platform.runLater(chooseResourceForMarket);
        }
    }

    public void sendChoice(Choice choice) {

        sendMessage(choice);
        ((Pane)getClient().getStage().getScene().getRoot()).getChildren().remove(3);


    }

    /**
     * Scene buttons get bound to controller
     * @param url is ignored
     * @param resourceBundle is ignored
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cardButton.setOnAction( e ->
        {

            sendChoice(Choice.CARD_SHOP);
            ViewPersonalBoard.getController().isCardShopOpen(true);

        });
        productionButton.setOnAction( e ->
        {

            sendChoice(Choice.PRODUCTION);
            ViewPersonalBoard.getController().isProduction(true);

        });
        resourceMarketButton.setOnAction( e ->
        {

            sendChoice(Choice.RESOURCE_MARKET);
            ViewPersonalBoard.getController().isMarket(true);

        });


    }
}
