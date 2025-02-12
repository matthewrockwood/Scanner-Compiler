package org.example;

import java.io.IOException;
import java.io.PushbackReader;

public class Scanner {
    enum TOKEN{SCANEOF, ID, CONSTINT, VAR, OUTPUT, INITIALIZE, EQUALS, IF, THEN, ENDIF, COMPUTE, PLUS};
    String[] reservedWords;
    PushbackReader pb;
    StringBuilder tokenBuffer = new StringBuilder(); // this is used to store a token.


    Scanner(PushbackReader pb){
        this.pb = pb;
        reservedWords = new String[]{"var", "output", "initialize", "if", "then", "endif", "compute"};
    }
    public int readNextChar(){
        int ans;
        try {
            ans = pb.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }
    public void unreadChar(int c){
        try {
            pb.unread(c);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public TOKEN scan(){
    int c = readNextChar();

    while(c!=-1){
        if(Character.isWhitespace(c)){
            c = readNextChar();
            continue;
        } else if (Character.isDigit(c)) {
            tokenBuffer.setLength(0);
            tokenBuffer.append((char)c);
            c = readNextChar();
            while(Character.isDigit(c)){
                tokenBuffer.append((char)c);
                c = readNextChar();
            }
            unreadChar(c);
            return TOKEN.CONSTINT;
        }
        else if (c=='+') return TOKEN.PLUS;
        else if (c=='=') return TOKEN.EQUALS;
        //for letters and such
        else if (Character.isLetter(c)) {
            tokenBuffer.setLength(0);
            tokenBuffer.append((char)c);
            c = (char)readNextChar();
            while (Character.isLetter(c)||Character.isDigit(c)){
                tokenBuffer.append((char)c);
                c = (char)readNextChar();
            }
            unreadChar(c);
            String temp = getTokenBufferString();
           // System.out.println(temp);
            if(temp.equals("if")) return TOKEN.IF;
            else if(temp.equals("then")) return TOKEN.THEN;
            else if(temp.equals("endif")) return TOKEN.ENDIF;
            else if(temp.equals("initialize")) return TOKEN.INITIALIZE;
            else if(temp.equals("compute")) return TOKEN.COMPUTE;
            else if(temp.equals("var")) return TOKEN.VAR;
            else if(temp.equals("output")) return TOKEN.OUTPUT;
            else return TOKEN.ID;

        }
        c = readNextChar();
    }
    return TOKEN.SCANEOF;
    }
    public String getTokenBufferString(){
        String temp = tokenBuffer.toString();
        return temp;
    }




}
