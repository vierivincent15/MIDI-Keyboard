package piano;


import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import midi.Midi;
import music.NoteEvent;
import music.Pitch;
import midi.Instrument;


public class PianoMachine {
	
	private Midi midi;
	private Instrument instrument = Midi.DEFAULT_INSTRUMENT;;
	private int noteShift = 0;
	private List<String> noteEvents = new ArrayList<>();
	private List<NoteEvent> recordedNote = new ArrayList<>();
	private boolean recording = false;

    
	/**
	 * constructor for PianoMachine.
	 * 
	 * initialize midi device and any other state that we're storing.
	 */
    public PianoMachine() {
    	try {
            midi = Midi.getInstance();
        } catch (MidiUnavailableException e1) {
            System.err.println("Could not initialize midi device");
            e1.printStackTrace();
            return;
        }
    }

    //TODO write method spec
    public void beginNote(Pitch rawPitch) {
        //TODO implement for question 1
        String stringNote = rawPitch.toString();
        if(noteEvents.indexOf(stringNote) == -1){
            noteEvents.add(stringNote);
            midi.beginNote(rawPitch.transpose(noteShift).toMidiFrequency(), instrument);
        }
        if(recording){
            int noteValue = rawPitch.hashCode() + noteShift;
            recordedNote.add(new NoteEvent(new Pitch(noteValue), new Date().getTime(), instrument, NoteEvent.Kind.start));
        }
    }

    //TODO write method spec
    public void endNote(Pitch rawPitch) {
        //TODO implement for question 1
        String stringNote = rawPitch.toString();
        if(noteEvents.indexOf(stringNote) != -1){
            noteEvents.remove(stringNote);
            midi.endNote(rawPitch.transpose(noteShift).toMidiFrequency(), instrument);
        }
        if(recording){
            int noteValue = rawPitch.hashCode() + noteShift;
            recordedNote.add(new NoteEvent(new Pitch(noteValue), new Date().getTime(), instrument, NoteEvent.Kind.stop));
        }
    }
    
    //TODO write method spec
    public void changeInstrument() {
       	//TODO: implement for question 2
        this.instrument = instrument.next();
    }
    
    //TODO write method spec
    public void shiftUp() {
    	//TODO: implement for question 3
        if(noteShift < 24){
            noteShift += 12;    //one octave is 12 tone shift
        }
    }
    
    //TODO write method spec
    public void shiftDown() {
    	//TODO: implement for question 3
        if(noteShift > -24){
            noteShift -= 12;    //one octave is 12 tone shift
        }
    }

    //TODO write method spec
    public boolean toggleRecording() {
        //TODO: implement for question 4
        if(recording == true){
            recording = false;
            return false;
        }
        else{
            recordedNote.clear();
            recording = true;
            return true;
        }
    }

    //TODO write method spec
    public void playback() {
        //TODO: implement for question 4
        if(!recording){
            int size = recordedNote.size();
            int[] time = new int[size];
            time[size-1] = 0;
            for(int i=0; i < recordedNote.size()-1;i++) {
                time[i] = (int) Math.floor((recordedNote.get(i + 1).getTime() - recordedNote.get(i).getTime()) / 10.0);
            }
            for(int i=0; i<recordedNote.size(); i++){
                NoteEvent thisNote = recordedNote.get(i);
                if(thisNote.getKind() == NoteEvent.Kind.start){
                    midi.beginNote(thisNote.getPitch().toMidiFrequency(), thisNote.getInstr());
                }
                else{
                    midi.endNote(thisNote.getPitch().toMidiFrequency(), thisNote.getInstr());
                }
                midi.rest(time[i]);
            }

        }
    }

}
