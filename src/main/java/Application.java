import calc.entity.*;
import calc.repository.PlayerRepository;
import calc.service.MatchService;
import calc.service.PlayerService;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.*;

@SpringBootApplication
@ComponentScan(basePackages={"calc.controller","calc.repository","calc.entity","calc.rest","calc.service","calc.social"})
@EnableJpaRepositories(basePackages = { "calc.repository" })
@EnableSwagger2
@EntityScan(basePackages = { "calc.entity" })
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
    private PlayerService playerService;
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
        List<Player> players = Arrays.asList(
                new Player("AAAAA"),
                new Player("BBBBB"),
                new Player("CCCCC"),
                new Player("DDDDD"),
                new Player("EEEEE"),
                new Player("FFFFF"),
                new Player("GGGGG"),
                new Player("HHHHH"));

        for(Player player : players){
            playerService.save(player);
        }

        List<Tournament> tournaments = Arrays.asList(
                new Tournament("Tournament1", sports.get(0), players.get(0)),
                new Tournament("Tournament2", sports.get(0), players.get(1)),
                new Tournament("Tournament3", sports.get(1), players.get(2)),
                new Tournament("Tournament4", sports.get(1), players.get(3)),
                new Tournament("Tournament5", sports.get(2), players.get(4)),
                new Tournament("Tournament6", sports.get(3), players.get(1)),
                new Tournament("Tournament7", sports.get(3), players.get(2)),
                new Tournament("Tournament8", sports.get(2), players.get(3)));

        for(Tournament tournament : tournaments){
            repoTournament.save(tournament);

            for(Player player : players){
                List<Player> opponents = new ArrayList<Player>(players);;
                opponents.remove(player);
                for(int i = 0;i<2;i++){
                    Random rdm = new Random();
                    Player opponent = opponents.get(rdm.nextInt(opponents.size() - 1));

                    int result = rdm.nextInt(1);
                    matchService.addMatch(tournament, result == 0 ? player : opponent, result != 0 ? player : opponent, false);
                }
            }
        }
    }
}
