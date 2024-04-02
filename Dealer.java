import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Dealer extends Player{

    public Dealer() {
        super("Dealer");
    }

    /**
     * Invokes Dealer.dealSingleCard() method two times and then reevaluates Player.totalSum field
     * for all the players from the list passed as a parameter.
     * @param deck - deck of cards passed further as a parameter to Dealer.dealSingleCard() method
     * @param players - list of all the players, to whom the method is to be invoked
     */
    public void initialDeal(Deck deck, ArrayList<Player> players){
        for (Player player : players){
            for (int i = 0; i < 2; i++){
                dealSingleCard(deck, player);
            }
            player.evaluateTotalSum();
        }
    }

    /**
     * Receives the copy of Player.cards list, adds the first card of Deck.cards to the copy,
     * removes that card from Deck.cards, then if the dealt card is an Ace, increments the
     * value of Player.numberOfAces, in other case increases the value of Player.sumWithoutAces
     * by the value of the dealt card. Assigns to Player.cards the value of its local copy with added dealt card.
     */
    public void dealSingleCard(Deck deck, Player player){
        ArrayList<Card> localCards = player.getCards();
        Card dealtCard = deck.getCards().get(0);
        if (dealtCard.getName().equals("Ace")){
            player.setNumberOfAces(player.getNumberOfAces() + 1);
        }
        else{
            player.setSumWithoutAces(player.getSumWithoutAces() + dealtCard.getValue());
        }
        localCards.add(dealtCard);
        deck.cards.remove(0);
        player.setCards(localCards);
    }

    /* Dealer invokes on itself the method dealSingleCard(), until Dealer's hand exceeds 17 */
    public void dealToItself(Deck deck){

        while (this.getTotalSum() < 17){
            this.dealSingleCard(deck, this);
            this.evaluateTotalSum();
        }
    }

    /* Shuffles the contents of Deck.cards, randomises the positions of the cards in Deck */
    public void shuffleDeck(Deck deck){
        Collections.shuffle(deck.cards);
    }

    /**
     * Dealer asks Player if they are willing to hit, expects input of '1' or '2' as an answer, keeps asking till the answer is valid.
     * @return - true, if the Player are willing to take one more card
     *         - false, if the Player are willing to stop taking cards
     */
    public boolean askIfDealAnotherCard(Player player) throws IOException {

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Dealer - to " + player.getName() + ": Your current cards are: " );
        System.out.print(player.formCardsMiddle());
        System.out.println(". Deal one more card - (1) or stop now - (2) ?");

        boolean result;
        while (true){

            String answer = consoleReader.readLine();
            if (answer.equals("1")){
                result = true;
                break;
            }
            else if (answer.equals("2")){
                result = false;
                break;
            }
            else{
                System.out.println("Wrong number or symbol.");
            }
        }
        return result;
    }

    /* Method is override for Dealer to hide its first card after initial cards deal according to game rules */
    @Override
    public String formCardsInitial(){

        return "Dealer: ? " + this.getCards().get(1).getName();
    }
    
}

