package Assembler;

import java.io.IOException;

public class init {
    public static void main(String[] args) throws IOException {
        String maxlFilePath = "D://courses//CS_courses//nand2tetris//projects//06//pong//PongL.asm";
        String maxlOutFile = "MaxLOut.txt";
        
        BasicHackAssembler basicHackAssembler = new BasicHackAssembler(maxlFilePath, maxlOutFile);
        basicHackAssembler.assemble();
    }
}
