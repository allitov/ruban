package io.allitov.deicstra;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeicstraTest {

    @Test
    void shouldFindShortestDistances() {
        int[][] edges = {
                {0, 1, 4},
                {0, 2, 1},
                {2, 1, 2},
                {1, 3, 5},
                {2, 3, 10}
        };

        int[] distances = Deicstra.dijkstra(4, edges, 0);

        assertThat(distances).containsExactly(0, 3, 1, 8);
    }

    @Test
    void shouldRelaxVertexThroughCheaperPath() {
        int[][] edges = {
                {0, 1, 10},
                {0, 2, 3},
                {2, 1, 4},
                {1, 3, 2},
                {2, 3, 8}
        };

        int[] distances = Deicstra.dijkstra(4, edges, 0);

        assertThat(distances).containsExactly(0, 7, 3, 9);
    }

    @Test
    void shouldHandleParallelEdgesAndSelfLoop() {
        int[][] edges = {
                {0, 1, 7},
                {0, 1, 3},
                {1, 1, 1},
                {1, 2, 2}
        };

        int[] distances = Deicstra.dijkstra(3, edges, 0);

        assertThat(distances).containsExactly(0, 3, 5);
    }

    @Test
    void shouldMarkUnreachableVerticesAsInfinity() {
        int[][] edges = {{0, 1, 7}};

        int[] distances = Deicstra.dijkstra(3, edges, 0);

        assertThat(distances).containsExactly(0, 7, Deicstra.INFINITY);
    }

    @Test
    void shouldFindDistancesFromNonZeroSource() {
        int[][] edges = {
                {0, 1, 4},
                {1, 2, 6},
                {0, 2, 2}
        };

        int[] distances = Deicstra.dijkstra(3, edges, 1);

        assertThat(distances).containsExactly(Deicstra.INFINITY, 0, 6);
    }

    @Test
    void shouldReturnZeroForSingleVertex() {
        int[] distances = Deicstra.dijkstra(1, new int[][]{}, 0);

        assertThat(distances).containsExactly(0);
    }

    @Test
    void shouldThrowWhenEdgeWeightIsNegative() {
        int[][] edges = {
                {0, 1, 1},
                {1, 2, -3}
        };

        assertThatThrownBy(() -> Deicstra.dijkstra(3, edges, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("negative");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArguments")
    void shouldThrowWhenArgumentsAreInvalid(int vertexCount, int[][] edges, int source) {
        assertThatThrownBy(() -> Deicstra.dijkstra(vertexCount, edges, source))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(0, new int[][]{}, 0),
                Arguments.of(3, null, 0),
                Arguments.of(3, new int[][]{}, -1),
                Arguments.of(3, new int[][]{}, 3),
                Arguments.of(3, new int[][]{{0}}, 0),
                Arguments.of(3, new int[][]{{0, 4, 1}}, 0),
                Arguments.of(3, new int[][]{{4, 0, 1}}, 0),
                Arguments.of(3, new int[][]{null}, 0),
                Arguments.of(3, new int[][]{{0, 1, -1}}, 0)
        );
    }
}
