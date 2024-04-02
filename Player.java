import java.util.ArrayList;

public class Player {

    private final String name;
    private int numberOfAces;
    private int sumWithoutAces;
    private int totalSum;
    private ArrayList<Card> cards;



    public Player(String name) throws IllegalArgumentException{
        if (name.length() == 0){
            throw new IllegalArgumentException();
        }
        else {
            this.name = name;
        }
        this.totalSum = 0;
        this.numberOfAces = 0;
        this.sumWithoutAces = 0;
        this.cards = new ArrayList<>();
    }


    public String getName(){
        return this.name;
    }

    public void setTotalSum(int totalSum){
        this.totalSum = totalSum;
    }

    public int getTotalSum(){
        return this.totalSum;
    }

    public int getNumberOfAces() {
        return numberOfAces;
    }

    public void setNumberOfAces(int numberOfAces) {
        this.numberOfAces = numberOfAces;
    }

    public void setCards(ArrayList<Card> cards){
        this.cards = cards;

    }

    public ArrayList<Card> getCards(){
        return this.cards;
    }

    public int getSumWithoutAces() {
        return sumWithoutAces;
    }

    public void setSumWithoutAces(int sumWithoutAces) {
        this.sumWithoutAces = sumWithoutAces;
    }

    /**
     * Defines and assigns the value to Player.totalSum according to Player.numberOfAces and Player.sumWithoutAces.
     * Since the fact that the value of Aces can be 1 or 11, the method's purpose is to calculate the highest possible value
     * of the hand with Aces without exceeding 21.
     */
    public void evaluateTotalSum(){
        int result = this.getSumWithoutAces() + this.getNumberOfAces();
        for (int i = 0; i < this.numberOfAces; i++){
            if (result + 10 <= 21){ result += 10; }
        }
        this.setTotalSum(result);
    }

    /* Method is used for Player to automatically display their cards after initial cards deal */
    public String formCardsInitial(){
        String result = this.getName() + ": " + this.getCards().get(0).getName() + " ";
        result += this.getCards().get(1).getName();
        return result;
    }

    /* Method is used for Player to automatically display their cards during secondary deal and displaying the game results */
    public String formCardsMiddle(){
        String result = "";
        for (int i = 0; i < this.getCards().size(); i++){
            result += this.getCards().get(i).getName();
            if (i!=this.getCards().size()-1){
                result += " ";
            }
        }
        return result;
    }
}
