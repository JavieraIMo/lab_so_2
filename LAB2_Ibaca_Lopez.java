import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class LAB2_Ibaca_Lopez {

	// =============================
	// Clases auxiliares
	// =============================

	static class Arista {
		int origen, destino, peso;
		Arista(int o, int d, int p) {
			origen = o; 
            destino = d; 
            peso = p;
		}
	}

	static class Actualizacion {
		int nodo, distancia, padre;
		Actualizacion(int n, int d, int p) {
			nodo = n; 
            distancia = d; 
            padre = p;
		}
	}

	// =============================
	// Variables globales
	// =============================

	static int numHilos;
	static int numNodos;
	static int nodoInicio;
	static int nodoFinal;
	static ArrayList<Arista> aristas = new ArrayList<>();
	static int[] distancias;
	static int[] predecesores;

	// =============================
	// Métodos principales
	// =============================

	// Lee el archivo de entrada y carga los datos
	static void leerArchivo(String nombreArchivo) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
			String linea;

			// lee los primeros datos
			numHilos = Integer.parseInt(br.readLine().trim());
			br.readLine(); // Saltar numProcesos (solo para C/C++)
			numNodos = Integer.parseInt(br.readLine().trim());
			nodoInicio = Integer.parseInt(br.readLine().trim());
			nodoFinal = Integer.parseInt(br.readLine().trim());

			aristas.clear();
			while ((linea = br.readLine()) != null) {
				linea = linea.trim();
				if (linea.isEmpty()) continue;
				String[] partes = linea.split("\\s+");
				if (partes.length == 3) {
					int origen = Integer.parseInt(partes[0]);
					int destino = Integer.parseInt(partes[1]);
					int peso = Integer.parseInt(partes[2]);
					aristas.add(new Arista(origen, destino, peso));
				}
			}
			br.close();

		} catch (IOException | NumberFormatException e) {
			System.err.println("Error al leer el archivo: " + e.getMessage());
			System.exit(1);
		}
	}

	// Inicializa distancias y predecesores en infinito (excepto el nodo de inicio)
	static void inicializarBellmanFord() {
		
		distancias = new int[numNodos];
		predecesores = new int[numNodos];

		for (int i = 0; i < numNodos; i++) {
			distancias[i] = Integer.MAX_VALUE; // Representa infinito
			predecesores[i] = -1; // Sin predecesor
		}

		distancias[nodoInicio] = 0;// la distancia al nodo de inicio es 0
	}

	// Divide la lista de aristas en sublistas, asignando las aristas de forma equitativa (round-robin) a 
	// cada hilo para balancear la carga de trabajo.
	static ArrayList<ArrayList<Arista>> dividirAristas() {

		ArrayList<ArrayList<Arista>> particiones = new ArrayList<>();

		for (int i = 0; i < numHilos; i++) {// inicializa las sublistas vacías
			particiones.add(new ArrayList<>());
		}

		for (int i = 0; i < aristas.size(); i++) {// distribuye las aristas de manera equitativa (round-robin)
			int indiceHilo = i % numHilos;
			particiones.get(indiceHilo).add(aristas.get(i));
		}

		return particiones;
	}

	// Cada hilo procesa un subconjunto de aristas y, si encuentra una relajación posible,
	// propone una actualización de distancia y predecesor agregándola a una lista compartida.
	static class HiloBellmanFord extends Thread {
		ArrayList<Arista> aristasAsignadas;
		List<Actualizacion> actualizacionesCompartidas;

		// Constructor recibe las aristas y la lista compartida de actualizaciones
		HiloBellmanFord(ArrayList<Arista> aristas, List<Actualizacion> actualizacionesCompartidas) {
			this.aristasAsignadas = aristas;
			this.actualizacionesCompartidas = actualizacionesCompartidas;
		}

		/**
		 * Procesa las aristas asignadas al hilo. Por cada arista, verifica si se puede relajar
		 * (es decir, si se encuentra un camino más corto al nodo destino). Si es así, agrega una
		 * propuesta de actualización a la lista compartida. Los mensajes por consola permiten
		 * seguir el flujo de ejecución y depurar el comportamiento concurrente.
		 */
		@Override
		public void run() {
			System.out.println("[Hilo " + Thread.currentThread().getId() + "] Iniciando procesamiento de " + aristasAsignadas.size() + " aristas");
			for (Arista arista : aristasAsignadas) {
				int u = arista.origen;
				int v = arista.destino;
				int peso = arista.peso;

				// Lee la distancia actual al nodo origen de forma segura
				int distanciaU;
				synchronized (distancias) {
					distanciaU = distancias[u];
				}

				// Si se puede relajar la arista, propone una actualización
				if (distanciaU != Integer.MAX_VALUE && distanciaU + peso < distancias[v]) {
					System.out.println("[Hilo " + Thread.currentThread().getId() + "] Relajando arista " + u + " -> " + v + " con peso " + peso);
					// Agrega la actualización a la lista compartida de manera segura
					synchronized (actualizacionesCompartidas) {
						actualizacionesCompartidas.add(new Actualizacion(v, distanciaU + peso, u));
					}
				}
			}
			System.out.println("[Hilo " + Thread.currentThread().getId() + "] Finalizó procesamiento");
		}
	}

	// Aplica actualizaciones
	static boolean aplicarActualizaciones(List<Actualizacion> actualizaciones) {
		return false;
	}

	// Detecta ciclo negativo
	static boolean detectarCicloNegativo() {
		return false;
	}

	// Reconstruye la ruta
	static ArrayList<Integer> reconstruirRuta() {
		return null;
	}

	// Imprime la ruta
	static void imprimirRuta(ArrayList<Integer> ruta) {
	}

	// Guarda la salida en salidaThread.txt
	static void guardarSalida(ArrayList<Integer> ruta, int latencia) {
	}

	// Ejecuta una iteración de Bellman-Ford con hilos 
	static void bellmanFordThreads() {
		System.out.println("=== INICIO bellmanFordThreads ===");
		inicializarBellmanFord();
		ArrayList<ArrayList<Arista>> particiones = dividirAristas();

		// Lista compartida para actualizaciones
		List<Actualizacion> actualizacionesCompartidas = Collections.synchronizedList(new ArrayList<>());

		// Crear y lanzar los hilos
		List<Thread> hilos = new ArrayList<>();
		for (int i = 0; i < numHilos; i++) {
			Thread hilo = new HiloBellmanFord(particiones.get(i), actualizacionesCompartidas);
			hilos.add(hilo);
			hilo.start();
		}

		// Esperar a que todos los hilos terminen
		for (Thread hilo : hilos) {
			try {
				hilo.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Mostrar actualizaciones propuestas
		System.out.println("Actualizaciones propuestas por los hilos:");
		for (Actualizacion act : actualizacionesCompartidas) {
			System.out.println("  Nodo: " + act.nodo + ", Distancia: " + act.distancia + ", Padre: " + act.padre);
		}
		System.out.println("=== FIN bellmanFordThreads ===");
	}

	// Main
	public static void main(String[] args) {
        
		if (args.length != 1) {
			System.out.println("archivo.txt");
			return;
		}

		leerArchivo(args[0]);
		bellmanFordThreads();
	}
}