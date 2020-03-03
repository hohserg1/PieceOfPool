package hohserg.peice.of.pool.example;

import hohserg.peice.of.pool.MainPool;
import hohserg.peice.of.pool.MicroPool;

import java.util.ArrayList;
import java.util.UUID;

public class JavaExample {

    private static final String id = UUID.randomUUID().toString();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++)
            some();

    }

    public static void some() {
        //get pool for ListBuffer[Int]
        MicroPool<ArrayList<Integer>> pool = MainPool.startPool(id, ArrayList::new, ArrayList::clear);

        //get pooled list
        ArrayList<Integer> list = pool.create();

        list.add(1);
        System.out.println(list);

        //clear all used lists and return to pool
        pool.free();
    }
}
