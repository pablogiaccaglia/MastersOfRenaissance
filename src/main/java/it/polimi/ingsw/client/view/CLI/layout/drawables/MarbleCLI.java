package it.polimi.ingsw.client.view.CLI.layout.drawables;

import it.polimi.ingsw.client.view.CLI.textUtil.Background;
import it.polimi.ingsw.client.view.CLI.textUtil.Color;
import it.polimi.ingsw.network.assets.marbles.MarbleAsset;
import it.polimi.ingsw.network.assets.resources.ResourceAsset;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MarbleCLI {

    WHITE(MarbleAsset.WHITE,Color.WHITE),
    BLUE(MarbleAsset.BLUE,Color.SHIELD),
    GRAY(MarbleAsset.GRAY,Color.STONE),
    YELLOW(MarbleAsset.YELLOW,Color.GOLD),
    PURPLE(MarbleAsset.PURPLE,Color.SERVANT),
    RED(MarbleAsset.RED,Color.RED);

    private final MarbleAsset res;
    private final Color c;

    MarbleCLI(MarbleAsset res, Color c) {
        this.res = res;
        this.c = c;
    }

    public Drawable toBigDrawableList(){

        Drawable dwList = new Drawable();
        dwList.add(0,"    █      ",c,Background.DEFAULT);
        dwList.add(0," ███████   ",c,Background.DEFAULT);
        dwList.add(0,"█████████  ",c,Background.DEFAULT);
        dwList.add(0," ███████   ",c,Background.DEFAULT);
        dwList.add(0,"    █      ",c,Background.DEFAULT);
        return dwList;
    }

    public static List<Drawable> toList(){
        return Arrays.stream(MarbleCLI.values()).limit(6).map(MarbleCLI::toBigDrawableList).collect(Collectors.toList());
    }

    public static int width(){
        return WHITE.toBigDrawableList().getWidth();
    }
    public static int height(){
        return WHITE.toBigDrawableList().getHeight();
    }

    public MarbleAsset getRes() {
        return res;
    }

    public Color getC() {
        return c;
    }

    public static MarbleCLI fromAsset(MarbleAsset asset){
        int rNum = asset.ordinal();
        MarbleCLI[] val = MarbleCLI.values();
        return rNum>val.length ? MarbleCLI.WHITE: val[rNum];
    }


}
