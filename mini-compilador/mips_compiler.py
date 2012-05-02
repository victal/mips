"""
See README
"""
# -*- coding: utf-8 -*-
import handlers
import sys


def _decode_instr(instr):
    """
    Recebe uma instrucao em formato mnemonico e decodifica ela
    em instruction type E instruction arguments (se existirem)
    Ex.: Recebe ADD R1, R0, R10
         Retorna ('ADD', ['R1', 'R0', 'R10'])

    """
    instr = instr.strip()

    instr_t = instr.split()[0]

    instr = ''.join(instr.split()[1:]).strip()
    instr_args = []
    if instr:
        instr_args = [x.strip() for x in instr.split(',')]

    return (instr_t, instr_args)


def _fetch_handler(instr_t):
    """
    Recebe um isntruction type e retorna o callback para
    a funcao apropriada para tratar esta instrucao
    """
    instr_t = instr_t.lower()

    INSTRUCTION_HANDLER = {
        'nop': handlers.nop,
        'add': handlers.add,
        'mul': handlers.mul,

        'beq': handlers.beq,
        'addi': handlers.addi,
        'li': handlers.li,
        'ble': handlers.ble,
        'lw': handlers.lw,
        'sw': handlers.sw,
    }

    return INSTRUCTION_HANDLER.get(instr_t, None)


def compile_instruction(instr):
    """
    Recebe uma instrucao ``instr`` em formato mnemonico
    e converte ela para formato binario (32 bits)

    """
    instr_t, instr_args = _decode_instr(instr)

    handler = _fetch_handler(instr_t)

    return handler(instr_args)


def compile_list(instr_list):
    """
    Receives a list of instruction in mnemonic format
    and convert them to binary format (32bit)

    """
    compiled = [compile_instruction(x) for x in instr_list]
    return compiled


def compile_list_from_file(filepath):
    f = open(filepath)
    instr_list = f.readlines()
    compiled = compile_list(instr_list)
    f.close()
    return compiled


if __name__ == '__main__':
    argv = sys.argv[1:]
    argc = len(argv)

    if argc < 1:  # Uso incorreto do programa
        print 'Uso:  python %s %s %s' % (__file__,
                                         'Entrada',
                                         'Saida[opcional]')
        sys.exit()

    filepath_in = argv[0]  # arquivo de entrada
    filepath_out = 'out.dat'  # arquivo de saida
    if argc > 1:
        filepath_out = argv[1]

    compiled = [x + '\n' for x in compile_list_from_file(filepath_in)]

    #escreve para o arquivo de saida
    f = open(filepath_out, 'w')
    f.writelines(compiled)
    f.close()
