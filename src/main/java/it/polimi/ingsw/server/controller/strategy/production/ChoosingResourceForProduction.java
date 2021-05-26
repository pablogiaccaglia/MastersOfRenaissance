package it.polimi.ingsw.server.controller.strategy.production;

import it.polimi.ingsw.server.controller.EventValidationFailedException;
import it.polimi.ingsw.server.controller.strategy.GameStrategy;
import it.polimi.ingsw.server.messages.clienttoserver.events.Validable;
import it.polimi.ingsw.server.messages.clienttoserver.events.productionevent.ChooseResourcesForProductionEvent;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.states.State;
/**
 * This implementation allows the user to sequentially select the input and output optional choices. The inputs are
 * chosen first, and it has already been calculated that there will be enough basic resources to complete the choice.
 * The user can reset mid-choice, allowing them to perform it from the start again.
 */
public class ChoosingResourceForProduction implements GameStrategy {
    public State execute(GameModel gamemodel, Validable event) throws EventValidationFailedException
    {
        //ON EVENT CHOOSERESOURCEEVENT
        //msg is int from 0 to 3, if its 4 is reset choiche
        int msg=2;

        event.validate(gamemodel);

        for(int i=0;i<((ChooseResourcesForProductionEvent) event).getChosenResourcesForProduction().length;i++)
        {
            if(gamemodel.getCurrentPlayer().getPersonalBoard().firstProductionSelectedWithChoice().get().choiceCanBeMadeOnInput())
                gamemodel.getCurrentPlayer().getPersonalBoard().performChoiceOnInput(msg);
            else
                gamemodel.getCurrentPlayer().getPersonalBoard().performChoiceOnOutput(Resource.fromInt(msg));
        }

        return State.CHOOSING_CARD_FOR_PRODUCTION;
    }
}
