import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    //call hashset directly
    shortHelper(vertex, k, new HashSet<>());
  }

  public static void shortHelper(Vertex<String> vertex, int k, Set<Vertex<String>> visited) {
    // If we've already visited the neighbor or null, skip over it
    if (vertex == null || visited.contains(vertex)) return;

    // If visiting a 'new' neighbor, add it to the hashset and print its value if less than k
    visited.add(vertex);
    if (vertex.data.length() < k) {
      System.out.println(vertex.data);
    }

    //For each loop to recursively visit all neighbors
    for (Vertex<String> neighbor : vertex.neighbors) {
      shortHelper(neighbor, k, visited);
    }


  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    return longestHelper(vertex, new HashSet<>());
  }

  public static String longestHelper(Vertex<String> vertex, Set<Vertex<String>> visited) {
    if (vertex == null || visited.contains(vertex)) return "";

    visited.add(vertex);
    String longest = vertex.data;
    
    for (Vertex<String> neighbor : vertex.neighbors) {
      String nLength = longestHelper(neighbor, visited);
      if (nLength.length() > longest.length()) {
        longest = nLength;
      }
    }    
    return longest;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    printSelfHelper(vertex, new HashSet<>());
  }

  public static <T> void printSelfHelper(Vertex<T> vertex, Set<Vertex<T>> visited) {
    if (vertex == null || visited.contains(vertex)) return;

    // If visiting a 'new' neighbor, add it to the hashset and print its value
    visited.add(vertex);
    
    // Check if the vertex has itself as a neighbor (self-loop)
    if(vertex.neighbors.contains(vertex)) {
      System.out.println(vertex.data);
    }

    // For each loop to recursively visit all neighbors
    for (Vertex<T> neighbor : vertex.neighbors) {
      printSelfHelper(neighbor, visited);
    }

  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    return reachHelper(start, destination, new HashSet<>());
  }
  
  public static boolean reachHelper(Airport current, Airport destination, Set<Airport> visited) {
    if (current == null || visited.contains(current)) return false;

    if (current == destination) return true;

    visited.add(current);

    for (Airport neighbor : current.getOutboundFlights()) {
      if (reachHelper(neighbor, destination, visited)) return true;
    }
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    if (graph == null || graph.isEmpty()) return new HashSet<>();  

  if (!graph.containsKey(starting)) return new HashSet<>(graph.keySet());

  Set<T> visited = new HashSet<>();
  unreachHelper(starting, graph, visited);

  Set<T> unreachableNodes = new HashSet<>(graph.keySet());
  unreachableNodes.removeAll(visited);

  return unreachableNodes;
  }

  public static <T> void unreachHelper(T node, Map<T, List<T>> graph, Set<T> visited) {
    if (visited.contains(node)) return;

    visited.add(node);

    for (T neighbor : graph.getOrDefault(node, Collections.emptyList())) {
        unreachHelper(neighbor, graph, visited);
    }
  }
}
