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

	// Inicializa distancias y predecesores
	static void inicializarBellmanFord() {
	}

	// Divide las aristas entre los hilos
	static ArrayList<ArrayList<Arista>> dividirAristas() {
		return null;
	}

	// Trabajo de cada hilo
	static class HiloBellmanFord extends Thread {
		ArrayList<Arista> aristasAsignadas;
		HiloBellmanFord(ArrayList<Arista> aristas) {
			this.aristasAsignadas = aristas;
		}
		public void run() {
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

	// Ejecuta Bellman-Ford con hilos
	static void bellmanFordThreads() {
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

