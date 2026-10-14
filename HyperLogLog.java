public class HyperLogLog {
    private final int b, m;
    private final int[] registers;
    private final double alpha;

    public HyperLogLog(int b) {
        this.b = b;
        this.m = 1 << b;
        this.registers = new int[m];
        if (m == 16) alpha = 0.673;
        else if (m == 32) alpha = 0.697;
        else if (m == 64) alpha = 0.709;
        else alpha = 0.7213 / (1.0 + 1.079 / m);
    }

    public void add(String id) {
        int hash = id.hashCode();
        int j = hash & (m - 1);
        int w = hash >>> b;
        int rank = Integer.numberOfLeadingZeros(w) + 1;
        registers[j] = Math.max(registers[j], rank);
    }

    public double estimateCardinality() {
        double sum = 0.0;
        for (int val : registers) sum += Math.pow(2.0, -val);
        double raw = alpha * m * m / sum;
        if (raw <= 2.5 * m) {
            int zeros = 0;
            for (int val : registers) if (val == 0) zeros++;
            if (zeros > 0) raw = m * Math.log((double) m / zeros);
        }
        return raw;
    }
}
