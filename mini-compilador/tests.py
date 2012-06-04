"""
Dependencies: Nose
    pip install nose

How to run: On the project root run (in a terminal)
    python tests.py
"""
# -*- coding: utf-8 -*-
import unittest
from nose.tools import assert_equal

import mips_compiler as comp


class CompileInstructionTest(unittest.TestCase):

    def test_add(self):
        instr = "add R9,R1,R7"
        instr_bin = comp.compile_instruction(instr)
        assert_equal("00000000001001110100100000100000", instr_bin)

    def test_mul(self):
        instr = "mul R9,R1,R7"
        instr_bin = comp.compile_instruction(instr)
        assert_equal("00000000001001110100100000011000", instr_bin)

    def test_nop(self):
        instr = "nop"
        instr_bin = comp.compile_instruction(instr)
        assert_equal("00000000000000000000000000000000", instr_bin)

    def test_addi(self):
        instr = "addi R10,R0,100"
        instr_bin = comp.compile_instruction(instr)
        assert_equal("00100000000010100000000001100100", instr_bin)

    def test_li(self):
        instr = "li R10, 100"  # equivalente a addi r10, r0, 100
        instr_bin = comp.compile_instruction(instr)
        assert_equal("00100000000010100000000001100100", instr_bin)

    def test_ble(self):
        instr = "ble R6,R10,12"
        instr_bin = comp.compile_instruction(instr)
        assert_equal("00011100110010100000000000001100", instr_bin)

    def test_lw(self):
        instr = "lw R1,24(R0)"
        instr_bin = comp.compile_instruction(instr)
        assert_equal("10001100000000010000000000011000", instr_bin)

    def test_sw(self):
        instr = "sw R9,24(R0)"
        instr_bin = comp.compile_instruction(instr)
        assert_equal("10101100000010010000000000011000", instr_bin)

    def test_ignore_spaces(self):
        instr = "add R9,   R1,R7"
        instr_bin = comp.compile_instruction(instr)
        assert_equal("00000000001001110100100000100000", instr_bin)

        instr = "mul   R9  , R1, R7  "
        instr_bin = comp.compile_instruction(instr)
        assert_equal("00000000001001110100100000011000", instr_bin)

        instr = "sw   R9,  24  (  R0  ) "
        instr_bin = comp.compile_instruction(instr)
        assert_equal("10101100000010010000000000011000", instr_bin)

    def test_ignore_case(self):
        instr = "SW R9,24(R0)"
        instr_bin = comp.compile_instruction(instr)
        assert_equal("10101100000010010000000000011000", instr_bin)

        instr = "LI R10, 100"
        instr_bin = comp.compile_instruction(instr)
        assert_equal("00100000000010100000000001100100", instr_bin)

        instr = "BLE R6,R10,12"
        instr_bin = comp.compile_instruction(instr)
        assert_equal("00011100110010100000000000001100", instr_bin)


class CompileInstructionBatchTest(unittest.TestCase):

    def setUp(self):
        self.instrs = ['addi R10,R0,100',
                       'sw R0,24(R0)',
                       'sw R0,28(R0)',
                       'lw R6,28(R0)',
                       'mul R7,R6,R6',
                       'lw R1,24(R0)',
                       'add R9,R1,R7',
                       'sw R9,24(R0)',
                       'addi R6,R6,1',
                       'sw R6,28(R0)',
                       'ble R6,R10,12']
        self.instrs_compiled = ['00100000000010100000000001100100',
                                '10101100000000000000000000011000',
                                '10101100000000000000000000011100',
                                '10001100000001100000000000011100',
                                '00000000110001100011100000011000',
                                '10001100000000010000000000011000',
                                '00000000001001110100100000100000',
                                '10101100000010010000000000011000',
                                '00100000110001100000000000000001',
                                '10101100000001100000000000011100',
                                '00011100110010100000000000001100']

    def test_compile_list_instructions(self):
        assert_equal(self.instrs_compiled, comp.compile_list(self.instrs))

    def test_compile_list_instructions_from_file(self):
        filepath = 'test.dat'
        f = open(filepath, 'w')
        f.writelines([x + '\n' for x in self.instrs])
        f.close()

        compiled = comp.compile_list_from_file(filepath)

        assert_equal(self.instrs_compiled, compiled)


class CompileInstructionBatchTestWithLoopLabels(unittest.TestCase):

    def setUp(self):
        self.instrs_compiled = ['00100000000010100000000001100100',
                                '10101100000000000000000000011000',
                                '10101100000000000000000000011100',
                                '10001100000001100000000000011100',
                                '00000000110001100011100000011000',
                                '10001100000000010000000000011000',
                                '00000000001001110100100000100000',
                                '10101100000010010000000000011000',
                                '00100000110001100000000000000001',
                                '10101100000001100000000000011100',
                                '00011100110010100000000000001100']

    def test_loop_label_converter(self):
        self.instrs = ['addi R10,R0,100',
                       'sw R0,24(R0)',
                       'sw R0,28(R0)',
                       'LOOP: lw R6,28(R0)',
                       'mul R7,R6,R6',
                       'lw R1,24(R0)',
                       'add R9,R1,R7',
                       'sw R9,24(R0)',
                       'addi R6,R6,1',
                       'sw R6,28(R0)',
                       'ble R6,R10,LOOP']
        self.instrs_no_label = ['addi R10,R0,100',
                                'sw R0,24(R0)',
                                'sw R0,28(R0)',
                                'lw R6,28(R0)',
                                'mul R7,R6,R6',
                                'lw R1,24(R0)',
                                'add R9,R1,R7',
                                'sw R9,24(R0)',
                                'addi R6,R6,1',
                                'sw R6,28(R0)',
                                'ble R6,R10,12']
        comp._convert_loop_labels(self.instrs)
        assert_equal(self.instrs_no_label, self.instrs)

    def test_compile_list_instructions_label_inline(self):
        self.instrs = ['addi R10,R0,100',
                       'sw R0,24(R0)',
                       'sw R0,28(R0)',
                       'LOOP: lw R6,28(R0)',
                       'mul R7,R6,R6',
                       'lw R1,24(R0)',
                       'add R9,R1,R7',
                       'sw R9,24(R0)',
                       'addi R6,R6,1',
                       'sw R6,28(R0)',
                       'ble R6,R10,LOOP']
        assert_equal(self.instrs_compiled, comp.compile_list(self.instrs))

    def test_compile_list_instructions_label_newline(self):
        self.instrs = ['addi R10,R0,100',
                       'sw R0,24(R0)',
                       'sw R0,28(R0)',
                       'LOOP:',
                       'lw R6,28(R0)',
                       'mul R7,R6,R6',
                       'lw R1,24(R0)',
                       'add R9,R1,R7',
                       'sw R9,24(R0)',
                       'addi R6,R6,1',
                       'sw R6,28(R0)',
                       'ble R6,R10,LOOP']
        assert_equal(self.instrs_compiled, comp.compile_list(self.instrs))

    def test_compile_list_instructions_multiple_labels(self):
        self.instrs = ['addi R10,R0,100',
                       'sw R0,24(R0)',
                       'sw R0,28(R0)',
                       'LOOP:',
                       'lw R6,28(R0)',
                       'mul R7,R6,R6',
                       'lw R1,24(R0)',
                       'LOOP2: add R9,R1,R7',
                       'sw R9,24(R0)',
                       'LOOP4:',
                       'addi R6,R6,1',
                       'sw R6,28(R0)',
                       'ble R6,R10,LOOP',
                       'ble R6,R10,LOOP2']
        self.instrs_compiled = ['00100000000010100000000001100100',
                                '10101100000000000000000000011000',
                                '10101100000000000000000000011100',
                                '10001100000001100000000000011100',
                                '00000000110001100011100000011000',
                                '10001100000000010000000000011000',
                                '00000000001001110100100000100000',
                                '10101100000010010000000000011000',
                                '00100000110001100000000000000001',
                                '10101100000001100000000000011100',
                                '00011100110010100000000000001100',
                                '00011100110010100000000000011000']
        assert_equal(self.instrs_compiled, comp.compile_list(self.instrs))


if __name__ == '__main__':
    unittest.main()
