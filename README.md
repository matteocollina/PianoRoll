# Microtonal PianoRoll

![PianoRoll Microtonal](https://raw.githubusercontent.com/matteocollina/PianoRoll/master/ScreenProjects/Schermata%202018-02-19%20alle%2016.42.05.png)

## Compile
Download and include in your project:  
- [jMusic (1.6.5)] (https://sourceforge.net/projects/jmusic/files/jMusic_Stable/)  
- [jSyn] (http://www.softsynth.com/jsyn/developers/download.php)  

I tested with Java 8.


## Description
Swing application that displays a flat roll style interface in which, however, the notes do not correspond to the chromatic scale of the equal tempered system, but can be chosen starting frequency, final frequency and number of subdivisions of the vertical axis.

On the left of the main screen a pseudo-keyboard (where there are no white and black keys but only a sequence of identical keys) and to the right of it a grid of positions corresponding to time (horizontally) and note (vertically) .

The user can draw the notes to play and play them.

The settings concern the stamp to be used, the minimum and maximum frequency, the number of subdivisions of the keyboard and the BPM for the execution and the number of measurements.

The minimum duration of the notes is 1/16 but it is possible to change it in the ConfigManager with effective remodeling of the PianoRoll.


## Next? ##  
- Possibility to insert notes of different duration
- Graphic effect to the play of a note
- Insertion of the "loop" button which cycles the sequence
- Optimization of settings saving with score maintenance
