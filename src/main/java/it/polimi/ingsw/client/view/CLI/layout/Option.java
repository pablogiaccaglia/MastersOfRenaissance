package it.polimi.ingsw.client.view.CLI.layout;

import it.polimi.ingsw.client.view.CLI.layout.drawables.Canvas;
import it.polimi.ingsw.client.view.CLI.layout.drawables.Drawable;
import it.polimi.ingsw.client.view.CLI.layout.drawables.DrawableLine;
import it.polimi.ingsw.client.view.CLI.textUtil.Background;
import it.polimi.ingsw.client.view.CLI.textUtil.Color;
import it.polimi.ingsw.client.view.CLI.textUtil.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A selectable option in a CLIView that will execute the given code when perform is called
 */
public class Option extends GridElem {

    public enum VisMode {
        NUMBER_TO_BOTTOM {
            @Override
            public Drawable getDrawable(int idx, Drawable drawable, boolean enabled) {
                Drawable dw = Drawable.copyShifted(0,0,drawable);
                DrawableLine dwl = Option.numberLine(0,dw.getHeight(), idx,enabled);
                dw.addToCenter(dw.getWidth(),dwl.getString(),dwl.getColor(),dwl.getBackground());
                return dw;
            }
        },
        NUMBER_TO_BOTTOM_SPACED {
            @Override
            public Drawable getDrawable(int idx, Drawable drawable, boolean enabled) {
                Drawable dw = Drawable.copyShifted(0,0,drawable);
                DrawableLine dwl = Option.numberLine(0,dw.getHeight(), idx,enabled);
                dw.addToCenter(dw.getWidth(),dwl.getString(),dwl.getColor(),dwl.getBackground());
                dw.addEmptyLine();
                return dw;
            }
        },
        NUMBER_TO_LEFT {
            @Override
            public Drawable getDrawable(int idx, Drawable drawable, boolean enabled) {
                DrawableLine index = Option.numberLine(0,0,idx,enabled);
                Drawable dw = Drawable.copyShifted(index.getWidth(),0,drawable);
                dw.add(index);
                return dw;
            }
        }
        , NO_NUMBER_SPACE_BOTTOM {
            @Override
            public Drawable getDrawable(int idx, Drawable drawable, boolean enabled) {
                Drawable dw = Drawable.copyShifted(0,0,drawable);
                dw.addEmptyLine();
                return  dw;
            }
        },
        NO_NUMBER {
            @Override
            public Drawable getDrawable(int idx, Drawable drawable, boolean enabled) {
                return drawable;
            }
        };


        public abstract Drawable getDrawable(int idx, Drawable drawable, boolean enabled);

    }


    private VisMode mode;
    private boolean selected;
    private boolean enabled;
    private Runnable performer;
    private final Drawable drawable;
    private final Drawable selectedDrawable;

    public static Option from(String name){
        return Option.from(fromNameAndSub(name,""),()->{});
    }

    public static Option noNumber(String name){
        Option o = Option.from(fromNameAndSub(name,""),()->{});
        o.setMode(VisMode.NO_NUMBER_SPACE_BOTTOM);
        return o;
    }


    public static Option from(String name, Runnable performer){
        Option option = Option.from(name,"",performer);
        option.setPerformer(performer);
        return option;
    }

    public static Option from(String name,String subtitle,Runnable performer){
        Option option = Option.from(fromNameAndSub(name,subtitle),performer);
        option.setPerformer(performer);
        return option;
    }

    public static Option noNumber(Drawable drawable){
        Option o = Option.from(drawable,()->{});
        o.setMode(VisMode.NO_NUMBER_SPACE_BOTTOM);
        return o;
    }

    public static Option from(Drawable drawable, Runnable performer){
        Option option = new Option(drawable, Drawable.selectedDrawableList(drawable));
        option.setPerformer(performer);
        return option;
    }

    public static Option from(Drawable normalDwl, Drawable selectedDwl, Runnable performer){
        Option option = new Option(normalDwl,selectedDwl);
        option.setPerformer(performer);
        return option;
    }

    public static Option tabSpace(int numOfConsecutiveTabs){
        String tabs = "        ";
        String repeated = tabs.repeat(numOfConsecutiveTabs);
        return Option.noNumber(repeated);
    }

    public static Stream<Option> addSpacesToOptionList(int spaceLen, List<Option> options){

        if(options.isEmpty())
            return Stream.empty();

        List<Option> resizedList = new ArrayList<>((options.size()*3));
        resizedList.addAll(options);

        int originalSize = options.size();
        int j=0;
        int k=0;

        for(int i = 0 ; i<originalSize; i ++){
            j = k + 1;
            k = k + 2;
            resizedList.add(j, tabSpace(spaceLen));
        }

        return resizedList.stream();

    }

    private Option(Drawable normalDwl, Drawable selectedDwl) {
        mode = VisMode.NUMBER_TO_LEFT;
        this.drawable = normalDwl;
        selectedDrawable = selectedDwl;
        this.selected = false;
        this.setEnabled(true);
    }

    private static Drawable fromNameAndSub(String name,String subtitle){
        Drawable drawable = new Drawable();
        drawable.add(0, name);
        if (!subtitle.equals("")) {
            drawable.add(3,subtitle);
        }
        return drawable;
    }

    public void setMode(VisMode mode) {
        this.mode = mode;
    }

    public void setPerformer(Runnable performer) {
        this.performer = performer;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private Drawable getDrawableWithoutNumber(){
        if (selected)
            return selectedDrawable;
        else
            return drawable;
    }


    public void perform(){
        if (performer!=null)
            performer.run();
    }

    private static DrawableLine numberLine(int x, int y, int idx, boolean isEnabled){
        return new DrawableLine(x,y, StringUtil.twoDigitsRight(idx)+": ",isEnabled? Color.OPTION:Color.DISABLED, Background.DEFAULT);
    }


    @Override
    public int getNextElemIndex() {
        if  (mode.equals(VisMode.NO_NUMBER)||mode.equals(VisMode.NO_NUMBER_SPACE_BOTTOM))
            return getFirstIdx();
        else return getFirstIdx()+1;
    }

    @Override
    public Optional<Option> getOptionWithIndex(int i) {
        return Optional.ofNullable((getFirstIdx()==i&&!mode.equals(VisMode.NO_NUMBER_SPACE_BOTTOM)&&enabled)?this:null);
    }

    @Override
    public List<Option> getAllEnabledOption() {
        return getOptionWithIndex(getFirstIdx()).map(Arrays::asList).orElse(new ArrayList<>());
    }

    @Override
    public void addToCanvas(Canvas canvas, int x, int y) {
        Drawable toDisplay = mode.getDrawable(getFirstIdx(), getDrawableWithoutNumber(),enabled);
        Drawable copy = Drawable.copyShifted(x,y,toDisplay);
        canvas.addDrawable(copy);
    }

    @Override
    public int getMinWidth() {
        return mode.getDrawable(getFirstIdx(), drawable,enabled).getWidth();
    }

    @Override
    public int getMinHeight() {
        return mode.getDrawable(getFirstIdx(), drawable,enabled).getHeight();
    }


}
