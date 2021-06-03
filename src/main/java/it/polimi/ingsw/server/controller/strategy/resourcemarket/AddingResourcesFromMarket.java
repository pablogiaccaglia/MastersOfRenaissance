package it.polimi.ingsw.server.controller.strategy.resourcemarket;

import it.polimi.ingsw.network.messages.clienttoserver.events.Event;
import it.polimi.ingsw.server.controller.strategy.GameStrategy;
import it.polimi.ingsw.server.messages.clienttoserver.events.Validable;
import it.polimi.ingsw.server.messages.clienttoserver.events.marketboardevent.MoveResourceEvent;
import it.polimi.ingsw.server.messages.messagebuilders.Element;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.states.State;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * This implementation allows the user to move one at a time the resources from one of the depot lines to an empty
 * space. Before being able to proceed, the user needs to place all of the resources from the market either in the
 * DiscardBox or in one of their depot lines. For each resource in the DiscardBox all the other players get a faith point
 */
public class AddingResourcesFromMarket implements GameStrategy {

    List<Element> elementsToUpdate = new ArrayList<>();

    public Pair<State, List<Element>> execute(GameModel gamemodel, Validable event)
    {

        int startPos = ((MoveResourceEvent) event).getStartPos();
        int endPos = ((MoveResourceEvent) event).getEndPos();

        gamemodel.getCurrentPlayer().getPersonalBoard().move(startPos, endPos);

        elementsToUpdate.add(Element.SimpleWareHouseLeadersDepot);

        return new Pair<>(State.CHOOSING_POSITION_FOR_RESOURCES, elementsToUpdate);

    }

}
