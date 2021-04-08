package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Production;
import it.polimi.ingsw.server.model.Resource;
import javafx.util.Pair;

import java.util.List;

/**
 * Class for Development Cards. Since the color is not strictly tied to the input/output we totally decoupled that
 */
public class DevelopmentCard
{

    /**
     * Enum to indicate card color
     */
    private final DevelopmentCardColor cardType;
    /**
     * Production is the class to effectively use the card
     */
    private Production production;
    private List<Pair<Resource,Integer>> costList;
    private int victoryPoints;
    private int level;

    public DevelopmentCard(int level, DevelopmentCardColor cardType)
    {
        this(level,cardType,Production.basicProduction(),1);
    }

    public DevelopmentCard(int level, DevelopmentCardColor cardType,Production production)
    {
        this(level,cardType,production,1);
    }

    public DevelopmentCard(int level, DevelopmentCardColor cardType,Production production, int victoryPoints)
    {
        this.production = production;
        this.level = level;
        this.cardType = cardType;
        this.victoryPoints=victoryPoints;
    }
    public DevelopmentCard(int level, DevelopmentCardColor cardType,Production production, int victoryPoints, List<Pair<Resource,Integer>> costList)
    {
        this.production = production;
        this.level = level;
        this.cardType = cardType;
        this.victoryPoints=victoryPoints;
    }
    public int getLevel() {
        return level;
    }
    public DevelopmentCardColor getCardType() {
        return cardType;
    }

    public Production getProduction(){return production;}

}