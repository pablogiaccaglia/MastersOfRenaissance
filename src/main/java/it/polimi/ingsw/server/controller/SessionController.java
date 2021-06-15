package it.polimi.ingsw.server.controller;


import it.polimi.ingsw.network.messages.servertoclient.MatchesData;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.messages.messagebuilders.Element;
import it.polimi.ingsw.server.utils.Deserializator;
import it.polimi.ingsw.server.utils.Serializator;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class SessionController {
    
    private HashMap<UUID,Match> matches = new HashMap<>();

    private HashMap<UUID, Long> matchesDisconnectionTimes = new HashMap<>();
    private List<ClientHandler> playersInLobby = new ArrayList<>();
    private static SessionController single_instance = null;
    private final String matchesFolderName = "src/savedMatches";
    private final int maxSecondsOffline = 1800; //30 minutes

    public static SessionController getInstance()
    {
        if (Objects.isNull(single_instance))
            single_instance = new SessionController();
        return single_instance;
    }

    private SessionController(){
        reloadServer();
    }

    public void addPlayerToLobby(ClientHandler clientHandler){
        playersInLobby.add(clientHandler);
    }

    public Match getLastMatch(){
           return matches.get(matches.size()-1);
    }

    public Optional<Match> getMatch(UUID matchId){
        return Optional.of(matches.get(matchId));
    }

    public Match addNewMatch(int maxPlayers, ClientHandler clientHandler){
        Match match = new Match(maxPlayers);
        matches.put(match.getMatchId(),match);
        clientHandler.setMatch(match);
        return match;
    }

    /**
     * Adds player to match and start
     * @return -1 if match is full or other problems
     */
    public int addPlayerToMatch(UUID matchID, String nickname, ClientHandler clientHandler){

        Match match = matches.get(matchID);

        if (match.canAddPlayer()){
            match.addPlayer(nickname,clientHandler);
            clientHandler.setMatch(match);
            return match.getLastPos();
        }

        else return -1;
    }

    public void startMatchAndNotifyStateIfPossible(Match match) {

        if (!match.canAddPlayer()) {
            playersInLobby.removeAll(match.clientsStream().collect(Collectors.toList()));
            match.startGame();
        }
    }

    public void joinMatchAndNotifyStateIfPossible(Match match, String playerNickname){

            List<Element> elements = new ArrayList<>(Arrays.asList(Element.values()));
            match.notifyStateToAllPlayers(elements, match.getGame().getPlayer(playerNickname).get());

    }


    public void notifyPlayersInLobby(ClientHandler cl){
        playersInLobby.forEach(
                clientHandler -> {
                    try {
                        clientHandler.sendAnswerMessage(new MatchesData(matchesData(clientHandler)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public Map<UUID, String[]> matchesData(ClientHandler clientHandler){

        return matches.entrySet().stream()

                .filter(match->
                        (clientHandler.getMatch().isPresent() && clientHandler.getMatch().get().getMatchId().equals(match.getKey()))
                        ||
                        (match.getValue().canAddPlayer())

                )
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry->entry.getValue().getOnlinePlayers()
                ));
    }

    private void reloadServer() {

        File folder = new File(Paths.get(matchesFolderName).toAbsolutePath().toString());

        Arrays.stream(folder.listFiles()).filter(File::isFile).forEach((file)->{
                Match match = Deserializator.deserializeMatch(folder.toString() +'/' + file.getName());
                matches.put(match.getMatchId(),match);
        });
    }

    //saved match for a player
    public Optional<UUID> checkForSavedMatches(String nickname){

        File folder = new File(matchesFolderName);
        folder = new File(folder.getAbsolutePath());

        return Arrays.stream(folder.listFiles()).filter(File::isFile).map(File::getName)
                .filter(s -> s.contains(nickname))
                .map(s->UUID.fromString(StringUtils.substringAfterLast(s,"|")))
                .findFirst();
    }

    public void saveMatch(Match match){

        String gameName = match.getSaveName();
        String folderAbsolutePath =  Paths.get(matchesFolderName).toAbsolutePath().toString();
        String path = folderAbsolutePath + '/' + gameName + ".json";

        try {
            Serializator.serializeMatch(match, path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Optional<Match> loadMatch(UUID gameId) {

        File folder = new File(matchesFolderName);
        return Arrays.stream(folder.listFiles()).filter(File::isFile)
                .filter(file -> file.getName().contains(gameId.toString()))
                .findFirst()
                .map((file -> Deserializator.deserializeMatch(file.getName() + ".json")));

    }

    public void deleteGame(UUID gameId){

        File folder = new File(Paths.get(matchesFolderName).toAbsolutePath().toString());

        Arrays.stream(folder.listFiles()).filter(File::isFile)
                .filter(file -> file.getName().contains(gameId.toString()))
                .findFirst().ifPresent(File::delete);
    }

    public boolean checkGameTimeout(UUID gameId){

        long currentTime =  System.currentTimeMillis();
        long disconnectionTime = matchesDisconnectionTimes.get(gameId);
        int elapsedTimeInSeconds = (int) TimeUnit.MILLISECONDS.toSeconds(currentTime - disconnectionTime);
        return elapsedTimeInSeconds > maxSecondsOffline;

    }

    public void registerDisconnection(Match match){
        matchesDisconnectionTimes.put(match.getMatchId(),System.currentTimeMillis());
        match.stopGame();
    }

    public void setPlayerOffline(ClientHandler clientHandler){

        if(clientHandler.getMatch().isPresent()){
            Match match = clientHandler.getMatch().get();
            match.setOfflineUser(clientHandler);

            if(match.areAllPlayersOffline())
                registerDisconnection(match);
        }

    }




}
