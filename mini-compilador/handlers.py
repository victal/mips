# -*- coding: utf-8 -*-


class _RType(object):
    """
    Handler generico para uma instrucao R type,
    como a instrucao add ou a mul.
    """
    def __init__(self, funct=None, prefix='000000', shamt='00000'):
        """
        Todas as RType sao parecidas, a menos do valor de funct
        """
        self.prefix = prefix
        self.funct = funct
        self.shamt = shamt

    def convert(self, instr_args):
        """
        Recebe uma lista de argumentos da funcao e retorna
        a instrucao em formato binario
        Exemplo:
            Recebe:  ['R9','R1','R7']
            Retorna: prefix + '00001' + '00111' + '01001' + shamt + funct
        """
        instr_args = [int(x.lstrip('R')) for x in instr_args]
        args_bin = [bin(x)[2:].zfill(5) for x in instr_args]

        instr_bin = self.prefix + args_bin[1] + args_bin[2] + args_bin[0]  \
            + self.shamt + self.funct

        return instr_bin


class _IType(object):
    """
    Handler generico para uma instrucao I type,
    como a instrucao addi ou a ble

    """
    def __init__(self, funct=None, prefix='', reverse_regs=False):
        """
        Algumas IType preferem colocar rt na frente de rs (no formato bin).
        Nestes casos, passe rt_before_rs como True na construcao

        """
        self.prefix = prefix
        self.reverse_regs = reverse_regs

    def convert(self, instr_args):
        """
        Recebe uma lista de argumentos da funcao e retorna
        a instrucao em formato binario
        Exemplo:
            Recebe:  ['R10','R0','100']
            Retorna: prefix + '00000' + '01010' + '0000000001100100'
        """
        regs_args = [int(x.lstrip('R')) for x in instr_args[:-1]]
        regs_bin = [bin(x)[2:].zfill(5) for x in regs_args]

        try:
            imm = int(instr_args[-1])
            imm_bin = bin(imm)[2:].zfill(16)
        except ValueError:  # it is a lw/sw    Ex.: lw R1, 24(R0)
            imm = int(instr_args[-1].split('(')[0].strip())
            imm_bin = bin(imm)[2:].zfill(16)

            reg_arg = instr_args[-1].split('(')[1].split(')')[0].strip()
            reg_arg = int(reg_arg.lstrip('R'))
            regs_bin += [bin(reg_arg)[2:].zfill(5)]

        if self.reverse_regs:
            regs_bin = [regs_bin[1], regs_bin[0]]  # troca a ordem

        instr_bin = self.prefix + regs_bin[0] + regs_bin[1] + imm_bin

        return instr_bin


def factory_rtype(type_string):
    factory_args = {
        'add': {'funct': '100000'},
        'mul': {'funct': '011000'},
    }
    kwargs = factory_args[type_string]
    return _RType(**kwargs)


def factory_itype(type_string):
    factory_args = {
        'addi': {'prefix': '001000', 'reverse_regs': True},
        'ble': {'prefix': '000111'},
        'lw': {'prefix': '100011', 'reverse_regs': True},
        'sw': {'prefix': "101011", 'reverse_regs': True},
    }
    kwargs = factory_args[type_string]
    return _IType(**kwargs)


def add(instr_args):
    r_type = factory_rtype('add')
    return r_type.convert(instr_args)


def mul(instr_args):
    r_type = factory_rtype('mul')
    return r_type.convert(instr_args)


def nop(instr_args):
    return '00000000000000000000000000000000'


def addi(instr_args):
    i_type = factory_itype('addi')
    return i_type.convert(instr_args)


def li(instr_args):
    i_type = factory_itype('addi')
    instr_args = [instr_args[0], 'R0', instr_args[1]]
    return i_type.convert(instr_args)


def ble(instr_args):
    i_type = factory_itype('ble')
    return i_type.convert(instr_args)


def lw(instr_args):
    i_type = factory_itype('lw')
    return i_type.convert(instr_args)


def sw(instr_args):
    i_type = factory_itype('sw')
    return i_type.convert(instr_args)
