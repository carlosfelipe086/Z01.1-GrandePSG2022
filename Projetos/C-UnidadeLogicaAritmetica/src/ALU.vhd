-- Elementos de Sistemas
-- by Luciano Soares
-- ALU.vhd

-- Unidade Lógica Aritmética (ULA)
-- Recebe dois valores de 16bits e
-- calcula uma das seguintes funções:
-- X+y, x-y, y-x, 0, 1, -1, x, y, -x, -y,
-- X+1, y+1, x-1, y-1, x&y, x|y
-- De acordo com os 6 bits de entrada denotados:
-- zx, nx, zy, ny, f, no.
-- Também calcula duas saídas de 1 bit:
-- Se a saída == 0, zr é definida como 1, senão 0;
-- Se a saída <0, ng é definida como 1, senão 0.
-- a ULA opera sobre os valores, da seguinte forma:
-- se (zx == 1) então x = 0
-- se (nx == 1) então x =! X
-- se (zy == 1) então y = 0
-- se (ny == 1) então y =! Y
-- se (f == 1) então saída = x + y
-- se (f == 0) então saída = x & y
-- se (no == 1) então saída = !saída
-- se (out == 0) então zr = 1
-- se (out <0) então ng = 1

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity ALU is
    port (
            x,y:   in STD_LOGIC_VECTOR(15 downto 0);  -- entradas de dados da ALU
            zx:    in STD_LOGIC;                      -- zera a entrada x
            nx:    in STD_LOGIC;                      -- inverte a entrada x
            zy:    in STD_LOGIC;                      -- zera a entrada y
            ny:    in STD_LOGIC;                      -- inverte a entrada y
            f:     in STD_LOGIC_VECTOR(1 downto 0);   -- se 0 calcula x & y, se 1 x + y, se 10 x xor y
            s:     in STD_LOGIC_VECTOR(1 downto 0);   -- se realiza ou não operações shift (00 - não, 01 a direita, 10 a esquerda)
            no:    in STD_LOGIC;                      -- inverte o valor da saída
            zr:    out STD_LOGIC;                     -- setado se saída igual a zero
            ng:    out STD_LOGIC;                     -- setado se saída é negativa
            saida: out STD_LOGIC_VECTOR(15 downto 0); -- saída de dados da ALU
            estouro: out STD_LOGIC                   -- estouro da soma
    );
end entity;

architecture  rtl OF alu is
  -- Aqui declaramos sinais (fios auxiliares)
  -- e componentes (outros módulos) que serao
  -- utilizados nesse modulo.

    component zerador16 IS
        port(
             z   :  in STD_LOGIC;
             a   :  in STD_LOGIC_VECTOR(15 downto 0);
             y   :  out STD_LOGIC_VECTOR(15 downto 0)
        );
    end component;

    component inversor16 is
        port(
             z   :  in STD_LOGIC;
             a   :  in STD_LOGIC_VECTOR(15 downto 0);
             y   :  out STD_LOGIC_VECTOR(15 downto 0)
        );
    end component;
    
    component Add16 is
        port(
             a       :  in STD_LOGIC_VECTOR(15 downto 0);
             b       :  in STD_LOGIC_VECTOR(15 downto 0);
             q       :  out STD_LOGIC_VECTOR(15 downto 0);
             e       : out STD_LOGIC
        );
    end component;

    component And16 is
        port(
             a   :  in  STD_LOGIC_VECTOR(15 downto 0);
             b   :  in  STD_LOGIC_VECTOR(15 downto 0);
             q   :  out STD_LOGIC_VECTOR(15 downto 0)
        );
    end component;

    component XOR16 is
        port(
            a   :  in  STD_LOGIC_VECTOR(15 downto 0);
            b   :  in  STD_LOGIC_VECTOR(15 downto 0);
            q   :  out STD_LOGIC_VECTOR(15 downto 0)
        );
    end component;

    component comparador16 is
        port(
             a   :  in STD_LOGIC_VECTOR(15 downto 0);
             zr  :  out STD_LOGIC;
             ng  :  out STD_LOGIC
        );
    end component;

    component Mux16_2 is
        port (
             a   :  in  STD_LOGIC_VECTOR(15 downto 0);
             b   :  in  STD_LOGIC_VECTOR(15 downto 0);
             c   :  in  STD_LOGIC_VECTOR(15 downto 0);
             sel :  in  STD_LOGIC_VECTOR(1 downto 0);
             q   :  out STD_LOGIC_VECTOR(15 downto 0)
        );
    end component;

    component Shift16 is
        port (
             a:   in  STD_LOGIC_VECTOR(15 downto 0);
			 sel: in  STD_LOGIC_VECTOR(1 downto 0);
			 q:   out STD_LOGIC_VECTOR(15 downto 0)
        );
    end component;

   SIGNAL zxout,zyout,nxout,nyout,andout,adderout,xorout,muxout,preshift, precomp: std_logic_vector(15 downto 0);
   SIGNAL e: std_logic;

begin

  -- Implementação vem aqui!
    zeradorX     :  zerador16    PORT MAP (zx, x, zxout); 
    negadorX     :  inversor16   PORT MAP (nx, zxout, nxout); 
    zeradorY     :  zerador16    PORT MAP (zy, y, zyout); 
    negadorY     :  inversor16   PORT MAP (ny, zyout, nyout); 
    addXY        :  Add16        PORT MAP (nxout, nyout, adderout,e);
    andXY        :  And16        PORT MAP (nxout, nyout, andout); 
    xorXY        :  XOR16        PORT MAP (nxout, nyout, xorout);
    selectAddAnd :  Mux16_2      PORT MAP (andout, adderout,xorout, f, muxout); 
    negadorMux   :  inversor16   PORT MAP (no, muxout, preshift); 
    shift        :  Shift16       PORT MAP (preshift, s, precomp);
    comparador   :  comparador16 PORT MAP (precomp, zr, ng);

    saida       <=  precomp;
    estouro     <= e;

end architecture;