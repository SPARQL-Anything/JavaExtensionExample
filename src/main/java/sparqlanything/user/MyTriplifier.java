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
        String rootId = "my_root_id";
        String dataSourceId = SPARQLAnythingConstants.DATA_SOURCE_ID;

        InputStream inputStream = Triplifier.getInputStream(properties);
        facadeXGraphBuilder.addRoot(rootId);
        int slot = 1;
        for(int byteRead = inputStream.read(); byteRead!=-1; byteRead = inputStream.read()){
            facadeXGraphBuilder.addValue(dataSourceId, rootId, slot++, (char) byteRead);
        }
        inputStream.close();
    }

    @Override
    public Set<String> getMimeTypes() {
        return Sets.newHashSet("my-mime-type");
    }

    @Override
    public Set<String> getExtensions() {
        return Sets.newHashSet("myext");
    }
}
