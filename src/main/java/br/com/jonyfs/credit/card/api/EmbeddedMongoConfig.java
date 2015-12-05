package br.com.jonyfs.credit.card.api;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Configuration;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;

@Configuration
public class EmbeddedMongoConfig {

	private static final String MONGO_DB_URL = "localhost";
	private static final int MONGO_DB_PORT = 12345;

	MongodStarter starter = MongodStarter.getDefaultInstance();
	MongodExecutable mongodExecutable;

	@PostConstruct
	public void construct() throws UnknownHostException, IOException {
		IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(MONGO_DB_URL, MONGO_DB_PORT, true)).build();
		mongodExecutable = starter.prepare(mongodConfig);
		MongodProcess mongod = mongodExecutable.start();
	}

	@PreDestroy
	public void destroy() {
		if (mongodExecutable != null) {
			mongodExecutable.stop();
		}
	}
}