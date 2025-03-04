package project;

import java.util.ArrayList;
import java.util.Collections;

public class GroupOfCards {

    private ArrayList<Card> cards;

    public GroupOfCards() {
        cards = new ArrayList<>();
        setCards();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards() {
        for (Card.Suit s : Card.Suit.values()) {
            for (Card.Value v : Card.Value.values()) {
                cards.add(new Card(s, v));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

}
