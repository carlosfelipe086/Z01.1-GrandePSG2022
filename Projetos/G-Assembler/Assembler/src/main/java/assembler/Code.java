/**
 * Curso: Elementos de Sistemas
 * Arquivo: Code.java
 */

package assembler;

import java.util.Arrays;
import java.util.List;

/**
 * Traduz mnemônicos da linguagem assembly para códigos binários da arquitetura Z0.
 */
public class Code {

    /**
     * Retorna o código binário do(s) registrador(es) que vão receber o valor da instrução.
     * @param  mnemnonic vetor de mnemônicos "instrução" a ser analisada.
     * @return Opcode (String de 4 bits) com código em linguagem de máquina para a instrução.
     */
    public static String dest(String[] mnemnonic) {
        String opcode="0000";
        int len = mnemnonic.length;
        switch (mnemnonic[0]){
            case "movw":
                for (int i=2; i<len; i++){
                    switch (mnemnonic[i]){
                        case "%A":
                            opcode = opcode.substring(0,3) +'1';
                            break;
                        case "%D" :
                            opcode = opcode.substring(0,2) + '1' + opcode.substring(3);
                            break;
                        case "(%A)" :
                            opcode = opcode.substring(0,1) + '1' + opcode.substring(2);
                            break;
                    }
                }
                break;
            case "addw":
                for (int i=3; i<len; i++){
                    switch (mnemnonic[i]){
                        case "%A":
                            opcode= opcode.substring(0,3) + '1';
                            break;
                        case "%D":
                            opcode = opcode.substring(0,2) + '1' + opcode.substring(3);
                            break;
                        case "(%A)":
                            opcode = opcode.substring(0,1) + '1'+ opcode.substring(2);
                            break;}}
                break;



            case "incw":
            case "negw":
            case  "notw":
            case "decw":
                switch (mnemnonic[1]){
                    case "%A":
                        opcode = opcode.substring(0,3)+ '1';
                        break;
                    case "%D":
                        opcode = opcode.substring(0,2)+ '1'+ opcode.substring(3);
                        break;
                    case "(%A)":
                        opcode = opcode.substring(0,1)+ '1'+ opcode.substring(2);
                        break;}
                break;
            case "subw":
                for (int i=3; i<len; i++){
                    switch (mnemnonic[i]){
                        case "%A":
                            opcode = opcode.substring(0,3)+ '1';
                            break;
                        case "%D":
                            opcode = opcode.substring(0,2)+ '1'+ opcode.substring(3);
                            break;
                        case "(%A)":
                            opcode = opcode.substring(0,1)+ '1'+ opcode.substring(2);
                            break;}
                }
            case "rsubw":
                for (int i=3; i<len;i++){
                    switch (mnemnonic[i]){
                        case "%A":
                            opcode = opcode.substring(0,3)+ '1';
                            break;
                        case "%D":
                            opcode = opcode.substring(0,2)+ '1'+ opcode.substring(3);
                            break;
                        case "(%A)":
                            opcode = opcode.substring(0,1)+ '1'+ opcode.substring(2);
                            break;}}
                break;
            case "andw":
            case "orw":
                switch (mnemnonic[len-1]){
                    case "%A":
                        opcode = opcode.substring(0,3)+ '1';
                        break;
                    case "%D":
                        opcode = opcode.substring(0,2)+ '1'+ opcode.substring(3);
                        break;
                    case "(%A)":
                        opcode = opcode.substring(0,1)+ '1'+ opcode.substring(2);
                        break;}
                break;


                }
                return opcode;
                }




    /**
     * Retorna o código binário do mnemônico para realizar uma operação de cálculo.
     * @param  mnemnonic vetor de mnemônicos "instrução" a ser analisada.
     * @return Opcode (String de 7 bits) com código em linguagem de máquina para a instrução.
     */
    public static String comp(String[] mnemnonic) {
        /* TODO: implementar */
        switch (mnemnonic[0]){
            case "movw":
                switch (mnemnonic[1]) {
                    case "%A":
                        return "000110000";
                    case "%D":
                        return "000001100";
                    case "(%A)":
                        return "001110000";
                    case "$1":
                        return "000111111";
                    case "$0":
                        return "000101010";
                    case "$-1":
                        return "000111010";
                }

            case "addw" :
                switch (mnemnonic[1]){
                    case "$1":
                        switch (mnemnonic[2]){
                            case "%A":
                                return "000110111";
                            case "(%A)":
                                return "001110111";
                            case "%D":
                                return "000011111";
                        }
                    case "$-1":
                        switch (mnemnonic[2]){
                            case "%A":
                                return "000110010";
                            case "(%A)":
                                return "001110010";
                            case "%D":
                                return "000001110";
                        }
                    case "%A":
                        switch (mnemnonic[2]){
                            case "%D":
                                return "000000010";
                            case "$1":
                                return "000110111";
                            case "$-1":
                                return "000110010";
                        }
                    case "(%A)":
                        switch (mnemnonic[2]){
                            case "%D":
                                return "001000010";
                            case "$1":
                                return "001110111";
                            case "$-1":
                                return "001110010";
                        }
                    case "%D":
                        switch (mnemnonic[2]){
                            case "%A":
                                return "000000010";
                            case "(%A)":
                                return "001000010";
                            case "$1":
                                return "000011111";
                            case "$-1":
                                return "000001110";
                        }
                }

            case "incw":
                switch (mnemnonic[1]){
                    case "%A":
                        return "000110111";
                    case "%D":
                        return "000011111";
                    case "(%A)":
                        return "001110111";
                }

            case "subw":
                switch (mnemnonic[1]){
                    case "%A":
                        switch (mnemnonic[2]){
                            case "%D":
                                return "000000111";
                            case "$1":
                                return "000110010";
                            case "$-1":
                                return "000110111";
                        }
                    case "(%A)":
                        switch (mnemnonic[2]){
                            case "%D":
                                return "001000111";
                            case "$1":
                                return "001110010";
                            case "$-1":
                                return "001110111";
                        }
                    case "%D":
                        switch (mnemnonic[2]){
                            case "%A":
                                return "000010011";
                            case "(%A)":
                                return "001010011";
                            case "$1":
                                return "000001110";
                            case "$-1":
                                return "000011111";
                        }
                }

            case "decw":
                switch (mnemnonic[1]){
                    case "%A":
                        return "000110010";
                    case "(%A)":
                        return "001110010";
                    case "%D":
                        return "000001110";
                }
            case "rsubw":
                if (mnemnonic[1]=="%D" && mnemnonic[2]=="%A"){
                    return "000000111";
                } else if (mnemnonic[1]=="%A" && mnemnonic[2]=="%D"){
                    return "000010011";
                }else if (mnemnonic[1]=="%D" && mnemnonic[2]=="(%A)"){
                    return "001000111";
                }
                else if (mnemnonic[1]=="(%A)" && mnemnonic[2]=="%D"){
                    return "001010011";
                }
                else if (mnemnonic[1]=="$1" && mnemnonic[2]=="%D"){
                    return "000001110";
                }
                else if (mnemnonic[1]=="$1" && mnemnonic[2]=="%A"){
                    return "000110010";
                }
                else if (mnemnonic[1]=="$1" && mnemnonic[2]=="(%A)"){
                    return "001110010";
                }
            case "notw":
                switch (mnemnonic[1]){
                    case "%A":
                        return "000110001";
                    case "(%A)":
                        return "001110001";
                    case "%D":
                        return "000001101";
                }
            case "negw":
                switch (mnemnonic[1]){
                    case "%A":
                        return "000110011";
                    case "(%A)":
                        return "001110011";
                    case "%D":
                        return "000001111";

                }

            case "andw":
                if (mnemnonic[1]=="(%A)"){
                    return "001000000";

                }else if (mnemnonic[1]!="(%A)"){
                    return "000000000";}

            case "orw":
                if ((mnemnonic[1]=="%A" && mnemnonic[2]=="%D") || (mnemnonic[1]=="%D" && mnemnonic[2]== "%A")){
                    return "000010101";
                }else if ((mnemnonic[1]=="(%A)" && mnemnonic[2]=="%D") || (mnemnonic[1]=="%D" && mnemnonic[2]== "(%A)")){
                    return "001010101";
                }

            case "nop":
                return "000000000";
            case "jmp":
            case "je":
            case "jne":
            case "jg":
            case "jge":
            case "jl":
            case "jle":
                return "000001100";}

        return "000001100";
        }

    	

    /**
     * Retorna o código binário do mnemônico para realizar uma operação de jump (salto).
     * @param  mnemnonic vetor de mnemônicos "instrução" a ser analisada.
     * @return Opcode (String de 3 bits) com código em linguagem de máquina para a instrução.
     */
    public static String jump(String[] mnemnonic) {
        switch (mnemnonic[0]){
            case "jmp"  : return "111";
            case "jge"  : return "011";
            case "je" :   return "010";
            case "jne" : return "101";
            case "jg" : return "001";

            case "jl" : return "100";
            case "jle" : return "110";

            default    : return "000";
        }


    }

    /**
     * Retorna o código binário de um valor decimal armazenado numa String.
     * @param  symbol valor numérico decimal armazenado em uma String.
     * @return Valor em binário (String de 15 bits) representado com 0s e 1s.
     */
    public static String toBinary(String symbol) {
        /* TODO: implementar */
        int dec = Integer.parseInt(symbol);
        String bin = Integer.toBinaryString(dec);
        String binario = "";
        for (int i=0; i<16-bin.length();i++){
            binario += "0";
        }
        binario += bin;
    	return binario;
    }

}
