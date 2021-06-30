package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.simplemodel.PlayerCache;
import it.polimi.ingsw.client.view.GUI.util.CardSelector;
import it.polimi.ingsw.client.view.GUI.util.NodeAdder;
import it.polimi.ingsw.client.view.abstractview.CardShopViewBuilder;
import it.polimi.ingsw.client.view.abstractview.ProductionViewBuilder;
import it.polimi.ingsw.network.assets.DevelopmentCardAsset;
import it.polimi.ingsw.network.assets.LeaderCardAsset;
import it.polimi.ingsw.network.jsonUtils.JsonUtility;
import it.polimi.ingsw.network.simplemodel.SimpleCardCells;
import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Path;
import java.util.*;

public class Productions3D implements PropertyChangeListener {

    private final BoardView3D view3D;
    private Group prodGroup;

    public Productions3D(BoardView3D view3D) {
        this.view3D = view3D;
    }

    /**
     * Is used to initialize the productions
     */
    public void updateProds() {
        Group parent = view3D.parent;
        BoardView3D.Mode mode = view3D.mode;
        PlayerCache cache = view3D.getCache();

        if (prodGroup == null)
        {
            prodGroup = new Group();
            NodeAdder.addNodeToParent(parent, view3D.boardRec, prodGroup, new Point3D(0,0,0));
        }
        Button productionButton = new Button();
        productionButton.setText("Produce");
        productionButton.setLayoutX(500);
        productionButton.setLayoutY(1550);
        productionButton.setTranslateZ(-15);
        productionButton.setPrefHeight(80);
        productionButton.setPrefWidth(200);
        productionButton.setStyle("-fx-font-size:30");

        productionButton.setOpacity(mode.equals(BoardView3D.Mode.CHOOSE_PRODUCTION)?1:0);

        productionButton.setOnAction(e -> {
            if (mode.equals(BoardView3D.Mode.CHOOSE_PRODUCTION))
                ProductionViewBuilder.sendProduce();
        });

        List<Node> prodList = new ArrayList<>();

        prodList.add(productionButton);

        Rectangle basic=new Rectangle(300,300);
        basic.setOpacity(0);
        //basic.setMouseTransparent(true);

        NodeAdder.shiftAndAddToList(prodList,basic,new Point3D(600,1200,0));
        basic.setOnMouseClicked(p->{
            if (mode.equals(BoardView3D.Mode.CHOOSE_PRODUCTION)) {
                ProductionViewBuilder.sendChosenProduction(0);
            }
        });

        SimpleCardCells simpleCardCells = cache.getElem(SimpleCardCells.class).orElseThrow();
        Map<Integer, Optional<DevelopmentCardAsset>> frontCards=simpleCardCells.getDevCardsOnTop();

        for (Map.Entry<Integer, Optional<DevelopmentCardAsset>> entry : frontCards.entrySet()) {
            Integer key = entry.getKey();
            Optional<DevelopmentCardAsset> value = entry.getValue();
            ImagePattern tempImage;
            Path path;
            Rectangle rectangle = new Rectangle(462, 698);
            //rectangle.setMouseTransparent(true);
            System.out.println(JsonUtility.serialize(value.orElse(null)));
            if (value.isEmpty()) {
                tempImage = new ImagePattern(new Image("assets/devCards/grayed out/BACK/Masters of Renaissance__Cards_BACK_BLUE_1.png"));
                rectangle.setOpacity(0);
            } else {
                path = simpleCardCells.getDevCardsCells().get(key).get().get(simpleCardCells.getDevCardsCells().get(key).get().size()-1).getCardPaths().getKey();
                tempImage = CardSelector.imagePatternFromAsset(path);
                System.out.println(path);
            }

            rectangle.setLayoutX(400 + 250 * key);
            rectangle.setLayoutY(0);
            rectangle.setOnMouseClicked(p -> {
                if (mode.equals(BoardView3D.Mode.CHOOSE_POS_FOR_CARD) && simpleCardCells.isSpotAvailable(key)) {
                    CardShopViewBuilder.sendCardPlacementPosition(key);
                } else if (mode.equals(BoardView3D.Mode.CHOOSE_PRODUCTION))
                    if (value.isPresent())
                        if(value.get().getDevelopmentCard().isSelectable())
                            ProductionViewBuilder.sendChosenProduction(key);
            });
            rectangle.setFill(tempImage);


            NodeAdder.shiftAndAddToList(prodList, rectangle, new Point3D(20 + 220 * key, 700, -20));
        }

        Collection<LeaderCardAsset> activeBonus = simpleCardCells.getActiveProductionLeaders().values();
        Rectangle temp;
        for (LeaderCardAsset bonus : activeBonus) {
            int count = 0;
            temp = new Rectangle(462, 698);
            temp.setTranslateY(250);
            temp.setLayoutX(400 + 750 + 250 * (count + 1));

            temp.setFill(CardSelector.imagePatternFromAsset(bonus.getCardPaths().getKey()));
            NodeAdder.shiftAndAddToList(prodList, temp, new Point3D(680 + 220 * (count + 1), 700, -20));
        }
        prodGroup.getChildren().setAll(prodList);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //Event is a state
        if (evt.getPropertyName().equals(evt.getNewValue()))
            Platform.runLater(this::updateProds);
    }
}
