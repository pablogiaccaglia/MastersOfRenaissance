package it.polimi.ingsw.network.messages.servertoclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.network.messages.NetworkMessage;
import it.polimi.ingsw.network.messages.clienttoserver.ClientToServerMessage;
import it.polimi.ingsw.RuntimeTypeAdapterFactory;

import java.util.UUID;

import static it.polimi.ingsw.server.model.jsonUtility.serialize;

/**
 * A message representing an answer to a command.
 * Can be send even without a command
 * Contains common methods between client and server
 */
public abstract class ServerToClientMessage extends NetworkMessage
{
    UUID parentIdentifier;

    /**
     * Initializes the answer message.
     * @param command The CommandMsg being answered.
     */
    public ServerToClientMessage(ClientToServerMessage command)
    {
        this.parentIdentifier = command.getIdentifier();
    }

    /**
     * Initializes a ServerToClientMessage that doesn't answer any messages.
     */
    public ServerToClientMessage()
    {
        this.parentIdentifier = null;
    }


    /**
     * Returns the identifier of the message being answered.
     * @return The UUID of the answered message.
     */
    public UUID getParentIdentifier()
    {
        return parentIdentifier;
    }

    public String serialized()
    {

        RuntimeTypeAdapterFactory<ServerToClientMessage> serverToJson = RuntimeTypeAdapterFactory.of(ServerToClientMessage.class);

        //Register here all the message types
        serverToJson.registerSubtype(CreatedMatchStatus.class);
        serverToJson.registerSubtype(JoinStatus.class);
        serverToJson.registerSubtype(MatchesData.class);
        serverToJson.registerSubtype(StateMessage.class);

        Gson gson1 = new GsonBuilder()
                .registerTypeAdapterFactory(serverToJson)
                .create();

        return serialize(this,ServerToClientMessage.class, gson1);

    }

}
