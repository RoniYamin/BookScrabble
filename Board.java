package test;


import java.util.ArrayList;
import java.util.HashSet;

public class Board {
    private Tile[][] boardTiles;
    boolean boardIsEmpty = true;
    private Tile.Bag bag;
    ArrayList<Word> words;
    private static Board board = null;
    private HashSet<Word> uniqueWords;

    private Board() {
        this.boardTiles = new Tile[15][15];
        this.bag = Tile.Bag.getBag();
        this.words = new ArrayList<>();
        this.uniqueWords = new HashSet<>(); // Initialize uniqueWords HashSet
    }

    public static Board getBoard() {
        if (board == null) {
            board = new Board();
        }
        return board;
    }

    public Tile[][] getTiles() {
        Tile[][] copyTiles = new Tile[15][15];
        for(int i = 0; i<15; i++)
        {
            System.arraycopy(boardTiles[i], 0, copyTiles[i],0,15);
        }
        return copyTiles;
    }

    private boolean inBounds(Word word) {
        final int wordCount = word.getWordTiles().length;
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();
        //length of board
        if (row >= 0 && row <= 14 && col >= 0 && col <= 14) {
            if (vertical) {
                if (row + wordCount <= 14)
                    return true;
            } else if (col + wordCount <= 14)
                return true;
        }
        return false;
    }

    private boolean nextTo (Word word) {
        final int wordCount = word.getWordTiles().length;
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();

        if(vertical) {
            for(int i=0; i<=wordCount; i++)
            {
                if ((row+i == row) && ((row-1)>=0))
                {
                    if(boardTiles[row-1][col]!=null)
                        return true;
                }
                if ((i == wordCount) && ((row+1)<=14))
                {
                    if(boardTiles[row+1][col]!=null)
                        return true;
                }
                if((boardTiles[row+i][col-1]!=null) && ((col-1)>=0))
                    return true;
                if((boardTiles[row+i][col+1]!=null) && ((col+1)<=14))
                    return true;
            }
        }

        else{
            for(int i=0; i<=wordCount; i++)
            {
                if ((col+i == col) && ((col-1)>=0))
                {
                    if(boardTiles[row][col-1]!=null)
                        return true;
                }
                if ((i == wordCount) && ((col+1)<=14))
                {
                    if(boardTiles[row][col+1]!=null)
                        return true;
                }
                if((boardTiles[row-1][col+i]!=null) && ((row-1)>=0))
                    return true;
                if((boardTiles[row+1][col+i]!=null) && ((row+1)<=14))
                    return true;
            }
        }
        return false;
    }

    private boolean freePlace (Word word) {
        final int wordCount = word.getWordTiles().length;
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();
        Tile[] wordTiles = word.getWordTiles();

        if(vertical) {
            for(int i=0; i<wordCount; i++) {
                if((boardTiles[row+i][col]!=null)  && (wordTiles[i].letter!='_') && (wordTiles[i]!=null))
                    return false;
            }
        }
        else{
            for(int i=0; i<wordCount; i++) {
                if(boardTiles[row][col+i]!=null && (wordTiles[i].letter!='_') && (wordTiles[i]!=null))
                    return false;
            }
        }
        return true;
    }

    public boolean boardLegal(Word word) {
        final int wordCount = word.getWordTiles().length;
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();

        if (boardTiles[7][7] == null) {
            if (inBounds(word)) {
                if (vertical) {
                    if (col == 7 && row <= 7 && row + wordCount >= 7)
                        return true;
                }
                else if (row == 7 && col <= 7 && col + wordCount >= 7)
                    return true;
            }

        } else if (inBounds(word)) {
            if(nextTo(word)) {
                if(freePlace(word))
                    return true;
            }
//            if (vertical) {
//                for (int i = 0; i < wordCount; i++) {
//                    if (row + i >= 0 && row + i < 15 && col >= 0 && col < 15 && (boardTiles[row + i][col+1] != null) || (boardTiles[row + i][col-1] != null))
//                        return true;
//                }
//            } else {
//                for (int i = 0; i < wordCount; i++) {
//                    if (row >= 0 && row < 15 && col + i >= 0 && col + i < 15 && (boardTiles[row-1][col + i] != null) ||(boardTiles[row+1][col + i] != null))
//                        return true;
//                }
//            }
        }
        return false;
    }

    public boolean dictionaryLegal(Word word) {
        return true;
    }

    public ArrayList<Word> getWords(Word word) {
        ArrayList<Word> newWords = new ArrayList<>(); // ArrayList to store new words

        final int wordCount = word.getWordTiles().length;
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();

        Word modifiedWord= fixWord(word);

        // Check if the modified word is already in the set of unique words
        if (!uniqueWords.contains(modifiedWord)) {
            newWords.add(modifiedWord); // Add the modified word to newWords if it's unique
        }

        // Check for new words formed by extending existing words on the board
        for (int i = 0; i < wordCount; i++) {
            int currRow = row;
            int currCol = col;
            if (vertical) {
                currRow += i;
            } else {
                currCol += i;
            }

            // Check for words formed horizontally
            if (vertical) {
                if (currCol > 0 && boardTiles[currRow][currCol - 1] != null && boardTiles[currRow][currCol + 1] != null) {
                    Word newWord = getHorizontalWord(currRow, currCol);
                    if (!uniqueWords.contains(newWord)) {
                        newWords.add(newWord);
                    }
                }
                else if (currCol > 0 && boardTiles[currRow][currCol - 1] != null) {
                    Word newWord = getHorizontalWord(currRow, currCol);
                    if (!uniqueWords.contains(newWord)) {
                        newWords.add(newWord);
                    }
                }
                else if (currCol > 0 && boardTiles[currRow][currCol + 1] != null) {
                    Word newWord = getHorizontalWord(currRow, currCol);
                    if (!uniqueWords.contains(newWord)) {
                        newWords.add(newWord);
                    }
                }
            }

            // Check for words formed vertically
            else {
                if (currRow > 0 && boardTiles[currRow - 1][currCol] != null && boardTiles[currRow + 1][currCol] != null) {
                    Word newWord = getVerticalWord(currRow, currCol);
                    if (!uniqueWords.contains(newWord)) {
                        newWords.add(newWord);
                    }
                }
                else if (currRow > 0 && boardTiles[currRow - 1][currCol] != null) {
                    Word newWord = getVerticalWord(currRow, currCol);
                    if (!uniqueWords.contains(newWord)) {
                        newWords.add(newWord);
                    }
                }
                else if (currRow > 0 && boardTiles[currRow + 1][currCol] != null) {
                    Word newWord = getVerticalWord(currRow, currCol);
                    if (!uniqueWords.contains(newWord)) {
                        newWords.add(newWord);
                    }
                }
            }
        }
        //System.out.println(String.join(", ", newWords.toString()));
        return newWords;
    }

    private Word getHorizontalWord(int row, int col) {
        int startCol = col;
        while (startCol > 0 && boardTiles[row][startCol - 1] != null) {
            startCol--;
        }
        int endCol = col;
        while (endCol < 14 && boardTiles[row][endCol + 1] != null) {
            endCol++;
        }
        Tile[] tiles = new Tile[endCol - startCol + 1];//?
        for (int i = startCol; i <= endCol; i++) {
            tiles[i - startCol] = boardTiles[row][i];
        }
        return new Word(tiles, row, startCol, false);
    }

    private Word getVerticalWord(int row, int col) {
        int startRow = row;
        while (startRow > 0 && boardTiles[startRow - 1][col] != null) {
            startRow--;
        }
        int endRow = row;
        while (endRow < 14 && boardTiles[endRow + 1][col] != null) {
            endRow++;
        }
        Tile[] tiles = new Tile[endRow - startRow + 1];
        for (int i = startRow; i <= endRow; i++) {
            tiles[i - startRow] = boardTiles[i][col];
        }
        return new Word(tiles, startRow, col, true);
    }


    private enum SpecialPlace {
        NORMAL,
        DOUBLE_LETTER,
        TRIPLE_LETTER,
        DOUBLE_WORD,
        TRIPLE_WORD,
        FIRST_WORD_ON_BOARD
    }

    private SpecialPlace getSpecialPlace(int row, int col) {//dictionery
        if ((row == 0 || row == 14) && (col == 0 || col == 7 || col == 14)) {
            return SpecialPlace.TRIPLE_WORD;
        } else if ((row == 7) && (col == 0 || col == 14)) {
            return SpecialPlace.TRIPLE_WORD;
        } else if ((row == 1 || row == 13) && (col == 1 || col == 13)) {
            return SpecialPlace.DOUBLE_WORD;
        } else if ((row == 2 || row == 12) && (col == 2 || col == 12)) {
            return SpecialPlace.DOUBLE_WORD;
        } else if ((row == 3 || row == 11) && (col == 3 || col == 11)) {
            return SpecialPlace.DOUBLE_WORD;
        } else if ((row == 4 || row == 10) && (col == 4 || col == 10)) {
            return SpecialPlace.DOUBLE_WORD;
        } else if ((row == 5 || row == 9) && (col ==1 || col == 5 || col == 9 || col == 13)) {
            return SpecialPlace.TRIPLE_LETTER;
        } else if ((row == 1 || row == 13) && (col == 5 || col == 9)) {
            return SpecialPlace.TRIPLE_LETTER;
        } else if ((row == 0 || row == 7 || row == 14) && (col == 3 || col == 11)) {
            return SpecialPlace.DOUBLE_LETTER;
        } else if ((row == 3 || row == 11) && (col == 0 || col == 7 || col == 14)) {
            return SpecialPlace.DOUBLE_LETTER;
        } else if ((row == 2 || row == 12) && (col == 6 || col == 8)) {
            return SpecialPlace.DOUBLE_LETTER;
        } else if ((row == 6 || row == 8) && (col == 2 || col == 6 || col == 8 || col == 12)) {
            return SpecialPlace.DOUBLE_LETTER;
        } else if (boardIsEmpty) {
            return SpecialPlace.FIRST_WORD_ON_BOARD;
        } else {
            return SpecialPlace.NORMAL; // Default to normal if not a special place
        }
    }

    private Word fixWord(Word word){
        final int wordCount = word.getWordTiles().length;
        Tile[] wordTiles = word.getWordTiles();
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();

        // Replace underscores with letters from the boardTiles array
        Tile[] wordTilesCopy = wordTiles.clone(); // Create a copy to avoid modifying the original word
        for (int i = 0; i < wordCount; i++) {
            if (wordTilesCopy[i] == null || wordTilesCopy[i].letter == '_') {//underscore
                if (vertical) {
                    wordTilesCopy[i] = boardTiles[row + i][col];
                } else {
                    wordTilesCopy[i] = boardTiles[row][col + i];
                }
            }
        }

        // Create a new word with underscores replaced by letters
        Word modifiedWord = new Word(wordTilesCopy, row, col, vertical);

        return modifiedWord;
    }

    private int getScore(Word word) {
        final int wordCount = word.getWordTiles().length;
        Tile[] wordTiles = word.getWordTiles();
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();
        int wordScore = 0;
        int doubleWord = 0;
        int tripleWord = 0;

        int index = 0; // Index to iterate through wordTiles array
        Word newWord = fixWord(word);
        Tile[] newWordTiles = newWord.getWordTiles();

        while (index < wordCount) {
            Tile tile = newWordTiles[index];
            int tileScore = tile.score;
            SpecialPlace place = getSpecialPlace(row, col);
            switch (place) {
                case DOUBLE_LETTER:
                    tileScore *= 2;
                    break;
                case TRIPLE_LETTER:
                    tileScore *= 3;
                    break;
                case DOUBLE_WORD:
                    doubleWord++;
                    break;
                case TRIPLE_WORD:
                    tripleWord++;
                    break;
                case FIRST_WORD_ON_BOARD:
                    boardIsEmpty = false;
                    doubleWord++;
                    break;
                default:
                    break;
            }
            wordScore += tileScore;

            // Move to the next column or row based on word direction
            if (word.isVertical()) {
                row++;
            } else {
                col++;
            }

            index++; // Move to the next tile in the word
        }
        if (doubleWord>0) {
            wordScore *= 2*doubleWord;
        }
        if (tripleWord>0) {
            wordScore *= 3*tripleWord;
        }
        return wordScore;
    }

    public int tryPlaceWord(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        if (!boardLegal(word))
            return 0;
        if (!dictionaryLegal(word))
            return 0;

        Word newWord = fixWord(word);
        Tile[] newWordTiles = newWord.getWordTiles();
        int index = 0;
        while (index < newWordTiles.length) {
            Tile tile = newWordTiles[index];
            boardTiles[row][col] = tile; // Place the tile onto the board
            // Move to the next column or row based on word direction
            if (word.isVertical()) {
                row++;
            } else {
                col++;
            }
            index++; // Move to the next tile in the word
        }

        ArrayList<Word> words = getWords(word);

        for (Word w : words)
        {
            if (!dictionaryLegal(w))
                return 0;
        }

        Tile[] wordTiles = word.getWordTiles();
        int totalScore = 0;

        for (Word w : words) {
            //add into the hash set
            uniqueWords.add(w);
            totalScore += getScore(w);
        }
        return totalScore;
    }
}
