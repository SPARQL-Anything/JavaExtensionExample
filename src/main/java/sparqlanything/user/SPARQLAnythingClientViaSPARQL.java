package sparqlanything.user ;

import io.github.sparqlanything.engine.FacadeX;
import io.github.sparqlanything.engine.TriplifierRegisterException;
import org.apache.jena.query.*;
import org.apache.jena.sparql.engine.main.QC;

public class SPARQLAnythingClientViaSPARQL {

	public static void main(String[] args) throws TriplifierRegisterException {

		// Set FacadeX OpExecutor as default executor factory
		QC.setFactory(ARQ.getContext(), FacadeX.ExecutorFactory);

		// Register the new Triplifier
		FacadeX.Registry.registerTriplifier(MyTriplifier.class.getCanonicalName(), new String[]{"myext"}, new String[]{"my-mime-type"});

		// Execute the query by using standard Jena ARQ's API
		Dataset kb = DatasetFactory.createGeneral();

		Query query = QueryFactory.create(
						"PREFIX fx:  <http://sparql.xyz/facade-x/ns/> " +
						"PREFIX xyz: <http://sparql.xyz/facade-x/data/> " +
						"SELECT ?slotNumber ?o { " +
								"SERVICE <x-sparql-anything:> { " +
									"fx:properties fx:content 'abc' ; " +
										"fx:media-type 'my-mime-type' . " +
									"?s ?p ?o " +
									"BIND(fx:cardinal(?p) AS ?slotNumber) " +
									"FILTER(BOUND(?slotNumber))" +
								"}}");

		System.out.println(ResultSetFormatter.asText(QueryExecutionFactory.create(query,kb).execSelect()));


	}
}
