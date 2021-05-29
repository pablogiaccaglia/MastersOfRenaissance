package it.polimi.ingsw.server.controller.strategy.leader;

import it.polimi.ingsw.server.controller.strategy.GameStrategy;
import it.polimi.ingsw.server.messages.clienttoserver.events.Validable;
import it.polimi.ingsw.server.messages.clienttoserver.events.leaderphaseevent.PlayLeaderEvent;
import it.polimi.ingsw.server.messages.messagebuilders.Element;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.states.State;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 *  If the chosen Leader is playable, its effect is activated. If not, nothing happens.
 */
public class ActivatingLeader implements GameStrategy {

    List<Element> elementsToUpdate = new ArrayList<>();

    public Pair<State, List<Element>> execute(GameModel gamemodel, Validable event)
    {
        //ON EVENT PLAYLEADEREVENT
        //MESSAGE IS INT 2
    //    if(gamemodel.getCurrentPlayer().getPersonalBoard().isLeaderRequirementsSatisfied(gamemodel.getCurrentPlayer().getLeaders().get(2))&&
    //        gamemodel.getCurrentPlayer().getLeaders().get(2).getState()== NetworkLeaderState.INACTIVE)

        gamemodel.getCurrentPlayer().getLeader(((PlayLeaderEvent) event).getLeaderId()).get().activate(gamemodel);

        elementsToUpdate.add(Element.SimpleWareHouseLeadersDepot);
        elementsToUpdate.add(Element.SimplePlayerLeaders);
        elementsToUpdate.add(Element.SimpleFaithTrack);

        return new Pair<>(State.LEADER_END, elementsToUpdate);

    }
}
