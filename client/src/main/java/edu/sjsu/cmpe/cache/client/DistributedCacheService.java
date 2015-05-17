package edu.sjsu.cmpe.cache.client;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Distributed cache service
 * 
 */
public class DistributedCacheService implements CacheServiceInterface {
    private final String cacheServer_URL;

    public DistributedCacheService(String server_URL) {
        this.cacheServer_URL = server_URL;
    }

    public String getCacheServerUrl() {
        return cacheServer_URL;
    }

    /**
     * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#get(long)
     */
    @Override
    public String get(long key) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(this.cacheServer_URL + "/cache/{key}")
                    .header("accept", "application/json")
                    .routeParam("key", Long.toString(key)).asJson();
        } catch (UnirestException e) {
            System.err.println(e);
        }
        String value = response.getBody().getObject().getString("value");

        return value;
    }

    /**
     * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#put(long,
     *      java.lang.String)
     */
    @Override
    public void put(long key, String value) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest
                    .put(this.cacheServer_URL + "/cache/{key}/{value}")
                    .header("accept", "application/json")
                    .routeParam("key", Long.toString(key))
                    .routeParam("value", value).asJson();
        } catch (UnirestException e) {
            System.err.println(e);
        }

        if (response.getCode() != 200) {
            System.out.println("Failed to add node data to the cache.");
        }
    }
}
