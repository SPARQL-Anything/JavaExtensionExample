package sparqlanything.user ;

import io.github.sparqlanything.engine.FacadeX;
import org.apache.jena.query.*;
import org.apache.jena.sparql.engine.main.QC;

public class SPARQLAnythingClientViaSPARQL {

	public static void main(String[] args){

		// Set FacadeX OpExecutor as default executor factory
		QC.setFactory(ARQ.getContext(), FacadeX.ExecutorFactory);

		// Execute the query by using standard Jena ARQ's API
		Dataset kb = DatasetFactory.createGeneral();

		Query query = QueryFactory.create(
						"PREFIX fx:  <http://sparql.xyz/facade-x/ns/> " +
						"PREFIX xyz: <http://sparql.xyz/facade-x/data/> " +
						"SELECT ?o { " +
								"SERVICE <x-sparql-anything:> { " +
									"fx:properties fx:content '[1,2,3]' ; " +
										"fx:media-type 'application/json' . " +
									"?s fx:anySlot ?o" +
								"}}");

		System.out.println(ResultSetFormatter.asText(QueryExecutionFactory.create(query,kb).execSelect()));


	}
}
