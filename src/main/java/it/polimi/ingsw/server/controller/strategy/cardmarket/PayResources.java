package it.polimi.ingsw.server.controller.strategy.cardmarket;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.player.State;

public abstract class PayResources extends CardMarketStrategy
{
    public State execute(GameModel gamemodel)
    {
        //ON EVENT CARDSHOPEVENT
        return State.CHOOSING_POSITION_FOR_DEVCARD;
    }
}