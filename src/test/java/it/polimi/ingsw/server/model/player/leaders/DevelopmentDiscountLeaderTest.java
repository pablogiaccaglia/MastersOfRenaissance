package it.polimi.ingsw.server.model.player.leaders;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.network.assets.devcards.DevelopmentCardColor;
import javafx.util.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DevelopmentDiscountLeaderTest
{

    @Test
    public void ActivateAndDiscard()
    {
        //ROUTINE
        Pair<Resource, Integer> discountTest = new Pair<>(Resource.GOLD, 3);
        Pair<Resource, Integer> costTest = new Pair<>(Resource.SERVANT, 3);
        Pair<DevelopmentCardColor, Integer> cardcostTest = new Pair<>(DevelopmentCardColor.BLUE, 3);


        List<Pair<Resource, Integer>> requirementsTest = new ArrayList<>();
        List<Pair<DevelopmentCardColor, Integer>> requirementsCardsTest = new ArrayList<>();

        requirementsTest.add(costTest);
        requirementsCardsTest.add(cardcostTest);

        List<String> nicknames = new ArrayList<>();
        nicknames.add("testPlayer");
        boolean isSinglePlayer = true;
        GameModel gamemodel = new GameModel(nicknames, isSinglePlayer,null);

        assertNotNull(discountTest);
        DevelopmentDiscountLeader leadertest = new DevelopmentDiscountLeader(LeaderState.INACTIVE, 3, requirementsTest, requirementsCardsTest, discountTest);
        assertEquals(LeaderState.INACTIVE, leadertest.getState());

        assertNotNull(leadertest.getDiscount());

        leadertest.activate(gamemodel);
        assertEquals(LeaderState.ACTIVE, leadertest.getState());
        assertNotNull(leadertest.getDiscount());

        leadertest = new DevelopmentDiscountLeader(LeaderState.INACTIVE, 3, requirementsTest, requirementsCardsTest, discountTest);
        leadertest.discard(gamemodel);
        assertEquals(LeaderState.DISCARDED, leadertest.getState());

    }
}