package nus.edu.module2assessment.config;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.database}")
    private int redisDatabase;

    //private static final String REDIS_PASSWORD = System.getenv("redis_key");

    private final Logger logger = Logger.getLogger(RedisConfig.class.getName());

    @Bean("My Template")
    public RedisTemplate<String, Object> createRedisTemplate() {
        
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        
        config.setHostName(redisHost);
        config.setPort(redisPort.get());
/*         logger.log(Level.INFO, "Set Redis password"); 
        if(null != REDIS_PASSWORD) {
            config.setPassword(REDIS_PASSWORD);
            logger.log(Level.INFO, "Set Redis password"); 
        } */
        config.setDatabase(redisDatabase);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
        template.setValueSerializer(
                serializer
        );
        return template;
    }

    
}

