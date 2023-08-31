package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Assembler.Parser;

/**
 * TestParser
 * TODO: The current Parser has not yet been able to ignore white spaces and comments, 
 * which will be solved soon
 */
public class TestParser {

    String addFilePath = "D:\\courses\\CS_courses\\n" + //
            "and2tetris\\projects\\06\\add\\Add.asm";
    String maxFilePath = "D:\\courses\\CS_courses\\n" + //
            "and2tetris\\projects\\06\\max\\Max.asm";

    @Test
    public void instructionTypeTest() {
        Parser maxParser = new Parser(maxFilePath);
        Parser addParser = new Parser(addFilePath);
        // test for @ instruction
        for (int i = 0; i < 7; i++) {
            addParser.advance();
        }
        int addInstruction = addParser.instuctionType();
        assertEquals(1, addInstruction);

        // test for label instruction identification
        // i.e. (xxx)
        for (int i = 0; i < 17; i++) {
            maxParser.advance();
        }
        int maxInstruction1 = maxParser.instuctionType();
        assertEquals(2, maxInstruction1);

        // test for c instruction
        // i.e. D=M
        for (int i = 0; i < 2; i++) {
            maxParser.advance();
        }
        int maxInstruction2 = maxParser.instuctionType();
        assertEquals(3, maxInstruction2);
    }

    @Test
    public void symbolTest() {
        Parser maxParser = new Parser(maxFilePath);
        Parser addParser = new Parser(addFilePath);
        // test for @xxx parsing
        for (int i = 0; i < 7; i++) {
            addParser.advance();
        }
        assertEquals(1, addParser.instuctionType());
        assertEquals("2", addParser.symbol());

        // test for (xxx) parsing
        for (int i = 0; i < 17; i++) {
            maxParser.advance();
        }
        assertEquals(2, maxParser.instuctionType());
        assertEquals("OUTPUT_FIRST", maxParser.symbol());
    }

    @Test
    public void destTest() {
        Parser maxParser = new Parser(maxFilePath);
        // test for D=M
        for (int i = 0; i < 8; i++) {
            maxParser.advance();
        }
        assertEquals(3, maxParser.instuctionType());
        assertEquals("D", maxParser.dest());

        // test for D=D-M
        for (int i = 0; i < 2; i++) {
            maxParser.advance();
        }
        assertEquals(3, maxParser.instuctionType());
        assertEquals("D", maxParser.dest());

        // test for D;JGP
        for (int i = 0; i < 2; i++) {
            maxParser.advance();
        }
        assertEquals(3, maxParser.instuctionType());
        assertEquals("", maxParser.dest());

        // test for M=D
        for (int i = 0; i < 10; i++) {
            maxParser.advance();
        }
        assertEquals(3, maxParser.instuctionType());
        assertEquals("M", maxParser.dest());
    }

    @Test
    public void compTest() {
        Parser maxParser = new Parser(maxFilePath);

        // test for D=M
        for (int i = 0; i < 8; i++) {
            maxParser.advance();
        }
        assertEquals(3, maxParser.instuctionType());
        assertEquals("M", maxParser.comp());
        
        // test for D=D-M
        for (int i = 0; i < 2; i++) {
            maxParser.advance();
        }
        assertEquals(3, maxParser.instuctionType());
        assertEquals("D-M", maxParser.comp());
        
        // test for 0;JMP
        for (int i = 0; i < 6; i++) {
            maxParser.advance();
        }
        assertEquals(3, maxParser.instuctionType());
        assertEquals("0", maxParser.comp());
    }

    @Test
    public void jumpTest() {
        Parser maxParser = new Parser(maxFilePath);

        // test for D;JGT
        for (int i = 0; i < 12; i++) {
            maxParser.advance();
        }
        assertEquals(3, maxParser.instuctionType());
        assertEquals("JGT", maxParser.jump());

        // test for 0;JMP
        for (int i = 0; i < 4; i++) {
            maxParser.advance();
        }
        assertEquals(3, maxParser.instuctionType());
        assertEquals("JMP", maxParser.jump());
    }

    @Test
    public void ignoreTest() {
        Parser maxParser = new Parser(maxFilePath);

        maxParser.advance();  // This file is part of www.nand2tetris.org
        maxParser.advance();  // and the book "The Elements of Computing Systems"
        maxParser.advance();  // by Nisan and Schocken, MIT Press.
        maxParser.advance();  // File name: projects/06/max/Max.asm
        maxParser.advance();  // (white line)
        assertTrue(maxParser.shouldIgnore());
 
        maxParser.advance();  // Computes R2 = max(R0, R1)  (R0,R1,R2 refer to RAM[0],RAM[1],RAM[2])
        assertTrue(maxParser.shouldIgnore());
    }

}
