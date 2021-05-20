package it.polimi.ingsw.server.messages.messagebuilders;

import it.polimi.ingsw.network.assets.marbles.MarbleAsset;
import it.polimi.ingsw.server.model.market.Marble;
import javafx.util.Pair;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

public class MarketBoardMessageBuilder {

    public static UUID[][] marketBoardAdapter(Marble[][] marketMarbles, int marketRows, int marketColumns){
        List<UUID> marbles  = Arrays
                .stream(marketMarbles)
                .flatMap(Arrays::stream)
                .map(marble ->
                        UUID.nameUUIDFromBytes(MarbleAsset.fromInt(Marble.getMarbleNumber(marble)).getName().getBytes(StandardCharsets.UTF_8)))
                .collect(Collectors.toList());

        return IntStream.range(0, marketColumns*marketColumns)
                .mapToObj((pos)->new Pair<>(pos,marbles.get(pos)))
                .collect(groupingBy((e)->e.getKey()% marketRows))
                .values()
                .stream()
                .map(MarketBoardMessageBuilder::pairToValue)
                .toArray(UUID[][]::new);
    }

    private static UUID[] pairToValue(List<Pair<Integer, UUID>> pos_marArray){
        return pos_marArray.stream().map(Pair::getValue).toArray(UUID[]::new);
    }

}