package it.polimi.ingsw.client.view.abstractview;

import it.polimi.ingsw.client.view.CLI.ProductionViewCLI;
import it.polimi.ingsw.client.view.GUI.BoardView3D;
import it.polimi.ingsw.network.messages.clienttoserver.events.EventMessage;
import it.polimi.ingsw.network.messages.clienttoserver.events.productionevent.ChooseResourcesForProductionEvent;
import it.polimi.ingsw.network.messages.clienttoserver.events.productionevent.FinalProductionPhaseEvent;
import it.polimi.ingsw.network.messages.clienttoserver.events.productionevent.ToggleProductionAtPosition;
import it.polimi.ingsw.network.simplemodel.SimpleCardCells;

import java.beans.PropertyChangeEvent;
import java.util.List;

import static it.polimi.ingsw.client.simplemodel.State.*;

public abstract class ProductionViewBuilder extends ViewBuilder{

    public static ViewBuilder getBuilder(boolean isCLI) {
        if (isCLI) return new ProductionViewCLI();
        else return new BoardView3D();
    }
   public SimpleCardCells getSimpleCardCells(){
       return getSimpleModel().getElem(SimpleCardCells.class).orElseThrow();
   }

    public static void sendChosenProduction(int pos){
        getClient().getServerHandler().sendCommandMessage(new EventMessage(new ToggleProductionAtPosition(pos)));
    }

    public static void sendProduce(){
        getClient().getServerHandler().sendCommandMessage(new EventMessage(new FinalProductionPhaseEvent(0)));
    }

    public static void sendChosenResources(List<Integer> chosenInputPos,List<Integer> chosenOutputRes){
        getClient().getServerHandler().sendCommandMessage(new EventMessage(new ChooseResourcesForProductionEvent(chosenInputPos,chosenOutputRes)));
    }

    /**
     * Called each time the player needs to select a resource for a production
     */
    public abstract void choosingResForProduction();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (CHOOSING_RESOURCE_FOR_PRODUCTION.name().equals(propertyName)) {
            choosingResForProduction();
        } else if (CHOOSING_PRODUCTION.name().equals(propertyName)) {
            getClient().changeViewBuilder(ProductionViewBuilder.getBuilder(getClient().isCLI()));
        } else MiddlePhaseViewBuilder.middlePhaseCommonTransition(evt);
    }

}
