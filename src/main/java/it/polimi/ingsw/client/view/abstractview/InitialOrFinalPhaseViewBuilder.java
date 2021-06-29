package it.polimi.ingsw.client.view.abstractview;

import it.polimi.ingsw.client.simplemodel.State;
import it.polimi.ingsw.client.view.CLI.InitialOrFinalPhaseCLI;
import it.polimi.ingsw.client.view.GUI.InitialOrFinalPhaseGUI;

import java.beans.PropertyChangeEvent;

import static it.polimi.ingsw.client.simplemodel.State.INITIAL_PHASE;
import static it.polimi.ingsw.client.simplemodel.State.MIDDLE_PHASE;

public abstract class InitialOrFinalPhaseViewBuilder extends ViewBuilder {
    public static boolean isInitial;


    /**
     * @param isInitial is a boolean to control the next transition
     */
    public InitialOrFinalPhaseViewBuilder(boolean isInitial) {
        InitialOrFinalPhaseViewBuilder.isInitial = isInitial;

    }

    public InitialOrFinalPhaseViewBuilder() {
        isInitial=true;

    }

    public static ViewBuilder getBuilder(boolean isCLI,boolean initial){
        if (isCLI)
            return new InitialOrFinalPhaseCLI(initial);
        else return new InitialOrFinalPhaseGUI(initial);
    }

    /**
     * This listener centralizes the possible interaction with Leader during the player turn
     * @param evt not null
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();


        if (MIDDLE_PHASE.name().equals(propertyName)) {
            getClient().changeViewBuilder(MiddlePhaseViewBuilder.getBuilder(getClient().isCLI()));
        } else if (INITIAL_PHASE.name().equals(propertyName)) {
            getClient().changeViewBuilder(getBuilder(getClient().isCLI(), true));
        }else if (State.FINAL_PHASE.name().equals(propertyName)) {
            getClient().changeViewBuilder(getBuilder(getClient().isCLI(), false));
        }else if (State.END_PHASE.name().equals(propertyName)) {
            getClient().changeViewBuilder(WinLooseBuilder.getBuilder(getClient().isCLI()));
        }else if(State.IDLE.name().equals(propertyName)) {
            getClient().changeViewBuilder(IDLEViewBuilder.getBuilder(getClient().isCLI()));
        }else
            ViewBuilder.printWrongStateReceived(evt);
    }



}
