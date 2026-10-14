import java.util.*;

public class KruskalMST {
    public static class MSTEdge implements Comparable<MSTEdge> {
        public String u, v;
        public double weight;
        public MSTEdge(String u, String v, double weight) {
            this.u = u; this.v = v; this.weight = weight;
        }
        @Override
        public int compareTo(MSTEdge o) {
            return Double.compare(this.weight, o.weight);
        }
    }

    public static class DSU {
        private final Map<String, String> parent = new HashMap<>();
        public void makeSet(Set<String> nodes) {
            for (String n : nodes) parent.put(n, n);
        }
        public String find(String i) {
            if (!parent.get(i).equals(i)) {
                parent.put(i, find(parent.get(i)));
            }
            return parent.get(i);
        }
        public boolean union(String i, String j) {
            String rootI = find(i), rootJ = find(j);
            if (!rootI.equals(rootJ)) {
                parent.put(rootI, rootJ);
                return true;
            }
            return false;
        }
    }

    public static List<MSTEdge> findMST(Graph network) {
        List<MSTEdge> mst = new ArrayList<>();
        List<MSTEdge> edges = network.getAllEdges();
        Collections.sort(edges);
        DSU dsu = new DSU();
        dsu.makeSet(network.getNodes());
        for (MSTEdge edge : edges) {
            if (dsu.union(edge.u, edge.v)) {
                mst.add(edge);
            }
        }
        return mst;
    }
}
