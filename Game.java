import  java.io.*;
import java.util.ArrayList;
import java.lang.Thread;


public class Game {

    private ArrayList<Player> players;
    private final Deck deck;
    private final Dealer dealer;
    private final static String filePath;

    /* Default location of the file where the names of the players are to be stored */
    static {
        filePath = System.getProperty("user.dir") + "\\previousSessionPlayers.txt";
    }


    public Game(){
        this.players = new ArrayList<>();
        this.deck = new Deck();
        this.dealer = new Dealer();
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(ArrayList<Player> players) throws IllegalArgumentException{
        if (players.size() < 2 || players.size() > 7){
            throw new IllegalArgumentException();
        }
        else {
            this.players = players;
        }
    }

    /* Displays game menu for the case that the previous game session exists */
    public void showDefaultMenu() {

        displayMessage(null, "GAME MENU", '/');
        this.sleep();
        ArrayList<String> block = new ArrayList<>();
        block.add("same list of players - 1");
        block.add("remove some players - 2");
        block.add("add some new players - 3");
        block.add("new list of players - 4");
        block.add("exit the game - 5");
        displayMessage(block, null, '/');
    }

    /* Displays game menu for the case that the previous game session doesn't exist */
    public void showInitialMenu() {
        displayMessage(null, "No previous sessions detected.", '/');
        this.sleep();
        displayMessage(null, "GAME MENU", '/');
        this.sleep();
        ArrayList<String> block = new ArrayList<>();
        block.add("new list of players - 1");
        block.add("exit the game - 2");
        displayMessage(block, null, '/');
    }


    public void sleep(){

        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            System.out.println("InterruptedException thrown in the method sleep().");
        }
    }


    public void sleep(int ms){

        try{
            Thread.sleep(ms);
        }
        catch (InterruptedException e){
            System.out.println("InterruptedException thrown in the method sleep().");
        }
    }

    /**
     * Is called for the input of new Players, to complete the existing list of Players or to refill it.
     * However, the method itself does not change the value of Game.players.
     * @param message - part of instruction message for the input, differs according to purpose of the method call
     * @param min - lowest numbers of Players that can be input
     * @param max - highest number of Players that can be input
     * @return list of newly-input initialized Players
     */
    public ArrayList<Player> inputPlayers(String message, int min, int max) throws IOException{

        if (max == 0){
            System.out.println("There's maximal number of players in the game already.");
            return new ArrayList<>();
        }

        String formattedRange = String.format(" (%d-%d). ", min, max);
        System.out.print(message + formattedRange);
        System.out.println("When You will have all of them input, press 'Enter' one more time: ");

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Player> players = new ArrayList<>();
        String entry;
        int counter = 0;

        while (counter < max){
            entry = consoleReader.readLine();
            if (entry.equals("") && counter<min){
                System.out.println("Not enough players. Input at least " + (min-counter) + " more: ");
            }
            else if (!entry.equals("")){
                boolean wasPLayerFound = false;
                for (Player player : players){
                    if (player.getName().equals(entry)){
                        System.out.println("There's a player by that name already. Try another name: ");
                        wasPLayerFound = true;
                        break;
                    }
                }
                if (!wasPLayerFound){
                    players.add(new Player(entry));
                    counter++;
                }
            }
            else {
                break;
            }
        }
        return players;
    }

    /**
     * Checks the existence of the previous game session by trying to access the file
     * where the names of players of the previous game session are to be stored.
     * @return - true, if the file contains valid number of players' names
     *         - false, if the file doesn't exist or contains invalid number of players' names
     */
    public boolean checkIfLastSessionExists() throws IOException{
        File file = new File(filePath);
        boolean newFileCreated = file.createNewFile();
        if (newFileCreated) { return false; }
        try {
            this.setPlayers(getPreviousSessionPlayers());
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * Utility method.
     * Prints the required amount of similar characters in a row, optionally with preceding linebreak.
     * @param character - specific character to be repeatedly printed
     * @param numberOfPrints - number of repeated prints
     * @param isPrecedingLinebreak - flag to see if the preceding linebreak is to be printed
     */
    public static void multiPrint(char character, int numberOfPrints, boolean isPrecedingLinebreak){

        if (isPrecedingLinebreak) {
            System.out.println();
        }

        for (int i = 0; i < numberOfPrints; i++){
            System.out.print(character);
        }
    }

    /**
     * Utility method.
     * Is used to wrap messages into frames consisting of specific characters and print them.
     * @param block - for messages that consist of multiple lines
     * @param message - for messages that consist of a single line
     * @param character - specific character that the frames consist of
     */
    public static void displayMessage(ArrayList<String> block, String message, char character){

        if (block == null){
            int maxLength = message.length();

            multiPrint(character, maxLength+10, false);
            multiPrint(character, 2, true);
            multiPrint(' ', maxLength+6, false);
            multiPrint(character, 2, false);

            multiPrint(character, 2, true);
            System.out.print("   " + message + "   ");
            multiPrint(character, 2, false);

            multiPrint(character, 2, true);
            multiPrint(' ', maxLength+6, false);
            multiPrint(character, 2, false);
            multiPrint(character, maxLength+10, true);
            System.out.println();
        }

        else {
            int maxLength = 0;
            for (String line : block){
                if (line.length() > maxLength){
                    maxLength = line.length();
                }
            }

            multiPrint(character, maxLength+10, false);
            multiPrint(character, 2,true);
            multiPrint(' ', maxLength+6, false);
            multiPrint(character, 2, false);

            for (String s : block) {
                multiPrint(character, 2, true);
                System.out.print("   " + s);
                multiPrint(' ', maxLength - s.length(), false);
                System.out.print("   ");
                multiPrint(character, 2, false);
            }

            multiPrint(character, 2,true);
            multiPrint(' ', maxLength+6, false);
            multiPrint(character, 2, false);
            multiPrint(character, maxLength+10, true);
            System.out.println();
        }
    }

    /* Prints all the players that Game.players list currently contains */
    public void displayCurrentPlayers(){
        ArrayList<String> block = new ArrayList<>();
        block.add("Currently playing:");
        block.add("");
        for (Player player : this.players){
            block.add(player.getName());
        }
        displayMessage(block, null, '/');
    }

    /**
     * Reads the names of players of the previous game session from the file where they are stored.
     * @return - list of Player objects with corresponding values of Player.name.
     */
    public static ArrayList<Player> getPreviousSessionPlayers() throws IOException{
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        ArrayList<Player> players = new ArrayList<>();
        String playerName;
        while ((playerName = fileReader.readLine()) != null && !(playerName.equals(""))) {
            players.add(new Player(playerName));
        }
        fileReader.close();
        return players;
    }

    /* Is used to input and remove the valid number of valid players that user is willing to remove. */
    public void removeSomePlayers() throws IOException{
        int initialNumberOfPlayers = this.getPlayers().size();
        if (initialNumberOfPlayers == 2) {
            System.out.println("There are just 2 players remaining already. You cannot remove any more players.");
            return;
        }
        System.out.println("Please, input the names of the players You would like to remove.\n" +
                "When You will have all of them input, press 'Enter' one more time: ");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String playerName;
        int counter = 0;
        while (!(playerName = consoleReader.readLine()).equals("")){
            boolean isPlayerFound = false;
            for (int i = 0; i < this.getPlayers().size(); i++){
                if (this.getPlayers().get(i).getName().equals(playerName)){
                    this.getPlayers().remove(i);
                    System.out.println(playerName + " was removed.");
                    counter ++;
                    isPlayerFound = true;
                    break;
                }
            }
            if (!isPlayerFound){
                System.out.println("No players found by that name.");
            }

            if (counter == initialNumberOfPlayers-2){
                System.out.println("You have reached the minimal number of " +
                        "players required for the game. You cannot remove any more players.");
                break;
            }
        }
    }

    /**
     * Invokes corresponding methods to particular user responses to the game menu.
     * General purpose of the invoked methods is to define the value of Game.players for the current game session.
     * @param mode - 'd' for default game menu, see Game.showDefaultMenu() output
     *             - 'i' for initial game menu, see Game.showInitialMenu() output
     * @return response "exit", if the user is willing to exit the game
     *                  "play", if the user is willing to initiate new game session
     */
    public String processMenuResponse(char mode) throws IOException{

        System.out.println("\nPlease, input the number according to game menu: ");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String response = consoleReader.readLine();
        boolean responseFlag = false;
        if (mode == 'd') {
            while (!responseFlag) {
                switch (response) {
                    case "1" -> responseFlag = true;
                    case "2" -> {
                        this.displayCurrentPlayers();
                        this.removeSomePlayers();
                        responseFlag = true;
                    }
                    case "3" -> {
                        this.displayCurrentPlayers();
                        ArrayList<Player> playersToAdd = this.inputPlayers("Please, enter the names of the players" +
                                " You would like to add", 0, 7 - this.getPlayers().size());
                        this.players.addAll(playersToAdd);
                        responseFlag = true;
                    }
                    case "4" -> {
                        players = inputPlayers("Please, input the list of the new players", 2, 7);
                        this.setPlayers(players);
                        responseFlag = true;
                    }
                    case "5" -> {
                        return "exit";
                    }
                    default ->
                            System.out.println("Wrong number or symbol. Please, input the number according to game menu: ");
                }
            }
        }
        else if (mode == 'i'){
            while (!responseFlag) {
                switch (response) {
                    case "1" -> {
                        players = inputPlayers("Please, enter the names of the new players", 2, 7);
                        this.setPlayers(players);
                        responseFlag = true;
                    }
                    case "2" -> {
                        return "exit";
                    }
                    default ->
                            System.out.println("Wrong number or symbol. Please, input the number according to game menu: ");
                }
            }
        }
        return "play";
    }

    /* Writes the names of the players of the current game session to the file where they are to be stored */
    public void saveCurrentSession() throws IOException{
        BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
        for (Player player : this.getPlayers()){
            fileWriter.write((player.getName() + "\n").toCharArray());
        }
        fileWriter.close();
    }

    /**
     * Method's call initiates the whole process of Dealer asking each of the Players if they are willing to take
     * one more card until their hand's value exceeds 21 and
     * reevaluating Player.cards and Player.totalSum fields according to hit for each of the Players.
     */
    public void secondaryDeal() throws IOException{

        for (int i = 0; i < this.players.size()-1; i++){
            Player player = this.players.get(i);
            if (this.players.get(i).getTotalSum()!=21){
                while(this.dealer.askIfDealAnotherCard(player)) {

                    dealer.dealSingleCard(this.deck, player);
                    player.evaluateTotalSum();

                    if (this.players.get(i).getTotalSum() > 21) {
                        String string = this.players.get(i).getName() + ", your current cards are: ";
                        string += this.players.get(i).formCardsMiddle();
                        string += ". This hand looses (Sum " + this.players.get(i).getTotalSum() + ").";
                        displayMessage(null, string, '/');
                        break;
                    }
                    else if (this.players.get(i).getTotalSum() == 21) {
                        String string = this.players.get(i).getName() + ", your current cards are: ";
                        string += this.players.get(i).formCardsMiddle();
                        string += ". Sum is 21!";
                        displayMessage(null, string, '/');
                        break;
                    }
                }
            }
            else {
                displayMessage(null, this.players.get(i).getName() + " has BlackJack!", '/');
            }
        }
    }

    /* Displays cards of the Dealer after he finishes hitting */
    public void showCardsOfDealer(){
        displayMessage(null, this.dealer.getName() + "'s cards are: "
                + this.dealer.formCardsMiddle() + ". Sum: " + this.dealer.getTotalSum(), '/');
    }

    /**
     * Stores lines prepared for the output consisting of names of the Players from Game.players
     * whose sum of the hand is in particular range, listed cards of these Players and
     * sums of their hands. Returns this list prepared for the output.
     * @param min - lower limit of the range
     * @param max - higher limit of the range
     * @return - list of names of the Players whose sum of the hand is in particular range
     */
    public ArrayList<String> getPlayersWithSum(int min, int max){

        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < this.players.size()-1; i++){

            Player player = this.players.get(i);
            if (player.getTotalSum() >= min && player.getTotalSum() <= max){
                result.add(player.getName() + " : (" + player.formCardsMiddle() + ") (" + player.getTotalSum() + ")");
            }
        }
        return result;
    }

    /**
     * Processes and displays the results of the game by accessing Player.totalSum field of all the Players.
     * If Dealer's hand value exceeds 21, displays the message that all the players with (hand <= 21) as winners.
     * If Dealer's hand does not exceed 21, displays all the Players with hands higher or equal to the Dealer's.
     * In case that there are no such hands, displays the message that the Dealer wins.
     */
    public void defineWinnersAndPrint(){
        
        if (this.dealer.getTotalSum() > 21){
            displayMessage(null, this.dealer.getName() + " looses (Sum " + dealer.getTotalSum()
                    + ").", '/');
            displayMessage(null, "WINNERS:", '/');
            this.sleep();
            displayMessage(getPlayersWithSum(4, 21), null, '\\');
            return;
        }

        ArrayList<String> winners = getPlayersWithSum(this.dealer.getTotalSum()+1, 21);
        ArrayList<String> ties = getPlayersWithSum(this.dealer.getTotalSum(), this.dealer.getTotalSum());

        if (winners.size() > 0){
            displayMessage(null, "WINNERS:", '/');
            this.sleep();
            displayMessage(winners, null, '\\');
        }

        if (ties.size() > 0){
            displayMessage(null, "PUSHES:", '/');
            this.sleep();
            displayMessage(ties, null, '\\');
        }

        else if (winners.size() == 0){
            String message = this.dealer.getName() + " has the highest hand. " + this.dealer.getName() + " wins.";
            displayMessage(null, message, '/');
        }

    }


    public static void main(String[] args) throws IOException{

        displayMessage(null, "Welcome to the game!", '/');

        while (true){
            Game game = new Game();
            game.sleep();
            String response;
            if (game.checkIfLastSessionExists()){
                game.showDefaultMenu();
                game.sleep();
                response = game.processMenuResponse('d');
            }
            else {
                game.showInitialMenu();
                game.sleep();
                response = game.processMenuResponse('i');
            }

            if (response.equals("exit")){ break; }
            game.saveCurrentSession();
            game.dealer.shuffleDeck(game.deck);
            game.players.add(game.dealer);
            game.sleep(500);
            game.dealer.initialDeal(game.deck, game.players);
            ArrayList<String> cardsInitial = new ArrayList<>();
            for (Player p : game.players){
                cardsInitial.add(p.formCardsInitial());
            }
            displayMessage(cardsInitial, null,  '\\');
            game.sleep(500);
            game.secondaryDeal();
            game.dealer.dealToItself(game.deck);
            game.sleep(2000);
            game.showCardsOfDealer();
            game.sleep(2000);
            game.defineWinnersAndPrint();
            game.sleep(3000);
        }
    }
}