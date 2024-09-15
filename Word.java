package test;


import java.util.Arrays;
import java.util.Objects;

public class Word {
    private Tile[] wordTiles;
    private final int row, col;
    private final boolean vertical;


    public Word(Tile[] wordTiles, int row, int col, boolean vertical) {
        this.wordTiles = wordTiles;
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }

    public Tile[] getWordTiles() {
        return wordTiles;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVertical() {
        return vertical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return row == word.row && col == word.col && vertical == word.vertical && Objects.deepEquals(wordTiles, word.wordTiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(wordTiles), row, col, vertical);
    }

    @Override
    public String toString(){
        String s = "";
        for(Tile tile : wordTiles)
        {
            if(tile!=null)
                s += tile.letter;
        }
        return s;
    }
}
