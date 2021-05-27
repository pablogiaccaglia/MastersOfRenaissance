package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.view.CLI.CLIelem.Option;
import it.polimi.ingsw.client.view.CLI.CLIelem.Title;
import it.polimi.ingsw.client.view.CLI.CLIelem.body.HorizontalListBody;
import it.polimi.ingsw.client.view.CLI.textUtil.Background;
import it.polimi.ingsw.client.view.CLI.textUtil.Color;
import it.polimi.ingsw.client.view.CLI.textUtil.Drawable;
import it.polimi.ingsw.client.view.CLI.textUtil.DrawableList;
import org.apache.commons.compress.archivers.sevenz.CLI;

import java.beans.PropertyChangeEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class TestViewBuilder extends it.polimi.ingsw.client.view.abstractview.TestViewBuilder implements CLIBuilder {

    private HorizontalListBody hor;

    @Override
    public void run() {

        getCLIView().setTitle(new Title("Testing horizontal list, to start the cli remove comments in client"));
        AtomicInteger resSelected = new AtomicInteger();

        HorizontalListBody horList = new HorizontalListBody(getCLIView().getMaxBodyHeight());
        horList.addOption(Option.from("12345678901234567890\nname1","subtitle1",()->{
            resSelected.getAndIncrement();
            getCLIView().setTitle(new Title("Selected res "+resSelected.get()));
            getCLIView().setBody(hor);
            getCLIView().refreshCLI();
        }));

        DrawableList d = new DrawableList();
        d.add(new Drawable(0,0,"I'm a black title", Color.ANSI_WHITE, Background.ANSI_BLACK_BACKGROUND));
        d.add(new Drawable(3,1,"I'm a red body of text\nshifted to the left", Color.ANSI_RED, Background.DEFAULT));
        horList.addOption(Option.from(d,()->{
            resSelected.getAndIncrement();
            getCLIView().setTitle(new Title("Selected res "+resSelected.get()));
            getCLIView().setBody(hor);
            getCLIView().refreshCLI();
        }));

        DrawableList d2 = new DrawableList();
        d2.add(new Drawable(0,0,"I'm a square", Color.ANSI_BLACK, Background.ANSI_YELLOW_BACKGROUND));
        d2.addEmptyLine();
        d2.add(0,"hi");
        d2.shift(4,0);

        DrawableList d3 = new DrawableList();
        d3.add(new Drawable(0,0,"+--------+", Color.ANSI_BLACK, Background.ANSI_CYAN_BACKGROUND));
        d3.add(0,"|        |",Color.ANSI_BLACK, Background.ANSI_CYAN_BACKGROUND);
        d3.add(0,"|   hi   |",Color.ANSI_BLACK, Background.ANSI_CYAN_BACKGROUND);
        d3.add(0,"|________|",Color.ANSI_BLACK, Background.ANSI_CYAN_BACKGROUND);
        d3 = DrawableList.shifted(5,3,d3);

        horList.addOption(Option.from(new DrawableList(d2,d3),()->{
            resSelected.getAndIncrement();
            getCLIView().setTitle(new Title("Selected res "+resSelected.get()));
            getCLIView().setBody(hor);
            getCLIView().refreshCLI();
        }));

        hor = horList;
        getCLIView().setBody(horList);


        String compactMessage = "Press send to compact, times left: ";
        String widenMessage = "Press send to widen, times left: ";

        getCLIView().runOnInput(widenMessage,getWdCode(compactMessage,widenMessage,8));
        getCLIView().refreshCLI();

    }
    private Runnable getCompactCode(String widenMessage,String compactMessage,int timesLeft){
        String wd = widenMessage+timesLeft;
        return ()->{
            if (timesLeft==0){
                getCLIView().setBody(hor);//To show options
                getCLIView().refreshCLI();
            } else {
                hor.setMode(HorizontalListBody.Mode.COMPACT);
                getCLIView().runOnInput(wd, getWdCode(compactMessage, widenMessage, timesLeft - 1));
                getCLIView().refreshCLI();
            }
        };
    }

    private Runnable getWdCode(String compact,String widen,int timesLeft){
        return ()->{
            if (timesLeft==0){
                getCLIView().setBody(hor);//To show options
                getCLIView().refreshCLI();
            } else {
                hor.setMode(HorizontalListBody.Mode.WIDE);
                getCLIView().runOnInput(compact+timesLeft, getCompactCode(widen, compact, timesLeft - 1));
            }
            getCLIView().refreshCLI();
        };
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName());
    }
}
