/**
 * Curso: Elementos de Sistemas
 * Arquivo: Code.java
 * Created by Luciano Soares <lpsoares@insper.edu.br>
 * Date: 2/05/2017
 * Adaptado por Rafael Corsi <rafael.corsi@insper.edu.br>
 * Date: 5/2018
 */

package vmtranslator;

import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * Traduz da linguagem vm para códigos assembly.
 */
public class Code {

    PrintWriter outputFile = null;  // arquivo .nasm de saída
    String filename = null;         // arquivo .vm de entrada
    int lineCode = 0;               // Linha do codigo vm que gerou as instrucoes
    int cont = 0;
    /**
     * Abre o arquivo de saida e prepara para escrever
     * @param filename nome do arquivo NASM que receberá o código traduzido.
     */
    public Code(String filename) throws FileNotFoundException,IOException {
        File file = new File(filename);
        this.outputFile = new PrintWriter(new FileWriter(file));
    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para executar o comando aritmético.
     * @param  command comando aritmético a ser analisado.
     */
    public void writeArithmetic(String command) {

        if ( command.equals("")) {
            Error.error("Instrução invalida");
        }

        List<String> commands = new ArrayList<String>();

        if(command.equals("add")) {
            commands.add(String.format("; %d - ADD", lineCode++));
            commands.add("leaw $0, %A");
            commands.add("movw (%A), %A");
            commands.add("decw %A");
            commands.add("movw (%A), %D");
            commands.add("decw %A");
            commands.add("addw (%A), %D, %D");
            commands.add("movw %D, (%A)");
            commands.add("addw $1, %A, %D");
            commands.add("leaw $0, %A");
            commands.add("movw %D, (%A)");

        } else if (command.equals("sub")) {
            commands.add(String.format("; %d - SUB", lineCode++));
            commands.add("leaw $0, %A");
            commands.add("movw (%A), %A");
            commands.add("decw %A");
            commands.add("movw (%A), %D");
            commands.add("decw %A");
            commands.add("subw (%A), %D, %D");
            commands.add("movw %D, (%A)");
            commands.add("addw $1, %A, %D");
            commands.add("leaw $0, %A");
            commands.add("movw %D, (%A)");

        } else if (command.equals("neg")) {
            commands.add(String.format("; %d - NEG", lineCode++));
            commands.add("leaw $0, %A");
            commands.add("movw (%A), %A");
            commands.add("decw %A");
            commands.add("movw (%A), %D");
            commands.add("negw %D");
            commands.add("movw %D, (%A)");

        } else if (command.equals("eq")) {
            commands.add(String.format("; %d - EQ", lineCode++));
            commands.add("leaw $SP, %A");
            commands.add("movw (%A), %A"); // movo o que está em SP pra A
            commands.add("decw %A"); // decresci uma linha
            commands.add("movw (%A), %D"); //move o que está no SP-1 para D
            commands.add("decw %A"); // decresci outra linha
            commands.add("movw (%A), %A"); // move o que está no SP-2 para A
            commands.add("subw %A, %D, %D"); //subtrai para ver se dá 0

            commands.add("leaw $TRUE, %A"); //
            commands.add("je %D");
            commands.add("nop");
            commands.add("leaw $SP, %A");
            commands.add("movw (%A), %A");
            commands.add("decw %A");
            commands.add("decw %A");
            commands.add("movw $0, (%A)"); // retorna false
            commands.add("incw %A");
            commands.add("movw %A, %D");
            commands.add("leaw $SP, %A");
            commands.add("movw %D, (%A)"); // mover o valor do noso SP para ele
            commands.add("leaw $END, %A");
            commands.add("jmp");
            commands.add("nop");

            commands.add("TRUE:");
            commands.add("leaw $SP, %A");
            commands.add("movw (%A), %A");
            commands.add("decw %A");
            commands.add("decw %A");
            commands.add("movw $-1, (%A)"); // retorna false
            commands.add("incw %A");
            commands.add("movw %A, %D");
            commands.add("leaw $SP, %A");
            commands.add("movw %D, (%A)"); // mover o valor do noso SP para ele
            commands.add("leaw $END, %A");
            commands.add("jmp");
            commands.add("nop");

            commands.add("END:");


        } else if (command.equals("gt")) {
            commands.add(String.format("; %d - GT", lineCode++));
            commands.add("leaw $SP, %A");
            commands.add("movw (%A), %A"); // movo o que está em SP pra A
            commands.add("decw %A"); // decresci uma linha
            commands.add("movw (%A), %D"); //move o que está no SP-1 para D
            commands.add("decw %A"); // decresci outra linha
            commands.add("movw (%A), %A"); // move o que está no SP-2 para A
            commands.add("subw %A, %D, %D"); //subtrai para ver se dá 0

            commands.add("leaw $TRUE, %A"); //
            commands.add("jg %D");
            commands.add("nop");
            commands.add("leaw $SP, %A");
            commands.add("movw (%A), %A");
            commands.add("decw %A");
            commands.add("decw %A");
            commands.add("movw $0, (%A)"); // retorna false
            commands.add("incw %A");
            commands.add("movw %A, %D");
            commands.add("leaw $SP, %A");
            commands.add("movw %D, (%A)"); // mover o valor do noso SP para ele
            commands.add("leaw $END, %A");
            commands.add("jmp");
            commands.add("nop");

            commands.add("TRUE:");
            commands.add("leaw $SP, %A");
            commands.add("movw (%A), %A");
            commands.add("decw %A");
            commands.add("decw %A");
            commands.add("movw $-1, (%A)"); // retorna false
            commands.add("incw %A");
            commands.add("movw %A, %D");
            commands.add("leaw $SP, %A");
            commands.add("movw %D, (%A)"); // mover o valor do noso SP para ele
            commands.add("leaw $END, %A");
            commands.add("jmp");
            commands.add("nop");

            commands.add("END:");

        } else if (command.equals("lt")) {
            commands.add(String.format("; %d - LT", lineCode++));
            commands.add("leaw $SP, %A");
            commands.add("movw (%A), %A"); // movo o que está em SP pra A
            commands.add("decw %A"); // decresci uma linha
            commands.add("movw (%A), %D"); //move o que está no SP-1 para D
            commands.add("decw %A"); // decresci outra linha
            commands.add("movw (%A), %A"); // move o que está no SP-2 para A
            commands.add("subw %A, %D, %D"); //subtrai para ver se dá 0

            commands.add("leaw $TRUE, %A"); //
            commands.add("jl %D");
            commands.add("nop");
            commands.add("leaw $SP, %A");
            commands.add("movw (%A), %A");
            commands.add("decw %A");
            commands.add("decw %A");
            commands.add("movw $0, (%A)"); // retorna false
            commands.add("incw %A");
            commands.add("movw %A, %D");
            commands.add("leaw $SP, %A");
            commands.add("movw %D, (%A)"); // mover o valor do noso SP para ele
            commands.add("leaw $END, %A");
            commands.add("jmp");
            commands.add("nop");

            commands.add("TRUE:");
            commands.add("leaw $SP, %A");
            commands.add("movw (%A), %A");
            commands.add("decw %A");
            commands.add("decw %A");
            commands.add("movw $-1, (%A)"); // retorna false
            commands.add("incw %A");
            commands.add("movw %A, %D");
            commands.add("leaw $SP, %A");
            commands.add("movw %D, (%A)"); // mover o valor do noso SP para ele
            commands.add("leaw $END, %A");
            commands.add("jmp");
            commands.add("nop");

            commands.add("END:");

        } else if (command.equals("and")) {
            commands.add(String.format("; %d - AND", lineCode++));
            commands.add("leaw $SP, %A");
            commands.add("movw (%A), %A"); // em A o valor da linha do SP
            commands.add("decw %A"); // decresci uma linha
            commands.add("movw (%A), %D"); // peguei o que estava em SP-1 e foi pra D
            commands.add("decw %A"); // decresci mais uma linha
            commands.add("andw (%A), %D, %D");
            commands.add("movw %D, (%A)"); // movi o valor do and para onde a SP-2 estava
            commands.add("incw %A"); // incrementei uma linha do SP-2, para que agora no SP-1 seja meu novo SP
            commands.add("movw %A, %D"); // movi o valor do novo SP para D
            commands.add("leaw $SP, %A");
            commands.add("movw %D, (%A)"); // salvei esse novo valor no SP

        } else if (command.equals("or")) {
            commands.add(String.format("; %d - OR", lineCode++));
            commands.add("leaw $SP, %A");
            commands.add("movw (%A), %A");
            commands.add("decw %A");
            commands.add("movw (%A), %D");
            commands.add("decw %A");
            //commands.add("movw (%A), %A");
            commands.add("orw (%A), %D, %D");
            commands.add("movw %D, (%A)");
            commands.add("incw %A");
            commands.add("movw %A, %D");
            commands.add("leaw $SP, %A");
            commands.add("movw %D, (%A)");

        } else if (command.equals("not")) {
            commands.add("leaw $SP, %A");
            commands.add("movw (%A), %A"); //linha do SP em A
            commands.add("decw %A"); // SP-1
            commands.add("movw (%A), %D");
            commands.add("notw %D");
            commands.add("leaw $SP, %A");
            commands.add("movw %D, (%A)");

        }

        String[] stringArray = new String[ commands.size() ];
        commands.toArray( stringArray );
        write(stringArray);

    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para executar o comando de Push ou Pop.
     * @param  command comando de push ou pop a ser analisado.
     * @param  segment segmento de memória a ser usado pelo comando.
     * @param  index índice do segkento de memória a ser usado pelo comando.
     */
    public void writePushPop(Parser.CommandType command, String segment, Integer index) {

        if ( command.equals("")) {
            Error.error("Instrução invalida");
        }

        List<String> commands = new ArrayList<String>();

        if(command == Parser.CommandType.C_POP) {
            commands.add(String.format("; %d - POP %s %d", lineCode++ ,segment, index));

            if (segment.equals("constant")) {
                Error.error("Não faz sentido POP com constant");

            } else if (segment.equals("local")) {
                commands.add("leaw $SP, %A");                           //Carrega SP
                commands.add("movw (%A), %D");                          //Coloca o ponteiro em D
                commands.add("decw %D");                                //Decresce o ponteiro
                commands.add("movw %D, (%A)");                          //Atualiza SP
                commands.add("leaw $" + index.toString() + ",%A");      //Carrega o index do POP
                commands.add("movw %A, %D");                            //Move o index para D

                commands.add("leaw $LCL, %A");      //Carrega LOCAL
                commands.add("movw (%A), %A");      //Move o valor da ramLOCAL para A
                commands.add("addw %A, %D, %D");    //Soma o index com o local e salva o local de salvamento em %D

                commands.add("leaw $R5, %A");       //Carrega TEMP0 em A
                commands.add("movw %D, (%A)");      //Salva o local de acesso em temp 0

                commands.add("leaw $SP, %A");       //Carrega SP
                commands.add("movw (%A), %A");      //Coloca o ponteiro em A
                commands.add("movw (%A), %D");      //Move o valor da RAM do ponteiro em D
                commands.add("leaw $R5, %A");       //Carrega TEMP0
                commands.add("movw (%A), %A");      //Coloca o local de salvamento em %A
                commands.add("movw %D, (%A)");      //Salva %D em (%A)

            } else if (segment.equals("argument")) {

                commands.add("leaw $SP, %A");                           //Carrega SP
                commands.add("movw (%A), %D");                          //Coloca o ponteiro em D
                commands.add("decw %D");                                //Decresce o ponteiro
                commands.add("movw %D, (%A)");                          //Atualiza SP
                commands.add("leaw $" + index.toString() + ",%A");      //Carrega o index do POP
                commands.add("movw %A, %D");                            //Move o index para D

                commands.add("leaw $ARG, %A");      //Carrega ARG
                commands.add("movw (%A), %A");      //Move o valor da ramARG para A
                commands.add("addw %A, %D, %D");    //Soma o index com o local e salva o local de salvamento em %D

                commands.add("leaw $R5, %A");       //Carrega TEMP0 em A
                commands.add("movw %D, (%A)");      //Salva o local de acesso em temp 0

                commands.add("leaw $SP, %A");       //Carrega SP
                commands.add("movw (%A), %A");      //Coloca o ponteiro em A
                commands.add("movw (%A), %D");      //Move o valor da RAM do ponteiro em D
                commands.add("leaw $R5, %A");       //Carrega TEMP0
                commands.add("movw (%A), %A");      //Coloca o local de salvamento em %A
                commands.add("movw %D, (%A)");      //Salva %D em (%A)

            } else if (segment.equals("this")) {

                commands.add("leaw $SP, %A");                           //Carrega SP
                commands.add("movw (%A), %D");                          //Coloca o ponteiro em D
                commands.add("decw %D");                                //Decresce o ponteiro
                commands.add("movw %D, (%A)");                          //Atualiza SP
                commands.add("leaw $" + index.toString() + ",%A");      //Carrega o index do POP
                commands.add("movw %A, %D");                            //Move o index para D

                commands.add("leaw $THIS, %A");     //Carrega THIS
                commands.add("movw (%A), %A");      //Move o valor da ramTHIS para A
                commands.add("addw %A, %D, %D");    //Soma o index com o local e salva o local de salvamento em %D

                commands.add("leaw $R5, %A");       //Carrega TEMP0 em A
                commands.add("movw %D, (%A)");      //Salva o local de acesso em temp 0

                commands.add("leaw $SP, %A");       //Carrega SP
                commands.add("movw (%A), %A");      //Coloca o ponteiro em A
                commands.add("movw (%A), %D");      //Move o valor da RAM do ponteiro em D
                commands.add("leaw $R5, %A");       //Carrega TEMP0
                commands.add("movw (%A), %A");      //Coloca o local de salvamento em %A
                commands.add("movw %D, (%A)");      //Salva %D em (%A)

            } else if (segment.equals("that")) {

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw (%A), %D");                      //Coloca o ponteiro em D
                commands.add("decw %D");                            //Decresce o ponteiro
                commands.add("movw %D, (%A)");                      //Atualiza o SP
                commands.add("leaw $" + index.toString() + ",%A");  //Carrega o index do POP
                commands.add("movw %A, %D");                        //Move o index para D

                commands.add("leaw $THAT, %A");     //Carrega THAT
                commands.add("movw (%A), %A");      //Move o valor da ramTHAT para A
                commands.add("addw %A, %D, %D");    //Soma o index com o local e salva o local de salvamento em %D

                commands.add("leaw $R5, %A");       //Carrega TEMP0 em A
                commands.add("movw %D, (%A)");      //Salva o local de acesso em temp 0

                commands.add("leaw $SP, %A");       //Carrega SP
                commands.add("movw (%A), %A");      //Coloca o ponteiro em A
                commands.add("movw (%A), %D");      //Move o valor da RAM do ponteiro em D
                commands.add("leaw $R5, %A");       //Carrega TEMP0
                commands.add("movw (%A), %A");      //Coloca o local de salvamento em %A
                commands.add("movw %D, (%A)");      //Salva %D em (%A)

            } else if (segment.equals("static")) {

            } else if (segment.equals("temp")) {

                Integer popRam = index + 5;     //Endereco do temp que sera salvo

                commands.add("leaw $SP,%A");  //Carrega SP
                commands.add("movw (%A),%D"); //Coloca o ponteiro em D
                commands.add("decw %D");      //Decresce o ponteiro
                commands.add("movw %D,(%A)"); //Atualiza o SP
                commands.add("movw (%A),%A"); //Coloca no regA a linha do SP
                commands.add("movw (%A),%D"); //Coloca esse valor da linha em D

                commands.add("leaw $" + popRam.toString() + ",%A"); //Carrega o valor do temp a ser salvo
                commands.add("movw %D,(%A)");

            } else if (segment.equals("pointer")) {

                commands.add("leaw $SP,%A");  //Carrega SP
                commands.add("movw (%A),%D"); //Coloca o ponteiro em D
                commands.add("decw %D");      //Decresce o ponteiro
                commands.add("movw %D,(%A)"); //Atualiza o SP
                commands.add("movw (%A),%A"); //Coloca no regA a linha do SP
                commands.add("movw (%A),%D"); //Coloca esse valor da linha em D


                if(index==0) {
                    //THIS
                    commands.add("leaw $THIS,%A");
                    commands.add("movw %D,(%A)");

                } else {
                    //THAT
                    commands.add("leaw $THAT,%A");
                    commands.add("movw %D,(%A)");
                }
            }
        } else if (command == Parser.CommandType.C_PUSH) {
            commands.add(String.format("; %d - PUSH %s %d", lineCode++ ,segment, index));

            if (segment.equals("constant")) {
                commands.add("leaw $" + index.toString() + ", %A"); //Carrega valor da constante
                commands.add("movw %A, %D");                        //Move a constante para %D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw (%A), %A");                      //Coloca o ponteiro em %A
                commands.add("movw %D, (%A)");                      //Move o valor da constante para onde aponta SP
                commands.add("incw %A");                            //Incrementa o SP
                commands.add("movw %A, %D");                        //Coloca o ponteiro em %D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw %D, (%A)");                      //Atualiza o SP

            } else if (segment.equals("local")) {
                commands.add("leaw $" + index.toString() + ", %A"); //Carrega valor do index
                commands.add("movw %A, %D");                        //Coloca o index em %D

                commands.add("leaw $LCL, %A");                      //Carrega LOCAL
                commands.add("movw (%A), %A");                      //Valor do local para A
                commands.add("addw %A, %D, %A");                    //Soma o index com o local para salvar o registrador do push
                commands.add("movw (%A), %D");                      //Move esse endereço para D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw (%A), %A");                      //Coloca o ponteiro em %A
                commands.add("movw %D, (%A)");                      //Move o valor do LOCAL para onde aponta SP
                commands.add("incw %A");                            //Incrementa o SP
                commands.add("movw %A, %D");                        //Coloca o ponteiro em %D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw %D, (%A)");                      //Atualiza o SP

            } else if (segment.equals("argument")) {

                commands.add("leaw $" + index.toString() + ", %A"); //Carrega valor do index
                commands.add("movw %A, %D");                        //Coloca o index em %D

                commands.add("leaw $ARG, %A");                      //Carrega ARG
                commands.add("movw (%A), %A");                      //Valor do ARG para A
                commands.add("addw %A, %D, %A");                    //Soma o index com o ARG para salvar o registrador do push
                commands.add("movw (%A), %D");                      //Move esse endereço para D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw (%A), %A");                      //Coloca o ponteiro em %A
                commands.add("movw %D, (%A)");                      //Move o valor da ARG para onde aponta SP
                commands.add("incw %A");                            //Incrementa o SP
                commands.add("movw %A, %D");                        //Coloca o ponteiro em %D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw %D, (%A)");                      //Atualiza o SP

            } else if (segment.equals("this")) {

                commands.add("leaw $" + index.toString() + ", %A"); //Carrega valor do index
                commands.add("movw %A, %D");                        //Coloca o index em %D

                commands.add("leaw $THIS, %A");                     //Carrega THIS
                commands.add("movw (%A), %A");                      //Valor do THIS para A
                commands.add("addw %A, %D, %A");                    //Soma o index com o ARG para salvar o registrador do push
                commands.add("movw (%A), %D");                      //Move esse endereço para D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw (%A), %A");                      //Coloca o ponteiro em %A
                commands.add("movw %D, (%A)");                      //Move o valor da THIS para onde aponta SP
                commands.add("incw %A");                            //Incrementa o SP
                commands.add("movw %A, %D");                        //Coloca o ponteiro em %D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw %D, (%A)");                      //Atualiza o SP

            } else if (segment.equals("that")) {

                commands.add("leaw $" + index.toString() + ", %A"); //Carrega valor do index
                commands.add("movw %A, %D");                        //Coloca o index em %D

                commands.add("leaw $THAT, %A");                     //Carrega THAT
                commands.add("movw (%A), %A");                      //Valor do THAT para A
                commands.add("addw %A, %D, %A");                    //Soma o index com o ARG para salvar o registrador do push
                commands.add("movw (%A), %D");                      //Move esse endereço para D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw (%A), %A");                      //Coloca o ponteiro em %A
                commands.add("movw %D, (%A)");                      //Move o valor da THAT para onde aponta SP
                commands.add("incw %A");                            //Incrementa o SP
                commands.add("movw %A, %D");                        //Coloca o ponteiro em %D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw %D, (%A)");                      //Atualiza o SP

            } else if (segment.equals("static")) {

            } else if (segment.equals("temp")) {

                commands.add("leaw $" + index.toString() + ",%A"); //Carrega valor do index
                commands.add("movw %A, %D");                        //Coloca o index em %D

                commands.add("leaw $R5, %A");                       //Carrega TEMP
                commands.add("addw %A, %D, %A");                    //Soma o index com o TEMP para salvar o registrador do push
                commands.add("movw (%A), %D");                      //Move o valor do endereco endereço para D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw (%A), %A");                      //Coloca o ponteiro em %A
                commands.add("movw %D, (%A)");                      //Move o valor da temp para onde aponta SP
                commands.add("incw %A");                            //Incrementa o SP
                commands.add("movw %A, %D");                        //Coloca o ponteiro em %D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw %D, (%A)");                      //Atualiza o SP

            } else if (segment.equals("pointer")) {
                if(index==0) {
                    commands.add("leaw $THIS, %A");                     //Carrega THIS
                } else {
                    commands.add("leaw $THAT, %A");                     //Carrega THAT
                }
                commands.add("movw (%A), %D");                      //Move o valor do endereco endereço para D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw (%A), %A");                      //Coloca o ponteiro em %A
                commands.add("movw %D, (%A)");                      //Move o valor do THIS para onde aponta SP
                commands.add("incw %A");                            //Incrementa o SP
                commands.add("movw %A, %D");                        //Coloca o ponteiro em %D

                commands.add("leaw $SP, %A");                       //Carrega SP
                commands.add("movw %D, (%A)");                      //Atualiza o SP
            }
        }

        String[] stringArray = new String[ commands.size() ];
        commands.toArray( stringArray );
        write(stringArray);

    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para inicializar o processo da VM (bootstrap).
     * Também prepara a chamada para a função Sys.init
     * O código deve ser colocado no início do arquivo de saída.
     */
    public void writeInit(boolean bootstrap, boolean isDir) {

        List<String> commands = new ArrayList<String>();

        if(bootstrap || isDir)
            commands.add( "; Inicialização para VM" );

        if(bootstrap) {
            commands.add("leaw $256,%A");
            commands.add("movw %A,%D");
            commands.add("leaw $SP,%A");
            commands.add("movw %D,(%A)");
        }

        if(isDir){
            commands.add("leaw $Main.main, %A");
            commands.add("jmp");
            commands.add("nop");
        }

        if(bootstrap || isDir) {
            String[] stringArray = new String[commands.size()];
            commands.toArray(stringArray);
            write(stringArray);
        }
    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para gerar o labels (marcadores de jump).
     * @param  label define nome do label (marcador) a ser escrito.
     */
    public void writeLabel(String label) {

        List<String> commands = new ArrayList<String>();
        commands.add( "; Label (marcador)" );

    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para gerar as instruções de goto (jumps).
     * Realiza um jump incondicional para o label informado.
     * @param  label define jump a ser realizado para um label (marcador).
     */
    public void writeGoto(String label) {

        List<String> commands = new ArrayList<String>();
        commands.add(String.format("; %d - Goto Incondicional", lineCode++));

    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para gerar as instruções de goto condicional (jumps condicionais).
     * Realiza um jump condicional para o label informado.
     * @param  label define jump a ser realizado para um label (marcador).
     */
    public void writeIf(String label) {

        List<String> commands = new ArrayList<String>();
        commands.add(String.format("; %d - Goto Condicional", lineCode++));

     }

    /**
     * Grava no arquivo de saida as instruções em Assembly para uma chamada de função (Call).
     * @param  functionName nome da função a ser "chamada" pelo call.
     * @param  numArgs número de argumentos a serem passados na função call.
     */
    public void writeCall(String functionName, Integer numArgs) {

        List<String> commands = new ArrayList<String>();
        commands.add(String.format("; %d - chamada de funcao %s", lineCode++, functionName));

    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para o retorno de uma sub rotina.
     */
    public void writeReturn() {

        List<String> commands = new ArrayList<String>();
        commands.add(String.format("; %d - Retorno de função", lineCode++));

    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para a declaração de uma função.
     * @param  functionName nome da função a ser criada.
     * @param  numLocals número de argumentos a serem passados na função call.
     */
    public void writeFunction(String functionName, Integer numLocals) {

        List<String> commands = new ArrayList<String>();
        commands.add(String.format("; %d - Declarando função %s", lineCode++, functionName));

    }

    /**
     * Armazena o nome do arquivo vm de origem.
     * Usado para definir os dados estáticos do código (por arquivo).
     * @param file nome do arquivo sendo tratado.
     */
    public void vmfile(String file) {

        int i = file.lastIndexOf(File.separator);
        int j = file.lastIndexOf('.');
        this.filename = file.substring(i+1,j);

    }

    // grava as instruções em Assembly no arquivo de saída
    public void write(String[] stringArray) {
        // gravando comandos no arquivos
        for (String s: stringArray) {
            this.outputFile.println(s);
        }
    }

    // fecha o arquivo de escrita
    public void close() throws IOException {
        this.outputFile.close();
    }

}
