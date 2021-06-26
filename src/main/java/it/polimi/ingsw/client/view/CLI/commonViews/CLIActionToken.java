package it.polimi.ingsw.client.view.CLI.commonViews;

import it.polimi.ingsw.client.view.CLI.CLIelem.Title;
import it.polimi.ingsw.client.view.CLI.CLIelem.body.CanvasBody;
import it.polimi.ingsw.client.view.CLI.layout.Option;
import it.polimi.ingsw.client.view.CLI.layout.drawables.Drawable;
import it.polimi.ingsw.client.view.CLI.layout.drawables.DrawableLine;
import it.polimi.ingsw.client.view.CLI.layout.drawables.Timer;
import it.polimi.ingsw.client.view.CLI.layout.recursivelist.Column;
import it.polimi.ingsw.client.view.CLI.textUtil.Background;
import it.polimi.ingsw.client.view.CLI.textUtil.Color;
import it.polimi.ingsw.client.view.abstractview.ViewBuilder;
import it.polimi.ingsw.network.assets.tokens.ActionTokenAsset;
import it.polimi.ingsw.network.simplemodel.SimpleSoloActionToken;

import java.beans.PropertyChangeEvent;

public class CLIActionToken extends ViewBuilder{


    @Override
    public void run() {

        String coloredTitle = Color.colorString("Here is this turn Action Token! . Seconds left for viewing : 6", Color.GREEN);
        Title title = new Title("Here is this turn Action Token! . Seconds left for viewing : 6", Color.GREEN);

        getCLIView().setTitle(title.toString());

        Column grid = new Column();
        Option tokenBox = Option.noNumber(buildActionTokenBox());
        grid.addElem(tokenBox);
        CanvasBody body = CanvasBody.centered(grid);

        getCLIView().setBody(body);

        getCLIView().show();
        getCLIView().runOnInput("", () -> {});
        Timer.showSecondsOnCLI(getCLIView(), "Here is this turn Action Token! . Seconds left for viewing : ");
        getClient().changeViewBuilder(getClient().getSavedViewBuilder());


    }

    public static void showActionTokenBeforeTransition(){

        SimpleSoloActionToken soloActionToken = getSimpleModel().getPlayerCache(0).getElem(SimpleSoloActionToken.class).get();

        if(!soloActionToken.hasTokenBeenShown()){
            soloActionToken.tokenWillBeShown();
            if(getClient().isCLI()) {
                getClient().changeViewBuilder(new CLIActionToken());
            }
        }

    }


    private static Drawable buildActionTokenBox(){

        SimpleSoloActionToken actionToken = getSimpleModel().getPlayerCache(0).getElem(SimpleSoloActionToken.class).orElseThrow();
        ActionTokenAsset tokenAsset = actionToken.getSoloActionToken();

        String[] words = tokenAsset.getEffectDescription().split(" ");
        int lineLen = 27;
        int lettersCounter = 0;
        int yPos = 3;
        int xPos = 2;

        Drawable drawable= new Drawable();


        drawable.add(0,"╔════════════════════════════════╗");
        drawable.add(0,"║                                ║");
        drawable.add(new DrawableLine(8, 1, "Action Token", Color.BIGHT_GREEN, Background.DEFAULT));
        drawable.add(0,"║ ────────────────────────────── ║");

        drawable.add(0,"║                                ║");

        for(String word : words){
            lettersCounter = lettersCounter + word.length() + 1;

            if(lettersCounter>30){
                drawable.add(0,"║                                ║");
                yPos++;
                xPos = 2;
                lettersCounter = word.length();
            }

            drawable.add(new DrawableLine(xPos, yPos, word + " ", Color.YELLOW, Background.DEFAULT));
            xPos = xPos + word.length() + 1;

        }
        drawable.add(0,"║ ────────────────────────────── ║");
        drawable.add(0,"║                                ║");
        drawable.add(0,"╚════════════════════════════════╝");

        return drawable;

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }


}