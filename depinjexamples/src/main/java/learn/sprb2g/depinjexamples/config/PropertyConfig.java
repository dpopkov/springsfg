package learn.sprb2g.depinjexamples.config;

import learn.sprb2g.depinjexamples.examplebeans.FakeDataSource;
import learn.sprb2g.depinjexamples.examplebeans.FakeJmsBroker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfig {

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

    @Bean
    public FakeDataSource fakeDataSource() {
        FakeDataSource ds = new FakeDataSource();
        ds.setUser(user);
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
}
