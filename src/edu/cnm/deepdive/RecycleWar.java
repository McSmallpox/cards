package edu.cnm.deepdive;

import edu.cnm.deepdive.Deck.DeckEmptyException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RecycleWar extends SimpleWar {

private List<Card> pile1;
private List<Card> pile2;

  public RecycleWar(Random rng) {
    super(rng);
    pile1 = new ArrayList<>();
    pile2 = new ArrayList<>();
    try {
      while (true) {
        pile1.add(getDeck().deal());
        pile2.add(getDeck().deal());
      }
    } catch (DeckEmptyException e) {
      //Do nothing.
    }
  }

  @Override
  public void play() {
    List<Card> warPile = new LinkedList<>();
    boolean tied = false;
    do {
      Card card1 = null;
      Card card2 = null;
      try {
        card1 = pile1.remove(0);
      } catch (IndexOutOfBoundsException e) {
        if (card1 == null);
      }
      try {
        card2 = pile2.remove(0);
      } catch (IndexOutOfBoundsException e) {
        if (card2 == null);
      }
      warPile.add(card1);
      warPile.add(card2);
      int comparison = getReferree().compare(card1, card2);
      if (comparison == 0) {
        tied = true;
        try {
          for (int i = 0; i < 3; i++) {
            card1 = pile1.remove(0);
            card2 = pile2.remove(0);
            warPile.add(card1);
            warPile.add(card2);
            Collections.addAll(warPile, card1, card2);
          }
        } catch (Exception e) {
          //do nothing
        }
      }else if (comparison > 0) {
        pile1.addAll((warPile));
      } else if (comparison < 0) {
        pile2.addAll(warPile);
      }
    } while (tied);
  }
}
