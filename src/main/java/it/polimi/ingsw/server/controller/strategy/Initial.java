package it.polimi.ingsw.server.controller.strategy;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.player.State;

/**
 * On turn start, the player can decide wether to play a Leader or a Normal Move. On turnstart the flag
 * Past is refreshed. It will be set to true once the MIDDLE PHASE will be passed.  If they decide to
 *  * play a leader, but its not possible, that part will be skipped.
 */
public class Initial implements GameStrategy {
    public State execute(GameModel gamemodel)
    {
        //MESSAGE IS SKIPLEADER OR CHOOSELEADER (0 or 1)
        int msg=0;
        //gamemodel.getCurrentPlayer().setMacroState(State.INITIAL_PHASE);
        if(gamemodel.getCurrentPlayer().anyLeaderPlayable())
            return State.MIDDLE_PHASE;
        else
            return State.SHOWING_LEADERS_INITIAL;
    }
}
