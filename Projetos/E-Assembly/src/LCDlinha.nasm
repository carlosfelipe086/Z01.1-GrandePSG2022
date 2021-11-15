; Arquivo: LCDQuadrado.nasm
; Curso: Elementos de Sistemas
; Criado por: Rafael Corsi
; Data: 28/3/2018
;
; Desenhe uma linha no LCD


leaw $16384, %A
movw %A, %D
leaw $1, %A
movw %D, (%A)


LOOP:
leaw $1, %A
movw (%A), %D

movw %D, %A
movw $-1, (%A)

leaw $1, %A
addw %D, $1, (%A)

leaw $16403, %A
subw %D, %A , %D
leaw $LOOP, %A
jne %D
nop