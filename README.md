# BookScrabble Server-Side Development

## Overview
This project involves developing a server-side system for a Book version of the Scrabble game. The system is designed to manage the game's rules, board, and tiles, ensuring correct word placement and scoring based on a predefined dictionary of words.

## Features
- **Tile Management**: Handles tiles that cannot be changed, each with a letter and score.
- **Bag Management**: Uses a single instance to manage the tile bag, allowing for random and specific tile draws, and adding tiles back into the bag.
- **Board Management**: Uses a single instance to manage the game board, including placing tiles, handling bonus squares, and checking if words are placed correctly.
- **Word Placement**: Checks if words can be placed on the board according to the rules and dictionary, and calculates scores based on bonus squares.

## Technologies Used
- 🖥️ **Java**: For implementing server-side logic and object-oriented programming.
- 🧩 **Design Patterns**: Singleton and immutability patterns for efficient and reliable object management.
