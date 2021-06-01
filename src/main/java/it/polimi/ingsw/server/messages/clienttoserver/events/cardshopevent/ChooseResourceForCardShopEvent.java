package it.polimi.ingsw.server.messages.clienttoserver.events.cardshopevent;

import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.player.board.PersonalBoard;
import it.polimi.ingsw.server.model.states.State;
import it.polimi.ingsw.server.messages.clienttoserver.events.Validable;
import it.polimi.ingsw.server.model.GameModel;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Client side {@link it.polimi.ingsw.network.messages.clienttoserver.events.Event Event} created when {@link GameModel#currentPlayer currentPlayer} has to pick {@link it.polimi.ingsw.server.model.Resource Resources}
 * from his {@link it.polimi.ingsw.server.model.player.board.PersonalBoard PersonalBoard} deposits during
 * {@link State#CHOOSING_RESOURCES_FOR_DEVCARD CHOOSING_RESOURCES_FOR_DEVCARD} phase by performing a
 * game turn action processed to accomplish server-side client validation.
 */
public class ChooseResourceForCardShopEvent extends it.polimi.ingsw.network.messages.clienttoserver.events.cardshopevent.ChooseResourceForCardShopEvent implements Validable {

    private transient PersonalBoard currentPlayerPersonalBoard;
    private final int[] chosenResourcesArray = new int[4];

    /**
     * Server-side initializer to setup common attributes among {@link State#MIDDLE_PHASE MIDDLE_PHASE}
     * events.
     * @param gameModel {@link GameModel} of the event's current game on which event validation has to be performed.
     */
    private void initializeMiddlePhaseEventValidation(GameModel gameModel){
        this.currentPlayerPersonalBoard = gameModel.getCurrentPlayer().getPersonalBoard();
    }


    /**
     * Method to verify if current player's {@link it.polimi.ingsw.server.model.cards.CardShop#purchasedCard devCard}
     * from {@link it.polimi.ingsw.server.model.cards.CardShop CardShop} in
     * {@link State#CHOOSING_RESOURCES_FOR_DEVCARD CHOOSING_RESOURCES_FOR_DEVCARD} phase has
     * resources and level requirements matching current player's {@link it.polimi.ingsw.server.model.player.board.PersonalBoard PersonalBoard}
     * availability.
     * @return true if resources and level requirements are satisfied, otherwise false
     */
    private boolean validateDevCardRequirements(DevelopmentCard card){
        return currentPlayerPersonalBoard.isDevelopmentCardAvailable(card);
    }

    @Override
    public boolean validate(GameModel gameModel) {

        initializeMiddlePhaseEventValidation(gameModel);
        buildResourcesArray();

        return isGameStarted(gameModel)
                && checkResourcesType()
                && checkResourcePositionUniqueness()
                && checkResourcesPositionIndexes()
                && checkResourcesPositions()
                && checkResourceValidity(gameModel.getPurchasedCard())
                && checkResourceRequirements()
                && validateDevCardRequirements(gameModel.getPurchasedCard());
    }

    /**
     * @return true if chosen {@link it.polimi.ingsw.server.model.Resource resources} actually match
     * {@link GameModel#currentPlayer currentPlayer} {@link it.polimi.ingsw.server.model.player.board.PersonalBoard PersonalBoard}
     * deposits availability, otherwise false.
     */
    private boolean checkResourceRequirements(){
        return currentPlayerPersonalBoard.hasResources(chosenResourcesArray);
    }

    private boolean checkResourcesType(){
        return chosenResourcesPositions.stream().noneMatch(resource -> Resource.fromIntFixed(resource).equals(Resource.EMPTY));
    }

    private boolean checkResourcesPositions(){
       return chosenResourcesPositions.stream()
               .anyMatch(position ->
                       !currentPlayerPersonalBoard.getResourceAtPosition(position).equals(Resource.fromIntFixed(position)));
    }

    private boolean checkResourcesPositionIndexes(){
        return chosenResourcesPositions.stream().anyMatch(i -> ( i<-8 || (i>-5 && i<0) ));
    }

    private boolean checkResourcePositionUniqueness(){
        return chosenResourcesPositions.stream().distinct()
                .count() == chosenResourcesPositions.size();
    }

    private boolean checkResourceValidity(DevelopmentCard card){
        return Arrays.equals(card.getCostAsArray(), chosenResourcesArray);
    }
    
    private void buildResourcesArray(){

        Map<Integer, List<Integer>> tempMap = chosenResourcesPositions.stream()
                .collect(Collectors.groupingBy(value -> value,
                        Collectors.mapping(Function.identity(), Collectors.toList())));

        List<Pair<Integer, Integer>> chosenResourcesList = tempMap
                .keySet().stream()
                .map(key ->
                        new Pair<>(key, tempMap.get(key).stream().reduce(0, Integer::sum)))
                .collect(Collectors.toList());


        for (Pair<Integer, Integer> resourceIntegerPair : chosenResourcesList)
            chosenResourcesArray[resourceIntegerPair.getKey()] += resourceIntegerPair.getValue();
        
    }

    public List<Integer> getChosenResources(){
        return chosenResourcesPositions;
    }

    public int[] getChosenResourcesArray() {
        return chosenResourcesArray;
    }
}
