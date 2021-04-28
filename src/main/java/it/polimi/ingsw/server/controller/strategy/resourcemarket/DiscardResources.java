package it.polimi.ingsw.server.controller.strategy.resourcemarket;

import it.polimi.ingsw.server.controller.strategy.Final;
import it.polimi.ingsw.server.controller.strategy.GameStrategy;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.State;

import java.util.ArrayList;
import java.util.List;

/**
 * This implementation calculates the movements on the Faith Track based on the resources left in the
 * discardbox.
 */
public class DiscardResources implements GameStrategy {
    public State execute(GameModel gamemodel)
    {
        //ON EVENT DISCARDRESOURCEEVENT

        gamemodel.getCurrentPlayer().getPersonalBoard().discardResources();
        int temp=gamemodel.getCurrentPlayer().getPersonalBoard().getBadFaithToAdd();
        for(int i=0;i<temp;i++)
        {
            gamemodel.addFaithPointToOtherPlayers();
            //check vatican report
            //gamemodel.haswon;
            //if someone won, set state
        }

        temp=gamemodel.getCurrentPlayer().getPersonalBoard().getFaithToAdd();
        for(int i=0;i<temp;i++)
        {
            gamemodel.getCurrentPlayer().moveOnePosition();
            //check vatican report
            //gamemodel.haswon;
        }

        return new Final().execute(gamemodel);

    }


}