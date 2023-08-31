package Assembler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * BasicHackAssembler can only hanle files with no symbols
 */
public class BasicHackAssembler {

    Parser parser;
    Code code;
    SymbolTable symbolTable;
    File outF;
    FileWriter FW;

    public BasicHackAssembler(String filePath, String outFile) {
        parser = new Parser(filePath);
        code = new Code();
        symbolTable = new SymbolTable();

        try {
            outF = new File(outFile);
            if (outF.createNewFile()) {
                System.out.println("File created: " + outF.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred when creating a file.");
            e.printStackTrace();
        }

        try {
            FW = new FileWriter(outFile);
            System.out.println("A new file writer successfully initialized");
        } catch (IOException e) {
            System.out.println("An error occurred when creating a file writer.");
            e.printStackTrace();
        }
    }

    public boolean isAIns() {
        return parser.instuctionType() == 1;
    }

    public boolean isLIns() {
        return parser.instuctionType() == 2;
    }
    
    public boolean isCIns() {
        return parser.instuctionType() == 3;
    }

    public boolean isValid() {
        int type = parser.instuctionType();
        return type == 1 || type == 2 || type == 3;
    }

    public void assemble() throws IOException {
        while (parser.hasMoreLines() || parser.currIns != "null") {
            if (isAIns()) {   // case of A-instruction (@xxx")
                String symbol = parser.symbol();
                int addrDeInt = Integer.valueOf(symbol);
                String addrBiStr = Integer.toBinaryString(0x10000 | addrDeInt).substring(1);
                FW.write(addrBiStr + "\n");
             } else if (isCIns()) {  // case of C-instruction
                String dest = parser.dest();
                String comp = parser.comp();
                String jump = parser.jump();
                String destCode = code.dest(dest);
                String compCode = code.comp(comp);
                String jumpCode = code.jump(jump);
                String toWrite = "111" + compCode + destCode + jumpCode + "\n";
                FW.write(toWrite);
            }
            parser.advance();
        }
        FW.close();
        System.out.println("File writing completed.");
    }



    
}
