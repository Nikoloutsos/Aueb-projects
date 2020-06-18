package test;

import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.*;

public class ZooKeeperTest {
    public static final String MOCK_CONSUMER_REGISTER_REQUEST = "{ \"identity\" : \"consumer\"}";
    public static final String MOCK_BROKER_REGISTER_REQUEST = "{ \"identity\" : \"BROKER\", \"connection_info\" : \"127.0.0.1:3030\" }";
    public static final String MOCK_PUBLISHER_REGISTER_REQUEST = "{ \"identity\" : \"PUBLISHER\", \"connection_info\" : \"127.0.0.2:6000\", \"artists\" : [ \"adele\", \"green day\", \"simple plan\", \"Shakira\", \"Bruno mars\", \"Justin bieber\", \"test\", \"test1\"] }";

    /**
     * Manual test
     */
    @Test
    public void should_return_correct_sync_info() throws Exception{
        /**
         * Before running this test open zookeeper server
         */

        // CREATE MOCK BROKER
        Socket mock1 = new Socket("localhost", 666);

        Socket mock2 = new Socket("localhost", 666);

        Socket mock3 = new Socket("localhost", 666);

        PrintWriter pr1 = new PrintWriter(mock1.getOutputStream(), true);
        PrintWriter pr2 = new PrintWriter(mock2.getOutputStream(), true);
        PrintWriter pr3 = new PrintWriter(mock3.getOutputStream(), true);

        pr1.println("{ \"identity\" : \"BROKER\", \"connection_info\" : \"127.0.0.1:3030\" }");
        pr2.println("{ \"identity\" : \"BROKER\", \"connection_info\" : \"127.0.0.1:3031\" }");
        pr3.println("{ \"identity\" : \"BROKER\", \"connection_info\" : \"127.0.0.1:3032\" }");

        // CREATE PUBLISHER
        Socket mock11 = new Socket("localhost", 666);

//        Socket mock22 = new Socket("localhost", 666);

        PrintWriter pr11 = new PrintWriter(mock11.getOutputStream(), true);
        pr11.println(MOCK_PUBLISHER_REGISTER_REQUEST);
//        PrintWriter pr22 = new PrintWriter(mock22.getOutputStream(), true);


        /**
         * Manually type 'START' to zookeeper
         */
        System.out.println("REMEMBER: type 'START' into zookeeper terminal");

        //Take a random broker's received sync info
        BufferedReader br = new BufferedReader(new InputStreamReader(mock1.getInputStream()));
        String syncInfo = br.readLine();
        System.out.println(syncInfo);

        JSONObject jsonObject = new JSONObject(syncInfo);
    }


    /**
     * Manual test
     */
    @Test
    public void should_return_sync_info_to_consumer() throws Exception{
        /**
         * Before running this test open zookeeper server
         */
        Socket mock1 = new Socket("localhost", 666);

        PrintWriter pr = new PrintWriter(mock1.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(mock1.getInputStream()));
        pr.println(MOCK_CONSUMER_REGISTER_REQUEST);

        System.out.println(br.readLine());
    }


}