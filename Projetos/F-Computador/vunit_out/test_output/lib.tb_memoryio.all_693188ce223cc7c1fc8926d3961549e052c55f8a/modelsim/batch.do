onerror {quit -code 1}
source "/home/borg/Desktop/Psg3/Z01.1-GrandePSG2022/Projetos/F-Computador/vunit_out/test_output/lib.tb_memoryio.all_693188ce223cc7c1fc8926d3961549e052c55f8a/modelsim/common.do"
set failed [vunit_load]
if {$failed} {quit -code 1}
set failed [vunit_run]
if {$failed} {quit -code 1}
quit -code 0
