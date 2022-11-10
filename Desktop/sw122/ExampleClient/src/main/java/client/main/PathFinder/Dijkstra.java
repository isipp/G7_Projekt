package client.main.PathFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

class Dijkstra {
    static List<Integer> li = new ArrayList<>();
    private static void printRoute(int prev[], int i) {
        if (i < 1)
            return;

        printRoute(prev, prev[i]);
        System.out.print(i + " ");
    }
    private static void getRoute(int prev[], int i) {
        if (i < 1)
            return;

        getRoute(prev, prev[i]);
        li.add(i);
    }

    public static List<Integer> getLi() {
        return li;
    }

    // Run Dijkstra's algorithm on given graph
    public static void shortestPath(Graph graph, int source, int N, int dest) {
        // create min heap and push source node having distance 0
        PriorityQueue<Node> minHeap = new PriorityQueue<>(
                (lhs, rhs) -> lhs.weight - rhs.weight);

        minHeap.add(new Node(source, 0));

        // set infinite distance from source to v initially
        List<Integer> dist = new ArrayList<>(
                Collections.nCopies(N, Integer.MAX_VALUE));

        // distance from source to itself is zero
        dist.set(source, 0);

        // boolean array to track vertices for which minimum
        // cost is already found
        boolean[] done = new boolean[N];
        done[0] = true;

        // stores predecessor of a vertex (to print path)
        int prev[] = new int[N];
        prev[0] = -1;

        // run till minHeap is not empty
        while (!minHeap.isEmpty()) {
            // Remove and return best vertex
            Node node = minHeap.poll();

            // get vertex number
            int u = node.vertex;

            // do for each neighbor v of u
            for (Edge edge : graph.adjList.get(u)) {
                int v = edge.dest;
                int weight = edge.weight;

                // Relaxation step
                if (!done[v] && (dist.get(u) + weight) < dist.get(v)) {
                    dist.set(v, dist.get(u) + weight);
                    prev[v] = u;
                    minHeap.add(new Node(v, dist.get(v)));
                }
            }

            // marked vertex u as done so it will not get picked up again
            done[u] = true;
        }

        li = new ArrayList<>();
        for (int i = 1; i < N; ++i) {
            if(dest == i){
                getRoute(prev, i);
            }

         //   printRoute(prev, i);
         //   System.out.println("]");
        }


    }
}
