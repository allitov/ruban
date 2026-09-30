package io.allitov.ford;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

/**
 * Утилитный класс с реализацией алгоритма Форда — Беллмана
 * поиска кратчайших путей от одной вершины до всех остальных
 * во взвешенном графе, в том числе с рёбрами отрицательного веса.
 */
@UtilityClass
public class Ford {

    /**
     * Расстояние до недостижимой из стартовой вершины вершины.
     */
    public static final int INFINITY = Integer.MAX_VALUE;

    /**
     * Находит кратчайшие расстояния от заданной вершины до всех остальных
     * вершин графа по алгоритму Форда — Беллмана.
     *
     * @param vertexCount количество вершин графа, пронумерованных от 0 до vertexCount - 1
     * @param edges       список рёбер графа, каждое ребро задано массивом {from, to, weight}
     * @param source      номер стартовой вершины
     * @return массив кратчайших расстояний от стартовой вершины до всех остальных;
     * расстояние до недостижимой вершины равно {@link #INFINITY}
     * @throws IllegalArgumentException если граф или стартовая вершина заданы некорректно
     * @throws IllegalStateException    если из стартовой вершины достижим цикл отрицательного веса
     */
    public int[] bellmanFord(int vertexCount, int[][] edges, int source) {
        checkArguments(vertexCount, edges, source);

        int[] distances = new int[vertexCount];
        Arrays.fill(distances, INFINITY);
        distances[source] = 0;

        for (int i = 0; i < vertexCount - 1; i++) {
            boolean relaxed = false;

            for (int[] edge : edges) {
                int from = edge[0];
                int to = edge[1];
                int weight = edge[2];

                if (distances[from] != INFINITY && distances[from] + weight < distances[to]) {
                    distances[to] = distances[from] + weight;
                    relaxed = true;
                }
            }

            if (!relaxed) {
                break;
            }
        }

        checkNegativeCycle(edges, distances);

        return distances;
    }

    /**
     * Проверяет корректность аргументов алгоритма.
     *
     * @param vertexCount количество вершин графа
     * @param edges       список рёбер графа
     * @param source      номер стартовой вершины
     */
    private void checkArguments(int vertexCount, int[][] edges, int source) {
        if (vertexCount <= 0) {
            throw new IllegalArgumentException("Vertex count must be positive: " + vertexCount);
        }
        if (edges == null) {
            throw new IllegalArgumentException("Edges must not be null");
        }
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException(
                    "Source vertex must be in range [0, " + (vertexCount - 1) + "]: " + source);
        }

        for (int[] edge : edges) {
            if (edge == null || edge.length != 3) {
                throw new IllegalArgumentException("Each edge must be an array {from, to, weight}");
            }
            if (edge[0] < 0 || edge[0] >= vertexCount || edge[1] < 0 || edge[1] >= vertexCount) {
                throw new IllegalArgumentException(
                        "Edge vertices must be in range [0, " + (vertexCount - 1) + "]: " + Arrays.toString(edge));
            }
        }
    }

    /**
     * Проверяет, достижим ли из стартовой вершины цикл отрицательного веса.
     *
     * @param edges     список рёбер графа
     * @param distances текущие расстояния от стартовой вершины
     */
    private void checkNegativeCycle(int[][] edges, int[] distances) {
        for (int[] edge : edges) {
            if (distances[edge[0]] != INFINITY && distances[edge[0]] + edge[2] < distances[edge[1]]) {
                throw new IllegalStateException("Graph contains a negative cycle reachable from the source vertex");
            }
        }
    }
}
