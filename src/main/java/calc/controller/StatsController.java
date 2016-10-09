package calc.controller;

import calc.entity.Match;
import calc.entity.Stats;
import calc.repository.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*
@RestController
public class StatsController {

    @Autowired
    private StatsRepository repository;

    @RequestMapping("/stats")
    public Stats stats(@RequestParam(value="playerId") Long playerId,@RequestParam(value="tournamentName") String tournamentName) {
        return repository.findByPlayerAndTournament(playerId, tournamentName);
    }
}
*/