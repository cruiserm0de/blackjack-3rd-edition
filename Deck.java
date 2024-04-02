import java.util.ArrayList;
import java.util.Set;

public class Deck {

    ArrayList<Card> cards;

    public Deck(){
        Set<String> cardNames = Card.valuesTable.keySet();
        this.cards = new ArrayList<>();
        for (String cardName : cardNames){
            for (int i = 0; i < 4; i++){
                this.cards.add(new Card(cardName));
            }
        }
    }


    public ArrayList<Card> getCards(){
        return this.cards;
    }


    public void printDeck(){
        for (Card card : cards){
            System.out.print(card.getName());
        }
    }
}

