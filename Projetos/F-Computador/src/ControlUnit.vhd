-- Elementos de Sistemas
-- developed by Luciano Soares
-- file: ControlUnit.vhd
-- date: 4/4/2017
-- Modificação:
--   - Rafael Corsi : nova versão: adicionado reg S
--
-- Unidade que controla os componentes da CPU

library ieee;
use ieee.std_logic_1164.all;

entity ControlUnit is
    port(
		instruction                 : in STD_LOGIC_VECTOR(17 downto 0);  -- instrução para executar
		zr,ng                       : in STD_LOGIC;                      -- valores zr(se zero) e
                                                                     -- ng (se negativo) da ALU
		muxALUI_A                   : out STD_LOGIC;                     -- mux que seleciona entre
                                                                     -- instrução  e ALU para reg. A
		muxAM                       : out STD_LOGIC;                     -- mux que seleciona entre
                                                                     -- reg. A e Mem. RAM para ALU
                                                                     -- A  e Mem. RAM para ALU
		zx, nx, zy, ny, f, no       : out STD_LOGIC;                     -- sinais de controle da ALU
		loadA, loadD, loadM, loadPC : out STD_LOGIC                      -- sinais de load do reg. A,
                                                                     -- reg. D, Mem. RAM e Program Counter
    );
end entity;

architecture arch of ControlUnit is
  

begin

  loadD <= instruction(17) and instruction(4);

  loadM <= instruction(17) and instruction(5);

  loadA <= (instruction(17) and instruction(3)) or not instruction(17);

  loadPC <= instruction(17) and
            ((instruction(0) and (not zr and not ng)) or --j0(>0) and (not 0 and not negativo)
             (instruction(1) and zr)                  or --j1(=0) and (0)
             (instruction(2) and (not zr and ng))     or --j2(<0) and (not 0 and negativo)

             (instruction(0) and instruction(1) and instruction(2))); --jmp

  --muxALUI_A, se 1 registra A
  muxALUI_A <= not instruction(17);
  --Mux AM = r0
  muxAM <= instruction(13);

  --Entradas da ULA
  zx <= instruction(17) and instruction(12);
  nx <= instruction(17) and instruction(11);
  zy <= instruction(17) and instruction(10);
  ny <= instruction(17) and instruction(9);
  f  <= instruction(17) and instruction(8);
  no <= instruction(17) and instruction(7);

end architecture;
