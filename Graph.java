import java.util.*;

public class Graph {
    public static class Edge {
        public String target;
        public double weight;
        public Edge(String target, double weight) {
            this.target = target;
            this.weight = weight;
        }
    }


    private final Map<String, List<Edge>> adjList = new HashMap<>();

    public void addEdge(String u, String v, double weight) {

        adjList.putIfAbsent(u, new ArrayList<>());
        adjList.putIfAbsent(v, new ArrayList<>());

        adjList.get(u).add(new Edge(v, weight));
        adjList.get(v).add(new Edge(u, weight));
    }

    public List<Edge> getNeighbors(String node) {
        return adjList.getOrDefault(node, new ArrayList<>());
    }

    public Set<String> getNodes() {
        return adjList.keySet();
    }

    public List<KruskalMST.MSTEdge> getAllEdges() {
        List<KruskalMST.MSTEdge> all = new ArrayList<>();
        Set<String> vis = new HashSet<>();
        for (String u : adjList.keySet()) {
            for (Edge e : adjList.get(u)) {
                String key = u.compareTo(e.target) < 0 ? u + "-" + e.target : e.target + "-" + u;
                if (!vis.contains(key)) {
                    vis.add(key);
                    all.add(new KruskalMST.MSTEdge(u, e.target, e.weight));
                }
            }
        }
        return all;
    }
}