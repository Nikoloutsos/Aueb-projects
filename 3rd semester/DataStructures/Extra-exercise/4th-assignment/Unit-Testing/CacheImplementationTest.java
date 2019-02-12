import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CacheImplementationTest {

    /**
     * This is a small Test (Needs approx 1-10seconds)
     */
    @Test
    public void small_data_test() throws IOException {
        String dataFile = "C:\\Users\\KwstasNiks\\Desktop\\data-1000.dat";
        String requestsFile = "C:\\Users\\KwstasNiks\\Desktop\\requests-10000.dat";
        int cachesize = 100;

        Cache<String, String> cache = new CacheImplementation<>(cachesize);

        DataSource dataSource = new DataSource(dataFile);
        WorkloadReader requestReader = new WorkloadReader(requestsFile);

        String key;

        while ((key = requestReader.nextRequest()) != null) {
            String data = cache.lookUp(key);
            if (data == null) {//data not in cache
                data = dataSource.readItem(key);
                if (data == null) {
                    throw new IllegalArgumentException("DID NOT FIND DATA WITH KEY " + key + ". Have you set up files properly?");
                } else {
                    cache.store(key, data);
                }
            }
        }
        requestReader.close();
        Assert.assertEquals(cache.getHits(), 1030);
    }

    /**
     * This is a Large Test (Needs approx 1 minute)
     */
    @Test
    public void Large_Data_Test() throws IOException {
        String dataFile = "C:\\Users\\KwstasNiks\\Desktop\\data-5000.dat";
        String requestsFile = "C:\\Users\\KwstasNiks\\Desktop\\requests-100000.dat";
        int cachesize = 500;

        Cache<String, String> cache = new CacheImplementation<>(cachesize);

        DataSource dataSource = new DataSource(dataFile);
        WorkloadReader requestReader = new WorkloadReader(requestsFile);

        String key;

        while ((key = requestReader.nextRequest()) != null) {
            String data = cache.lookUp(key);
            if (data == null) {//data not in cache
                data = dataSource.readItem(key);
                if (data == null) {
                    throw new IllegalArgumentException("DID NOT FIND DATA WITH KEY " + key + ". Have you set up files properly?");
                } else {
                    cache.store(key, data);
                }
            }
        }
        requestReader.close();
        Assert.assertEquals(cache.getHits(), 10074);
    }

    /**
     * cacheSize ought to be a Natural number.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void IllegalArgument_Test() {
        Cache<String, String> cache = new CacheImplementation<>(-100);
    }





}