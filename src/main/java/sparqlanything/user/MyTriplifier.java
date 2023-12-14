package sparqlanything.user;

import com.google.common.collect.Sets;
import io.github.sparqlanything.model.FacadeXGraphBuilder;
import io.github.sparqlanything.model.SPARQLAnythingConstants;
import io.github.sparqlanything.model.Triplifier;
import io.github.sparqlanything.model.TriplifierHTTPException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class MyTriplifier implements Triplifier {

    @Override
    public void triplify(Properties properties, FacadeXGraphBuilder facadeXGraphBuilder) throws IOException, TriplifierHTTPException {

        // Declare the identifier of the data source id, use the default data source id "".
        String dataSourceId = SPARQLAnythingConstants.DATA_SOURCE_ID;

        // Get the identifier of the root container
        String rootId = Triplifier.getRootArgument(properties);

        // Get the input stream form the resource
        InputStream inputStream = Triplifier.getInputStream(properties);

        // add the root container
        facadeXGraphBuilder.addRoot(dataSourceId);

        // add slots to the root container
        int slot = 1;
        for (int byteRead = inputStream.read(); byteRead != -1; byteRead = inputStream.read()) {
            facadeXGraphBuilder.addValue(dataSourceId, rootId, slot++, (char) byteRead);
        }
        inputStream.close();
    }

    /*
    Define the mime types of the triplifier
     */
    @Override
    public Set<String> getMimeTypes() {
        return Sets.newHashSet("my-mime-type");
    }

    /*
    Define the mime types of the extensions
     */
    @Override
    public Set<String> getExtensions() {
        return Sets.newHashSet("myext");
    }
}
