; Arquivo: SWeLED.nasm
; Curso: Elementos de Sistemas
; Criado por: Rafael Corsi
; Data: 28/3/2018
;
; Faça os LEDs exibirem 
; LED = ON ON ON ON ON !SW3 !SW2 !SW1 0
; Mesma questão da prova

leaw $21185, %A
movw (%A), %D
notw %D

; Aplica mascara and
leaw $510, %A
andw %A, %D, %D

; Aplica mascara or
;leaw $496, %A
;orw %A, %D, %D

; Coloca o resultado nos LEDs
leaw $21184, %A
movw %D, (%A)

;Volta pro comeco
leaw $0, %A
jmp
nop


