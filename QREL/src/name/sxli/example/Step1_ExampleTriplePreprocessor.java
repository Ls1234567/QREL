package name.sxli.example;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jgrapht.graph.SimpleGraph;

import name.dxliu.bean.SimpleEdge;

/**
 * This class is an example class for preprocessing of the entity-relations graph's triples.
 * The expected result of this class contains a dictionary of URI<-->Id, and triples described by Id corresponding to the 
 * original triple file. Id numbers from entities, types and properties must be separable (required in mining stage).
 */
public class Step1_ExampleTriplePreprocessor {
	
	public static void main(String[] args) throws IOException {
		List<String> allLine = Files.readAllLines(Paths.get("example/ExampleTriples"), Charset.defaultCharset());
		
		Set<String> properties = new TreeSet<>();
		Set<String> entities = new TreeSet<>();
		
		for(String line:allLine){
			String[] triple = line.split(",");	
			entities.add(triple[0]);
			properties.add(triple[1]);
			entities.add(triple[2]);
		}
		List<String> vocabularies = new ArrayList<>();
		int propertyRange,entityRange;
		
		vocabularies.addAll(properties);
		String propertiesRange = "0-"+(vocabularies.size()-1);		
		propertyRange = vocabularies.size()-1;
		
		vocabularies.addAll(entities);
		String entitiesRange = (vocabularies.size()-entities.size())+"-"+ (vocabularies.size()-1);
		entityRange = vocabularies.size()-1;
		System.out.println(propertiesRange+','+entitiesRange);
		
		Map<String,Integer> dictionary = new TreeMap<>();
		for(int i = 0,len = vocabularies.size();i<len;i++){
			dictionary.put(vocabularies.get(i), i);
		}
		
		String outDir = "example";
		FileWriter fileWriter = new FileWriter(outDir+"/out_id_relation_triples");
		
		SimpleGraph< Integer, SimpleEdge> graph = new SimpleGraph<>(SimpleEdge.class);
		
		for(String line:allLine){
			String[] triple = line.split(",");				
			
			fileWriter.write(dictionary.get(triple[0])+" "+dictionary.get(triple[1])+" "+dictionary.get(triple[2])+"\n");
			fileWriter.flush();		
			
			graph.addVertex(dictionary.get(triple[0]));
			graph.addVertex(dictionary.get(triple[2]));
			graph.addEdge(dictionary.get(triple[0]), dictionary.get(triple[2]));

		}
		fileWriter.close();
		
		fileWriter = new FileWriter(outDir+"/out_dict");
		for(int i = 0,len = vocabularies.size();i<len;i++){
			fileWriter.write(i+","+vocabularies.get(i)+"\n");
			fileWriter.flush();			
		}
		fileWriter.close();
		
		fileWriter = new FileWriter(outDir+"/out_undirected_graph");
		for(SimpleEdge edges : graph.edgeSet()){
			
			Integer source =  (Integer) edges.getSource();
			Integer target =  (Integer) edges.getTarget();
			
			fileWriter.write(source+" "+target+"\n");
			fileWriter.flush();	
		}
		fileWriter.close();
		
		fileWriter = new FileWriter(outDir+"/out_id_range");		
		fileWriter.write("property:"+0+"-"+propertyRange+"\n");
		fileWriter.write("entity:"+(propertyRange+1)+"-"+entityRange+"\n");
		fileWriter.flush();	
		fileWriter.close();
		System.out.println(propertyRange+" "+entityRange);
		
	}
	
}
