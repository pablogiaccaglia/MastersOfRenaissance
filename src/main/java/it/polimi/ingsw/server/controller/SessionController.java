package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.network.messages.servertoclient.MatchesData;
import it.polimi.ingsw.network.messages.servertoclient.state.StateMessageContainer;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.model.State;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.server.model.jsonUtility.deserialize;

public class SessionController {
    
    private HashMap<UUID,Match> matches = new HashMap<>();
    private List<String> playersInLobby = new ArrayList<>();
    private static SessionController single_instance = null;

    public static SessionController getInstance()
    {
        if (single_instance == null)
            single_instance = new SessionController();

        return single_instance;
    }

    public void addPlayerToLobby(String nickname){
        playersInLobby.add(nickname);
    }

    public Match getLastMatch(){
           return matches.get(matches.size()-1);
    }

    public Match addNewMatch(int maxPlayers){
        Match match = new Match(maxPlayers);
        matches.put(match.getMatchId(),match);
        return match;
    }

    /**
     * Adds player to match and start
     * @param matchID
     * @param nickname
     * @return false if match is full
     */
    public boolean addPlayerToMatchAndStartWhenReady(UUID matchID, String nickname, ClientHandler clientHandler){
        Match match = matches.get(matchID);
        if (match.canAddPlayer()){
            match.addPlayer(nickname,clientHandler);
            if (!match.canAddPlayer()) {
                match.startGame();
                try {
                    match.currentPlayerClientHandler().sendAnswerMessage(
                            new StateMessageContainer(State.SETUP_PHASE.toStateMessage(match.getGame()))
                            );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            notifyMatchChanges();
            return true;
        }
        else return false;
    }

    public void notifyMatchChanges(){
        matches.values().stream().filter(Match::hasNotStarted).flatMap(Match::clientsStream).forEach(
                clientHandler -> {
                    try {
                        clientHandler.sendAnswerMessage(new MatchesData(matchesData()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public Map<UUID, String[]> matchesData(){
        return matches.entrySet().stream()
                .collect(Collectors.toMap(
                        entry->entry.getKey(),
                        entry->entry.getValue().getOnlinePlayers()
                ));
    }

    public void reloadServer() {
        File folder = new File("src/main/resources/savedMatches/");
        Arrays.stream(folder.listFiles()).filter(File::isFile).forEach((file)->{
                Match match = deserialize(folder.toString() + file.getName() + ".json", Match.class);
                matches.put(match.getMatchId(),match);
        });
    }

    public Optional<UUID> checkForSavedMatches(String nickname){
        File folder = new File("src/main/resources/savedMatches/");
        return Arrays.stream(folder.listFiles()).filter(File::isFile).map(File::getName)
                .filter(s -> s.contains(nickname))
                .map(s->UUID.fromString(StringUtils.substringAfterLast(s,"|")))
                .findFirst();
    }

    public void saveMatch(Match match) throws IOException {
        String gameName = match.getSaveName();
        Writer writer = new FileWriter("src/main/resources/savedMatches/"+gameName+".json");
        Gson gson = new GsonBuilder().create();
        gson.toJson(match, writer);
        writer.flush(); //flush data to file
        writer.close(); //close write
    }

    public Optional<Match> loadMatch(UUID gameId) {
        File folder = new File("src/main/resources/savedMatches/");
        return Arrays.stream(folder.listFiles()).filter(File::isFile)
                .filter(file -> file.getName().contains(gameId.toString()))
                .findFirst()
                .map((file -> deserialize(folder.toString() + file.getName() + ".json", Match.class)));
    }

    public void deleteGame(UUID gameId){
        File folder = new File("src/main/resources/savedMatches/");
        Arrays.stream(folder.listFiles()).filter(File::isFile)
                .filter(file -> file.getName().contains(gameId.toString()))
                .findFirst().ifPresent(File::delete);
    }
}
