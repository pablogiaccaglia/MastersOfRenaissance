package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.DevelopmentCardColor;
import it.polimi.ingsw.server.model.market.MarketLeader;
import it.polimi.ingsw.server.model.player.LeaderState;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.board.LeaderDepot;
import it.polimi.ingsw.server.model.player.board.PersonalBoard;
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
        LeaderDepot depotTest= new LeaderDepot(2,Resource.GOLD);
        Resource bonus=Resource.GOLD;
        Production productiontest = Production.basicProduction();


        Pair<Resource, Integer> costTest = new Pair<>(Resource.GOLD, 3);
        Pair<DevelopmentCardColor, Integer> cardcostTest = new Pair<DevelopmentCardColor,Integer>(DevelopmentCardColor.BLUE, 3);
        DevelopmentCard testcard= new DevelopmentCard(1,DevelopmentCardColor.BLUE);

        List<Pair<Resource, Integer>> requirementsTest = new ArrayList<Pair<Resource, Integer>>();
        List<Pair<DevelopmentCardColor, Integer>> requirementsCardsTest = new ArrayList<Pair<DevelopmentCardColor, Integer>>();

        requirementsTest.add(costTest);
        requirementsCardsTest.add(cardcostTest);

        Player player= new Player();
        GameModel gamemodel = new GameModel();
        gamemodel.setCurrentPlayer(player);

        DevelopmentDiscountLeader leadertestDD;
        DepositLeader leadertestD;
        Leader leadertestM;
        ProductionLeader leadertestP;

        //FALLISCE NON HA NIENTE
        leadertestM = new MarketLeader(LeaderState.INACTIVE, 3, requirementsTest, requirementsCardsTest, bonus);
        assertFalse(leadertestM.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().addResources(new int[]{0,0,3,0,0,0,0});
        assertFalse(leadertestM.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().removeResources(new int[]{0,0,3,0,0,0,0});

        // FALLISCE HA RISORSA MA NON CARTA
        leadertestDD = new DevelopmentDiscountLeader(LeaderState.INACTIVE, 3, requirementsTest, requirementsCardsTest, discountTest);
        assertFalse(leadertestDD.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().addResources(new int[]{3,0,0,0,0,0,0});
        assertFalse(leadertestDD.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().removeResources(new int[]{3,0,0,0,0,0,0});

        // FALLISCE HA MOLTA RISORSA MA NON CARTA
        leadertestDD = new DevelopmentDiscountLeader(LeaderState.INACTIVE, 3, requirementsTest, requirementsCardsTest, discountTest);
        assertFalse(leadertestDD.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().addResources(new int[]{30,0,0,0,0,0,0});
        assertFalse(leadertestDD.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().removeResources(new int[]{30,0,0,0,0,0,0});

        gamemodel.getCurrentPlayer().getPersonalBoard().addDevelopmentCardToCell(testcard,1);
        //HA CARTA MA NON RISORSA
        leadertestD = new DepositLeader(LeaderState.INACTIVE, 3, requirementsTest, requirementsCardsTest, depotTest);
        assertFalse(leadertestD.areRequirementsSatisfied(gamemodel));


        //HA CARTA E RISORSA
        leadertestP = new ProductionLeader(LeaderState.INACTIVE, 3, requirementsTest, requirementsCardsTest, productiontest);
        assertFalse(leadertestP.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().addResources(new int[]{3,0,0,0,0,0,0});
        assertTrue(leadertestP.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().removeResources(new int[]{3,0,0,0,0,0,0});

        //HA CARTA E TANTA RISORSA
        leadertestP = new ProductionLeader(LeaderState.INACTIVE, 3, requirementsTest, requirementsCardsTest, productiontest);
        assertFalse(leadertestP.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().addResources(new int[]{30,0,0,0,0,0,0});
        assertTrue(leadertestP.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().removeResources(new int[]{30,0,0,0,0,0,0});


        gamemodel.getCurrentPlayer().getPersonalBoard().addDevelopmentCardToCell(testcard,1);
        //HA TANTE CARTE E NON RISORSA
        leadertestP = new ProductionLeader(LeaderState.INACTIVE, 3, requirementsTest, requirementsCardsTest, productiontest);
        assertFalse(leadertestP.areRequirementsSatisfied(gamemodel));
        assertTrue(leadertestP.areRequirementsSatisfied(gamemodel));

        //HA TANTE CARTE E RISORSE GIUSTE
        leadertestP = new ProductionLeader(LeaderState.INACTIVE, 3, requirementsTest, requirementsCardsTest, productiontest);
        assertFalse(leadertestP.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().addResources(new int[]{3,0,0,0,0,0,0});
        assertTrue(leadertestP.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().removeResources(new int[]{3,0,0,0,0,0,0});

        //HA TANTE CARTE E TANTE RISORSE
        leadertestP = new ProductionLeader(LeaderState.INACTIVE, 3, requirementsTest, requirementsCardsTest, productiontest);
        assertFalse(leadertestP.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().addResources(new int[]{30,0,0,0,0,0,0});
        assertTrue(leadertestP.areRequirementsSatisfied(gamemodel));
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().removeResources(new int[]{30,0,0,0,0,0,0});



    }




}