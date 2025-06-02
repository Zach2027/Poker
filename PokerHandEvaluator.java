//chatgpt
import java.util.*;
import java.util.stream.Collectors;

public class PokerHandEvaluator {

    // Enum for hand rankings
    public enum HandRank {
        HIGH_CARD(1),
        ONE_PAIR(2),
        TWO_PAIR(3),
        THREE_OF_A_KIND(4),
        STRAIGHT(5),
        FLUSH(6),
        FULL_HOUSE(7),
        FOUR_OF_A_KIND(8),
        STRAIGHT_FLUSH(9),
        ROYAL_FLUSH(10);

        private final int value;

        HandRank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // Card class for rank and suit
    static class Card implements Comparable<Card> {
        int rank;   // 2 to 14 (Ace high)
        char suit;  // 'H', 'D', 'C', 'S'

        public Card(String cardStr) {
            String[] parts = cardStr.split(" of ");
            this.rank = rankFromString(parts[0]);
            this.suit = parts[1].charAt(0); // Hearts => H, Diamonds => D, Clubs => C, Spades => S
        }

        private int rankFromString(String r) {
            switch (r) {
                case "2": return 2;
                case "3": return 3;
                case "4": return 4;
                case "5": return 5;
                case "6": return 6;
                case "7": return 7;
                case "8": return 8;
                case "9": return 9;
                case "10": return 10;
                case "Jack": return 11;
                case "Queen": return 12;
                case "King": return 13;
                case "Ace": return 14;
                default: throw new IllegalArgumentException("Invalid rank: " + r);
            }
        }

        @Override
        public int compareTo(Card o) {
            return o.rank - this.rank; // Descending order
        }

        @Override
        public String toString() {
            String rankStr;
            switch (rank) {
                case 14: rankStr = "Ace"; break;
                case 13: rankStr = "King"; break;
                case 12: rankStr = "Queen"; break;
                case 11: rankStr = "Jack"; break;
                default: rankStr = String.valueOf(rank);
            }
            String suitStr;
            switch (suit) {
                case 'H': suitStr = "Hearts"; break;
                case 'D': suitStr = "Diamonds"; break;
                case 'C': suitStr = "Clubs"; break;
                case 'S': suitStr = "Spades"; break;
                default: suitStr = "?";
            }
            return rankStr + " of " + suitStr;
        }
    }

    // Result of hand evaluation
    public static class EvaluatedHand implements Comparable<EvaluatedHand> {
        HandRank rank;
        List<Card> cards;  // best 5-card hand
        List<Integer> rankValues; // for tie-breakers

        public EvaluatedHand(HandRank rank, List<Card> cards, List<Integer> rankValues) {
            this.rank = rank;
            this.cards = cards;
            this.rankValues = rankValues;
        }

        @Override
        public int compareTo(EvaluatedHand o) {
            if (this.rank.getValue() != o.rank.getValue()) {
                return this.rank.getValue() - o.rank.getValue();
            }
            // Tie-break by rankValues descending
            for (int i = 0; i < Math.min(this.rankValues.size(), o.rankValues.size()); i++) {
                int diff = this.rankValues.get(i) - o.rankValues.get(i);
                if (diff != 0) return diff;
            }
            return 0;
        }

        @Override
        public String toString() {
            return rank.name().replace('_', ' ') + " - " + cards;
        }
    }

    // Main method to evaluate best hand given 7 cards
    public static EvaluatedHand evaluateBestHand(List<String> cardStrs) {
        if (cardStrs.size() != 7) {
            throw new IllegalArgumentException("Need exactly 7 cards");
        }

        List<Card> cards = cardStrs.stream()
            .map(Card::new)
            .sorted()
            .collect(Collectors.toList());

        List<List<Card>> all5CardCombos = combinations(cards, 5);

        EvaluatedHand best = null;

        for (List<Card> combo : all5CardCombos) {
            EvaluatedHand eval = evaluateFiveCardHand(combo);
            if (best == null || eval.compareTo(best) > 0) {
                best = eval;
            }
        }

        return best;
    }

    // Evaluate 5 card poker hand
    private static EvaluatedHand evaluateFiveCardHand(List<Card> hand) {
        Collections.sort(hand);

        boolean flush = isFlush(hand);
        boolean straight = isStraight(hand);
        Map<Integer, Integer> rankCounts = countRanks(hand);

        if (flush && straight) {
            if (hand.get(0).rank == 14 && hand.get(1).rank == 13) {
                // Royal Flush
                return new EvaluatedHand(HandRank.ROYAL_FLUSH, hand, getRankValuesForStraight(hand));
            } else {
                return new EvaluatedHand(HandRank.STRAIGHT_FLUSH, hand, getRankValuesForStraight(hand));
            }
        }

        // Check Four of a Kind
        int fourKindRank = getRankWithCount(rankCounts, 4);
        if (fourKindRank != -1) {
            List<Integer> tiebreak = new ArrayList<>();
            tiebreak.add(fourKindRank);
            tiebreak.addAll(getRemainingRanks(rankCounts, fourKindRank));
            return new EvaluatedHand(HandRank.FOUR_OF_A_KIND, hand, tiebreak);
        }

        // Check Full House
        int threeKindRank = getRankWithCount(rankCounts, 3);
        int pairRank = getRankWithCount(rankCounts, 2);
        if (threeKindRank != -1 && pairRank != -1) {
            List<Integer> tiebreak = Arrays.asList(threeKindRank, pairRank);
            return new EvaluatedHand(HandRank.FULL_HOUSE, hand, tiebreak);
        }

        if (flush) {
            return new EvaluatedHand(HandRank.FLUSH, hand, getRanksDescending(hand));
        }

        if (straight) {
            return new EvaluatedHand(HandRank.STRAIGHT, hand, getRankValuesForStraight(hand));
        }

        if (threeKindRank != -1) {
            List<Integer> tiebreak = new ArrayList<>();
            tiebreak.add(threeKindRank);
            tiebreak.addAll(getRemainingRanks(rankCounts, threeKindRank));
            return new EvaluatedHand(HandRank.THREE_OF_A_KIND, hand, tiebreak);
        }

        // Two pairs?
        List<Integer> pairs = getRanksWithCount(rankCounts, 2);
        if (pairs.size() >= 2) {
            pairs.sort(Collections.reverseOrder());
            List<Integer> tiebreak = new ArrayList<>(pairs);
            tiebreak.addAll(getRemainingRanks(rankCounts, pairs.get(0), pairs.get(1)));
            return new EvaluatedHand(HandRank.TWO_PAIR, hand, tiebreak);
        }

        // One pair?
        if (pairs.size() == 1) {
            List<Integer> tiebreak = new ArrayList<>();
            tiebreak.add(pairs.get(0));
            tiebreak.addAll(getRemainingRanks(rankCounts, pairs.get(0)));
            return new EvaluatedHand(HandRank.ONE_PAIR, hand, tiebreak);
        }

        // High card
        return new EvaluatedHand(HandRank.HIGH_CARD, hand, getRanksDescending(hand));
    }

    private static boolean isFlush(List<Card> hand) {
        char suit = hand.get(0).suit;
        for (Card c : hand) {
            if (c.suit != suit) return false;
        }
        return true;
    }

    private static boolean isStraight(List<Card> hand) {
        List<Integer> ranks = hand.stream().map(c -> c.rank).distinct().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        // Special case: A,5,4,3,2 (wheel)
        if (ranks.equals(Arrays.asList(14,5,4,3,2))) {
            return true;
        }

        for (int i = 0; i < ranks.size() - 1; i++) {
            if (ranks.get(i) - ranks.get(i+1) != 1) return false;
        }
        return ranks.size() == 5;
    }

    private static Map<Integer, Integer> countRanks(List<Card> hand) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (Card c : hand) {
            counts.put(c.rank, counts.getOrDefault(c.rank, 0) + 1);
        }
        return counts;
    }

    private static int getRankWithCount(Map<Integer, Integer> rankCounts, int count) {
        for (Map.Entry<Integer, Integer> e : rankCounts.entrySet()) {
            if (e.getValue() == count) {
                return e.getKey();
            }
        }
        return -1;
    }

    private static List<Integer> getRanksWithCount(Map<Integer, Integer> rankCounts, int count) {
        List<Integer> ranks = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : rankCounts.entrySet()) {
            if (e.getValue() == count) {
                ranks.add(e.getKey());
            }
        }
        return ranks;
    }

    private static List<Integer> getRemainingRanks(Map<Integer, Integer> rankCounts, Integer... excludeRanks) {
        Set<Integer> exclude = new HashSet<>(Arrays.asList(excludeRanks));
        List<Integer> remaining = new ArrayList<>();
        for (Integer r : rankCounts.keySet()) {
            if (!exclude.contains(r)) remaining.add(r);
        }
        remaining.sort(Collections.reverseOrder());
        return remaining;
    }

    private static List<Integer> getRanksDescending(List<Card> hand) {
        return hand.stream().map(c -> c.rank).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    private static List<Integer> getRankValuesForStraight(List<Card> hand) {
        List<Integer> ranks = hand.stream().map(c -> c.rank).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        // Handle wheel straight case
        if (ranks.equals(Arrays.asList(14,5,4,3,2))) {
            return Arrays.asList(5);
        }
        return Arrays.asList(ranks.get(0));
    }

    // Generate all combinations nCr of cards
    private static <T> List<List<T>> combinations(List<T> list, int r) {
        List<List<T>> combos = new ArrayList<>();
        combineHelper(list, r, 0, new ArrayList<>(), combos);
        return combos;
    }

    private static <T> void combineHelper(List<T> list, int r, int start, List<T> current, List<List<T>> combos) {
        if (current.size() == r) {
            combos.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < list.size(); i++) {
            current.add(list.get(i));
            combineHelper(list, r, i + 1, current, combos);
            current.remove(current.size() - 1);
        }
    }
}
