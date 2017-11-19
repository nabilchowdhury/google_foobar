import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Nabillionaire on 8/30/17.
 */
// Blossom algorithm implementation
public class Q42 {

    static class Edge {

        int from, to;

        Edge(int f, int t) {
            if (f > t) {
                int temp = f;
                f = t;
                t = temp;
            }
            from = f;
            to = t;
        }

        public String toString() {
            return "("+from+", "+to+")";
        }
    }

    static ArrayList<Edge> nonMatchingEdgeSet;
    static ArrayList<Edge> matchingEdgeSet;

    static int gcd(int x, int y) {
        while (y != 0) {
            int t = y;
            y = x % y;
            x = t;
        }

        return x;
    }

    static boolean checkIfInfinite(int x, int y) {
        int div = (x + y) / gcd(x, y);
        return (div & (div - 1)) != 0;
    }

    static int[][] createGraph(int[] arr) {

        int[][] graph = new int[arr.length][arr.length];

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (checkIfInfinite(arr[i], arr[j])) {
                    graph[i][j] = 1;
                    graph[j][i] = 1;
                    nonMatchingEdgeSet.add(new Edge(i, j));
                }
            }
        }

        return graph;
    }

    static int findEdge(int a, int b, ArrayList<Edge> edgeList) {
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        for (int i = 0; i < edgeList.size(); i++) {
            if (edgeList.get(i).from == a && edgeList.get(i).to == b) return i;
        }

        return -1;
    }

    public static int answer(int[] banana_list) {

        matchingEdgeSet = new ArrayList<>();
        nonMatchingEdgeSet = new ArrayList<>();

        ArrayList<Integer> exposed = new ArrayList<>();
        for (int i = 0; i < banana_list.length; i++) exposed.add(i);

        int[][] graph = createGraph(banana_list); // Also fills up nonMatchingEdgeSet

        boolean augmentingPathFound = true;

        while (augmentingPathFound) {
            augmentingPathFound = false;

            for (int i = 0; i < exposed.size(); i++) {
                boolean[] visited = new boolean[banana_list.length];
                ArrayList<Integer> augPath = new ArrayList<>();

                int curNode = exposed.get(i);
                boolean edgeInMatchingSet = false;

                for (;;) {
                    boolean found = false;
                    visited[curNode] = true;
                    int neighbor = -1;

                    for (int v = 0; v < banana_list.length; v++) {
                        if (!visited[v] && graph[curNode][v] == 1) {
                            if (!edgeInMatchingSet && findEdge(curNode, v, nonMatchingEdgeSet) > -1 || edgeInMatchingSet && findEdge(curNode, v, matchingEdgeSet) > -1) {
                                found = true;
                                neighbor = v;
                                edgeInMatchingSet = !edgeInMatchingSet;
                                break;
                            }
                        }
                    }


                    if (!found) {
                        if (augPath.size() > 0) {
                            curNode = augPath.get(augPath.size() - 1);
                            augPath.remove(augPath.size() - 1);
                            edgeInMatchingSet = !edgeInMatchingSet;
                        } else break;
                    } else {
                        augPath.add(curNode);
                        curNode = neighbor;
                    }

                    if (!exposed.contains(curNode) || curNode == exposed.get(i)) continue;

                    // augmented path found
                    augPath.add(curNode);

                    for (int p = 0; p < augPath.size() - 1; p++) {
                        int a = augPath.get(p);
                        int b = augPath.get(p + 1);
                        int inMatchingIndex = findEdge(a, b, matchingEdgeSet);
                        if (inMatchingIndex > -1) {
                            matchingEdgeSet.remove(inMatchingIndex);
                            nonMatchingEdgeSet.add(new Edge(a, b));
                        } else {
                            matchingEdgeSet.add(new Edge(a, b));
                            nonMatchingEdgeSet.remove(findEdge(a, b, nonMatchingEdgeSet));
                        }

                    }

                    exposed.remove(augPath.get(0));
                    exposed.remove(augPath.get(augPath.size() - 1));
                    augmentingPathFound = true;
                    break;

                }
            }


        }

        System.out.println(matchingEdgeSet.toString());

        return exposed.size();
    }

    public static void main(String[] args) {
        int[] arr = {1,7,3,21,13,19};
//        for (int i = 0; i < arr.length - 1; i++) {
//            for (int j = i + 1; j < arr.length; j++) {
//                System.out.println("("+arr[i]+", "+arr[j]+") -> "+checkIfInfinite(arr[i], arr[j]));
//            }
//        }

        System.out.println(answer(arr));

    }

}
