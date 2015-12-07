import java.util.*;

public class Edge implements Comparable<Edge>{
	
	private String source;
	private String destination;
	private int distance;
	
	public Edge(String source, String destination,int distance)
	{
		this.source = source;
		this.destination = destination;
		this.distance = distance;
	}
	public String getSource()
	{
		return this.source;
	}
	public String getDestination()
	{
		return this.destination;
	}
	
	public int getDistance()
	{
		return this.distance;
	}
	
	public int compareTo(Edge e2)
	{
		return Integer.compare(e2.getDistance(), distance);
	}
}
