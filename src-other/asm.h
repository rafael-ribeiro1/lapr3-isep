#ifndef ASM_H
#define ASM_H

typedef struct {
	char datetime[20];
	int idTransporte;
	int idFarmacia;
	int potencia;
	int capacidade;
	int carga;
} Slot;

int estimate(int potencia, int capacidadeBateria, int cargaAtual);

#endif
