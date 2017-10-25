package name.sxli.example;

import name.dxliu.oracle.DefaultOracle;

/**
 * This class demonstrate how to build an oracle for a given undirected-graph and store it for further usage.
 */
public class Step2_ExampleOracleUsage {
	
	public static void main(String[] args) {
		
		DefaultOracle oracle = new DefaultOracle();
		//construct
		oracle.ConstructIndex("example/out_undirected_graph");
		
		//store
		oracle.StoreIndex("example/oracle");//("src/oracle");//(args[1]);//	//"src/oracle"
		
		
		//load
		oracle=new DefaultOracle();
		oracle.LoadIndex("example/oracle");
		
		//query
		// 3 for Alice and 3 for Bob in example graph.
		int distance  = oracle.Query(3, 4);
		System.out.println("distance:"+distance);

		
	}
}
