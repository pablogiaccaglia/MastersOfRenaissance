package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.messages.clienttoserver.events.Event;
import it.polimi.ingsw.network.messages.servertoclient.state.StateInNetwork;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.strategy.GameStrategy;
import it.polimi.ingsw.server.controller.strategy.IDLE;
import it.polimi.ingsw.server.messages.clienttoserver.events.Validable;
import it.polimi.ingsw.server.messages.messagebuilders.Element;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.states.State;
import javafx.util.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Match {

    private final UUID matchId;
    private final List<User> onlineUsers = new ArrayList<>();
    private final List<String> offlineUsers = new ArrayList<>();
    private GameModel game;
    private final int maxPlayers;
    private Date createdTime;
    private String reasonOfGameEnd;

    private class User{
        String nickname;
        transient ClientHandler clientHandler;

        public User(String nickname, ClientHandler clientHandler) {
            this.nickname = nickname;
            this.clientHandler = clientHandler;
        }

        public String getNickname(){
            return nickname;
        }

    }

    public String getPlayerNicknameFromHandler(ClientHandler clientHandler){

        String playerNickname = onlineUsers.stream()
                .filter(user -> user.clientHandler == clientHandler)
                .findFirst().get()
                .nickname;

        return playerNickname;

    }

    public Match(int maxPlayers){
        matchId = UUID.randomUUID();
        this.maxPlayers = maxPlayers;
        createdTime = new Date(System.currentTimeMillis());
    }

    boolean canAddPlayer(){
        return onlineUsers.size()<maxPlayers;
    }

    void addPlayer(String nickname, ClientHandler clientHandler){
        onlineUsers.add(new User(nickname,clientHandler));
    }


    public void startGame() {

        this.game = new GameModel(onlineUsers.stream().map(u->u.nickname).collect(Collectors.toList()), onlineUsers.size()==1,this);
        game.start();
        game.getCurrentPlayer().setCurrentState(State.SETUP_PHASE);
        List<Element> elems = new ArrayList<>(Arrays.asList(Element.values()));


        notifyStateToAllPlayers(elems, game.getCurrentPlayer());

    }

    private Optional<User> currentUser(){

        String nickname = game.getCurrentPlayer().getNickName();
        return onlineUsers.stream().filter(user->user.nickname.equals(nickname)).findFirst();
    }

    public ClientHandler currentPlayerClientHandler(){

        return currentUser().map(user->user.clientHandler).orElse(onlineUsers.get(0).clientHandler);
    }

    boolean isSetupPhase(){
        return game.getOnlinePlayers().values().stream().map(Player::getCurrentState).anyMatch(state -> state.equals(State.SETUP_PHASE));
    }

    public UUID getMatchId() {
        return matchId;
    }

    public String[] getOnlinePlayers(){

        return Stream.concat(
                onlineUsers.stream().map(user->user.nickname),
                Stream.generate(()-> null)).limit(maxPlayers).toArray(String[]::new);

    }

    public boolean sameID(UUID uuid) {return uuid.equals(matchId);}

    /**
     * Displays matches that the client can join or the current match
     * @param current the current match.
     */
    public boolean shouldDisplay(Match current){return canAddPlayer()||sameID(current.matchId);}

    public Stream<ClientHandler> clientsStream(){
        return onlineUsers.stream().map(u->u.clientHandler);
    }

    public GameModel getGame(){
        return game;
    }
    public void reconnectPlayer(String nickname){
        game.getOfflinePlayers().values().stream().filter(player -> player.getNickName().equals(nickname)).findFirst()
                .ifPresent((p)->
                        game.setOnlinePlayer(p)
                        );
    }

    public void validateEvent(Validable event) throws EventValidationFailedException {
        if (!event.validate(game))
            throw new EventValidationFailedException();
    }

    public void transitionToNextState(Validable event) throws EventValidationFailedException {

        GameStrategy gameStrategy = game.getCurrentPlayer().getStatesTransitionTable().getStrategy(game.getCurrentPlayer().getCurrentState(), event);
        if (gameStrategy==null)
            throw new EventValidationFailedException();

        Pair<State, List<Element>> data = gameStrategy.execute(game, event);
        String nicknameOfPlayerSendingEvent =((Event) event).getPlayerNickname();

        Player playerSendingEvent = game.getPlayer(nicknameOfPlayerSendingEvent).get();
        data.getValue().add(Element.PlayersInfo);

        playerSendingEvent.setCurrentState(data.getKey());



        notifyStateToAllPlayers(data.getValue(), playerSendingEvent);

        //Todo check for better way
        if (playerSendingEvent.getCurrentState().equals(State.IDLE)) {

            if(game.getCurrentPlayer().equals(playerSendingEvent)) {

                game.setCurrentPlayer(game.getNextPlayer());

                if (game.getCurrentPlayer().getCurrentState().equals(State.SETUP_PHASE)) {

                    List<Element> elems = new ArrayList<>(Arrays.asList(Element.values()));

                    nicknameOfPlayerSendingEvent = ((Event) event).getPlayerNickname();
                    playerSendingEvent = game.getPlayer(nicknameOfPlayerSendingEvent).get();

                    notifyStateToAllPlayers(elems, playerSendingEvent);

                } else {
                    IDLE IDLEStrategy = new IDLE();
                    Pair<State, List<Element>> data2 = IDLEStrategy.execute(game, null);
                    game.getCurrentPlayer().setCurrentState(data2.getKey());

                    nicknameOfPlayerSendingEvent = ((Event) event).getPlayerNickname();
                    playerSendingEvent = game.getPlayer(nicknameOfPlayerSendingEvent).get();

                    notifyStateToAllPlayers(data.getValue(), playerSendingEvent);
                }
            }


        }

    }

    public void notifyStateToAllPlayers(List<Element> elems, Player playerNotifying){

        State state = playerNotifying.getCurrentState();

        clientsStream().forEach(clientHandler -> {

            StateInNetwork stateInNetwork = state.toStateMessage(getGame(), elems, game.getPlayerIndex(playerNotifying));

            try {
                clientHandler.sendAnswerMessage(stateInNetwork);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public String getSaveName(){
        return onlineUsers.stream().map(user->user.nickname).reduce("", String::concat).concat("|").concat(matchId.toString());
    }

    public int getLastPos() {
        return onlineUsers.size()-1;
    }

    public String getReasonOfGameEnd(){
        return reasonOfGameEnd;
    }

    public void setReasonOfGameEnd(String reasonOfGameEnd){
        this.reasonOfGameEnd = reasonOfGameEnd;
    }


}
