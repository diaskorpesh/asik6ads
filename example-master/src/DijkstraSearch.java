import java.util.*;

public class DijkstraSearch<T> extends Search<T> {
    private final Set<T> unsettled = new HashSet<>();
    private final Map<T, Double> dist = new HashMap<>();
    private final WeightedGraph<T> graph;

    public DijkstraSearch(WeightedGraph<T> graph, T source) {
        super(source);
        this.graph = graph;
        run();
    }

    private void run() {
        dist.put(source, 0.0);
        unsettled.add(source);
        while (!unsettled.isEmpty()) {
            T current = getMin(unsettled);
            unsettled.remove(current);
            marked.add(current);
            for (Map.Entry<T, Double> e : graph.neighborsWithWeights(current).entrySet()) {
                T neighbor = e.getKey();
                double weight = e.getValue();
                double newDist = dist.get(current) + weight;
                if (newDist < dist.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    dist.put(neighbor, newDist);
                    edgeTo.put(neighbor, current);
                    unsettled.add(neighbor);
                }
            }
        }
    }

    private T getMin(Set<T> set) {
        T min = null;
        for (T v : set) {
            if (min == null || dist.get(v) < dist.get(min)) min = v;
        }
        return min;
    }

    public double distanceTo(T v) {
        return dist.getOrDefault(v, Double.POSITIVE_INFINITY);
    }
}
