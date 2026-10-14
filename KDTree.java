public class KDTree {
    private static class KDNode {
        double[] point;
        String towerId;
        KDNode left, right;
        public KDNode(double[] point, String towerId) {
            this.point = point; this.towerId = towerId;
        }
    }

    private KDNode root;

    public void insert(double[] point, String towerId) {
        root = insertRec(root, point, towerId, 0);
    }

    private KDNode insertRec(KDNode node, double[] point, String towerId, int depth) {
        if (node == null) return new KDNode(point, towerId);
        int cd = depth % 2;
        if (point[cd] < node.point[cd]) node.left = insertRec(node.left, point, towerId, depth + 1);
        else node.right = insertRec(node.right, point, towerId, depth + 1);
        return node;
    }

    public String findNearest(double[] target) {
        Holder best = new Holder();
        findNearestRec(root, target, 0, best);
        return best.towerId;
    }

    private static class Holder {
        String towerId = null;
        double minDist = Double.MAX_VALUE;
    }

    private void findNearestRec(KDNode node, double[] target, int depth, Holder best) {
        if (node == null) return;
        double d = Math.sqrt(Math.pow(node.point[0] - target[0], 2) + Math.pow(node.point[1] - target[1], 2));
        if (d < best.minDist) { best.minDist = d; best.towerId = node.towerId; }
        int cd = depth % 2;
        boolean diff = target[cd] < node.point[cd];
        KDNode first = diff ? node.left : node.right;
        KDNode second = diff ? node.right : node.left;
        findNearestRec(first, target, depth + 1, best);
        if (Math.abs(target[cd] - node.point[cd]) < best.minDist) findNearestRec(second, target, depth + 1, best);
    }
}
