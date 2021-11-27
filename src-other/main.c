#include <stdio.h>
#include <stdlib.h>
#include <glob.h>
#include <unistd.h>
#include <string.h>
#include "asm.h"

int main(int argc, char **argv)
{
	glob_t paths;
	int retval;
	
	int idTransporte, idFarmacia, potencia, capacidade, carga, estimativa;
	
	int slotc = 0; // Contador de slots ocupados
	Slot *slotv = NULL; // Vetor dos slots oscupados
	
	FILE *lockFile;
	FILE *estFile;
	FILE *flagFile;
	
	int ler, i, j;
	
	paths.gl_pathc = 0;
	paths.gl_pathv = NULL;
	paths.gl_offs = 0;
	
	int timeout = 600, execTime = 0;
	
	// Obter fichiros que correspondem ao pattern de ficheiro de flag de lock
	retval = glob("locks/lock_????_??_??_??_??_??.data.flag", GLOB_NOSORT, NULL, &paths);
	if (retval != 0 && retval != GLOB_NOMATCH) {
		printf("glob failed\n");
		exit(1);
	}
	// Programa executa durante 10 minutos (600 segundos) no maximo
	while (execTime < timeout) {
		for (i = 0; i < paths.gl_pathc; i++) {
			// Obter datetime do filename da flag de lock
			char datetime[20];
			sscanf(paths.gl_pathv[i], "locks/lock_%[^.]", datetime);
			// Eliminar ficheiro de flag de lock
			remove(paths.gl_pathv[i]);
			// Gerar nome do ficehiro de lock
			char lockData[36];
			sprintf(lockData,"locks/lock_%s.data",datetime);
			// Obter dados do ficheiro
			if ((lockFile = fopen(lockData, "r")) == NULL) {
				continue;
			}
			ler = fscanf(lockFile, "%d %d %d %d %d", &idTransporte, &idFarmacia, &potencia, &capacidade, &carga);
			fclose(lockFile);
			if (ler != 5)  {
				continue;
			}
			// Criar slot de estacionamento e guardar no vetor
			Slot s;
			strcpy(s.datetime, datetime);
			s.idTransporte = idTransporte;
			s.idFarmacia = idFarmacia;
			s.potencia = potencia;
			s.capacidade = capacidade;
			s.carga = carga;
			if (slotc == 0) {
				slotv = calloc(1, sizeof(s));
			} else {
				slotv = realloc(slotv, (slotc + 1) * sizeof(s));
			}
			if (slotv == NULL) {
				printf("Error allocating memory\n");
				exit(1);
			}
			slotv[slotc++] = s;
			// Para cada slot em memória calcular a estimativa e gerar ficheiro
			for (j = 0; j < slotc; j++) {
				// Calcular estimativa de tempo
				estimativa = estimate(slotv[j].potencia / slotc, slotv[j].capacidade, slotv[j].carga);
				// Gerar nome do ficheiro de estimativa
				char estData[44];
				sprintf(estData,"estimates/estimate_%s.data", slotv[j].datetime);
				// Criar ficheiro de estimate
				if ((estFile = fopen(estData, "w")) == NULL) {
					continue;
				}
				fprintf(estFile, "%d %d %d", slotv[j].idTransporte, slotv[j].idFarmacia, estimativa);
				fclose(estFile);
				// Gerar nome do ficheiro de flag de estimativa
				char estFlag[49];
				sprintf(estFlag,"estimates/estimate_%s.data.flag", slotv[j].datetime);
				// Criar ficheiro de flag de estimativa
				flagFile = fopen(estFlag, "w");
				fclose(flagFile);
			}
		}

		sleep(10); // Verifica ficheiros a cada 10 segundos
		execTime += 10;

		globfree(&paths);
        paths.gl_pathc = 0;
        paths.gl_pathv = NULL;
        paths.gl_offs = 0;
        retval = glob("locks/lock_????_??_??_??_??_??.data.flag", GLOB_NOSORT, NULL, &paths);
        if (retval != 0 && retval != GLOB_NOMATCH) {
        	printf("glob failed\n");
        	exit(1);
        }
	}
	globfree(&paths);
	if (slotc != 0)
		free(slotv);
	
	return 0;
}

