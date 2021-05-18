package it.polimi.ingsw.server.utils;

import com.google.gson.*;
import it.polimi.ingsw.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.network.assets.*;
import it.polimi.ingsw.network.assets.devcards.NetworkDevelopmentCard;
import it.polimi.ingsw.network.assets.leaders.NetworkLeaderCard;
import it.polimi.ingsw.server.model.cards.DevelopmentCardColor;
import it.polimi.ingsw.network.jsonUtils.JsonUtility;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.cards.*;
import it.polimi.ingsw.server.model.cards.production.Production;
import it.polimi.ingsw.server.model.player.board.LeaderDepot;
import it.polimi.ingsw.server.model.player.leaders.*;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.*;

import static it.polimi.ingsw.server.utils.Deserializator.devCardsMap;

public class Serializator extends JsonUtility {

    public static final String frontDevCardPathString = "src/main/resources/assets/devCards/FRONT/Masters of Renaissance_Cards_FRONT_";
    public static final String backDevCardPathString = "src/main/resources/assets/devCards/BACK/Masters of Renaissance__Cards_BACK_";
    public static final String frontLeaderCardPathString = "src/main/resources/assets/leaders/FRONT/Masters of Renaissance_Cards_FRONT_";
    public static final String backLeaderCardPathString = "src/main/resources/assets/leaders/BACK/Masters of Renaissance__Cards_BACK.png";
    private static final GsonBuilder gsonBuilder = new GsonBuilder();
    //helper method to serialize cardshop configuration

    public static void cardShopSerialization(){
        CardShop shop = new CardShop(Deserializator.devCardsDeckDeserialization());
        serialize(configPathString + "CardShopConfig.json", shop, CardShop.class, gsonBuilder.registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).setPrettyPrinting().create());
    }


    private static Map<UUID , DevelopmentCardAsset> devCardsAssetsBuilder() {
        Map<UUID, NetworkDevelopmentCard> cardsFromJsonHandlerMap = devCardsMap();
        return cardsFromJsonHandlerMap.keySet().stream().map(id -> {
            NetworkDevelopmentCard card = cardsFromJsonHandlerMap.get(id);
            String cardPathSuffix = card.getCardType().toString() + "_" + card.getLevel() + ".png";
            String front = frontDevCardPathString + cardPathSuffix;
            String back = backDevCardPathString + cardPathSuffix;
            return new DevelopmentCardAsset(card, front, back, id);
        }).collect(Collectors.toMap(DevelopmentCardAsset::getCardId, Function.identity()));
    }

    private static Map<UUID , LeaderCardAsset> leaderCardsAssetsMapBuilder() {

        return IntStream.range(0, Deserializator.networkLeaderCardsDeserialization().size()).boxed().map(index -> {

            NetworkLeaderCard card = Deserializator.networkLeaderCardsDeserialization().get(index);
            String cardPathSuffix = index.toString() + ".png";
            String front = frontLeaderCardPathString + cardPathSuffix;
            return new LeaderCardAsset(card, front, backLeaderCardPathString, UUID.nameUUIDFromBytes(card.toString().getBytes(StandardCharsets.UTF_8)));

        }).collect(Collectors.toMap(LeaderCardAsset::getCardId, Function.identity()));

    }

    public static void devCardsAssetsSerialization(){

        Gson customGson = gsonBuilder.enableComplexMapKeySerialization().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).registerTypeHierarchyAdapter(Path.class, new PathConverter()).setPrettyPrinting().create();
        serialize(configPathString + "DevCardsAssetsMapConfig.json", devCardsAssetsBuilder(), Map.class, customGson);
    }

    public static void leaderCardsAssetsSerialization(){
        Gson customGson = gsonBuilder.enableComplexMapKeySerialization().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).registerTypeHierarchyAdapter(Path.class, new PathConverter()).setPrettyPrinting().create();
        serialize(configPathString + "leaderCardsAssetsMap.json", leaderCardsAssetsMapBuilder(), Map.class, customGson);
    }

    public static void leaderCardsArraySerialization() {
        Resource bonus;
        Production addProd;
        Pair<Resource, Integer> costTest;
        Pair<Resource, Integer> costTest2;
        Pair<Resource, Integer> costTest3;
        List<Pair<Resource, Integer>> requirementsTest;
        Pair<DevelopmentCardColor, Integer> costTestCards;
        Pair<DevelopmentCardColor, Integer> costTestCards2;
        List<Pair<DevelopmentCardColor, Integer>> requirementsTestCards;

        ArrayList<DevelopmentCard> series=new ArrayList<>();
        ArrayList<Leader> series2=new ArrayList<>();

        int victoryPoints;
        DevelopmentCardColor color;
        int level;

        addProd=new Production(new int[]{1,0,0,0,0,0,0},new int[]{0,0,0,0,2,0,0});
        costTest = new Pair<>(Resource.SHIELD, 2);
        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        victoryPoints=1;
        color=DevelopmentCardColor.GREEN;
        level=1;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));




        addProd=new Production(new int[]{2,0,0,0,0,0,0},new int[]{0,1,1,1,0,0,0});
        costTest = new Pair<>(Resource.SERVANT, 3);
        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        victoryPoints=3;
        level=1;
        color=DevelopmentCardColor.PURPLE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        addProd=new Production(new int[]{0,0,0,2,0,0,0},new int[]{1,1,1,0,0,0,0});
        costTest = new Pair<>(Resource.GOLD, 3);
        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        victoryPoints=3;
        level=1;
        color=DevelopmentCardColor.BLUE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        addProd=new Production(new int[]{0,2,0,0,0,0,0},new int[]{1,0,1,1,0,0,0});
        costTest = new Pair<>(Resource.STONE, 3);
        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        victoryPoints=3;
        level=1;
        color=DevelopmentCardColor.YELLOW;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));

        addProd=new Production(new int[]{0,1,0,1,0,0,0},new int[]{2,0,0,0,1,0,0});
        costTest = new Pair<>(Resource.SHIELD, 2);
        costTest2 = new Pair<>(Resource.GOLD, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=4;
        level=1;
        color=DevelopmentCardColor.GREEN;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //6
        addProd=new Production(new int[]{1,0,1,0,0,0,0},new int[]{0,0,0,2,1,0,0});
        costTest = new Pair<>(Resource.SERVANT, 2);
        costTest2 = new Pair<>(Resource.STONE, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=4;
        level=1;
        color=DevelopmentCardColor.PURPLE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));

        //7

        addProd=new Production(new int[]{0,0,1,1,0,0,0},new int[]{0,2,0,0,1,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 2);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=4;
        level=1;
        color=DevelopmentCardColor.BLUE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //8

        addProd=new Production(new int[]{1,1,0,0,0,0,0},new int[]{0,0,2,0,1,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 2);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 2);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=4;
        level=1;
        color=DevelopmentCardColor.YELLOW;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //9

        addProd=new Production(new int[]{0,0,0,1,0,0,0},new int[]{0,0,0,0,2,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 4);
        //costTest2 = new Pair<>(Resource.SHIELD, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=5;
        level=2;
        color=DevelopmentCardColor.GREEN;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));

//10
        addProd=new Production(new int[]{1,0,0,0,0,0,0},new int[]{0,0,0,0,2,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 4);
        //costTest2 = new Pair<>(Resource.SHIELD, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=5;
        level=2;
        color=DevelopmentCardColor.PURPLE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));
        //11

        addProd=new Production(new int[]{0,1,0,0,0,0,0},new int[]{0,0,0,0,2,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 4);
        //costTest2 = new Pair<>(Resource.SHIELD, 2);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=5;
        level=2;
        color=DevelopmentCardColor.BLUE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //12
        addProd=new Production(new int[]{0,0,0,1,0,0,0},new int[]{0,0,0,0,1,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 2);
        //costTest2 = new Pair<>(Resource.SHIELD, 2);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=1;
        level=1;
        color=DevelopmentCardColor.PURPLE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //13
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,2,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 4);
        //costTest2 = new Pair<>(Resource.SHIELD, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=5;
        level=2;
        color=DevelopmentCardColor.YELLOW;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //14
        addProd=new Production(new int[]{0,1,1,0,0,0,0},new int[]{0,0,0,3,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 3);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 2);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=5;
        level=2;
        color=DevelopmentCardColor.GREEN;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //15
        addProd=new Production(new int[]{1,1,0,0,0,0,0},new int[]{0,0,3,0,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 2);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 3);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=6 ;
        level=2;
        color=DevelopmentCardColor.PURPLE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //16
        addProd=new Production(new int[]{1,0,0,1,0,0,0},new int[]{0,3,0,0,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 3);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=6 ;
        level=2;
        color=DevelopmentCardColor.BLUE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //17
        addProd=new Production(new int[]{0,1,0,1,0,0,0},new int[]{3,0,0,0,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 3);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=6 ;
        level=2;
        color=DevelopmentCardColor.YELLOW;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //18
        addProd=new Production(new int[]{2,0,0,1,0,0,0},new int[]{0,0,0,2,2,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 5);
        //costTest2 = new Pair<>(Resource.SHIELD, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=7 ;
        level=2;
        color=DevelopmentCardColor.GREEN;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));

        //19
        addProd=new Production(new int[]{0,0,0,2,0,0,0},new int[]{2,0,0,0,2,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 5);
        //costTest2 = new Pair<>(Resource.SHIELD, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=7 ;
        level=2;
        color=DevelopmentCardColor.PURPLE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));

        //20
        addProd=new Production(new int[]{0,2,0,0,0,0,0},new int[]{0,0,2,0,2,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 5);
        //costTest2 = new Pair<>(Resource.SHIELD, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=7 ;
        level=2;
        color=DevelopmentCardColor.BLUE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //21
        addProd=new Production(new int[]{0,0,2,0,0,0,0},new int[]{0,2,0,0,2,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 5);
        //costTest2 = new Pair<>(Resource.SHIELD, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=7 ;
        level=2;
        color=DevelopmentCardColor.YELLOW;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //22
        addProd=new Production(new int[]{1,0,0,0,0,0,0},new int[]{0,0,2,0,1,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 3);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 3);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=8;
        level=2;
        color=DevelopmentCardColor.GREEN;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //23
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 2);
        //costTest2 = new Pair<>(Resource.GOLD, 3);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=1;
        level=1;
        color=DevelopmentCardColor.BLUE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //24
        addProd=new Production(new int[]{0,0,0,1,0,0,0},new int[]{0,2,0,0,1,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 3);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 3);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=8;
        level=2;
        color=DevelopmentCardColor.PURPLE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //25
        addProd=new Production(new int[]{0,1,0,0,0,0,0},new int[]{0,0,0,2,1,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 3);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 3);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=8;
        level=2;
        color=DevelopmentCardColor.BLUE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //26
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{2,0,0,0,1,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 3);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 3);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=8;
        level=2;
        color=DevelopmentCardColor.YELLOW;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //27
        addProd=new Production(new int[]{2,0,0,0,0,0,0},new int[]{0,0,0,3,2,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 6);
        //costTest2 = new Pair<>(Resource.STONE, 3);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=9;
        level=3;
        color=DevelopmentCardColor.GREEN;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //28
        addProd=new Production(new int[]{0,0,0,2,0,0,0},new int[]{3,0,0,0,2,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 6);
        //costTest2 = new Pair<>(Resource.STONE, 3);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=9;
        level=3;
        color=DevelopmentCardColor.PURPLE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));




        //29
        addProd=new Production(new int[]{0,2,0,0,0,0,0},new int[]{0,0,3,0,2,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 6);
        //costTest2 = new Pair<>(Resource.STONE, 3);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=9;
        level=3;
        color=DevelopmentCardColor.BLUE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //30
        addProd=new Production(new int[]{0,0,2,0,0,0,0},new int[]{0,3,0,0,2,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 6);
        //costTest2 = new Pair<>(Resource.STONE, 3);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=9;
        level=3;
        color=DevelopmentCardColor.YELLOW;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //31
        addProd=new Production(new int[]{1,1,0,0,0,0,0},new int[]{0,0,2,2,1,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 5);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=10;
        level=3;
        color=DevelopmentCardColor.GREEN;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));

        //32
        addProd=new Production(new int[]{0,0,1,1,0,0,0},new int[]{2,2,0,0,1,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 5);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=10;
        level=3;
        color=DevelopmentCardColor.PURPLE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //33
        addProd=new Production(new int[]{1,0,1,0,0,0,0},new int[]{0,2,0,2,1,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 5);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=10;
        level=3;
        color=DevelopmentCardColor.BLUE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //34
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,1,0,0,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 2);
        //costTest2 = new Pair<>(Resource.STONE, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=1;
        level=1;
        color=DevelopmentCardColor.YELLOW;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //35
        addProd=new Production(new int[]{0,1,0,1,0,0,0},new int[]{2,0,2,0,1,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 5);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=10;
        level=3;
        color=DevelopmentCardColor.YELLOW;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //36
        addProd=new Production(new int[]{0,1,0,0    ,0,0,0},new int[]{1,0,0,0,3,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 7);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=11;
        level=3;
        color=DevelopmentCardColor.GREEN;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));




        //37
        addProd=new Production(new int[]{0,1,0,0,0,0,0},new int[]{0,0,0,1,3,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 7);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 2);

        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=11;
        level=3;
        color=DevelopmentCardColor.PURPLE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));




        //38
        addProd=new Production(new int[]{0,0,0,1,0,0,0},new int[]{0,0,1,0,3,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 7);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 2);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=11;
        level=3;
        color=DevelopmentCardColor.BLUE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //39
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,1,0,0,0,3,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 7);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 2);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=11;
        level=3;
        color=DevelopmentCardColor.YELLOW;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //40
        addProd=new Production(new int[]{0,0,0,1,0,0,0},new int[]{3,0,1,0,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 4);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 4);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=12;
        level=3;
        color=DevelopmentCardColor.GREEN;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));






        //41
        addProd=new Production(new int[]{1,0,0,0,0,0,0},new int[]{0,1,0,3,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 4);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 4);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=12;
        level=3;
        color=DevelopmentCardColor.PURPLE;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //42
        addProd=new Production(new int[]{0,1,0,0,0,0,0},new int[]{1,0,3,0,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 4);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 4);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=12;
        level=3;
        color=DevelopmentCardColor.BLUE ;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //43
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,3,0,1,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 4);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 4);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        victoryPoints=12;
        level=3;
        color=DevelopmentCardColor.YELLOW ;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));




        //64
        addProd=new Production(new int[]{0,2,0,0,0,0,0},new int[]{1,0,1,1,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 3);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 4);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        //requirementsTest.add(costTest2);
        victoryPoints=3;
        level=1;
        color=DevelopmentCardColor.GREEN ;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //63
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{1,0,0,0,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 1);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 1);
        costTest3 = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 1);
        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        requirementsTest.add(costTest3);

        victoryPoints=2;
        level=1;
        color=DevelopmentCardColor.YELLOW ;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //62
        addProd=new Production(new int[]{0,1,0,0,0,0,0},new int[]{0,0,0,1,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 1);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 1);
        costTest3 = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 1);
        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        requirementsTest.add(costTest3);

        victoryPoints=2;
        level=1;
        color=DevelopmentCardColor.BLUE ;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));


        //56
        addProd=new Production(new int[]{1,0,0,0,0,0,0},new int[]{0,0,1,0,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 1);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 1);
        costTest3 = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 1);
        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        requirementsTest.add(costTest3);

        victoryPoints=2;
        level=1;
        color=DevelopmentCardColor.PURPLE ;

        series.add(new DevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));



        //45
        addProd=new Production(new int[]{1,0,0,0,0,0,0},new int[]{0,0,1,0,0,0,0});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 1);
        costTest2 = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 1);
        costTest3 = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 1);
        requirementsTest = new ArrayList<>();
        requirementsTest.add(costTest);
        requirementsTest.add(costTest2);
        requirementsTest.add(costTest3);

        victoryPoints=2;
        level=1;
        color=DevelopmentCardColor.GREEN ;

       /* series.add(new NetworkDevelopmentCard(level, color,addProd,victoryPoints,requirementsTest));
        Gson gsonprint= new Gson();
        System.out.println(gsonprint.toJson(series));
        */


        Leader leader;
        ProductionLeader cjclem;


        //61
        addProd=new Production(new int[]{1,0,0,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTestCards = new Pair<>(DevelopmentCardColor.GREEN, 1);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();

        requirementsTestCards = new ArrayList<Pair<DevelopmentCardColor, Integer>>();
        requirementsTestCards.add(costTestCards);

        victoryPoints=4;
        level=2;

        series2.add(new ProductionLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,addProd,level));



        //60
        addProd=new Production(new int[]{0,0,0,1,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTestCards = new Pair<>(DevelopmentCardColor.PURPLE, 1);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();

        requirementsTestCards = new ArrayList<Pair<DevelopmentCardColor, Integer>>();
        requirementsTestCards.add(costTestCards);

        victoryPoints=4;
        level=2;

        series2.add(new ProductionLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,addProd,level));


        //59
        addProd=new Production(new int[]{0,1,0,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTestCards = new Pair<>(DevelopmentCardColor.BLUE, 1);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();

        requirementsTestCards = new ArrayList<Pair<DevelopmentCardColor, Integer>>();
        requirementsTestCards.add(costTestCards);

        victoryPoints=4;
        level=2;

        series2.add(new ProductionLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,addProd,level));

        //58
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTestCards = new Pair<>(DevelopmentCardColor.YELLOW, 1);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();

        requirementsTestCards = new ArrayList<Pair<DevelopmentCardColor, Integer>>();
        requirementsTestCards.add(costTestCards);

        victoryPoints=4;
        level=2;

        series2.add(new ProductionLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,addProd,level));


        //57
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTestCards = new Pair<>(DevelopmentCardColor.PURPLE, 2);
        costTestCards2 = new Pair<>(DevelopmentCardColor.GREEN, 1);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();

        requirementsTestCards = new ArrayList<Pair<DevelopmentCardColor, Integer>>();
        requirementsTestCards.add(costTestCards);
        requirementsTestCards.add(costTestCards2);

        victoryPoints=5;
        level=1;
        bonus= it.polimi.ingsw.server.model.Resource.GOLD;

        series2.add(new MarketLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,bonus));


        //55
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTestCards = new Pair<>(DevelopmentCardColor.BLUE, 2);
        costTestCards2 = new Pair<>(DevelopmentCardColor.YELLOW, 1);

        requirementsTest = new ArrayList<>();

        requirementsTestCards = new ArrayList<>();
        requirementsTestCards.add(costTestCards);
        requirementsTestCards.add(costTestCards2);

        victoryPoints=5;
        bonus= it.polimi.ingsw.server.model.Resource.STONE;


        series2.add(new MarketLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,bonus));



        //54
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTestCards = new Pair<>(DevelopmentCardColor.GREEN, 2);
        costTestCards2 = new Pair<>(DevelopmentCardColor.PURPLE, 1);

        requirementsTest = new ArrayList<>();

        requirementsTestCards = new ArrayList<>();
        requirementsTestCards.add(costTestCards);
        requirementsTestCards.add(costTestCards2);

        victoryPoints=5;
        bonus = it.polimi.ingsw.server.model.Resource.SHIELD;


        series2.add(new MarketLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,bonus));


        //53
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTestCards = new Pair<>(DevelopmentCardColor.YELLOW, 2);
        costTestCards2 = new Pair<>(DevelopmentCardColor.BLUE, 1);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();

        requirementsTestCards = new ArrayList<Pair<DevelopmentCardColor, Integer>>();
        requirementsTestCards.add(costTestCards);
        requirementsTestCards.add(costTestCards2);

        victoryPoints=5;
        bonus = it.polimi.ingsw.server.model.Resource.SERVANT;


        series2.add(new MarketLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,bonus));


        LeaderDepot depo;

        //52
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 5);
        costTestCards2 = new Pair<>(DevelopmentCardColor.BLUE, 1);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTestCards = new ArrayList<Pair<DevelopmentCardColor, Integer>>();

        requirementsTest.add(costTest);

        victoryPoints=3;
        depo=new LeaderDepot(0, it.polimi.ingsw.server.model.Resource.GOLD);

        series2.add(new DepositLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,depo));


        //51
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 5);
        costTestCards2 = new Pair<>(DevelopmentCardColor.BLUE, 1);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTestCards = new ArrayList<Pair<DevelopmentCardColor, Integer>>();

        requirementsTest.add(costTest);

        victoryPoints=3;
        depo=new LeaderDepot(0, it.polimi.ingsw.server.model.Resource.SHIELD);

        series2.add(new DepositLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,depo));



        //50
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 5);
        costTestCards2 = new Pair<>(DevelopmentCardColor.BLUE, 1);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTestCards = new ArrayList<Pair<DevelopmentCardColor, Integer>>();

        requirementsTest.add(costTest);

        victoryPoints=3;
        depo=new LeaderDepot(0, it.polimi.ingsw.server.model.Resource.SERVANT);

        series2.add(new DepositLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,depo));




        //49
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 5);
        costTestCards2 = new Pair<>(DevelopmentCardColor.BLUE, 1);

        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTestCards = new ArrayList<Pair<DevelopmentCardColor, Integer>>();

        requirementsTest.add(costTest);

        victoryPoints=3;
        depo=new LeaderDepot(0, it.polimi.ingsw.server.model.Resource.STONE);

        series2.add(new DepositLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,depo));




        //48
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTestCards = new Pair<>(DevelopmentCardColor.YELLOW, 1);
        costTestCards2 = new Pair<>(DevelopmentCardColor.PURPLE, 1);


        requirementsTest = new ArrayList<Pair<it.polimi.ingsw.server.model.Resource, Integer>>();
        requirementsTestCards = new ArrayList<Pair<DevelopmentCardColor, Integer>>();

        requirementsTestCards.add(costTestCards);
        requirementsTestCards.add(costTestCards2);


        victoryPoints=2;
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.GOLD, 1);


        series2.add(new DevelopmentDiscountLeader(  LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,costTest));



        //47
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTestCards = new Pair<>(DevelopmentCardColor.GREEN, 1);
        costTestCards2 = new Pair<>(DevelopmentCardColor.BLUE, 1);


        requirementsTest = new ArrayList<>();
        requirementsTestCards = new ArrayList<>();

        requirementsTestCards.add(costTestCards);
        requirementsTestCards.add(costTestCards2);


        victoryPoints=2;
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.STONE, 1);


        series2.add(new DevelopmentDiscountLeader(  LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,costTest));



        //46
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTestCards = new Pair<>(DevelopmentCardColor.PURPLE, 1);
        costTestCards2 = new Pair<>(DevelopmentCardColor.BLUE, 1);


        requirementsTest = new ArrayList<>();
        requirementsTestCards = new ArrayList<>();

        requirementsTestCards.add(costTestCards);
        requirementsTestCards.add(costTestCards2);


        victoryPoints=2;
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SHIELD, 1);


        series2.add(new DevelopmentDiscountLeader(  LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,costTest));



        //45
        addProd=new Production(new int[]{0,0,1,0,0,0,0},new int[]{0,0,0,0,1,0,1});
        costTestCards = new Pair<>(DevelopmentCardColor.YELLOW, 1);
        costTestCards2 = new Pair<>(DevelopmentCardColor.GREEN, 1);


        requirementsTest = new ArrayList<>();
        requirementsTestCards = new ArrayList<>();

        requirementsTestCards.add(costTestCards);
        requirementsTestCards.add(costTestCards2);


        victoryPoints=2;
        costTest = new Pair<>(it.polimi.ingsw.server.model.Resource.SERVANT, 1);


        series2.add(new DevelopmentDiscountLeader(LeaderState.INACTIVE,victoryPoints,requirementsTest,requirementsTestCards,costTest));


        RuntimeTypeAdapterFactory<Leader> shapeAdapterFactory = RuntimeTypeAdapterFactory.of(Leader.class);


        shapeAdapterFactory.registerSubtype(DepositLeader.class);
        shapeAdapterFactory.registerSubtype(MarketLeader.class);
        shapeAdapterFactory.registerSubtype(ProductionLeader.class);
        shapeAdapterFactory.registerSubtype(DevelopmentDiscountLeader.class);


        Gson gson1 = gsonBuilder
                .registerTypeAdapterFactory(shapeAdapterFactory).setPrettyPrinting()
                .create();

        serialize(configPathString+"LeadersConfig.json", series2.toArray(Leader[]::new), Leader[].class, gson1);
     /*   String serialized = gson1.toJson(series2.toArray(Leader[]::new), Leader[].class);
        Writer writer = new FileWriter("src/main/resources/config/LeadersConfig.json");
        writer.write(serialized);
        writer.flush(); //flush data to file   <---
        writer.close(); //close write          <---  */


        //   Leader[] cards.leaders = deserialize("src/main/resources/config/LeadersConfig.json" , Leader[].class);
    }

    public static void main(String[] args) throws IOException {
    /*
        --- * uncomment to update config files / test serialization * ---

        cardShopSerialization();
        devCardsAssetsSerialization();
        leaderCardsArraySerialization();
        MarketBoard test = marketBoardDeserialization();
        serialize(configPathString + "MarketBoardConfig.json", test, MarketBoard.class);
        faithTrackDeserialization();
        devCardsAssetsSerialization();
        leaderCardsAssetsSerialization();

     */

    }


}
