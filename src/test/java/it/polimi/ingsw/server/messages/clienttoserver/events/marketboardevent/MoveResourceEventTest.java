package it.polimi.ingsw.server.messages.clienttoserver.events.marketboardevent;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clienttoserver.events.marketboardevent.MoveResourceEvent;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.market.MarketLine;
import it.polimi.ingsw.server.model.player.board.Box;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.network.jsonUtility.deserializeFromString;
import static it.polimi.ingsw.network.jsonUtility.serialize;
import static org.junit.Assert.*;

public class MoveResourceEventTest {

    MoveResourceEvent clientEventTest;
    it.polimi.ingsw.server.messages.clienttoserver.events.marketboardevent.MoveResourceEvent serverEventTest;
    GameModel gameModelTest;
    String serializedEvent;
    Gson gson;
    int resourceNumber = 100;

    @Before
    public void setUp() throws Exception {
        initializeGameModel();
        gameModelTest.setCurrentPlayer(gameModelTest.getPlayer("testPlayer4"));
        gson = new Gson();
    }

    private void initializeGameModel(){
        List<String> players = new ArrayList<>();
        players.add("testPlayer1");
        players.add("testPlayer2");
        players.add("testPlayer3");
        players.add("testPlayer4");

        gameModelTest = new GameModel(players, false, null);
        gameModelTest.setGameStatus(true);
    }

    @Test
    public void validationOkTest(){
        validTestInitialization();
        serializedEvent = serialize(clientEventTest);
        serverEventTest = deserializeFromString(serializedEvent,  it.polimi.ingsw.server.messages.clienttoserver.events.marketboardevent.MoveResourceEvent.class , gson);
        assertTrue(serverEventTest.validate(gameModelTest));
    }

    @Test
    public void validationNotOkTest(){
        invalidTestInitialization();
        serializedEvent = serialize(clientEventTest);
        serverEventTest = deserializeFromString(serializedEvent,  it.polimi.ingsw.server.messages.clienttoserver.events.marketboardevent.MoveResourceEvent.class , gson);
        assertFalse(serverEventTest.validate(gameModelTest));
    }

    private void invalidTestInitialization(){
        for(MarketLine line : MarketLine.values()) {
            if(line!=MarketLine.INVALID_LINE)
                gameModelTest.chooseLineFromMarketBoard(line);
            if(!gameModelTest.areThereWhiteMarblesInPickedLine()) {
                System.out.println(line);
                break;
            }
        }

        Box discardBox = gameModelTest.getBoxResourcesFromMarketBoard();

        for(Resource resource : Resource.values()){
            if( resource.getResourceNumber() < 4 && discardBox.getNumberOf(resource) == 0) {
                System.out.println(discardBox.getNumberOf(resource));
                resourceNumber = resource.getResourceNumber();
                break;
            }
        }
        System.out.println(resourceNumber);
        gameModelTest.getCurrentPlayer().getPersonalBoard().setMarketBox(discardBox);
        clientEventTest = new MoveResourceEvent(resourceNumber - 4, resourceNumber);

    }

    private void validTestInitialization(){

       for(MarketLine line : MarketLine.values()) {
           if(line!=MarketLine.INVALID_LINE)
            gameModelTest.chooseLineFromMarketBoard(line);
           if(!gameModelTest.areThereWhiteMarblesInPickedLine()) {
              // System.out.println(line);
               break;
           }
       }

        Box discardBox = gameModelTest.getBoxResourcesFromMarketBoard();

        for(Resource resource : Resource.values()){
            if( resource.getResourceNumber() < 4 && discardBox.getNumberOf(resource) != 0) {
             //   System.out.println(discardBox.getNumberOf(resource));
                resourceNumber = resource.getResourceNumber();
                break;
            }
        }
      //  System.out.println(resourceNumber);
        gameModelTest.getCurrentPlayer().getPersonalBoard().setMarketBox(discardBox);
        clientEventTest = new MoveResourceEvent(resourceNumber - 4, resourceNumber);
    }
}