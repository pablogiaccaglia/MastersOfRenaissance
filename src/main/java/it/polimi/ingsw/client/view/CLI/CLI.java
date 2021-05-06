package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.CLI.CLIelem.*;
import it.polimi.ingsw.client.view.CLI.CLIelem.body.Body;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * Builds the CLI.<br>
 * Usage: Add {@link it.polimi.ingsw.client.view.CLI.CLIelem.CLIelem elements}
 * like optionList, body, title, spinner, and then call {@link #displayWithDivider()} or {@link #displayWithScroll()}.
 * Call {@link #refreshCLI()} to update.
 */
public class CLI {
    private final Client client;
    private Title title;
    private Optional<Spinner> spinner;
    private Optional<CLIelem> body;
    private OptionList[] optionListAtPos;
    private Optional<Option> lastChoice=Optional.empty();
    public final AtomicBoolean stopASAP;
    private Thread inputThread;
    private String inputMessage;
    private String lastInput;
    private int lastInt;
    private boolean isTakingInput;
    private Runnable afterInput;


    public CLI(Client client) {
        optionListAtPos = Stream.generate(OptionList::new).limit(CLIPos.values().length).toArray(OptionList[]::new);
        this.client = client;
        stopASAP = new AtomicBoolean(false);
        spinner = Optional.empty();
        body = Optional.empty();
    }

    public void setTitle(Title title){
        if (this.title!=null)
            title.removeFromPublishers(client);
        this.title = title;
        this.title.setCLIAndUpdateSubscriptions(this,client);
    }

    public void setOptionList(CLIPos pos,OptionList optionList){
        optionList.setCLIAndUpdateSubscriptions(this, client);
        optionListAtPos[pos.ordinal()]=optionList;
        optionList.selectOption();
    }

    public void setLastChoice(Optional<Option> integerOptionPair ) {
        this.lastChoice = integerOptionPair;
    }

    public void setBody(Body body){
        this.body.ifPresent(b->b.removeFromPublishers(client));
        this.body = Optional.ofNullable(body);
        this.body.ifPresent(b->b.setCLIAndUpdateSubscriptions(this,client));
    }

    public void setSpinner(Spinner spinner){
        this.spinner.ifPresent(s->s.removeFromPublishers(client));
        this.spinner = Optional.of(spinner);
        spinner.setCLIAndUpdateSubscriptions(this, client);
    }

    public int getLastInt() {
        return lastInt;
    }

    public String getLastInput() {
        return lastInput;
    }

    public void resetCLI(){
        try {
            clearOptions(this);
        } catch (ChangingViewBuilderBeforeTakingInput e){
            e.printStackTrace();
        }
    }

    private static void clearOptions(CLI cli) throws ChangingViewBuilderBeforeTakingInput {
        if (cli.isTakingInput)
        {
            throw new ChangingViewBuilderBeforeTakingInput();
        }
        if (cli.title!=null)
            cli.title.removeFromPublishers(cli.client);
        cli.title = null;

        cli.body.ifPresent(b->b.removeFromPublishers(cli.client));
        cli.body = Optional.empty();

        Arrays.stream(cli.optionListAtPos).forEach(o->o.removeFromPublishers(cli.client));
        cli.optionListAtPos = Stream.generate(OptionList::new).limit(CLIPos.values().length).toArray(OptionList[]::new);
        cli.spinner.ifPresent(s->s.removeFromPublishers(cli.client));
        cli.spinner = Optional.empty();
    }

    public void refreshCLI(){
        displayWithDivider();
    }

    public void updateListeners(){
        if (title!=null)
            title.setCLIAndUpdateSubscriptions(this,client);
        spinner.ifPresent(s->s.setCLIAndUpdateSubscriptions(this,client));
        Arrays.stream(optionListAtPos).forEach(o->o.setCLIAndUpdateSubscriptions(this,client));
    }

    public void performLastChoice(){
        lastChoice.ifPresent(Option::perform);
    }

    private synchronized void commonRunOnInput(String message, Runnable r, Runnable afterInput){
        if (inputThread!= null && inputThread.isAlive() && isTakingInput) {
            inputThread.interrupt();
            isTakingInput = false;
        }
        this.afterInput = afterInput;
        inputMessage = message;
        inputThread = new Thread(r);
    }

    private synchronized void callRunnableAfterGettingInput(){
        afterInput.run();
        inputThread = null;
    }

    public synchronized void runOnInput(String message, Runnable r1){
        Runnable r = ()-> {
            print(Color.colorString(message,Color.ANSI_GREEN));
            putEndDiv();
            lastInput = getInString();
            callRunnableAfterGettingInput();
        };
        commonRunOnInput(message,r,r1);
    }

    public synchronized void runOnIntInput(String message,String errorMessage,int min,int max, Runnable r1){
        Runnable r = ()-> {
            int choice;
            do  {
                print(Color.colorString(message,Color.ANSI_GREEN));
                putEndDiv();
                String in = getInString();
                try
                {
                    choice = Integer.parseInt(in);
                    if (choice<min||choice>max)
                    {
                        printError(errorMessage);
                        if (choice<min)
                            printError("Insert a GREATER number!");
                        else printError("Insert a SMALLER number!");
                    }else {
                        break;
                    }
                }
                catch (NumberFormatException e){
                    printError(errorMessage);
                    printError("Insert a NUMBER!");
                }
            }while(true);
            lastInt = choice;
            lastInput = Integer.toString(choice);
            callRunnableAfterGettingInput();
        };
        commonRunOnInput(message,r,r1);
    }

    private String getInString(){
        isTakingInput = true;
        Scanner scanner = new Scanner(System.in);
        isTakingInput = false;
        return scanner.nextLine();
    }

    private OptionList getOptionsAt(CLIPos cliPos){
        return optionListAtPos[cliPos.ordinal()];
    }

    private void print(String s){
        System.out.println(s);
    }

    private void printError(String error){
        System.out.println(Color.colorString(error,Color.ANSI_RED));
    }

    public void displayWithScroll(){
        scroll();
        display();
    }

    public void displayWithDivider(){
        putDivider();
        display();
    }

    /**
     * Used to refresh the screen
     */
    private synchronized void display(){

        if (title!=null){
            print(title.toString()+"\n");}

        String spaces = "                                                    ";
        String finalSpaces = spaces;
        getOptionsAt(CLIPos.TOP).toStringStream()
                .map(s -> finalSpaces +s.replace("\n","\n"+finalSpaces))
                .forEach(this::print);

        spaces ="                                                    ";
        String finalSpaces1 = spaces;
        getOptionsAt(CLIPos.CENTER).toStringStream()
                .map(s -> finalSpaces1 +s.replace("\n","\n"+finalSpaces1))
                .forEach(this::print);

        body.ifPresent(b->print(b.toString()));

        getOptionsAt(CLIPos.BOTTOM_LEFT).toStringStream()
                .forEach(this::print);

        spaces = "                                                    ";
        String finalSpaces2 = spaces;
        getOptionsAt(CLIPos.BOTTOM_RIGHT).toStringStream()
                .map(s -> finalSpaces2 +s.replace("\n","\n"+finalSpaces2))
                .forEach(this::print);

        spinner.ifPresent(spinner1 -> print(spinner1.toString()));

        //Todo: Sometimes the choices are not updated
        //Can be solved via a check after taking the input
        printError("Todo: Sometimes the choices are not updated, sometimes you hase to select new match 2 times");
        if (isTakingInput){//Should never get here
            print(Color.colorString(inputMessage,Color.ANSI_GREEN));
            putEndDiv();
        }
        else if (inputThread!=null && !inputThread.isAlive()) {
            inputThread.start();
        } else putEndDiv();
    }

    static void cleanConsole() {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                Runtime.getRuntime().exec("clear");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void putDivider(){
        print("||-----------------------------------------------------------");
    }

    public void putEndDiv(){
        print("-----------------------------------------------------------||");
    }

    public void scroll(){
        for (int i = 0; i < 40; i++) {
            System.out.println();}
    }

}
