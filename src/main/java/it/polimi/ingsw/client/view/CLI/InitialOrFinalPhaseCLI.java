package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.view.CLI.CLIelem.body.LeaderActionBody;
import it.polimi.ingsw.client.view.abstractview.InitialOrFinalPhaseViewBuilder;
import it.polimi.ingsw.network.simplemodel.SimplePlayerLeaders;


public class InitialOrFinalPhaseCLI extends InitialOrFinalPhaseViewBuilder implements CLIBuilder {


    public InitialOrFinalPhaseCLI(boolean isInitial) {
        super(isInitial);
    }

    @Override
    public void run() {
        SimplePlayerLeaders simplePlayerLeaders = getThisPlayerCache().getElem(SimplePlayerLeaders.class).orElseThrow();

        String initial = "InitialOrFinalStrategy";
        String end = "Final";
        getCLIView().setTitle(InitialOrFinalPhaseCLI.isInitial?initial:end+" phase");
        getCLIView().setBody(new LeaderActionBody(simplePlayerLeaders.getPlayerLeaders(),getClient()));
        getCLIView().show();
    }

}
