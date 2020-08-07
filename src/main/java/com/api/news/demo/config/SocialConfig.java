//package com.api.news.demo.config;
//
//import com.api.news.demo.repository.UserRepository;
//import com.api.news.demo.service.ConnectionSignUpImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.security.crypto.encrypt.Encryptors;
//import org.springframework.social.UserIdSource;
//import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
//import org.springframework.social.config.annotation.EnableSocial;
//import org.springframework.social.config.annotation.SocialConfigurer;
//import org.springframework.social.connect.ConnectionFactoryLocator;
//import org.springframework.social.connect.ConnectionRepository;
//import org.springframework.social.connect.ConnectionSignUp;
//import org.springframework.social.connect.UsersConnectionRepository;
//import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
//import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
//import org.springframework.social.connect.web.ConnectController;
//import org.springframework.social.google.connect.GoogleConnectionFactory;
//import org.springframework.social.security.AuthenticationNameUserIdSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableSocial
//// Load to Environment.
//@PropertySource("classpath:application.yml")
//public class SocialConfig implements SocialConfigurer {
//
////    @Autowired
////    DataSource dataSource;
//
//    @Autowired
//    private ConnectionSignUpImpl connectionSignUp;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Override
//    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
//
////        env.getProperty("social.auto-signup");
//
//        GoogleConnectionFactory gfactory = new GoogleConnectionFactory(
//                env.getProperty("security.oauth2.client.clientId"),
//                env.getProperty("security.oauth2.client.clientSecret")
//        );
//
//        gfactory.setScope(env.getProperty("security.oauth2.client.scope"));
//
//        cfConfig.addConnectionFactory(gfactory);
//
//    }
//
//    @Override
//    public UserIdSource getUserIdSource() {
//        return new AuthenticationNameUserIdSource();
//    }
//
//    @Override
//    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
////        JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
////        ConnectionSignUp connectionSignUp = new ConnectionSignUpImpl();
////        usersConnectionRepository.setConnectionSignUp(connectionSignUp);
////        return usersConnectionRepository;
//        final InMemoryUsersConnectionRepository inMemoryUsersConnectionRepository =
//                new InMemoryUsersConnectionRepository(connectionFactoryLocator);
//        inMemoryUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
//        return inMemoryUsersConnectionRepository;
//    }
//
//    @Bean
//    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
//        return new ConnectController(connectionFactoryLocator, connectionRepository);
//    }
//}
