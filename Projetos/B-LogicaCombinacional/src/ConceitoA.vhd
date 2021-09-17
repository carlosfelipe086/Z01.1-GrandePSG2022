----------------------------
-- Bibliotecas ieee       --
----------------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use work.all;

----------------------------
-- Entrada e saidas do bloco
----------------------------
entity ConceitoA is
	port(
		CLOCK_50 : in  std_logic;
		SW       : in  std_logic_vector(9 downto 0);
        HEX0     : out std_logic_vector(6 downto 0); -- 7seg0
        HEX1     : out std_logic_vector(6 downto 0); -- 7seg0
        HEX2     : out std_logic_vector(6 downto 0); -- 7seg0
		LEDR     : out std_logic_vector(9 downto 0)
	);
end entity;



----------------------------
-- Implementacao do bloco --
----------------------------
architecture rtl of ConceitoA is
component sevenSeg is
	port(
		bcd : in  STD_LOGIC_VECTOR(3 downto 0);
		leds: out STD_LOGIC_VECTOR(6 downto 0)
		);

end component;

component binarioToBcd is
	port(

		clk: in std_logic;
		reset: in std_logic;
        binary_in : in  std_logic_vector(9 downto 0);
        bcd0 : out  std_logic_vector(3 downto 0);
        bcd1 : out  std_logic_vector(3 downto 0);
        bcd2 : out  std_logic_vector(3 downto 0);
        bcd3 : out  std_logic_vector(3 downto 0);
		bcd4 : out  std_logic_vector(3 downto 0)
		
		);

end component;

--------------	
-- signals
--------------


	signal carry: std_logic_vector(11 downto 0);


---------------
-- implementacao
---------------
begin
		
	binarioBcd0 : binarioToBcd
	port map 
	(		
		clk => CLOCK_50,
		reset => '0',
        binary_in => SW,
        bcd0 => carry(3 downto 0),
        bcd1 => carry(7 downto 4),
		bcd2 => carry(11 downto 8),
		bcd3 => OPEN,
		bcd4 => OPEN
	);

	
	seven0 : sevenSeg
	port map 
	(
			bcd => carry(3 downto 0),
			leds => HEX0
	);

	seven1 : sevenSeg
	port map 
	(
			bcd => carry(7 downto 4),
			leds => HEX1
	);

	seven2 : sevenSeg
	port map 
	(
			bcd => carry(11 downto 8),
			leds => HEX2
	);


end rtl;