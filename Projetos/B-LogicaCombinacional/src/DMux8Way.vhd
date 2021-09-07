library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity DMux8Way is
	port ( 
			a:   in  STD_LOGIC;
			sel: in  STD_LOGIC_VECTOR(2 downto 0);
			q0:  out STD_LOGIC;
			q1:  out STD_LOGIC;
			q2:  out STD_LOGIC;
			q3:  out STD_LOGIC;
			q4:  out STD_LOGIC;
			q5:  out STD_LOGIC;
			q6:  out STD_LOGIC;
			q7:  out STD_LOGIC);
end entity;

architecture arch of DMux8Way is
begin
	process (a,sel)
		begin
			q0 <= '0';
			q1 <= '0';
			q2 <= '0';
			q3 <= '0';
			q4 <= '0';
			q5 <= '0';
			q6 <= '0';
			q7 <= '0';
			case sel is
	
				when "000" => q0 <= a;
				when "001" => q1 <= a;	
				when "010" => q2 <= a;
				when "011" => q3 <= a;
				when "100" => q4 <= a;
				when "101" => q5 <= a;
				when "110" => q6 <= a;
				when others => q7 <= a;
	
			end case ;
	end process;



end architecture;
