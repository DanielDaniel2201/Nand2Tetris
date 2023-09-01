package Assembler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HackAssembler {
    String fp;
    Parser parser;
    Code code;
    SymbolTable symbolTable;
    File outF;
    FileWriter FW;
    int currLine;
    int currVarAddr;

    public HackAssembler(String filePath, String outFile) {
        fp = filePath;
        parser = new Parser(filePath);
        code = new Code();
        symbolTable = new SymbolTable();

        try { // create a file to be written
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

        try {  // create a file writer
            FW = new FileWriter(outFile);
            System.out.println("A new file writer successfully initialized");
        } catch (IOException e) {
            System.out.println("An error occurred when creating a file writer.");
            e.printStackTrace();
        }

        currLine = -1;
        currVarAddr = 16;
    }

    private boolean isAIns() {
        return parser.instuctionType() == 1;
    }

    private boolean isLIns() {
        return parser.instuctionType() == 2;
    }
    
    private boolean isCIns() {
        return parser.instuctionType() == 3;
    }

    /*
    In the first pass, the assembler only takes care of label instructions and add
    the corresponding symbol-address pair to symbolTable

    currLine starts at 0 and is incremented by 1 whenever an A or C instruction is encountered
    */  
    private void scan1() throws IOException {
        while (parser.hasMoreLines() || parser.currIns != "null") {
            if (isAIns()) {   // an A-instruction (@xxx") is encountered, currLine incremented by 1
                currLine += 1;
             } else if (isCIns()) {  // a C-instruction is encountered, currLine incremented by 1
                currLine += 1;
            } else if (isLIns()) {   // an L-instruction is encountered, currLine does not change, add pairs to table
                String symbol = parser.symbol();
                int address = currLine + 1;
                symbolTable.addEnry(symbol, address);
            }
            parser.advance();
        }
        System.out.println("First pass completed.\n Here are the contents of the symbol tablt after scan1:\n");
        for (String symbol : symbolTable.symbolTable.keySet()) {
            System.out.print(symbol + "   ");
            System.out.println(symbolTable.getAddress(symbol));
        }
    }

    private void scan2() throws IOException {
        parser = new Parser(fp);
        while (parser.hasMoreLines() || parser.currIns != "null") {
            if (isAIns()) {   // an A-instruction (@xxx") is encountered
                String symbol = parser.symbol();
                if (isNumeric(symbol)) {
                    int addrDeInt = Integer.valueOf(symbol);
                    String addrBiStr = Integer.toBinaryString(0x10000 | addrDeInt).substring(1);
                    FW.write(addrBiStr + "\n");
                } else if (symbolTable.contains(symbol)) {
                    int addr = symbolTable.getAddress(symbol);
                    String addrBiStr = Integer.toBinaryString(0x10000 | addr).substring(1);
                    FW.write(addrBiStr + "\n");
                } else {
                    symbolTable.addEnry(symbol, currVarAddr);
                    String addrBiStr = Integer.toBinaryString(0x10000 | currVarAddr).substring(1);
                    FW.write(addrBiStr + "\n");
                    currVarAddr += 1;
                }
            } else if (isCIns()) {  // a C-instruction is encountered
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

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void assemble() throws IOException {
        scan1();
        scan2();
    }
}
