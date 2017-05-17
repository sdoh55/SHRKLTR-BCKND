package calc;

import calc.entity.*;
import calc.service.MatchService;
import calc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@ComponentScan(basePackages={"calc.controller","calc.repository","calc.entity","calc.rest","calc.service","calc.config"} , excludeFilters = { @ComponentScan.Filter(Configuration.class)})
@EnableJpaRepositories(basePackages = { "calc.repository" })
@EnableSwagger2
@EntityScan(basePackages = { "calc.entity" })
@Configuration
//@PropertySource("classpath:application.properties")
public class Application {

    public static void main(String[] args) { SpringApplication.run(Application.class, args);}

    @Autowired
    private CrudRepository<Sport,Long> repoSport;
    @Autowired
    private CrudRepository<Tournament,Long> repoTournament;
    @Autowired
    private CrudRepository<Match,Long> repoMatch;
    @Autowired
    private CrudRepository<Outcome,Long> repoOutcome;
    @Autowired
    private UserService userService;
    @Autowired
    private MatchService matchService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean(destroyMethod = "shutdown")
    public DataSource dataSource() {
        EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();
        factory.setDatabaseName("spring-social-quickstart");
        factory.setDatabaseType(EmbeddedDatabaseType.H2);
        factory.setDatabasePopulator(databasePopulator());
        return factory.getDatabase();
    }

    // internal helpers

    private DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("JdbcUsersConnectionRepository.sql", JdbcUsersConnectionRepository.class));
        return populator;
    }

    @PostConstruct
    public void initDB(){

       List<Sport> sports = Arrays.asList(
                new Sport("Pool"),
                new Sport("Ping Pong"),
                new Sport("Fussball"),
                new Sport("Darts"));

        for(Sport sport : sports){
            repoSport.save(sport);
        }
        List<User> users = Arrays.asList(
                new User("AAAAA"),
                new User("BBBBB"),
                new User("CCCCC"),
                new User("DDDDD"),
                new User("EEEEE"),
                new User("FFFFF"),
                new User("GGGGG"),
                new User("HHHHH"));

        for(User user : users){
            userService.save(user);
        }

        List<Tournament> tournaments = Arrays.asList(
                new Tournament("Tournament1", sports.get(0), users.get(0)),
                new Tournament("Tournament2", sports.get(0), users.get(1)),
                new Tournament("Tournament3", sports.get(1), users.get(2)),
                new Tournament("Tournament4", sports.get(1), users.get(3)),
                new Tournament("Tournament5", sports.get(2), users.get(4)),
                new Tournament("Tournament6", sports.get(3), users.get(1)),
                new Tournament("Tournament7", sports.get(3), users.get(2)),
                new Tournament("Tournament8", sports.get(2), users.get(3)));

        for(Tournament tournament : tournaments){
            repoTournament.save(tournament);

            for(User user : users){
                List<User> opponents = new ArrayList<User>(users);;
                opponents.remove(user);
                for(int i = 0;i<2;i++){
                    Random rdm = new Random();
                    User opponent = opponents.get(rdm.nextInt(opponents.size() - 1));

                    int result = rdm.nextInt(1);
                    matchService.addMatch(tournament, result == 0 ? user : opponent, result != 0 ? user : opponent, false);
                }
            }
        }
    }
}
