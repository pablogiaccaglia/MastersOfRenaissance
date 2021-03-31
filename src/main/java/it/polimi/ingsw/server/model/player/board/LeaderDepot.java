package it.polimi.ingsw.server.model.player.board;

import it.polimi.ingsw.server.model.Resource;

/**
 * The depot that a depotLeader gives the player when activated
 */
public class LeaderDepot extends Depot {

    /**
     * Creates a depot of dimension 2 and given type
     * @param globalPositionOfFirstElement global position of first element
     * @param type type of resource that the leaderDepot can store
     */
    LeaderDepot(int globalPositionOfFirstElement, Resource type) {
        super(2, globalPositionOfFirstElement, type);
    }
}
