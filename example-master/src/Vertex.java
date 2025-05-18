import java.util.*;

public class Vertex<T> {
    private final T data;
    private final Map<Vertex<T>, Double> adjacentVertices = new HashMap<>();

    public Vertex(T data) {
        this.data = data;
    }

    public void addAdjacentVertex(Vertex<T> dest, double weight) {
        adjacentVertices.put(dest, weight);
    }

    public Map<Vertex<T>, Double> getAdjacents() {
        return Collections.unmodifiableMap(adjacentVertices);
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Vertex<?> v) && Objects.equals(data, v.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
