package name.sxli.example;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.graph.DirectedMultigraph;

import name.dxliu.bean.IntegerEdge;
import name.dxliu.example.ExampleGraphAgent;
import name.dxliu.example.ExampleOracleAgent;
import name.sxli.qrel.IQueryRelaxation;
import name.sxli.qrel.QueryRelaxationOPT;

/**
 * This class demonstrate how to run the relaxquery algorithm.
 * The result of query relaxation is an array which is a subset of the query.
 * 
 */
public class Step3_ExampleQueryRelaxation {
	public static void main(String[] args) throws Exception {
		// read entity-relation graph.
		DirectedMultigraph<Integer, IntegerEdge> graph = new DirectedMultigraph<>(IntegerEdge.class);
		List<String> allLine = Files.readAllLines(Paths.get("example/out_id_relation_triples"), Charset.defaultCharset());
		for (String line : allLine) {
			String[] spo = line.split(" ");
			Integer source = Integer.valueOf(spo[0]);
			Integer target = Integer.valueOf(spo[2]);

			graph.addVertex(source);
			graph.addVertex(target);

			graph.addEdge(source, target, new IntegerEdge(source, target, Integer.parseInt(spo[1])));
		}
		ExampleGraphAgent graphAgent = new ExampleGraphAgent(graph);
		ExampleOracleAgent oracleAgent = new ExampleOracleAgent();
		
		Map<String,Integer> dictionary = readDictionary();
		
		int[] queryEntities=new int[3];
		queryEntities[0]=dictionary.get("Alice");
		queryEntities[1]=dictionary.get("Bob");
		queryEntities[2]=dictionary.get("Dan");
		
		int diameter=3;
		IQueryRelaxation qrel=new QueryRelaxationOPT();
		int[] result=qrel.relaxQuery(graphAgent, oracleAgent, diameter, queryEntities);
		
		for(Integer id:result){
			for(Map.Entry<String, Integer> entry:dictionary.entrySet()){
				if(entry.getValue()==id)
					System.out.print(entry.getKey()+" ");
			}
		}
	}
	
	private static Map<String,Integer> readDictionary() throws IOException {
		Map<String, Integer> dictionary = new TreeMap<>();
		List<String> allLine = Files.readAllLines(Paths.get("example/out_dict"), Charset.defaultCharset());
		for (String line : allLine) {
			String[] spo = line.split(",");
			Integer id = Integer.valueOf(spo[0]);
			String uri = spo[1];
			dictionary.put(uri, id);
		}
		return dictionary;
	}
}
