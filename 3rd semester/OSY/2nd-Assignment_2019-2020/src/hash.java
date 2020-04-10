import acm.program.*;

public class hash extends Program {
    final int N = 10;
    int keys = 0;

    public void run() {
        int key, pos, choice, telos = 0;
        int[] hash = new int[N];
        for (int i = 0; i<N; i++) hash[i] = 0;
        do{
            println("Menu");
            println("1 Insert key");
            println("2 Find Key");
            println("3 Display Key");
            println("4 Exit");

            choice = readInt("\nChoice");
            if(choice ==1){
                key = readInt("Give new key (greater than zero):");
                if(key>0) insertkey(hash, key);
                else println("key must be greater than zero");
            }
            if(choice ==2){
                key = readInt("Give key to search for");
                pos = findkey(hash,key);
                if(pos==-1){
                    println("Key not in hash table");
                }
                else{
                    println("key value " + hash[pos]);
                    println("table position" + pos);
                }

            }
            if(choice==3) displaytable(hash);
            if(choice==4) telos = 1;

        } while (telos==0);

    }

    void insertkey(int[] hash, int k){

        int position;
        position = findkey(hash,k);
        if(position!=-1) println("key is already in hash table");
        else
            if(keys<N){
                position = hashfunction(hash,k);
                hash[position]= k;
                keys++;
            }
            else println("hash table is full");
    }

    int hashfunction(int[] hash, int k){
        int position;

        position = k %N;
        while(hash[position]!=0){
            position++;
            position %= N;
        }
        return position;
    }

    int findkey(int[] hash, int k){
        int position, i=0, found = 0;
        position = k %N;
        while(i<N && found ==0) {
            i++;
            if (hash[position] == k)
                found = 1;
            else{
                position++;
                position %= N;
            }
        }
        if(found ==1)
            return position;
        else
            return-1;
    }

    void displaytable(int[] hash){
        int i;
        println("\npros key\n");
        for(i=0; i<N; i++)
            println(" "+i+" "+hash[i]);

    }
}
