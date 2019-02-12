import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;


public class SinglyLinkedListTest {
    SinglyLinkedList<String,String> object;

    @Test
    public void find_Something_That_Does_Not_Exist() {
        object = new SinglyLinkedList<>();
        Node<Model<String,String>> expected = null;
        object.put(new Node<Model<String,String>>(null,null,new Model<>("tzeni","tzeni")));

        Node<Model<String,String>> result = object.find("randomText");
        Assert.assertEquals(result, expected);
    }

    @Test
    public void find_Something_That_Does_Exist() {
        object = new SinglyLinkedList<>();
        Node<Model<String,String>> expected = null;
        expected = object.put(new Node<Model<String,String>>(null,null,new Model<>("test","tzeni")));

        Node<Model<String,String>> result = object.find("test");
        Assert.assertEquals(result, expected);
    }


    /**
     * Insert 5 items and check if they are inserted in the right order.
     */
    @Test
    public void put_5_items(){
        object = new SinglyLinkedList<>();
        ArrayList< Node<Model<String,String>>> nodesInserted = new ArrayList<>();

        for (int i=1;i<=5;++i){
            Node<Model<String,String>> node =
                    new Node<Model<String,String>>(null,null,new Model<>("test" + i,"tzeni"));
            nodesInserted.add(node);
            object.put(node);
        }

        for (int i=1;i<=5;++i){
            Assert.assertEquals(nodesInserted.get(i-1), object.find("test" + i));
        }
    }

    /**
     * Insert 5 items and then delete them.
     */
    @Test
    public void delete_all_5_items(){
        object = new SinglyLinkedList<>();

        ArrayList< Node<Model<String,String>>> nodesInserted = new ArrayList<>();

        for (int i=1;i<=5;++i){
            Node<Model<String,String>> node =
                    new Node<Model<String,String>>(null,null,new Model<>("test" + i,"tzeni"));
            nodesInserted.add(node);
            object.put(node);
        }

        for (int i=1;i<=5;++i) object.delete("test"+i);
        for(int i=1;i<=5;++i) Assert.assertNull(object.find("test"+i));
    }





}