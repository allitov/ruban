package io.allitov.crascal;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Утилитный класс с реализацией алгоритма Краскала
 * поиска минимального остовного дерева во взвешенном неориентированном графе.
 */
@UtilityClass
public class Crascal {

    /**
     * Находит минимальное остовное дерево графа по алгоритму Краскала
     * с системой непересекающихся множеств (DSU).
     *
     * @param vertexCount количество вершин графа, пронумерованных от 0 до vertexCount - 1
     * @param edges       список рёбер графа, каждое ребро задано массивом {from, to, weight};
     *                    граф считается неориентированным, вес ребра может быть отрицательным
     * @return рёбра минимального остовного дерева в порядке неубывания весов
     * @throws IllegalArgumentException если граф задан некорректно
     * @throws IllegalStateException    если граф не является связным
     */
    public int[][] kruskal(int vertexCount, int[][] edges) {
        checkArguments(vertexCount, edges);

        int[][] sortedEdges = edges.clone();
        Arrays.sort(sortedEdges, Comparator.comparingInt(edge -> edge[2]));

        DisjointSetUnion dsu = new DisjointSetUnion(vertexCount);
        List<int[]> treeEdges = new ArrayList<>(vertexCount - 1);

        for (int[] edge : sortedEdges) {
            if (treeEdges.size() == vertexCount - 1) {
                break;
            }
            if (dsu.union(edge[0], edge[1])) {
                treeEdges.add(edge);
            }
        }

        checkConnected(treeEdges.size(), vertexCount);

        return treeEdges.toArray(new int[0][]);
    }

    /**
     * Проверяет корректность аргументов алгоритма.
     *
     * @param vertexCount количество вершин графа
     * @param edges       список рёбер графа
     */
    private void checkArguments(int vertexCount, int[][] edges) {
        if (vertexCount <= 0) {
            throw new IllegalArgumentException("Vertex count must be positive: " + vertexCount);
        }
        if (edges == null) {
            throw new IllegalArgumentException("Edges must not be null");
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
     * Проверяет, что остовное дерево объединяет все вершины графа.
     *
     * @param treeEdgeCount количество рёбер, вошедших в остовное дерево
     * @param vertexCount   количество вершин графа
     */
    private void checkConnected(int treeEdgeCount, int vertexCount) {
        if (treeEdgeCount != vertexCount - 1) {
            throw new IllegalStateException("Graph is not connected");
        }
    }

    /**
     * Система непересекающихся множеств с сжатием путей
     * и объединением по рангу.
     */
    private static final class DisjointSetUnion {

        private final int[] parent;
        private final int[] rank;

        private DisjointSetUnion(int size) {
            this.parent = new int[size];
            this.rank = new int[size];

            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        /**
         * Находит представителя множества, содержащего вершину.
         *
         * @param vertex номер вершины
         * @return номер представителя множества
         */
        private int find(int vertex) {
            while (parent[vertex] != vertex) {
                parent[vertex] = parent[parent[vertex]];
                vertex = parent[vertex];
            }

            return vertex;
        }

        /**
         * Объединяет множества двух вершин, если они различны.
         *
         * @param first  номер первой вершины
         * @param second номер второй вершины
         * @return {@code true}, если вершины находились в разных множествах и были объединены
         */
        private boolean union(int first, int second) {
            int firstRoot = find(first);
            int secondRoot = find(second);

            if (firstRoot == secondRoot) {
                return false;
            }

            if (rank[firstRoot] < rank[secondRoot]) {
                int temporary = firstRoot;
                firstRoot = secondRoot;
                secondRoot = temporary;
            }

            parent[secondRoot] = firstRoot;
            if (rank[firstRoot] == rank[secondRoot]) {
                rank[firstRoot]++;
            }

            return true;
        }
    }
}
