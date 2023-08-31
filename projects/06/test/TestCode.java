package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Assembler.Code;

public class TestCode {
    Code code = new Code();
    
    @Test
    public void testComp() {
        assertEquals("0101010", code.comp("0"));
        assertEquals("0110000", code.comp("A"));
        assertEquals("0000010", code.comp("D+A"));
        assertEquals("1110111", code.comp("M+1"));
        assertEquals("1000010", code.comp("D+M"));
        assertEquals("1010101", code.comp("D|M"));
    }

    @Test
    public void testDest() {
        assertEquals("000", code.dest(""));
        assertEquals("010", code.dest("D"));
        assertEquals("011", code.dest("DM"));
        assertEquals("100", code.dest("A"));
        assertEquals("101", code.dest("AM"));
        assertEquals("111", code.dest("ADM"));
    }

    @Test
    public void testJump() {
        assertEquals("000", code.jump(""));
        assertEquals("010", code.jump("JEQ"));
        assertEquals("011", code.jump("JGE"));
        assertEquals("100", code.jump("JLT"));
        assertEquals("101", code.jump("JNE"));
        assertEquals("111", code.jump("JMP"));
    }
}
