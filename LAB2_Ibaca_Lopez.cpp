#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <unistd.h>   // Para la creación y comunicación de procesos usando fork()
#include <sys/wait.h> // Para esperar a los procesos hijos
#include <chrono>     // Para medir tiempo
#include <climits>    // Constantes de límites enteros - Bellmand-Ford necesita "infinito"
#include <algorithm>

using namespace std;

struct Arista {
    int origen;
    int destino;
    int peso;
};

struct Actualizacion {
    int nodo;
    int distancia;
    int padre;
};

// ======================================================
// CONSTANTES
// ======================================================

const int INF = 1000000000;

// ======================================================
// VARIABLES GLOBALES
// ======================================================

int numProcesos;
int numNodos;
int nodoInicio;
int nodoFinal;

vector<Arista> aristas;
vector<int> distancias;
vector<int> predecesor;

// ======================================================
// FUNCIONES
// ======================================================

// lee el archivo de entrada y carga los datos del .txt
// tiene que leer numProcesos, numHilos, numNodos, nodoInicio, nodoFinal, origen, destino, peso...
void leerArchivo(string nombreArchivo) {

    ifstream archivo(nombreArchivo);
    
    if (!archivo.is_open()) {
        cerr << "Error al abrir el archivo de entrada: " << nombreArchivo << endl;
        exit(1);
    }

    int numHilos; // Solo se usa para saltar el dato de hilos (Java)
    archivo >> numProcesos;
    archivo >> numHilos; // Se ignora en C++
    archivo >> numNodos;
    archivo >> nodoInicio;
    archivo >> nodoFinal;

    aristas.clear();
    int origen, destino, peso;

    while (archivo >> origen >> destino >> peso) {
        aristas.push_back({origen, destino, peso});
    }

    archivo.close();
}

// inicializa las distancias y predecesores para Bellman-Ford
// tiene que preparar los vectores (distancias y predecesr) porque desde el nodo inicial -> si mismo (distancia es 0)
void inicializarBellmanFord(){
    distancias.assign(numNodos, INF); //crea el vector dejando todas las posiciones con infinito
    predecesor.assign(numNodos, -1); //padres con -1 porque no se sabe por donde se llega a cada nodo
    distancias[nodoInicio] = 0;
}

// divide las aristas entre los procesos hijos
// reparte las conexiones entre los hijos y retorna el vector donde cada pos es una lista de aristas de un hijo
vector<vector<Arista>> dividirAristas() {
    vector<vector<Arista>> grupos(numProcesos);  // cada posicion es un hioj

    for (int i = 0; i < (int)aristas.size(); i++) {
        int procesoAsignado = i % numProcesos;
        grupos[procesoAsignado].push_back(aristas[i]);
    }

    return grupos;
}
// crea un pipe para cada proceso hijo para la comunicación (pipes[i][2])
void crearPipes(vector<vector<int>>& pipes) {
    pipes.resize(numProcesos, vector<int>(2)); 

    for (int i = 0; i < numProcesos; i++) {
        if (pipe(pipes[i].data()) == -1) {
            cerr << "Error al crear pipe para el proceso " << i << endl;
            exit(1);
        }
    }
}

// aqui va todo lo que hace cada hijo
// cada hijo recibe aristas, distancias (copia) y lo del pipe
// recorre las aristas y revisa si es que puede mejorar alguna de las distancias
// cuando encuentra que una mejora, crea la actualizacion y lo manda al padre
void trabajoHijo(vector<Arista> aristasAsignadas, int pipeEscritura);

// es la función usada por el padre para leer las actualizaciones que mandó un hijo
vector<Actualizacion> leerPipe(int pipeLectura);

// padre hace las actualizaciones recibidas
// deberia retornar true si es que hay al menos un cambio
bool aplicarActualizaciones(vector<Actualizacion> actualizaciones);

// ve si es que hay algun ciclo negativo
// en caso de que haya significa que el camino no es válido
bool detectarCicloNegativo();

// usa predecesor[i] = algo para reconstruir la ruta
vector<int> reconstruirRuta();

// printea la ruta por pantalla
void imprimirRuta(vector<int> ruta);

// guarda la ruta y la latencia en salidaFork.txt
// ej: Ruta: 0 -> 1 -> 3 -> 11
//     latencia total: 75
void guardarSalida(vector<int> ruta, int latencia);

// ejecuta Bellman-Ford utilizando fork() y pipes
void bellmanFordFork();
// tiene que hacer en orden:
// 1. inicializarBellmanFord()
// 2. dividirAristas()
// 3. repetir numNodos - 1 veces: 
//   . crear hijos con fork()
//   . cada hijo analiza sus aristas
//   . cada hijo manda actualizaciones por pipe
//   . el padre lee los pipes
//   . padre aplica actualizaciones
//   . el padre espera a los hijos con wait()
// 4. detectar ciclo negativo
// 5. reconstruir ruta
// 6. imprimir resultado final
// 7. guardar salidaFork.txt

// ======================================================
// Main
// ======================================================

int main(int argc, char* argv[]) {
    if (argc != 2) {
        cout << "Uso: ./programa archivo.txt" << endl;
        return 1;
    }

    leerArchivo(argv[1]);
    bellmanFordFork();
    return 0;
}