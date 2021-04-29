package it.polimi.ingsw.client.view.abstractview;

import java.beans.PropertyChangeEvent;

public abstract class ConnectToServerView extends View{


    /**
     * The main method of the view. Handles user interaction. User interaction
     * is considered ended when this method exits.
     *
     * @implNote This method shall exit as soon as possible after stopInteraction()
     * is called (from another thread).
     */
    @Override
    public void run() {

    }
}