package io.allitov.crascal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CrascalTest {

    @Test
    void shouldFindMinimumSpanningTree() {
        int[][] edges = {
                {0, 1, 1},
                {0, 2, 4},
                {1, 2, 2},
                {1, 3, 5},
                {2, 3, 3}
        };

        int[][] tree = Crascal.kruskal(4, edges);

        assertThat(tree).isDeepEqualTo(new int[][]{
                {0, 1, 1},
                {1, 2, 2},
                {2, 3, 3}
        });
    }

    @Test
    void shouldSkipEdgesThatCreateCycle() {
        int[][] edges = {
                {0, 2, 3},
                {0, 1, 1},
                {2, 3, 4},
                {1, 2, 2}
        };

        int[][] tree = Crascal.kruskal(4, edges);

        assertThat(tree).isDeepEqualTo(new int[][]{
                {0, 1, 1},
                {1, 2, 2},
                {2, 3, 4}
        });
    }

    @Test
    void shouldHandleParallelEdgesAndSelfLoop() {
        int[][] edges = {
                {0, 1, 7},
                {0, 1, 3},
                {1, 1, 1},
                {1, 2, 2}
        };

        int[][] tree = Crascal.kruskal(3, edges);

        assertThat(tree).isDeepEqualTo(new int[][]{
                {1, 2, 2},
                {0, 1, 3}
        });
    }

    @Test
    void shouldHandleNegativeWeights() {
        int[][] edges = {
                {0, 1, -5},
                {0, 2, 1},
                {1, 2, 2}
        };

        int[][] tree = Crascal.kruskal(3, edges);

        assertThat(tree).isDeepEqualTo(new int[][]{
                {0, 1, -5},
                {0, 2, 1}
        });
    }

    @Test
    void shouldReturnEmptyTreeForSingleVertex() {
        int[][] tree = Crascal.kruskal(1, new int[][]{});

        assertThat(tree).isEmpty();
    }

    @Test
    void shouldNotMutateInputEdges() {
        int[][] edges = {
                {0, 2, 3},
                {0, 1, 1},
                {1, 2, 2}
        };
        int[][] edgesCopy = {
                {0, 2, 3},
                {0, 1, 1},
                {1, 2, 2}
        };

        Crascal.kruskal(3, edges);

        assertThat(edges).isDeepEqualTo(edgesCopy);
    }

    @Test
    void shouldThrowWhenGraphIsDisconnected() {
        int[][] edges = {
                {0, 1, 1},
                {2, 3, 2}
        };

        assertThatThrownBy(() -> Crascal.kruskal(4, edges))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("not connected");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArguments")
    void shouldThrowWhenArgumentsAreInvalid(int vertexCount, int[][] edges) {
        assertThatThrownBy(() -> Crascal.kruskal(vertexCount, edges))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(0, new int[][]{}),
                Arguments.of(3, null),
                Arguments.of(3, new int[][]{{0}}),
                Arguments.of(3, new int[][]{{0, 4, 1}}),
                Arguments.of(3, new int[][]{{4, 0, 1}}),
                Arguments.of(3, new int[][]{null})
        );
    }
}
