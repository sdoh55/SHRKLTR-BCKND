package calc.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import calc.DTO.MatchDTO;
import calc.DTO.PlayerDTO;
import calc.DTO.StatsDTO;
import calc.entity.Match;
import calc.entity.Player;
import calc.entity.Stats;
import calc.service.MatchService;
import calc.service.PlayerService;
import calc.service.StatsService;
import calc.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private StatsService statsService;
    @Autowired
    private MatchService matchService;

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public List<PlayerDTO> players() {

        return playerService.findAll().stream()
                .map(players -> playerService.convertToDto(players)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/player/{playerId}", method = RequestMethod.GET)
    public PlayerDTO getPlayer(@PathVariable(value="playerId") Long playerId) {
        Player p =  playerService.findOne(playerId);
        return playerService.convertToDto(p);
    }

    //TODO probably need to send a bad request or something
    @RequestMapping(value = "/player", method = RequestMethod.POST)
    public PlayerDTO createPlayer(@RequestBody PlayerDTO player) {
        try {
            Player p = playerService.convertToEntity(player);
            return playerService.convertToDto(playerService.save(p));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/player/{playerId}", method = RequestMethod.PUT)
    public PlayerDTO updatePlayer(@PathVariable(value="playerId") Long playerId, @RequestBody PlayerDTO player) {
        Player p = playerService.findOne(playerId);
        p.setFirstName(player.getFirstName());
        p.setLastName(player.getLastName());
        p.setUserName(player.getUserName());

        return playerService.convertToDto(playerService.save(p));
    }

    @RequestMapping(value = "/player/{playerId}/stats", method = RequestMethod.GET)
    public List<StatsDTO> playerStats(@PathVariable(value="playerId") Long playerId) {
        List<Stats> s = statsService.findByPlayer(playerService.findOne(playerId));
        return s.stream()
                .map(stats -> statsService.convertToDto(stats)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/player/{playerId}/stats2", method = RequestMethod.GET)
    public StatsDTO playerStatsForTournament(@PathVariable(value="playerId") Long playerId, @RequestParam(value="tournamentName", defaultValue="") String tournamentName) {
        Stats s = statsService.findByPlayerAndTournament(playerId, tournamentName);
        return statsService.convertToDto(s);
    }
/*
    @RequestMapping(value = "/player/{playerId}/matchs", method = RequestMethod.GET)
    public List<Match> playerMacths(@PathVariable(value="playerId") Long playerId) {
        return repoMatchs.findByPlayer(playerService.findOne(playerId));
    }*/

    @RequestMapping(value = "/player/{playerId}/matchs", method = RequestMethod.GET)
    public List<MatchDTO> playerMatchsForTournament(@PathVariable(value="playerId") Long playerId, @RequestParam(value="tournamentName", required = false) String tournamentName) {
        List<Match> m = new ArrayList<>();
        if(tournamentName != null){
            m = matchService.findByPlayerByTournament(playerId,tournamentName);
        }else
            m = matchService.findByPlayer(playerId);

        return m.stream()
                .map(matchs -> matchService.convertToDto(matchs)).collect(Collectors.toList());
    }
}
