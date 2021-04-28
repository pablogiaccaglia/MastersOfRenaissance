package it.polimi.ingsw.client;

import it.polimi.ingsw.client.abstractview.CLI.ConnectToServerView;
import it.polimi.ingsw.client.abstractview.CLI.CreateJoinLoadMatchView;
import it.polimi.ingsw.client.messages.servertoclient.ClientMessage;
import it.polimi.ingsw.network.messages.clienttoserver.ClientToServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * A class that represents the server inside the client.
 */
public class ServerHandler implements Runnable
{
    private Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Client owner;
    private AtomicBoolean shouldStop = new AtomicBoolean(false);


    /**
     * Initializes a new handler using a specific socket connected to
     * a server.
     * @param server The socket connection to the server.
     */
    ServerHandler(Socket server, Client owner)
    {
        this.server = server;
        this.owner = owner;
    }


    /**
     * Connects to the server and runs the event loop.
     */
    @Override
    public void run()
    {
        try {
            output = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            System.out.println("could not open connection to " + server.getInetAddress());
            owner.transitionToView(new ConnectToServerView());
            return;
        }

        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("server " + server.getInetAddress() + " connection dropped");
        }

        try {
            server.close();
        } catch (IOException e) { }
        owner.terminate();
    }


    /**
     * An event loop that receives messages from the server and processes
     * them in the order they are received.
     * @throws IOException If a communication error occurs.
     */
    private void handleClientConnection() throws IOException
    {
        getClient().transitionToView(new CreateJoinLoadMatchView());
        try {
            boolean stop = false;
            while (!stop) {
                /* read commands from the server and process them */
                try {
                    String next = input.readObject().toString();
                    ClientMessage command = ClientMessage.deserialize(next);
                    command.processMessage(this);
                } catch (IOException e) {
                    /* Check if we were interrupted because another thread has asked us to stop */
                    if (shouldStop.get()) {
                        /* Yes, exit the loop gracefully */
                        stop = true;
                    } else {
                        /* No, rethrow the exception */
                        throw e;
                    }
                }
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from server");
        }
    }


    /**
     * The game instance associated with this client.
     * @return The game instance.
     */
    public Client getClient()
    {
        return owner;
    }


    /**
     * Sends a message to the server.
     * @param commandMsg The message to be sent.
     */
    public void sendCommandMessage(ClientToServerMessage commandMsg)
    {
        try {
            output.writeObject(commandMsg.serialized());
        } catch (IOException e) {
            System.out.println("Communication error");
            owner.terminate();
        }
    }


    /**
     * Requires the run() method to stop as soon as possible.
     */
    public void stop()
    {
        shouldStop.set(true);
        try {
            server.shutdownInput();
        } catch (IOException e) { }
    }
}