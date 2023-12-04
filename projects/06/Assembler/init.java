package Assembler;

import java.io.IOException;

public class init {
    public static void main(String[] args) throws IOException {
        String FilePath1 = "../../max/MaxL.asm";
        String OutFile = "MaxLOut.txt";
        String FilePath2 = "../../pong/Pong.asm";

        // BasicHackAssembler basicHackAssembler = new BasicHackAssembler(FilePath1, OutFile);
        // basicHackAssembler.assemble();
        
        HackAssembler HackAssembler = new HackAssembler(FilePath2, OutFile);
        HackAssembler.assemble();

    }
}
