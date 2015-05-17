package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.util.SortedMap;
import java.util.Collection;
import java.util.TreeMap;
import java.nio.charset.Charset;

/**
 * Created by snehal on 5/6/2015.
 * "Chronicle Map - Consistent Hash" Program
 */


public class ConsistentHash<T> {

    private final HashFunction hashFunction;
    private final int numOfReplicas;
    private final SortedMap<Integer, T> circleMap =
            new TreeMap<Integer, T>();

    public ConsistentHash(int numOfReplicas, Collection<T> nodes) {

        this.hashFunction = Hashing.md5();
        this.numOfReplicas = numOfReplicas;

        for (T node : nodes) {
            add(node);
        }
    }

    public void add(T node) {
        for (int i = 0; i < numOfReplicas; i++) {
            circleMap.put(hashFunction.hashString(node.toString() + i, Charset.defaultCharset()).asInt(),
                    node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numOfReplicas; i++) {
            circleMap.remove(hashFunction.hashString(node.toString() + i, Charset.defaultCharset()).asInt());
        }
    }

    public T get(Object key) {
        if (circleMap.isEmpty()) {
            return null;
        }
        int hashValue = hashFunction.hashInt((Integer)key).asInt();
        if (!circleMap.containsKey(hashValue)) {
            SortedMap<Integer, T> tailMap =
                    circleMap.tailMap(hashValue);
            hashValue = tailMap.isEmpty() ?
                    circleMap.firstKey() : tailMap.firstKey();
        }
        System.out.println("Node is: " + circleMap.get(hashValue));
        return circleMap.get(hashValue);
    }
}
