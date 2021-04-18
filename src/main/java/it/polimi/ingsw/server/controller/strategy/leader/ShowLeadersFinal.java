package it.polimi.ingsw.server.controller.strategy.leader;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.player.State;

public class ShowLeadersFinal extends LeaderStrategy
{
    public State execute(GameModel gamemodel)
    {
        //ON EVENT CHOOSELEADEREVENT
        return State.SHOWING_LEADERS_FINAL;
    }
}