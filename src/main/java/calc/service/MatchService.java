package calc.service;

import calc.DTO.MatchDTO;
import calc.ELO.EloRating;
import calc.entity.*;
import calc.repository.MatchRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by clementperez on 9/20/16.
 */
@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private StatsService statsService;
    @Autowired
    private ModelMapper modelMapper;

    public Match findOne(Long matchId){
        return matchRepository.findOne(matchId);
    }


    public Match addMatch(Tournament tournament, List<Outcome> outcomes) {

        if(outcomes.size() != 2 ||
                (outcomes.get(0).getResults().equals(Outcome.Result.WIN) && outcomes.get(1).getResults().equals(Outcome.Result.WIN)) ||
                (outcomes.get(0).getResults().equals(Outcome.Result.LOSS) && outcomes.get(1).getResults().equals(Outcome.Result.LOSS))){
            throw new AssertionError();
        }

        // this method init both players even when there is a tie
        Player winner = outcomes.get(0).getResults().equals(Outcome.Result.WIN) ? outcomes.get(0).getPlayer() : outcomes.get(1).getPlayer();
        Player looser = outcomes.get(0).getResults().equals(Outcome.Result.WIN) ? outcomes.get(1).getPlayer() : outcomes.get(0).getPlayer();
        Boolean isTie = outcomes.get(0).getResults().equals(Outcome.Result.TIE);

        return addMatch(tournament,winner,looser, isTie);
    }

    public Match addMatch(Tournament tournament, Player winner, Player looser, boolean isTie) {

        Stats winnerStats = statsService.findByPlayerAndTournamentCreateIfNone(winner,tournament);
        Stats loserStats = statsService.findByPlayerAndTournamentCreateIfNone(looser,tournament);

        double pointValue = EloRating.calculatePointValue(winnerStats.getScore(),loserStats.getScore(),isTie ? "=" : "+");

        return addMatch(tournament,winner,looser, pointValue, isTie);
    }

    protected Match addMatch(Tournament tournament, Player winner, Player looser, double pointValue, boolean isTie) {

        Match match = new Match(tournament);
        List<Outcome> outcomes = new ArrayList<>(Arrays.asList(
                new Outcome(pointValue, isTie ? Outcome.Result.TIE : Outcome.Result.WIN, match, winner),
                new Outcome(-pointValue, isTie ? Outcome.Result.TIE : Outcome.Result.LOSS, match, looser))
        );
        match.setOutcomes(outcomes);
        Match m = this.save(match);

        for (Outcome outcome : outcomes) {
            statsService.recalculateAfterOutcome(outcome);
        }

        return m;
    }

    List<Match> findByTournament(Tournament tournament){
        return matchRepository.findByTournament(tournament);
    }

    List<Match> findByTournamentName(String tournamentName){
        return matchRepository.findByTournamentName(tournamentName);
    }


    public List<Match> findByPlayerByTournament(Long playerId, String tournamentName){
        return matchRepository.findByPlayerIdByTournamentName(playerId, tournamentName);
    }


    public List<Match> findByPlayer(Long playerId) {
        return matchRepository.findByPlayerId(playerId);
    }

    public Iterable<Match> findAll() {
        return matchRepository.findAll();
    }

    public Match save(Match match){
        return matchRepository.save(match);
    }

    public Match convertToEntity(MatchDTO matchDto) throws ParseException {
        Match match = modelMapper.map(matchDto, Match.class);

        match.setMatchId(matchDto.getMatchId());
        match.setDate(matchDto.getDate());
        match.setTournament(tournamentService.findByName(matchDto.getTournamentName()));

        List<Outcome> outcomeSet = matchDto.getOutcomes().stream()
                .map(o -> {
                    try {
                        return outcomeService.convertToEntity(o);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
        match.setOutcomes(outcomeSet);

        return match;
    }

    public MatchDTO convertToDto(Match match) {
        MatchDTO matchDTO = modelMapper.map(match, MatchDTO.class);

        matchDTO.setMatchId(match.getMatchId());
        matchDTO.setDate(match.getDate());
        matchDTO.setTournamentName(match.getTournament().getName());

        if (match.getMatchId() != null)
            matchDTO.setOutcomes(match.getOutcomes().stream().map(o -> outcomeService.convertToDto(o) ).collect(Collectors.toList()));

        return matchDTO;
    }

}

