import java.util.*;

public class WeightedGraph<T> {
    private final boolean undirected;
    private final Map<T, Vertex<T>> vertices = new HashMap<>();

    public WeightedGraph() {
        this(true);
    }

    public WeightedGraph(boolean undirected) {
        this.undirected = undirected;
    }

    public void addVertex(T value) {
        vertices.computeIfAbsent(value, Vertex::new);
    }

    public boolean hasVertex(T value) {
        return vertices.containsKey(value);
    }

    public void addEdge(T from, T to, double weight) {
        addVertex(from);
        addVertex(to);
        Vertex<T> vFrom = vertices.get(from);
        Vertex<T> vTo = vertices.get(to);
        if (from.equals(to) || vFrom.getAdjacents().containsKey(vTo)) return;
        vFrom.addAdjacentVertex(vTo, weight);
        if (undirected) vTo.addAdjacentVertex(vFrom, weight);
    }

    public boolean hasEdge(T from, T to) {
        return hasVertex(from) && hasVertex(to)
                && vertices.get(from).getAdjacents().containsKey(vertices.get(to));
    }

    public double getWeight(T from, T to) {
        if (!hasEdge(from, to))
            throw new RuntimeException("Edge " + from + " -> " + to + " not found");
        return vertices.get(from).getAdjacents().get(vertices.get(to));
    }

    public Collection<T> adjacencyList(T value) {
        if (!hasVertex(value)) return List.of();
        return vertices.get(value).getAdjacents().keySet().stream()
                .map(Vertex::getData).toList();
    }

    public Map<T, Double> neighborsWithWeights(T value) {
        if (!hasVertex(value)) return Map.of();
        Map<T, Double> m = new HashMap<>();
        vertices.get(value).getAdjacents()
                .forEach((v, w) -> m.put(v.getData(), w));
        return m;
    }

    public Collection<Vertex<T>> getVertices() {
        return vertices.values();
    }

    public int getVerticesCount() {
        return vertices.size();
    }

    public int getEdgesCount() {
        int count = vertices.values().stream()
                .mapToInt(v -> v.getAdjacents().size()).sum();
        return undirected ? count / 2 : count;
    }
}
