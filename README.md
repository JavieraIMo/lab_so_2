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
# Estado del Proyecto

Actualmente implementado:
- Estructura base de ambos programas (C++ y Java) creada.
- Función de lectura de archivo implementada en ambos lenguajes.
- Modularización inicial en Java y C++.
- Makefile básico para compilar C++.

Pendiente:
- Implementar la lógica completa de Bellman-Ford en ambos lenguajes.
- Paralelización con fork/pipes en C++ y threads/sincronización en Java.
- Detección de ciclos negativos y reconstrucción de ruta.
- Salida detallada por pantalla y archivos de evidencia.
- Pruebas, informe técnico y documentación final.

## Formato del archivo de entrada
Debe seguir el formato especificado en el enunciado:
1. Número de procesos (C++)
2. Número de hilos (Java)
3. Número de nodos
4. Nodo de inicio
5. Nodo final
6. Lista de conexiones: origen destino peso
