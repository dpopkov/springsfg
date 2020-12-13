package learn.sprb2g.depinjexamples.config;

import learn.sprb2g.depinjexamples.examplebeans.FakeDataSource;
import learn.sprb2g.depinjexamples.examplebeans.FakeJmsBroker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
//@PropertySource({
//        "classpath:datasource.properties",
//        "classpath:jms.properties"
//})
@PropertySources({
        @PropertySource("classpath:datasource.properties"),
        @PropertySource("classpath:jms.properties")
})
public class PropertyConfig {

    /* Autowired in constructor. */
    private final Environment env;

    @Value("${learn.username}")
    String user;

    @Value("${learn.password}")
    String password;

    @Value("${learn.url}")
    String url;

    @Value("${learn.jms.username}")
    String jmsUsername;

    @Value("${learn.jms.password}")
    String jmsPassword;

    @Value("${learn.jms.url}")
    String jmsUrl;

    public PropertyConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public FakeDataSource fakeDataSource() {
        FakeDataSource ds = new FakeDataSource();
        ds.setUser(env.getProperty("LEARN_SFG_USERNAME"));  // LEARN_SFG_USERNAME - name of the environment variable
        ds.setPassword(password);
        ds.setUrl(url);
        return ds;
    }

    @Bean
    public FakeJmsBroker fakeJmsBroker() {
        FakeJmsBroker broker = new FakeJmsBroker();
        broker.setUsername(jmsUsername);
        broker.setPassword(jmsPassword);
        broker.setUrl(jmsUrl);
        return broker;
    }

    /*
    The Bean below is provided in the course,
    but the 'External Properties' example works without it.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
