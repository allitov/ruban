package io.allitov.deicstra;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Утилитный класс с реализацией алгоритма Дейкстры — поиска кратчайших путей
 * от одной вершины до всех остальных во взвешенном графе с неотрицательными
 * весами рёбер.
 */
@UtilityClass
public class Deicstra {

    /**
     * Расстояние до недостижимой из стартовой вершины вершины.
     */
    public static final int INFINITY = Integer.MAX_VALUE;

    /**
     * Находит кратчайшие расстояния от заданной вершины до всех остальных
     * вершин графа по алгоритму Дейкстры с приоритетной очередью.
     *
     * @param vertexCount количество вершин графа, пронумерованных от 0 до vertexCount - 1
     * @param edges       список рёбер графа, каждое ребро задано массивом {from, to, weight}
     * @param source      номер стартовой вершины
     * @return массив кратчайших расстояний от стартовой вершины до всех остальных;
     * расстояние до недостижимой вершины равно {@link #INFINITY}
     * @throws IllegalArgumentException если граф или стартовая вершина заданы некорректно,
     * в том числе если граф содержит ребро отрицательного веса
     */
    public int[] dijkstra(int vertexCount, int[][] edges, int source) {
        checkArguments(vertexCount, edges, source);

        List<List<int[]>> adjacency = buildAdjacencyList(vertexCount, edges);

        int[] distances = new int[vertexCount];
        Arrays.fill(distances, INFINITY);
        distances[source] = 0;

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(entry -> entry[1]));
        queue.offer(new int[]{source, 0});

        while (!queue.isEmpty()) {
            int[] entry = queue.poll();
            int vertex = entry[0];
            int distance = entry[1];

            if (distance > distances[vertex]) {
                continue;
            }

            for (int[] edge : adjacency.get(vertex)) {
                int to = edge[0];
                long newDistance = (long) distance + edge[1];

                if (newDistance < distances[to]) {
                    distances[to] = (int) newDistance;
                    queue.offer(new int[]{to, (int) newDistance});
                }
            }
        }

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
            if (edge[2] < 0) {
                throw new IllegalArgumentException(
                        "Edge weight must not be negative: " + Arrays.toString(edge));
            }
        }
    }

    /**
     * Строит список смежности графа из списка рёбер.
     *
     * @param vertexCount количество вершин графа
     * @param edges       список рёбер графа
     * @return список смежности, где для каждой вершины хранятся пары {to, weight}
     */
    private List<List<int[]>> buildAdjacencyList(int vertexCount, int[][] edges) {
        List<List<int[]>> adjacency = new ArrayList<>(vertexCount);
        for (int i = 0; i < vertexCount; i++) {
            adjacency.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            adjacency.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        return adjacency;
    }
}
