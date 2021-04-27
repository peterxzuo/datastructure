package com.peterzuo.test;

public class ServerSelectorWithConsistentHash implements ServerSelector {
    ArrayList<Server> servers;
    HashFunc hashFunc;
    ConsistentHashMap<Server> consistentHashMap;
    

    ServerSelectorWithConsistentHash(Collection<Server> initServers){
        servers = new ArrayList(initServers);
    }

    public Server select(String key){
        return consistentHashMap.getServer(key);
    }
