# Helmet Game

## Introduction      
Helmet is a game that consists in a player that has to run from the start door, on the left of the window,
to the exit door, on the right. Each time the last door is crossed the player wins points and appears
in front of the start door again. The difficulty of the game lies in the tools falling from the sky, which
take the player's lives (or points).

## Installation   
This is a Java project made in the [Eclipse IDE](http://www.eclipse.org/downloads/packages/). Any IDE
supporting Java can be used, though.   
As it's a visual game, it's essential to build a GUI, and the best library to do that in this language is 
[Java Swing](https://www.javatpoint.com/java-swing). The web linked provides a tutorial with detailed
explanation of the library's structure.     
>Java Swing tutorial is a part of Java Foundation Classes (JFC) that is used to create window-based applications.
>It is built on the top of AWT (Abstract Windowing Toolkit) API and entirely written in java.     

## Game details and rules   
The definition of this arcade game is the one said at the introduction, but all the measures and details
like the number of lives, points, or the presentation itself it's made up by me.    
 
1. The player is only moved by the left and right key arrows.  
2. Each time a run across the screen is completed, the player will win 5 points and a music of victory will pop.     
1. The exit door opens and closes every five seconds.  
1. Every 20 points is a new level and the speed of the tools falling increase.  
3. The player begins with 10 lives.      
4. The tools can be divided by the ones that benefit the player and the ones that harm them:     
    - "bad" tools:
        - hammer: takes two lives and returns the player to the start door.
        - screwdriver: takes one life and reverses the arrow keys for five seconds.
        - wrench: takes one life and maintains the player still for five seconds. 
    - "good" tools:
        - heart: adds one life to the player
        - shield: turns the player immune for seven seconds
When the player is hit by a "bad" tool a sound of complain will pop, if it's a "good" one the sound will be similar
to a "hurray".

Before each game, the program will ask for a user name. This is done with the purpose of identifying the top
scores that are stored in a JSON file. At the end of the game, the program will compare the last player's score
with the ones stored. If it's higher than one of them, the last score will be added to the top.   

The game end as the lives do so.

## Project's structure     
The main class of the project is called `Game.java`. Here are created all the essential instances for
running the program. The frame is created and repainted by a while loop to pretend movement.
That's why each mobile object need a position and speed variable. The first, is expressed in coordinates, but
as all the objects move vertically or horizontally, only one of them will change. The position variable's value
is where the object will start it's movement. The speed, is the amount of pixels that the object will move on
each repaint.      
To detect collisions the object has to be delimited in a geometric shape that doesn't change. With the method
`intersects()`, a `true` response is returned and makes easier to detect collisions.

In the case of the player, the movement is horizontal. If it collides with the right or left margin of
the frame, the player is relocated at the starting point. Then, if crashes with the closed door, the x
position maintains stable until the door is opened or the direction is changed, when the movement would return
to normal.   

The tools are all children classes of the father class `Tool.java`, that extends from `Thread`. This means,
each one of the tools is independent from the others, and the rest of the objects too, falling in the background
until the end of the program, unless it's told them the contrary. They are started at the `Game` class, and when they
arrive at the bottom of the frame, their position gets back to the top of the frame, but in a random x.   
Each tool has its effect, usually expressed in static variables due to the concurrency of the threads.    
For simplicity purposes all tools have the same bounds.

Finally, there's the JSON thing. The class `Score.java` stores the scores, as its name says, in two attributes:
the user name and the points itself. A method in `JSON.java` reads de document and parses it to Score
java objects and gather them in an array. The last score made by the player is compared with each one of the
top scores and when a lower score is found, this is substituted by the current. The new top scores
are sorted and then wrote and displayed.

Before ending, here's a link to a video of the original game:    
[12802 Nintendo Game & Watch Gold Helmet CN-07 1981 (Youtube)](https://www.youtube.com/watch?v=AsX9uDTmsc8)


