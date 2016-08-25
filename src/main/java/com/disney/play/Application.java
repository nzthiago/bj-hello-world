package com.disney.play;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@SpringBootApplication
@RestController
public class Application
{
    final static ShardedJedisPool redisStatsPool;
    
    static
    {
        List<JedisShardInfo> redisClickShard = new ArrayList<JedisShardInfo>();
        JedisShardInfo shardInfo = new JedisShardInfo("bj-hello-world.redis.cache.windows.net", 6379);
        shardInfo.setPassword("FpaDShz8Uj6frCAduCJuhQSlyPrz7//nCu1GD14+enU="); /* Use your access key. */
        redisClickShard.add(shardInfo);
        
        JedisPoolConfig config = new JedisPoolConfig();
        redisStatsPool = new ShardedJedisPool( config, redisClickShard);
    }
    
    @RequestMapping("/")
    public String index(@RequestParam(value="key", defaultValue = "no key") String key)
    {
        DatabaseReference d = FirebaseDatabase.getInstance().getReference();
        d.child("azureTest").setValue("helloWorld-key: [" + key + "]");
        
        /* In this line, replace <name> with your cache name: */
//        JedisShardInfo shardInfo = new JedisShardInfo("bj-hello-world.redis.cache.windows.net", 6379);
//        shardInfo.setPassword("FpaDShz8Uj6frCAduCJuhQSlyPrz7//nCu1GD14+enU="); /* Use your access key. */

//        Jedis jedis = new Jedis(shardInfo);
        
        String value = "NOT-SET";
        
        ShardedJedis shardedJedis = redisStatsPool.getResource();
        final Jedis jedis = shardedJedis.getShard("") ;

            jedis.set("key", key);
            value = jedis.get("key");
    
        
        return "{\"redis\":" + value + ",\"content\":\"Hello, World! at: " + System.currentTimeMillis() + "\"}";
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(new FileInputStream("serviceAccountCredentials.json"))
                .setDatabaseUrl("https://disneyplayapp.firebaseio.com/")
                .build();
               FirebaseApp.initializeApp(options);
        SpringApplication.run(Application.class, args);
    }
}