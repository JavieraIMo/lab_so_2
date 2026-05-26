# Laboratorio 2

## Integrantes

- Javiera Ibaca Morales - Rol: 202273624-0 - Paralelo: 200   
- Fernanda López Sal - Rol:  -  Paralelo: 200  

## Descripción General 

Este laboratorio consiste en desarrollar un motor de búsqueda de rutas de alto rendimiento para encontrar el camino de menor latencia entre dos nodos en una red de servidores, utilizando el algoritmo de Bellman-Ford. Se deben implementar dos versiones:  
- **Motor Alpha (C/C++)**: Utiliza procesos y pipes para paralelizar el cálculo  
- **Motor Beta (Java)**: Utiliza hilos y memoria compartida con mecanismos de sincronización  

## Especificaciones y Algoritmos 

- Procesamiento de un grafo dirigido, donde los nodos representan servidores y las aristas conexiones con latencia  
- Implementación del algoritmo de Bellman-Ford para hallar la ruta de menor latencia y detectar ciclos de peso negativo  
- Entrada por archivo de texto con la configuración de nodos, conexiones y parámetros de ejecución  
- Salida por pantalla detallando el progreso y resultados, y generación de archivos de evidencia (`salidaFork.txt` y `salidaThread.txt`)  
- Uso obligatorio de Makefile para compilar y ejecutar la versión en C/C++  

## Supuestos

- El archivo de entrada sigue estrictamente el formato indicado en el enunciado  
- No existen nodos o conexiones duplicadas  
- El sistema operativo de ejecución es Linux  
- El número de procesos/hilos no excede la cantidad de aristas  

## Consideraciones

- El código debe ser limpio, legible y estar comentado  
- La entrega es por GitHub el 27/05  
- El informe técnico debe incluir pruebas, análisis comparativo y conclusiones  

## Estado del Motor Beta (Java)

### Implementado:
- Estructura base y modularización del programa en Java.
- Lectura y carga del archivo de entrada.
- Inicialización de distancias y predecesores.
- División equitativa de aristas entre hilos.
- Clase de hilo (HiloBellmanFord) con lógica de relajación concurrente y prints de depuración.
- Aplicación de actualizaciones propuestas por los hilos.
- Detección de ciclos de peso negativo.
- Reconstrucción de la ruta óptima con mensajes de depuración.

### Pendiente:
- Implementar el ciclo completo de Bellman-Ford (repetir iteraciones y aplicar actualizaciones en cada ronda).
- Sincronización de barrera entre hilos por iteración (opcional para mayor control).
- Salida detallada por pantalla según especificación (inicio, progreso por iteración, informe final).
- Escritura del archivo salidaThread.txt con la ruta y latencia final.
- Pruebas integrales y validación de casos borde.


## Estado del Motor Alpha (C/C++)

### Implementado:
- Estructura base y modularización del programa en C++.
- Lectura y carga del archivo de entrada.
- Inicialización de distancias y predecesores.
- División equitativa de aristas entre procesos.
- Declaración de funciones para crear pipes, trabajo de hijos, aplicar actualizaciones, detectar ciclos negativos y reconstruir ruta.
- Esqueleto de la función principal bellmanFordFork con pasos detallados en comentarios.
- Makefile básico para compilar.

### Pendiente:
- Implementar la lógica completa de fork() y pipes para paralelización real.
- Implementar la lógica interna de trabajoHijo, leerPipe, aplicarActualizaciones, detectarCicloNegativo, reconstruirRuta, imprimirRuta y guardarSalida.
- Salida detallada por pantalla según especificación (inicio, progreso por iteración, informe final).
- Escritura del archivo salidaFork.txt con la ruta y latencia final.
- Pruebas integrales y validación de casos borde.

## Formato del archivo de entrada
Debe seguir el formato especificado en el enunciado:
1. Número de procesos (C++)
2. Número de hilos (Java)
3. Número de nodos
4. Nodo de inicio
5. Nodo final
6. Lista de conexiones: origen destino peso
