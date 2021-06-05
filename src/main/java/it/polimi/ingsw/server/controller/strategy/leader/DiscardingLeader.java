package it.polimi.ingsw.server.controller.strategy.leader;

import it.polimi.ingsw.server.controller.strategy.GameStrategy;
import it.polimi.ingsw.server.messages.clienttoserver.events.InitialOrFinalPhaseEvent;
import it.polimi.ingsw.server.messages.clienttoserver.events.Validable;
import it.polimi.ingsw.server.messages.messagebuilders.Element;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.states.State;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 *  If the chosen Leader is playable, it is discarded. If not, nothing happens.
 */
public class DiscardingLeader implements GameStrategy {

    List<Element> elementsToUpdate = new ArrayList<>();

    public Pair<State, List<Element>> execute(GameModel gamemodel, Validable event)
    {

        State currentState = gamemodel.getCurrentPlayer().getCurrentState();
        State nextPossibleState = currentState.equals(State.INITIAL_PHASE) ? State.MIDDLE_PHASE : State.IDLE;
        elementsToUpdate.add(Element.SimpleFaithTrack);
        elementsToUpdate.add(Element.SimplePlayerLeaders);

        gamemodel.getCurrentPlayer().discardLeader(((InitialOrFinalPhaseEvent) event).getLeaderId());
        gamemodel.getCurrentPlayer().moveOnePosition();

        if(gamemodel.getCurrentPlayer().hasReachedTrackEnd()){

            gamemodel.handleVaticanReport();

            if(gamemodel.checkTrackStatus()) {

                if(gamemodel.getMacroGamePhase().equals(GameModel.MacroGamePhase.ActiveGame))
                    gamemodel.setMacroGamePhase(GameModel.MacroGamePhase.LastTurn);

                else if(gamemodel.getPlayerIndex(gamemodel.getCurrentPlayer()) == (gamemodel.getOnlinePlayers().size() - 1))
                    gamemodel.setMacroGamePhase(GameModel.MacroGamePhase.GameEnded);

                return new Pair<>(State.IDLE, elementsToUpdate);

            }

        }

        else return gamemodel.getCurrentPlayer().anyLeaderPlayable()
                ? new Pair<>(currentState, elementsToUpdate)
                : new Pair<>(nextPossibleState, elementsToUpdate);


        return null;
    }


}
