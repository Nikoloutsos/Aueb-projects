import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Flatten;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;

import java.util.HashMap;

/**
 * Removes duplicates from amenities and adds an auto-increment Primary key
 * It handles threads with synchronized keyword because hashmap does not support Multi-threading
 */
public class AirbnbAmenityBridging {
    public static int number = 0;
    static final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
    private static Pipeline pipeline;

    public static void main(String[] args) {
        PipelineOptions options = PipelineOptionsFactory.create();
        pipeline = Pipeline.create(options);

        String basePath = AirbnbConstants.BASE_PATH;
        String[] amenity_files = {basePath + AirbnbConstants.PATH_AMENITY_AUSTIN,
        basePath + AirbnbConstants.PATH_AMENITY_BOSTON, basePath + AirbnbConstants.PATH_AMENITY_DENVER,
                basePath + AirbnbConstants.PATH_AMENITY_PORTLAND};

        PCollectionList<String> collectionList = PCollectionList.empty(pipeline);

        for (int i = 0; i < 4; i++) {
            String path = amenity_files[i];
            PCollection<String> t = pipeline.apply(TextIO.read().from(path));
            collectionList = collectionList.and(t);
        }
        PCollection<String> finalCollection = collectionList.apply(Flatten.<String>pCollections());

        finalCollection.apply(MapElements.via(new SimpleFunction<String, String>() {
            @Override
            public String apply(String input) {
                String[] items = input.split(Constants.DELIMETER);
                String amenity_type = items[1].toLowerCase();
                if(isFound(amenity_type)) return getNumber() + "," +amenity_type;
                else return "aaaaaaaaaaaaaa";
            }
        })).apply(TextIO.write().to(AirbnbConstants.PATH_WRITE_AMENITY).withoutSharding());
        pipeline.run().waitUntilFinish();
    }


    static synchronized boolean isFound(String amenity_type){
        if(hashMap.containsKey(amenity_type)){
            return false;
        }else{
            hashMap.put(amenity_type, true);
            return true;
        }
    }

    static synchronized int getNumber(){
        return ++number;
    }
}
