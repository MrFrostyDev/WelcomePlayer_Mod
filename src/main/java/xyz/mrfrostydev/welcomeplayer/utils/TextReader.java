package xyz.mrfrostydev.welcomeplayer.utils;

import java.util.ArrayDeque;

public class TextReader {
    private static final int TIME_BUFFER = 300; // Extra time for reading

    private int tickPerChar;

    private ArrayDeque<String> dialogDeque = new ArrayDeque<>();
    private StringRevealer stringReveal = new StringRevealer();

    private String currentText = ""; // Current active text being used
    private String displayText = ""; // Dialog that is displayed (slowly iterating till it matches currentText)
    private int messageTime = 0;
    private boolean permanent;

    public TextReader(int tickPerChar){
        this.tickPerChar = tickPerChar;
        this.permanent = false;
    }

    public void tick(){
        // if dialog queue is not empty and there isn't dialog currently in use
        if (!dialogDeque.isEmpty() && currentText.isEmpty()){
            currentText = dialogDeque.pop();
            int len = currentText.length();
            stringReveal.setString(currentText);
            messageTime = len * tickPerChar + TIME_BUFFER;
        }
        // if dialog is finished, reset
        if (messageTime <= 0){
            currentText = "";
            if(!permanent){
                displayText = "";
            }
        }
        if (messageTime > 0){
            messageTime--;
            if (messageTime % tickPerChar == 0) {
                displayText = stringReveal.reveal();
            }
        }
    }

    public void addText(String message){
        dialogDeque.add(message);
        permanent = false;
    }

    public void addPermenantText(String message){
        dialogDeque.add(message);
        permanent = true;
    }


    public String getDisplayText(){
        return displayText;
    }

    public ArrayDeque<String> getDialogQueue() {
        return dialogDeque;
    }

    public int getMessageTime() {
        return messageTime;
    }
}
