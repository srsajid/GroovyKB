package springboot.management.store.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by hungnguyen on 12/16/14.
 */
@Configuration
@PropertySource(value = "classpath:elasticsearch.properties")
@EnableElasticsearchRepositories(basePackages = "co.paan.repository")
public class ElasticsearchConfiguration {
    @Resource
    private Environment environment;


    @Bean
    public Client client() throws UnknownHostException {

        String server = "127.0.0.1";
        Integer port = 9300;
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "my-application").build();
        Client client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(server), port));

        return client;
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client) throws Exception {
        return new ElasticsearchTemplate(client);
    }


}
