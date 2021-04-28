package it.polimi.ingsw.network.messages.clienttoserver.events.leaderphaseevent;


import it.polimi.ingsw.network.messages.clienttoserver.events.Event;
import it.polimi.ingsw.server.model.GameModel;

/**
 * Client side {@link it.polimi.ingsw.network.messages.clienttoserver.events.Event Event} created when {@link GameModel#currentPlayer currentPlayer}
 * wants to activate his {@link it.polimi.ingsw.server.model.player.leaders.Leader Leaders}
 * during {@link it.polimi.ingsw.server.model.player.State#SHOWING_LEADERS_INITIAL SHOWING_LEADERS_INITIAL} or
 * {@link it.polimi.ingsw.server.model.player.State#SHOWING_LEADERS_FINAL SHOWING_LEADERS_FINAL} phase by performing a
 * game turn action processed to accomplish server-side client validation.
 */
public class PlayLeaderEvent extends ChooseLeaderEvent {

    /**
     * Client side {@link Event} constructor invoked when {@link it.polimi.ingsw.server.model.player.State#SHOWING_LEADERS_INITIAL SHOWING_LEADERS_INITIAL} or
     * {@link it.polimi.ingsw.server.model.player.State#SHOWING_LEADERS_FINAL SHOWING_LEADERS_FINAL} phase action is performed.
     * @param leaderNumber chosen {@link it.polimi.ingsw.server.model.player.leaders.Leader Leader} position in
     * {@link GameModel#currentPlayer currentPlayer}'s {@link it.polimi.ingsw.server.model.player.Player#leaders Leaders} List
     */
    public PlayLeaderEvent(int leaderNumber){
        super(leaderNumber);
    }

    /**
     * Default constructor for handling {@link it.polimi.ingsw.server.messages.clienttoserver.events.leaderphaseevent.PlayLeaderEvent} #SERVERPlayerLeaderEvent server side equivalent inherited Event handler
     */
    public PlayLeaderEvent(){}
}