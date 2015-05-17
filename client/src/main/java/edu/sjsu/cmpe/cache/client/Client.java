package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snehal on 5/6/2015.
 * "Chronicle Map" : Client Program
 */

public class Client {
    private final static List cacheServerList = new ArrayList();

    public static void main(String[] args) throws Exception {


        cacheServerList.add("http://localhost:3000");
        cacheServerList.add("http://localhost:3001");
        cacheServerList.add("http://localhost:3002");

        System.out.println("/ ***---- Cache Client is getting Started . ---- *** / \n");
        ConsistentHash consistent_Hash =new ConsistentHash(cacheServerList.size(), cacheServerList);

        System.out.println("/ ***---- Adding nodes into the cache servers (3000, 3001, 3002)(Consistent Hashing) ---- *** / \n");

        for(int i = 1; i <= 10; i++) {
            String cache_URL = consistent_Hash.get(i).toString();
            CacheServiceInterface cache = new DistributedCacheService(cache_URL);
            cache.put(i,String.valueOf((char) (i + 96)));
            System.out.println("PUT: " + i + " => " + String.valueOf((char) (i + 96)));
        }

        System.out.println("/ ***---- Retrieve cache Data from the servers (3000, 3001, 3002) (Consistent Hashing) ---- *** / \n");

        for(int i = 1; i <= 10; i++) {
            String cache_URL = consistent_Hash.get(i).toString();
            CacheServiceInterface cache = new DistributedCacheService(cache_URL);
            String cacheValue = cache.get(i);
            System.out.println("GET: " + i + " => "+ cacheValue);
            System.out.println("Cache data is retrieved : " + cacheValue);
        }

/*       HRWHash rendezvousHash = new HRWHash(cacheServerList.size(), cacheServerList);

         System.out.println("/ ***---- Adding nodes into the cache servers (3000, 3001, 3002) (HRW Hashing) ---- *** /n");

        for(int i = 1; i <= 10; i++) {
            String cache_URL = rendezvousHash.get(i).toString();
            CacheServiceInterface cache = new DistributedCacheService(cache_URL);
            cache.put(i,String.valueOf((char) (i + 96)));
            System.out.println("PUT: " + i + " => " + String.valueOf((char) (i + 96)));
        }

        System.out.println("/ ***---- Retrieve cache Data from the servers (3000, 3001, 3002) (HRW Hashing) ---- *** /n");

        for(int i = 1; i <= 10; i++) {
            String cache_URL = rendezvousHash.get(i).toString();
            CacheServiceInterface cache = new DistributedCacheService(cache_URL);
            String cacheValue = cache.get(i);
            System.out.println("GET: " + i + " => "+ cacheValue);
            System.out.println("Cache data is retrieved : " + cacheValue);
        } */

        System.out.println("/ ***---- Cache Client is getting exited. ---- *** / \n");
    }
}
