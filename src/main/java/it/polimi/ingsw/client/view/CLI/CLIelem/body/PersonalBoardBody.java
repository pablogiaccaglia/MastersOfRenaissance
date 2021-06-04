package it.polimi.ingsw.client.view.CLI.CLIelem.body;

import it.polimi.ingsw.client.simplemodel.PlayerCache;
import it.polimi.ingsw.client.simplemodel.SimpleModel;
import it.polimi.ingsw.client.view.CLI.CLIelem.CLIelem;
import it.polimi.ingsw.client.view.CLI.layout.Column;
import it.polimi.ingsw.client.view.CLI.layout.Option;
import it.polimi.ingsw.client.view.CLI.layout.Row;
import it.polimi.ingsw.client.view.CLI.layout.SizedBox;
import it.polimi.ingsw.client.view.CLI.layout.drawables.Drawable;
import it.polimi.ingsw.client.view.CLI.layout.drawables.DrawableFaithTrack;
import it.polimi.ingsw.client.view.CLI.layout.drawables.ResourceCLI;
import it.polimi.ingsw.client.view.CLI.textUtil.Background;
import it.polimi.ingsw.client.view.abstractview.ResourceMarketViewBuilder;
import it.polimi.ingsw.network.assets.resources.ResourceAsset;
import it.polimi.ingsw.network.simplemodel.SimpleDiscardBox;
import it.polimi.ingsw.network.simplemodel.SimpleFaithTrack;
import it.polimi.ingsw.network.simplemodel.SimpleStrongBox;
import it.polimi.ingsw.network.simplemodel.SimpleWarehouseLeadersDepot;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonalBoardBody extends CLIelem {

    public enum Mode{

        MOVING_RES(){
                @Override
        public void initialize(PersonalBoardBody board, PlayerCache cache, SimpleModel simpleModel){
                    SimpleFaithTrack[] simpleFaithTracks =Arrays.stream(simpleModel.getPlayersCaches())
                            .map(c->c.getElem(SimpleFaithTrack.class).orElseThrow()).toArray(SimpleFaithTrack[]::new);
                    board.faithTrack = new DrawableFaithTrack(cache.getElem(SimpleFaithTrack.class).orElseThrow(),simpleFaithTracks);
                    board.discardBox = discardBoxBuilder(cache.getElem(SimpleDiscardBox.class).orElseThrow());
                    board.wareHouseLeadersDepot = wareBuilder(cache.getElem(SimpleWarehouseLeadersDepot.class).orElseThrow());
                    board.strongBox = strongBoxBuilder(cache.getElem(SimpleStrongBox.class).orElseThrow());
        }},
        TEST() {
            @Override
            public void initialize(PersonalBoardBody board, PlayerCache cache, SimpleModel simpleModel) {
                board.wareHouseLeadersDepot = wareBuilder(cache.getElem(SimpleWarehouseLeadersDepot.class).orElseThrow());
            }
        };

        public abstract void initialize(PersonalBoardBody board, PlayerCache cache, SimpleModel simpleModel);
    }

    DrawableFaithTrack faithTrack;
    Column discardBox;
    Row strongBox;

    Column wareHouseLeadersDepot;

    public PersonalBoardBody(PlayerCache cache, Mode mode, SimpleModel simpleModel) {
        mode.initialize(this,cache, simpleModel);
    }

    private static Column discardBoxBuilder(SimpleDiscardBox simpleDiscardBox){

        Map<Integer, Pair<ResourceAsset, Integer>> discardBoxMap = simpleDiscardBox.getResourceMap();
        Stream<Option> optionList = discardBoxMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
            .map(e->optionFromAsset(e.getValue().getKey(),e.getValue().getValue(),0));
        Column discardedBoxList = new Column(optionList);
        discardedBoxList.addElem(Option.from("Discard", ResourceMarketViewBuilder::sendDiscard));
        return discardedBoxList;

    }

    private static Row strongBoxBuilder(SimpleStrongBox simpleStrongBox){

        Map<Integer, Pair<ResourceAsset, Pair<Integer, Integer>>> strongBoxMap = simpleStrongBox.getResourceMap();
        Stream<Option> optionList = strongBoxMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .map(e->optionFromAsset(e.getValue().getKey(), numAndSel(e).getKey(),numAndSel(e).getValue()));
        return new Row(optionList);

    }

    private static Pair<Integer, Integer> numAndSel(Map.Entry<Integer, Pair<ResourceAsset, Pair<Integer, Integer>>> e) {
        return e.getValue().getValue();
    }

    private static Option optionFromAsset(ResourceAsset asset,int n,int selected){
        Drawable dw = new Drawable();
        ResourceCLI resourceCLI = ResourceCLI.fromAsset(asset);
        dw.add(0, (n==1?"":n+" ")+resourceCLI.getFullName()+" ",resourceCLI.getC(), Background.DEFAULT);
        dw.add(0, (selected==0?"         ":selected+" selected"),resourceCLI.getC(), Background.DEFAULT);
        return Option.from(dw, ResourceMarketViewBuilder::sendDiscard);
    }

    private static Column wareBuilder(SimpleWarehouseLeadersDepot simpleWare){
        Column wareGrid = new Column();
        Map<Integer, List<Pair<ResourceAsset, Boolean>>> resMap = simpleWare.getDepots();
        List<Row> rows = resMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .map(e-> new Row(
                        e.getValue().stream().map(a -> optionFromAsset(a.getKey(), 1,0))))
                .collect(Collectors.toList());
        for (Row row : rows) {
            wareGrid.addElem(row);
        }
        return wareGrid;
    }


    public void selectInDiscardBox(){
        discardBox.selectAndRunOption(cli);
    }

    @Override
    public String toString() {
        Column root = new Column();
        Row endRow = root.addAndGetRow();

        Column depotsRow = endRow.addAndGetColumn();
        if (wareHouseLeadersDepot != null) {
            depotsRow.addElem(wareHouseLeadersDepot);
        }

        if (strongBox != null) {
            depotsRow.addElem(new SizedBox(0,2));
            depotsRow.addElem(strongBox);
        }

        if (discardBox != null) {
            endRow.addElem(discardBox);
        }
        return  (faithTrack==null?"":faithTrack.toString())+CanvasBody.centered(endRow).toString();
    }
}
