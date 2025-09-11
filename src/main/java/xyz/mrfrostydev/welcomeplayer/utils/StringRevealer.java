package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.network.chat.Component;

import java.util.Objects;

public class StringRevealer {

    protected String string;
    protected int visibleCharCount;

    public StringRevealer(){
        this.string = "";
        this.visibleCharCount = 0;
    }

    public StringRevealer(Component component){
        this.string = component.getString();
        this.visibleCharCount = 0;
    }

    public StringRevealer(String string){
        this.string = string;
        this.visibleCharCount = 0;
    }

    public boolean isEmpty(){
        return Objects.equals(string, "");
    }

    public boolean isRevealed(){
        return visibleCharCount >= string.length();
    }

    public void setString(String string) {
        this.string = string;
        this.visibleCharCount = 0;
    }

    public void setString(Component comp) {
        this.string = comp.getString();
        this.visibleCharCount = 0;
    }

    public String getString() {
        return string;
    }

    public String reveal(String string, int num){
        StringBuilder s = new StringBuilder();
        this.string = string;
        this.visibleCharCount = num;
        if(isRevealed()) return string;

        for (int i = 0; i< visibleCharCount; i++){
            s.append(string.charAt(i));
        }
        return s.toString();
    }

    public String reveal(int num){
        StringBuilder s = new StringBuilder();
        this.visibleCharCount = num;
        if(isRevealed()) return string;

        for (int i = 0; i< visibleCharCount; i++){
            s.append(string.charAt(i));
        }
        return s.toString();
    }

    public String reveal(){
        StringBuilder s = new StringBuilder();
        this.visibleCharCount++;
        if(isRevealed()) return string;

        for (int i = 0; i<visibleCharCount; i++){
            s.append(string.charAt(i));
        }
        return s.toString();
    }
}
