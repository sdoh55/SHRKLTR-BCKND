package calc.service;

import calc.DTO.MatchDTO;
import calc.DTO.OutcomeDTO;
import calc.ELO.EloRating;
import calc.entity.*;
import calc.repository.MatchRepository;
import calc.repository.OutcomeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by clementperez on 9/20/16.
 */
@Service
public class OutcomeService {

    @Autowired
    private OutcomeRepository outcomeRepository;
    @Autowired
    private MatchService matchService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private ModelMapper modelMapper;

    public List<Outcome> findByPlayerId(Long playerId) {
        return outcomeRepository.findByPlayerId(playerId);
    }

    public List<Outcome> findByMatchId(Long matchId) {
        return outcomeRepository.findByMatchId(matchId);
    }

    public Outcome convertToEntity(OutcomeDTO outcomeDto) throws ParseException {
        Outcome outcome = modelMapper.map(outcomeDto, Outcome.class);

        outcome.setOutcomeId(outcomeDto.getOutcomeId());
        outcome.setScoreValue(outcomeDto.getScoreValue());
        outcome.setResults(outcomeDto.getResult());
        if(outcomeDto.getMatchId() != null) {
            outcome.setMatch(matchService.findOne(outcomeDto.getMatchId()));
        }
        outcome.setPlayer(playerService.findByUserName(outcomeDto.getUserName()));

        return outcome;
    }

    public OutcomeDTO convertToDto(Outcome outcome) {
        OutcomeDTO outcomeDTO = modelMapper.map(outcome, OutcomeDTO.class);

        outcomeDTO.setOutcomeId(outcome.getOutcomeId());
        outcomeDTO.setScoreValue(outcome.getScoreValue());
        outcomeDTO.setResult(outcome.getResults());
        outcomeDTO.setMatchId(outcome.getMatch().getMatchId());
        outcomeDTO.setUserName(outcome.getPlayer().getUserName());

        return outcomeDTO;
    }
}

