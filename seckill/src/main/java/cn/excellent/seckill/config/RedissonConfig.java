package cn.excellent.seckill.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "isme.redis")
public class RedissonConfig {

    @Value("${isme.redis.cluster.nodes}")
    private String nodes;

    @Bean
    public Redisson redisson(){
        String[] node = nodes.split(",");
        for (int i = 0; i < node.length; i++) {
            node[i] = "redis://"+node[i];
        }
        RedissonClient redissonClient = null;
        Config config = new Config();
        config.useClusterServers().setScanInterval(2000).addNodeAddress(node);
        redissonClient = Redisson.create(config);
        return (Redisson) redissonClient;
    }



}
