package it.polimi.ingsw.network.messages.clienttoserver.events.leaderphaseevent;

import it.polimi.ingsw.network.messages.clienttoserver.events.Event;
import it.polimi.ingsw.network.messages.clienttoserver.events.InitialOrFinalPhaseEvent;
import it.polimi.ingsw.server.model.states.State;
import it.polimi.ingsw.server.model.GameModel;

import java.util.UUID;

/**
 * Client side {@link Event} created when {@link GameModel#currentPlayer currentPlayer} wants to discard a
 * {@link it.polimi.ingsw.server.model.player.leaders.Leader Leader}
 * during {@link State#SHOWING_LEADERS_INITIAL SHOWING_LEADERS_INITIAL} or
 * {@link State#SHOWING_LEADERS_FINAL SHOWING_LEADERS_FINAL} by performing a
 * game turn action processed to accomplish server-side client validation.
 */
public class DiscardLeaderEvent extends InitialOrFinalPhaseEvent {

    /**
     * Default constructor for handling {@link it.polimi.ingsw.server.messages.clienttoserver.events.leaderphaseevent.DiscardLeaderEvent #SERVERDiscardLeaderEvent}
     * server side equivalent inherited Event handler
     */
    public DiscardLeaderEvent(){}
}
