package redisdb;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import db.protocols.ICommands;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

@Component(service = ICommands.class, scope = ServiceScope.SINGLETON)
public class RedisCommandRepository implements ICommands {

	private StatefulRedisConnection<String, String> connection;
	private RedisClient redisClient;
	private String returnedSyncValue;
	
	private static final String HOST_LOCALE = "localhost";
	private static final String SENTINEL_MASTER_ID = "mymaster";
	
	@Activate
	void onInit() {
		// Ciclo de vida de onInit não utilizado.
	}

	void onDestroy() {
		this.closeConnection();
		this.shutDownRedisClient();
	}

	// START SET REGION
	@Override
	public void set(String key, String value) {
		this.createConnection()
			.getSyncAndSetCommand(key, value)
			.closeConnection()
			.shutDownRedisClient();
	}

	@Override
	public void hashSet(String key, String field, String value) {
		this.createConnection()
			.getSyncAndHashSetCommand(key, field, value)
			.closeConnection()
			.shutDownRedisClient();
	}
	
	@Override
	public void mapSet(String key, Map<String, String> map) {
		this.createConnection()
			.getSyncAndMapHashSetCommand(key, map)
			.closeConnection()
			.shutDownRedisClient();
	}

	private RedisCommandRepository getSyncAndSetCommand(String key, String value) {
		this.connection.sync().set(key, value);
		return this;
	}
	
	private RedisCommandRepository getSyncAndHashSetCommand(String key, String field, String value) {
		this.connection.sync().hset(key, field, value);
		return this;
	}
	
	private RedisCommandRepository getSyncAndMapHashSetCommand(String key, Map<String, String> map) {
		this.connection.sync().hset(key, map);
		return this;
	}
	// END SET REGION

	// START GET REGION
	@Override
	public String get(String key) {
		this.createConnection()
			.getSyncAndGetCommand(key)
			.closeConnection()
			.shutDownRedisClient();

		return this.returnedSyncValue;
	}
	
	@Override
	public String hashGet(String key, String field) {
		this.createConnection()
			.getSyncAndHashGetCommand(key, field)
			.closeConnection()
			.shutDownRedisClient();

		return this.returnedSyncValue;
	}
	
	private RedisCommandRepository getSyncAndGetCommand(String key) {
		this.returnedSyncValue = this.connection.sync().get(key);
		return this;
	}
	
	private RedisCommandRepository getSyncAndHashGetCommand(String key, String field) {
		this.returnedSyncValue = this.connection.sync().hget(key, field);
		return this;
	}
	// END GET REGION

	// START INFRA REGION
	private RedisCommandRepository closeConnection() {
		this.connection.close();
		return this;
	}

	private RedisCommandRepository shutDownRedisClient() {
		this.redisClient.shutdownAsync();
		return this;
	}

	private RedisCommandRepository createConnection() {

		RedisURI redisURI = RedisURI.builder()
				.withSentinel(HOST_LOCALE, 26379)
				.withSentinel(HOST_LOCALE, 26380)
				.withSentinel(HOST_LOCALE, 26381)
				.withSentinelMasterId(SENTINEL_MASTER_ID)
				.build();
		
		this.redisClient = RedisClient.create(redisURI);
		this.connection = redisClient.connect();

		return this;
	}
	// END INFRA REGION
}
