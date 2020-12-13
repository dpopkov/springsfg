package learn.sprb2g.depinjexamples.config;

import learn.sprb2g.depinjexamples.examplebeans.FakeDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:datasource.properties")
public class PropertyConfig {

    @Value("${learn.username}")
    String user;

    @Value("${learn.password}")
    String password;

    @Value("${learn.url}")
    String url;

    @Bean
    public FakeDataSource fakeDataSource() {
        FakeDataSource ds = new FakeDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setUrl(url);
        return ds;
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
