package it.polimi.ingsw.server.controller.strategy.cardmarket;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.player.State;

/**
 * This implementation simply sets the state for the view
 */
public class ShowCards extends CardMarketStrategy
{
    public State execute(GameModel gamemodel)
    {
        //ON EVENT CARDSHOPEVENT
        return State.SHOWING_CARD_SHOP;
    }
}
