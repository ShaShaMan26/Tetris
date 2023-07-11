# Sha's Tetris

A Game Boy styled version of Tetris.

## Download

You can download the game [here](https://cdn.shashack.org/Tetris/Sha's-Tetris.jar) 
for Windows operating systems.  

## Setup

Simply download and run `Sha's-Tetris.jar` to play.  
If the `.jar` isn't launching, ensure you have [Java](https://www.java.com/en/download/) downloaded.

## How to Play

### Gameplay

This version of Tetris plays just like you'd expect from any other version, but is most similar in game feel to that of 
Nintendo's 1989 *Tetris* for the Game Boy. Those familiar with that iteration of the Tetris should feel right at home.

### Controls

*Sha's Tetris* is controlled using the mouse in menus and keyboard in game.

#### In-Game

```
Movement
A or LEFT - Moves active Tetrimino to the left.
D or RIGHT - Moves active Tetrimino to the right.
S or Down - Soft Drops active Tetrimino down while held.
W or UP - Hard Drops active Tetrimino (if Hard Dropping is enabled).

Rotations
E or C - Rotates active Tetrimino clockwise.
Q or X - Rotates active Tetrimino counterclockwise.

Misc
ESC - Returns to Main Menu.
SPACE - Pauses game (toggle).
0 - Restarts game.
```

#### In-Menus

````
Use the mouse to navagate to, and left-click to select, menu options.
ESC - Ends the program and auto saves game data.
````

### Menus

Upon launching *Sha's Tetris*, you will be brought to the Main Menu. Here you can select `Play Game`, `Options`, or 
`Exit`.  
```
"Play Game" - Starts the game.  
"Options" - Brings up the Options Menu.  
"Exit" - Ends the program and auto saves game data.
```

#### Options

Inside the Options Menu are three toggleable settings: `Hard Drop`, `Ghost Piece`, and `Fullscreen`.
Active toggles are signified by a filled in square to the left of the respective setting's title.  

```
"Hard Drop" - Allows Tetriminos to be instantly dropped to the bottom of the game well.
"Ghost Piece" - Displays a phantom Tetrimino corresponding to where the current Tetrimino would land 
if left to fall to the bottom of the well.
"Fullscreen" - Fullscreens game window.
```

The Options Menu also houses global volume controls.

```
Left-clicking the "minus" or "plus" symbol on either side of the current volume number will decrement
or increment the game volume respectively.
```

*Left-click the `X` at the top right of the menu panel or press `ESC` to close the Options Menu.*

## Known Issues

 - Minor sprite flicker on Tetriminos when acting upon them (only happens at refresh rates above 60hz... I think).
 - Line clear animation sometimes fails to play (have no clue why this occurs, I think it has to do with threading).
 - Game Over can sometimes be triggered twice.
 - Queued Tetrimino sometimes displays outside queue box (dependent on window size).
