package it.polimi.ingsw.server.messages.clienttoserver.events.marketboardevent;

import it.polimi.ingsw.network.messages.clienttoserver.events.Event;
import it.polimi.ingsw.network.messages.servertoclient.State;
import it.polimi.ingsw.server.messages.clienttoserver.events.Validable;
import it.polimi.ingsw.server.model.GameModel;

/**
 * Client side {@link Event} created when player wants move {@link it.polimi.ingsw.server.model.Resource Resources} picked
 * from {@link it.polimi.ingsw.server.model.market.MarketBoard} and move the ones inside his Warehouse deposits
 * when {@link State#CHOOSING_POSITION_FOR_RESOURCES CHOOSING_POSITION_FOR_RESOURCES} game turn action is performed and
 * has to be processed to accomplish server-side client validation.
 */
public class MoveResourceEvent extends it.polimi.ingsw.network.messages.clienttoserver.events.marketboardevent.MoveResourceEvent implements Validable {

    @Override
    public boolean validate(GameModel model) {
        this.currentPlayerPersonalBoard = model.getCurrentPlayer().getPersonalBoard();
        return isGameStarted(model) && validatePositions();
    }

    /**
     * Method to validate initial and final positions for {@link it.polimi.ingsw.server.model.Resource Resources} taken from
     * {@link it.polimi.ingsw.server.model.market.MarketBoard MarketBoard} and placed in
     * {@link it.polimi.ingsw.server.model.player.board.WarehouseDepot WarehouseDepot}
     *
     * @param endPos  int value of the destination position for the {@link it.polimi.ingsw.server.model.Resource Resource} to move.
     *
     * @return true if starting position is among ones in the picked {@link it.polimi.ingsw.server.model.Resource Resources} and
     * ending position is among available ones inside {@link it.polimi.ingsw.server.model.player.board.WarehouseDepot WarehouseDepot}
     *
     */
    private boolean validateNewResourceToAdd(int endPos){
        return endPos >=0;
    }

    /**
     * Method to validate initial and final position for {@link it.polimi.ingsw.server.model.Resource Resources} moved
     * from a spot to another available inside {@link it.polimi.ingsw.server.model.player.board.WarehouseDepot WarehouseDepot}.
     *
     * @param startPos int value position of the {@link it.polimi.ingsw.server.model.Resource Resource} to move.
     * @param endPos  int value of the destination position for the {@link it.polimi.ingsw.server.model.Resource Resource} to move.
     *
     * @return true if starting position is among ones in inside {@link it.polimi.ingsw.server.model.player.board.WarehouseDepot WarehouseDepot} and
     * ending position is among available ones inside {@link it.polimi.ingsw.server.model.player.board.WarehouseDepot WarehouseDepot}
     */
    private boolean validateResourceToMove(int startPos, int endPos){
        return endPos >= 0 && currentPlayerPersonalBoard.availableMovingPositionsForResourceAt(startPos)
                .anyMatch(position -> position == endPos);
    }


    /**
     * @return true if given initial and final positions are valid ones, otherwise false.
     */
    private boolean validatePositions(){
        return (startPos>=-4 && startPos<=-1) ? validateNewResourceToAdd(endPos) :
                validateResourceToMove(startPos, endPos);
    }

}
