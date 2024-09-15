package test;


import java.util.Objects;
import java.util.Random;

public class Tile {
    public final char letter;
    public final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }


    @Override
    public String toString(){
        return this.letter + "";
    }

    public static class Bag {
        private final int[] bagTiles;
        private int sizeBagTiles;
        private final int OriginalSizeBagTiles;
        private final Tile[] tiles;
        private static Bag bag = null;

        private Bag() {
            bagTiles = new int[]{
                    9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
            };

            tiles = new Tile[]{
                    new Tile('A', 1),
                    new Tile('B', 3),
                    new Tile('C', 3),
                    new Tile('D', 2),
                    new Tile('E', 1),
                    new Tile('F', 4),
                    new Tile('G', 2),
                    new Tile('H', 4),
                    new Tile('I', 1),
                    new Tile('J', 8),
                    new Tile('K', 5),
                    new Tile('L', 1),
                    new Tile('M', 3),
                    new Tile('N', 1),
                    new Tile('O', 1),
                    new Tile('P', 3),
                    new Tile('Q', 10),
                    new Tile('R', 1),
                    new Tile('S', 1),
                    new Tile('T', 1),
                    new Tile('U', 1),
                    new Tile('V', 4),
                    new Tile('W', 4),
                    new Tile('X', 8),
                    new Tile('Y', 4),
                    new Tile('Z', 10)
            };

             sizeBagTiles = sizeOfBagTiles();
             OriginalSizeBagTiles = sizeOfBagTiles();
        }

        public int sizeOfBagTiles() {
            int sum = 0;
            for (int i = 0; i < bagTiles.length; i++) {
                sum = sum + bagTiles[i];
            }
            return sum;
        }

        public Tile getRand() {
            if (sizeBagTiles == 0)
                return null;
            Random rnd = new Random();
            int index;
            do {
                index = rnd.nextInt(bagTiles.length);
            }
            while (bagTiles[index] == 0);
            bagTiles[index]--;
            sizeBagTiles--;
            return tiles[index];
        }

        public Tile getTile(char c) {
            if (c == '_') {
                return new Tile('_', 0);
            }
            if (c < 'A' || c > 'Z') {
                return null;
            }
            if (bagTiles[c - 'A'] == 0)
                return null;
            bagTiles[c - 'A']--;
            sizeBagTiles--;
            return tiles[c - 'A'];
        }

        public void put(Tile t) {
            if(sizeBagTiles<OriginalSizeBagTiles) {
                bagTiles[t.letter - 'A'] += 1;
                sizeBagTiles++;
            }
        }

        public int size() {
            return sizeBagTiles;
        }

        public int[] getQuantities() {
            return bagTiles.clone();
        }

        public static Bag getBag() {
            if (bag == null) {
                bag = new Bag();
            }
            return bag;
        }
    }
}
