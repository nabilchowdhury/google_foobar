import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Nabillionaire on 8/30/17.
 */
public class Q41 {

    static ArrayList<Integer> curPath;
    static ArrayList<Integer> foundPath;

    static boolean dfsHelper(int[][] graph, int src, int dest, boolean[] visited) {
        if (src == dest) {
            foundPath = new ArrayList(curPath);
            return true;
        }

        if (visited[src]) return false;

        visited[src] = true;
        curPath.add(src);

        for (int i = 0; i < graph[src].length; i++) {
            if (graph[src][i] != 0) { // edge found
                boolean found = dfsHelper(graph, i, dest, visited);
                if (found) return true;
            }
        }

        curPath.remove(curPath.size() - 1);

        return false;
    }

    static boolean dfs(int[][] graph, int src, int dest) {
        curPath = new ArrayList();
        foundPath = new ArrayList();
        return dfsHelper(graph, src, dest, new boolean[graph.length]);
    }

    static int[][] addSourceAndSink(int[][] graph, int[] srcs, int[] dests) {
        int newLength = graph.length + 2;
        int[][] newGraph = new int[newLength][newLength];

        // set source node outgoing edge capacities to inf
        for (int s : srcs) {
            newGraph[0][s + 1] = -1;
        }

        // set sink node incoming edge capacities to inf
        for (int d : dests) {
            newGraph[d + 1][newLength - 1] = -1;
        }

        // fill in rest of newGraph with graph
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++)
                newGraph[i + 1][j + 1] = graph[i][j];
        }

        return newGraph;
    }

    public static int computeMaxFlow(int[][] graph, int[] srcs, int[] dests) {
        int[][] newGraph = addSourceAndSink(graph, srcs, dests);
        int maxFlow = 0;

        while (dfs(newGraph, 0, newGraph.length - 1)) {
            int minVal = Integer.MAX_VALUE;

            for (int i = 1; i < foundPath.size() - 1; i++) minVal = Math.min(minVal, newGraph[foundPath.get(i)][foundPath.get(i + 1)]);
            for (int i = 1; i < foundPath.size() - 1; i++) {
                int from = foundPath.get(i);
                int to = foundPath.get(i+1);
                newGraph[from][to] -= minVal;
                newGraph[to][from] += minVal;
            }

            maxFlow += minVal == Integer.MAX_VALUE ? 0 : minVal;
        }

        return maxFlow;
    }


    public static void main(String[] args) {
        int[][] graph ={{0,0,4,6,0,0},
                        {0,0,5,2,0,0},
                        {0,0,0,0,4,4},
                        {0,0,0,0,6,6},
                        {0,0,0,0,0,0},
                        {0,0,0,0,0,0}};

        int[] srcs = {0,1};
        int[] dests = {4,5};
        int[][] newGraph = addSourceAndSink(graph, srcs, dests);
        for (int[] r : newGraph) {
            System.out.println(Arrays.toString(r));
        }

        System.out.println(computeMaxFlow(graph, srcs, dests));

    }

}
