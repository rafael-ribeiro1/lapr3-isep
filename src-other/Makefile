exe: main.o asm.o
		gcc -Wall -g main.o asm.o -o exe

main.o: main.c asm.h
		gcc -Wall -g -c main.c

asm.o: asm.s
		gcc -Wall -g -c asm.s

run: exe
		./exe

clean:
		rm *.o exe
