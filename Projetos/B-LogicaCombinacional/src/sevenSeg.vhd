library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity sevenSeg is
	port (
			bcd : in  STD_LOGIC_VECTOR(3 downto 0);
			leds: out STD_LOGIC_VECTOR(6 downto 0));
end entity;

architecture arch of sevenSeg is
begin
	leds <= "0000001" when (bcd="0000") else -- 0
			"1001111" when (bcd="0001") else -- 1
			"0010010" when (bcd="0010") else -- 2
			"0000110" when (bcd="0011") else -- 3
			"1001100" when (bcd="0100") else -- 4
			"0100100" when (bcd="0101") else -- 5
			"0100000" when (bcd="0110") else -- 6
			"0001111" when (bcd="0111") else -- 7
			"0000000" when (bcd="1000") else -- 8 
			"0000100" when (bcd="1001") else -- 9
			"0001000" when (bcd="1010") else -- A
			"1100000" when (bcd="1011") else -- B 
			"0110001" when (bcd="1100") else -- C 
			"1000010" when (bcd="1101") else -- D 
			"0110000" when (bcd="1110") else -- E
			"0111000" when (bcd="1111") else -- F
			"1111111";

end architecture;
