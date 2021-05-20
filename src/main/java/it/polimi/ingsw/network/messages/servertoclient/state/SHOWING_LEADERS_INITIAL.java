package it.polimi.ingsw.network.messages.servertoclient.state;


import java.util.Map;
import java.util.UUID;

public class SHOWING_LEADERS_INITIAL extends StateInNetwork {

    Map<UUID, Boolean> playerLeaders;

    public SHOWING_LEADERS_INITIAL(Map<UUID, Boolean> playerLeaders){
        this.playerLeaders = playerLeaders;
    }

    public Map<UUID, Boolean> getPlayerLeaders(){
        return playerLeaders;
    }

}
