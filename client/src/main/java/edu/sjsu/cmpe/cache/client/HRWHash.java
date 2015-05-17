package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.util.Map;
import java.util.TreeMap;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.SortedMap;

/**
 * Created by snehal on 5/6/2015.
 * "Chronicle Map - Rendezvous or Highest Random Weight (HRW) hashing" Program
 */

public class HRWHash<T> {

    private final HashFunction hashFunctionHRW;
    private final int numOfReplicas;
    private final SortedMap<Integer, T> circleMap =
            new TreeMap<Integer, T>();

    public HRWHash(int numOfReplicas, Collection<T> nodes) {
        this.hashFunctionHRW = Hashing.md5();
        this.numOfReplicas = numOfReplicas;

        for (T node : nodes) {
            add(node);
        }
    }

    public void add(T node) {
        for (int i = 0; i < numOfReplicas; i++) {
            circleMap.put(hashFunctionHRW.hashString(node.toString() + i, Charset.defaultCharset()).asInt(),
                    node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numOfReplicas; i++) {
            circleMap.remove(hashFunctionHRW.hashString(node.toString() + i, Charset.defaultCharset()).asInt());
        }
    }

    public T get(Object key) {
        if (circleMap.isEmpty()) {
            return null;
        }
        Integer maxHashHRW = Integer.MIN_VALUE;
        T maxNodeHRW = null;

        for (Map.Entry<Integer, T> node: circleMap.entrySet()) {
            int hashValue = hashFunctionHRW.newHasher()
                    .putString(key.toString(),Charset.defaultCharset())
                    .putString(node.getValue().toString(),Charset.defaultCharset()).hash().asInt();

            if(hashValue>maxHashHRW) {
                maxHashHRW = hashValue;
                maxNodeHRW = node.getValue();
            }
        }
        System.out.println("Node: " + maxNodeHRW);
        return maxNodeHRW;
    }
}
