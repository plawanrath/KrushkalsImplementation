import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.Map.Entry;

public class Krushkals {
	private ArrayList<Edge> edgeList = new ArrayList<Edge>();
	private ArrayList<String> vertexList = new ArrayList<String>();
	private HashMap<String,HashMap<String,Integer>> allEdges = new HashMap<String,HashMap<String,Integer>>();
	
	//Checks to see if string is in an ArrayList
	public boolean inMyList(ArrayList<String> ls, String s)
	{
		for(String temp : ls)
			if(temp.equals(s))
				return true;
		return false;
	}
	
	//FInd the city name based on index
	public int getIndexInList(ArrayList<String> ls, String s)
	{
		for(int i=0;i<ls.size();i++)
			if(ls.get(i).equals(s))
				return i;
		return -1;
	}
	
	//check if we have already put the path in the HashMap to avoid repitition of edges
	public boolean checkSrcDescInHashMap(HashMap<String,HashMap<String,Integer>> map,String s,String d)
	{
		int found = 0;
		Iterator mp = map.entrySet().iterator();
		while(mp.hasNext())
		{
			Entry pair = (Entry) mp.next();
			String s1 = (String) pair.getKey();
			if(s1.equals(s) || s1.equals(d))
			{
				HashMap<String,Integer> v = (HashMap<String, Integer>) pair.getValue();
				if(s1.equals(s))
				{
					if(v.get(d) != null)
					{
						found = 1;
						break;
					}
				}
				else
				{
					if(v.get(s) != null)
					{
						found = 1;
						break;
					}					
				}
			}
		}
		if(found == 0)
			return false;
		else
			return true;
	}
	
	//Read data and organize it, put all vetices in vertexList and edges in edgeList which has Edge class elements also use HashMap to avoid repitition
	public void readData(String filename)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = null;
			while((line = br.readLine()) != null)
			{
				String[] words = line.split(",");
				String src = words[0];
				String dest = null;
				for(int i=1;i<words.length;i++)
				{
					if(i%2 != 0)
						dest = words[i];
					else
					{
						if(dest != null)
						{
							int distance = Integer.parseInt(words[i]);
							if(this.checkSrcDescInHashMap(allEdges, src, dest) == false)
							{
								Edge v = new Edge(src,dest,distance);
								edgeList.add(v);
								if(allEdges.get(src) == null)
								{
									HashMap<String,Integer> val = new HashMap<String,Integer>();
									val.put(dest, distance);
									allEdges.put(src, val);
								}
								else
								{
									HashMap<String,Integer> tmp = allEdges.get(src);
									tmp.put(dest, distance);
								}
								if(this.inMyList(vertexList, src) == false && this.inMyList(vertexList, dest) == false)
								{
									vertexList.add(src);
									vertexList.add(dest);
								}
								else if(this.inMyList(vertexList, src) == false)
								{
									vertexList.add(src);								
								}
								else if(this.inMyList(vertexList, dest) == false)
								{
									vertexList.add(dest);								
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			System.exit(0);
		}
	}
			
	public ArrayList<Edge> getEdges()
	{
		return this.edgeList;
	}
	
	public ArrayList<String> getVertexList()
	{
		return this.vertexList;
	}
	
	//Implements Krushkal's algorithm taken from Book by Mark Allen Weiss
	public ArrayList<Edge> krushkal()
	{
		DisjSets ds = new DisjSets(this.vertexList.size());
		ArrayList<Edge> mst = new ArrayList<Edge>();
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(this.edgeList.size(),new EdgeComparator());
		for(int i=0;i<edgeList.size();i++)
			pq.add(edgeList.get(i));
		while(mst.size() != this.vertexList.size() -1)
		{
			Edge e = pq.poll();
			String u = e.getSource();
			String v = e.getDestination();
			int indU = this.getIndexInList(vertexList, u);
			int indV = this.getIndexInList(vertexList, v);
			int findU = ds.find(indU);
			int findV = ds.find(indV);
			if(findU != findV)
			{
				//Accept
				mst.add(e);
				ds.union(findU, findV);
			}
		}
		return mst;
	}
	
	public static void main(String[] args)
	{
		String file = "assn9_data.csv";
		Krushkals k = new Krushkals();
		k.readData(file);
		ArrayList<Edge> minimumCostSpanningTree = k.krushkal();
		int totalCost = 0;
		System.out.println("The minimum cost spanning tree will contain the following edges");
		System.out.println();
		for(int i=0;i<minimumCostSpanningTree.size();i++)
		{
			Edge e = minimumCostSpanningTree.get(i);
			System.out.println(e.getSource() + "------" + e.getDestination() + " = " + e.getDistance());
			totalCost = totalCost + e.getDistance();
		}
		System.out.println();
		System.out.println("The total cost of the MST is " + totalCost);
	}
}
