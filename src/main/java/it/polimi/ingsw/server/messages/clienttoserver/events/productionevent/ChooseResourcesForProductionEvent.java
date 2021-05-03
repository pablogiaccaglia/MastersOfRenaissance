package it.polimi.ingsw.server.messages.clienttoserver.events.productionevent;

import it.polimi.ingsw.server.messages.clienttoserver.events.Validable;
import it.polimi.ingsw.server.model.GameModel;

public class ChooseResourcesForProductionEvent extends it.polimi.ingsw.network.messages.clienttoserver.events.productionevent.ChooseResourcesForProductionEvent implements Validable {

    @Override
    public boolean validate(GameModel gameModel) {
        initializeMiddlePhaseEventValidation(gameModel);
        return checkResourceRequirements() && checkProductionResources();
    }

    private boolean checkResourceRequirements(){
        return currentPlayerPersonalBoard.hasResources(chosenResources);
    }

    private boolean checkProductionResources(){
        return false;
    }

}
