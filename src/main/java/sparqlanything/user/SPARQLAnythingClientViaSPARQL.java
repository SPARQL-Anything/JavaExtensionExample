package sparqlanything.user ;

import io.github.sparqlanything.engine.FacadeX;
import io.github.sparqlanything.model.TriplifierRegister;
import io.github.sparqlanything.model.TriplifierRegisterException;
import org.apache.jena.query.*;
import org.apache.jena.sparql.engine.main.QC;
import org.apache.jena.sparql.function.FunctionRegistry;
import org.apache.jena.sparql.pfunction.PropertyFunctionRegistry;
import org.apache.jena.sys.JenaSystem;

public class SPARQLAnythingClientViaSPARQL {

	public static void main(String[] args) throws  TriplifierRegisterException {

		System.out.println(PropertyFunctionRegistry.chooseRegistry(ARQ.getContext()));

		JenaSystem.init();

		// Set FacadeX OpExecutor as default executor factory
		QC.setFactory(ARQ.getContext(), FacadeX.ExecutorFactory);

		// Register the new Triplifier
		TriplifierRegister.getInstance().registerTriplifier(MyTriplifier.class.getCanonicalName(), new String[]{"myext"}, new String[]{"my-mime-type"});

		// Register the new function
		FunctionRegistry.get().put("http://example.org/theAnswer", TheAnswer.class);

		// Register the new magic property
		PropertyFunctionRegistry.get().put("http://example.org/assign42", Assign42.class);

		// Execute the query by using standard Jena ARQ's API
		Dataset kb = DatasetFactory.createGeneral();

		Query query = QueryFactory.create(
						"PREFIX fx:  <http://sparql.xyz/facade-x/ns/> " +
						"PREFIX xyz: <http://sparql.xyz/facade-x/data/> " +
						"SELECT ?slotNumber ?o ?assignment ?answer{ " +
								"SERVICE <x-sparql-anything:> { " +
									"fx:properties fx:content 'abc' ; " +
										"fx:media-type 'my-mime-type' . " +
									"?s ?p ?o ." +
									"?s <http://example.org/assign42> ?assignment " +
									"BIND(fx:cardinal(?p) AS ?slotNumber) " +
									"BIND(<http://example.org/theAnswer>(?p) AS ?answer) " +
									"FILTER(BOUND(?slotNumber))" +
								"}}");

		System.out.println(ResultSetFormatter.asText(QueryExecutionFactory.create(query,kb).execSelect()));


	}
}
