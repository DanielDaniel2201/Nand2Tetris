package Assembler;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Parser
 */
public class Parser {
    // fs is an instance of Scanner and is used to scan the whole file input
    File file;
    Scanner fs;

    String currIns;
    // Initialization for Parser class
    public Parser (String filePath) {
        file = new File(filePath);
        try {
            fs = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        currIns = fs.nextLine();

    }

    // hasMoreLines returns whether the scanner still has content to scan
    public boolean hasMoreLines() {
        // discard the white space in the first line of the input
        if (currIns.contains(" ")) {
            currIns = currIns.replace(" ", "");
        }
        if (currIns.charAt(0) != '/' && currIns.contains("/")) {
            currIns = getRidOfComments(currIns);
        }
        while (shouldIgnore()) {
            currIns = fs.nextLine();
            if (currIns == "") {
                continue;
            }
            // discard the white space in the rest of the lines of the input
            if (currIns.contains(" ")) {
                currIns = currIns.replace(" ", "");
            }
            if (currIns.charAt(0) != '/' && currIns.contains("/")) {
                currIns = getRidOfComments(currIns);
            }
        }
        return fs.hasNextLine();
    }

    // advance advances the scanner past the current line
    public void advance() {
        if (hasMoreLines()) {
            currIns = fs.nextLine();
        } else {
            currIns = "null";
        }
    }

    // instructionType returns integer 1, 2, or 3 to indicate the insturction type (A, L, C, respectively)
    public int instuctionType () {
        boolean atSign = currIns.contains("@");
        boolean leftBracket = currIns.contains("(");
        
        if (atSign) {
            return 1;
        } else if (leftBracket) {
            return 2;
        } else {
            return 3;
        }
    }

    // symbol returns xxx provided the instruction is (xxx) or @xxx
    public String symbol() {
        String symbol;
        if (currIns.contains(")")) {
            symbol = currIns.replaceAll("[(]", "");
            symbol = symbol.replaceAll("[)]", "");
        } else {
            symbol = currIns.replaceAll("@", "");
        }
        return symbol;
    }

    // dest returns the dest part of c instruction
    public String dest() {
        String dest;

        if (currIns.contains("=")) {
            String[] arrOfStr = currIns.split("=");
            dest = arrOfStr[0];
        } else {
            dest = "";
        }
        return dest;
    }

    // comp returns the comp part of c instruction
    public String comp() {
        String comp;

        if (currIns.contains("=") && currIns.contains(";")){ // instruction contains dest, comp and jump
            String[] arrOfStr1 = currIns.split("=");
            String[] arrOfStr2 = arrOfStr1[1].split(";");
            comp = arrOfStr2[0];
        } else if (currIns.contains("=")) {  // instruction contains dest and comp but no jump
            String[] arrOfStr = currIns.split("=");
            comp = arrOfStr[1];
        } else if (currIns.contains(";")){  // instruction contains comp and jump but no dest
            String[] arrOfStr = currIns.split(";");
            comp =  arrOfStr[0];
        } else { // instruction only contains comp
            comp = currIns;
        }
        return comp;
    }

    // jump returns the jump part of c instruction
    public String jump() {
        String jump;

         if (currIns.contains("=") && currIns.contains(";")){ // instruction contains dest, comp and jump
            String[] arrOfStr1 = currIns.split("=");
            String[] arrOfStr2 = arrOfStr1[1].split(";");
            jump = arrOfStr2[1];
        } else if (currIns.contains("=")) {  // instruction contains dest and comp but no jump
            jump = "";
        } else if (currIns.contains(";")){  // instruction contains comp and jump but no dest
            String[] arrOfStr = currIns.split(";");
            jump =  arrOfStr[1];
        } else { // instruction only contains comp
            jump = "";
        }       

        return jump;
    }

    public boolean shouldIgnore() {
        return currIns.contains("//") || currIns == "";
    }

    public String getRidOfComments(String str) {
        int edge = 0;
        while (str.charAt(edge) != '/') {
            edge += 1;
        }
        return str.substring(0, edge);
    }
}
