import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {
	
	public int compare(Edge e1, Edge e2)
	{
		return e2.compareTo(e1);
	}

}
