package edu.cnm.deepdive;

import java.security.SecureRandom;
import java.util.Comparator;
import java.util.Random;

public class SimpleWar {

  private Deck deck;
  private Random rng;
  private int tally1;
  private int tally2;
  private Referree referree;

  public SimpleWar(Random rng) {
    this.rng = rng;
    deck = new Deck();
    deck.shuffle(rng);
  }

  public void play() {
    boolean tied;
    int pairsDealt =0;
    do {
      Card card1 = deck.deal();
      Card card2 = deck.deal();
      pairsDealt++;
      int comparison = referree.compare(card1, card2);

      if (comparison == 0) {
        tied = true;
        for (int i = 0; i <3; i++) {
          deck.deal();
          deck.deal();
          pairsDealt++;
        }
      } else if (comparison > 0) {
        tied = false;
        tally1 += 2 * pairsDealt;
      } else {
        tied = false;
        tally2 += 2 * pairsDealt;
      }

    } while (tied);
  }

  protected Deck getDeck() {
    return deck;
  }

  protected Random getRng() {
    return rng;
  }

  protected Referree getReferree() {
    return referree;
  }

  protected int getTally1() {
    return tally1;
  }

  protected void setTally1(int tally1) {
    this.tally1 = tally1;
  }

  protected int getTally2() {
    return tally2;
  }

  protected void setTally2(int tally2) {
    this.tally2 = tally2;
  }

  public static void main(String[] args) {
    SimpleWar simpleWar = new SimpleWar(new SecureRandom());
    try {
      while (true) {
        simpleWar.play();
      }
    } catch (Deck.DeckEmptyException e) {
      // Do nothing!
    } finally {
      System.out.printf("Player 1: %d. Player 2: %d.%n", simpleWar.tally1, simpleWar.tally2);
      if (simpleWar.tally1 > simpleWar.tally2) {
        System.out.println("Player 1 wins!");
      } else if (simpleWar.tally1 < simpleWar.tally2) {
        System.out.println("Player 2 wins!");
      } else {
        System.out.println("Tie!");
      }
    }
  }

  protected static class Referree implements Comparator<Card> {

    @Override
    public int compare(Card card1, Card card2) {
      if (card1.getRank() != Rank.ACE && card2.getRank() != Rank.ACE) {
        return card1.getRank().compareTo(card2.getRank());
      } else if (card1.getRank() != Rank.ACE) {
        return -1;
      } else if (card2.getRank() != Rank.ACE) {
        return 1;
      }
      return 0;
    }
  }


}
