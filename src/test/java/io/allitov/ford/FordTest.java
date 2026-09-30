package io.allitov.ford;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FordTest {

    @Test
    void shouldFindShortestDistancesWithPositiveWeights() {
        int[][] edges = {
                {0, 1, 4},
                {0, 2, 1},
                {2, 1, 2},
                {1, 3, 5},
                {2, 3, 10}
        };

        int[] distances = Ford.bellmanFord(4, edges, 0);

        assertThat(distances).containsExactly(0, 3, 1, 8);
    }

    @Test
    void shouldHandleNegativeEdges() {
        int[][] edges = {
                {0, 1, 1},
                {1, 2, -3},
                {0, 2, 2}
        };

        int[] distances = Ford.bellmanFord(3, edges, 0);

        assertThat(distances).containsExactly(0, 1, -2);
    }

    @Test
    void shouldMarkUnreachableVerticesAsInfinity() {
        int[][] edges = {{0, 1, 7}};

        int[] distances = Ford.bellmanFord(3, edges, 0);

        assertThat(distances).containsExactly(0, 7, Ford.INFINITY);
    }

    @Test
    void shouldReturnZeroForSingleVertex() {
        int[] distances = Ford.bellmanFord(1, new int[][]{}, 0);

        assertThat(distances).containsExactly(0);
    }

    @Test
    void shouldThrowWhenNegativeCycleIsReachable() {
        int[][] edges = {
                {0, 1, 1},
                {1, 2, -3},
                {2, 1, 1}
        };

        assertThatThrownBy(() -> Ford.bellmanFord(3, edges, 0))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("negative cycle");
    }

    @Test
    void shouldNotFailWhenNegativeCycleIsUnreachable() {
        int[][] edges = {
                {0, 1, 5},
                {2, 3, -4},
                {3, 2, 1}
        };

        int[] distances = Ford.bellmanFord(4, edges, 0);

        assertThat(distances).containsExactly(0, 5, Ford.INFINITY, Ford.INFINITY);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArguments")
    void shouldThrowWhenArgumentsAreInvalid(int vertexCount, int[][] edges, int source) {
        assertThatThrownBy(() -> Ford.bellmanFord(vertexCount, edges, source))
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
                Arguments.of(3, new int[][]{null}, 0)
        );
    }
}
