# MIDI-Keyboard
Play Music with Your Computer's Keyboard

## Task
Throughout this problem set we will be implementing a simple midi keyboard. We will give it the capability to:
1. Play notes using a row of keys on your computer keyboard
2. Change among a number of instruments
3. Change octaves
4. Record sequences of notes and play them back.

We have provided you with code that wraps a midi device, some abstractions for dealing with musical notes, and some Java Applet code that listens for keypresses. Our applet code calls several methods of PianoMachine; you should keep those methods as the entry points for PianoMachine and not change their signatures or specs (as we will use these functions for automated testing.) Do not modify any code inside the 'midi' or 'piano' packages. Do not modify any code in the PianoApplet class. Please note that we have provided a method, Midi.history(), which gives a text output from the piano and can be used for testing.
