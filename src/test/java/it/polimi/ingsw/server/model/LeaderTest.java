package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.player.LeaderState;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.board.Depot;
import it.polimi.ingsw.server.model.player.board.LeaderDepot;
import javafx.util.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LeaderTest
{

    @Test
    public void areRequirementsSatisfied() throws IOException
    {
        Pair<Resource, Integer> discountTest = new Pair<>(Resource.GOLD, 3);
        Pair<Resource, Integer> costTest = new Pair<>(Resource.SERVANT, 3);
        Pair<DevelopmentCardColor, Integer> cardcostTest = new Pair<DevelopmentCardColor,Integer>(DevelopmentCardColor.BLUE, 3);


        List<Pair<Resource, Integer>> requirementsTest = new ArrayList<Pair<Resource, Integer>>();
        List<Pair<DevelopmentCardColor, Integer>> requirementsCardsTest = new ArrayList<Pair<DevelopmentCardColor, Integer>>();

        requirementsTest.add(costTest);
        requirementsCardsTest.add(cardcostTest);

        Player player= new Player();
        GameModel gamemodel = new GameModel();
        gamemodel.setCurrentPlayer(player);

        DevelopmentDiscountLeader leadertest = new DevelopmentDiscountLeader(LeaderState.INACTIVE, 3, requirementsTest, requirementsCardsTest, discountTest);
        assertFalse(leadertest.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().addResources(new int[]{3,0,0,0,0,0,0});
        assertTrue(leadertest.areRequirementsSatisfied(gamemodel));

    }




}