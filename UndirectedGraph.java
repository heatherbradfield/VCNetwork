import java.util.*;

/**
 * A class that implements the ADT undirected graph.
 *
 * @author Heather Bradfield
 * @version 11/29/2015
 */

//public class UndirectedGraph<T> extends DirectedGraph<T> implements GraphInterface<T>, java.io.Serializable
public class UndirectedGraph<T> extends DirectedGraph<T> implements ConnectedGraphInterface<T>, java.io.Serializable
{
    public UndirectedGraph()
    {
        super();
    }

    public boolean addEdge(T begin, T end, double edgeWeight)
    {
        return super.addEdge(begin, end, edgeWeight) && super.addEdge(end, begin, edgeWeight);
    }

    public boolean addEdge(T begin, T end)
    {
        return super.addEdge(begin, end) && super.addEdge(end, begin);
    }

    public int getNumberOfEdges()
    {
        return super.getNumberOfEdges()/2;
    }

    public Stack<T> getTopologicalOrder() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public boolean isConnected(T origin)
    {
        boolean result = false;
        if (!isEmpty())
        {
            Queue<T> traversal = super.getBreadthFirstTraversal(origin);
            int numOfVertices = super.getNumberOfVertices();
            result = numOfVertices == traversal.size();
        }
        return result;
    }
}