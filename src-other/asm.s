.section .text
	.global estimate
estimate:
	#Prologue
	pushl %ebp 		# save previous stack frame pointer
	movl %esp , %ebp 	# the stack frame pointer for our function
	
	#pushl %ebx
	#pushl %edi
	#pushl %esi
	
	movl 8(%ebp), %ecx # Parametro potencia (kW)
	movl $0, %eax # Retorno default
	cmpl %eax, %ecx
	jle end # Salta para o fim se a potencia for zero (ou menor)
	
	movl 12(%ebp), %eax # Parametro capacidadeBateria (kW h)
	cdq
	idivl %ecx # t = E / P (Tempo total de uma carga em horas)
	
	movl 16(%ebp), %edx # Parametro cargaAtual (percentagem)
	movl $100, %ecx # Percentagem máxima
	subl %edx, %ecx # Percentagem de bateria que é necessario carregar
	
	# Calculo do tempo para carregar x% da bateria, sendo x a percentagem de bateria descarregada
	imul %ecx # Multiplicar pela percentagem descarregada
	movl $100, %ecx
	cdq
	idivl %ecx # Dividir por 100
	
end:
	#popl %esi
	#popl %edi
	#popl %ebx
	
	#Epilogue
	movl %ebp , %esp 	# restore the stack pointer (" clear " the stack )
	popl %ebp 			# restore the stack frame pointer
	ret
