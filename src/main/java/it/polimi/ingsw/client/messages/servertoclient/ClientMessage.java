package it.polimi.ingsw.client.messages.servertoclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.RuntimeTypeAdapterFactory;

import java.io.IOException;

/**
 * All the messages from the server to the client implement this interface.
 * It contains methods that can only be used in the client
 */
public interface ClientMessage {

    static ClientMessage deserialize(String jsonString) {
        RuntimeTypeAdapterFactory<ClientMessage> shapeAdapterFactory = RuntimeTypeAdapterFactory.of(ClientMessage.class);

        shapeAdapterFactory.registerSubtype(CreatedMatchStatus.class);

        Gson gson1 = new GsonBuilder()
                .registerTypeAdapterFactory(shapeAdapterFactory)
                .create();

        return gson1.fromJson(jsonString, ClientMessage.class);
    }

    /**
     * Method invoked in the client to process the message.
     */
    void processMessage(ServerHandler serverHandler) throws IOException;
}