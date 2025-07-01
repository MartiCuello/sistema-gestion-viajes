# Sistema de Gestión de Viajeros y Vuelos ✈️

Proyecto desarrollado en Java para la materia **Algoritmos y Estructuras de Datos II** (marzo 2025), con foco en eficiencia algorítmica y diseño de estructuras de datos personalizadas.
Este sistema simula una agencia de viajes, gestionando viajeros, ciudades, vuelos y consultas eficientes, cumpliendo con las restricciones de eficiencia solicitados.

--

## Contexto del proyecto

Este proyecto surge como trabajo obligatorio de la materia AED2, en el cual se proporciona una interfaz (`Sistema`) que no puede modificarse.
A partir de ella, se debía diseñar una implementación propia y construir un sistema completo que:

- Valide datos mediante expresiones regulares.
- Cumpla con restricciones de eficiencia: O(log n), O(n), O(k), etc.
- Evite el uso de clases no permitidas (ArrayList, HashMap, etc.).
- Aplique estructuras de datos diseñadas desde cero.
- Pase pruebas funcionales rigurosas con entradas de texto y sin interacción con el usuario.

--

## Objetivos

- Registrar y buscar viajeros por cédula y correo electrónico (O(log n)).
- Listar viajeros ordenados por distintos criterios.
- Registrar ciudades, conexiones y vuelos.
- Calcular caminos óptimos entre ciudades según distintas métricas (tiempo, costo, escalas).
- Diseñar estructuras adecuadas para cumplir con las exigencias de eficiencia.

---

## Estructuras de Datos Implementadas
Estructuras creadas desde cero, no se usaron colecciones de Java.

### Árboles Binarios de Búsqueda (ABB)

- Dos árboles distintos:
  - Uno ordenado por cédula.
  - Otro por correo electrónico.
- Permiten:
  - Búsqueda eficiente en O(log n).
  - Listados ordenados mediante recorrido in-order.
  - Inserciones ordenadas al agregar viajeros.

### ABB por Categoría y Edad

- Árboles separados por:
  - Categoría: Estándar, Frecuente, Platino.
  - Rango de edad: 14 rangos predefinidos (0–139 años).
- Permiten listar viajeros en O(k), donde *k* es la cantidad de viajeros que cumplen con el criterio

### Lista Simple

- Utilizada para los listados de salida de viajeros y para representar las conexiones salientes de cada ciudad en el grafo. 

### Grafo Dirigido con Pesos (Ponderado)

- Vértices: ciudades.
- Aristas: conexiones unidireccionales.
- Pesos: vuelos con duración, costo, y tipo (comercial/privado).
- Implementado con:
  - Vector fijo de ciudades.
  - Listas enlazadas de conexiones y vuelos por ciudad.
- Evita estructuras prohibidas, implementado 100% desde cero.

### Cola

- Implementada para utilizar en el algoritmo BFS.

---

## Algoritmos utilizados

| Algoritmo     | Uso                                                        |
|---------------|------------------------------------------------------------|
| Dijkstra      | Buscar camino más corto en duración y en costo (Req. 15 y 16). |
| BFS           | Buscar rutas con menor cantidad de escalas (Req. 14).      |
| Recorridos in-order / reverso | Para generar listados ordenados por cédula o correo. |

--

## Cumplimiento de eficiencia

| Requerimiento                         | Estructura elegida                     | Eficiencia alcanzada |
|--------------------------------------|----------------------------------------|-----------------------|
| Buscar por cédula / correo           | Árboles binarios ordenados             | O(log n)              |
| Listar por cédula / correo ordenado  | Recorrido in-order                     | O(n)                  |
| Listar por categoría / rango edad    | Árbol por filtro específico            | O(k)                  |
| Ruta con menos escalas               | BFS sobre grafo                        | O(V + E)              |
| Ruta más corta en tiempo o costo     | Dijkstra sobre grafo                   | O((V + E) log V)      |

---

Martina Cuello - 2025

