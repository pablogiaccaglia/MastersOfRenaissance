package it.polimi.ingsw.server.messages.clienttoserver;

import it.polimi.ingsw.network.messages.servertoclient.CreatedMatchStatus;
import it.polimi.ingsw.network.messages.servertoclient.MatchesData;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.Match;
import it.polimi.ingsw.server.controller.SessionController;

import java.io.IOException;

public class CreateMatchRequest extends it.polimi.ingsw.network.messages.clienttoserver.CreateMatchRequest implements ServerMessage {

    public CreateMatchRequest(int maxPlayers, String nickName) {
        super(maxPlayers, nickName);
    }

    /**
     * Method invoked in the server to process the message.
     */
    @Override
    public void processMessage(ClientHandler clientHandler) throws IOException {

        clientHandler.getMatch().ifPresentOrElse(
             (match)->{
                    try {
                        clientHandler.sendAnswerMessage(new MatchesData(SessionController.getInstance().matchesData(clientHandler)));
                        clientHandler.sendAnswerMessage(new CreatedMatchStatus(this,null, CreatedMatchStatus.motive.OTHER));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }},
             ()->{
                     try {
                         Match match = SessionController.getInstance().addNewMatch(maxPlayers);
                         clientHandler.setMatch(match);
                         SessionController.getInstance().addPlayerToMatchAndStartWhenReady(match.getMatchId(),nickName,clientHandler);
                         clientHandler.sendAnswerMessage(new CreatedMatchStatus(this,match.getMatchId(),null));
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                    });
    }
}
