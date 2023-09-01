package Assembler;

import java.util.HashMap;

public class Code {

    HashMap<String, String> CompTable;
    HashMap<String, String> DestTable;
    HashMap<String, String> JumpTable;

    public Code(){
        CompTable = new HashMap<>();
        DestTable = new HashMap<>();
        JumpTable = new HashMap<>();

        CompTable.put("0", "0101010");
        CompTable.put("1", "0111111");
        CompTable.put("-1", "0111010");
        CompTable.put("D", "0001100");
        CompTable.put("A", "0110000");
        CompTable.put("!D", "0001101");
        CompTable.put("!A", "0110001");
        CompTable.put("-D", "0001111");
        CompTable.put("-A", "0110011");
        CompTable.put("D+1", "0011111");
        CompTable.put("A+1", "0110111");
        CompTable.put("D-1", "0001110");
        CompTable.put("A-1", "0110010");
        CompTable.put("D+A", "0000010");
        CompTable.put("D-A", "0010011");
        CompTable.put("A-D", "0000111");
        CompTable.put("D&A", "0000000");
        CompTable.put("D|A", "0010101");

        CompTable.put("M", "1110000");
        CompTable.put("!M", "1110001");
        CompTable.put("-M", "1110011");
        CompTable.put("M+1", "1110111");
        CompTable.put("M-1", "1110010");
        CompTable.put("D+M", "1000010");
        CompTable.put("D-M", "1010011");
        CompTable.put("M-D", "1000111");
        CompTable.put("D&M", "1000000");
        CompTable.put("D|M", "1010101");

        DestTable.put("", "000");
        DestTable.put("M", "001");
        DestTable.put("D", "010");
        DestTable.put("DM", "011");
        DestTable.put("MD", "011"); // This record is created because in Rect.asm a dest part is written as "MD" instead of "DM" from the standard encoding and decoding table
        DestTable.put("A", "100");
        DestTable.put("AM", "101");
        DestTable.put("AD", "110");
        DestTable.put("ADM", "111");

        JumpTable.put("", "000");
        JumpTable.put("JGT", "001");
        JumpTable.put("JEQ", "010");
        JumpTable.put("JGE", "011");
        JumpTable.put("JLT", "100");
        JumpTable.put("JNE", "101");
        JumpTable.put("JLE", "110");
        JumpTable.put("JMP", "111");
    };

    public String dest(String destStr) {
        return DestTable.get(destStr);
    }
    
    public String comp(String compStr) {
        return CompTable.get(compStr);
    }
    
    public String jump(String jumpStr) {
        return JumpTable.get(jumpStr);
    }
}
