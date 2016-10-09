package calc.service;

import calc.DTO.SportDTO;
import calc.DTO.TournamentDTO;
import calc.entity.Sport;
import calc.entity.Tournament;
import calc.repository.SportRepository;
import calc.repository.TournamentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by clementperez on 9/25/16.
 */
@Service
public class SportService {

    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private SportRepository sportRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Tournament> findBySport(Sport sport){
        return tournamentRepository.findBySport(sport);
    }

    public Sport findByName(String name){
        return sportRepository.findByName(name);
    }

    public Sport convertToEntity(SportDTO sportDto) throws ParseException {

        Sport sport = modelMapper.map(sportDto, Sport.class);

        sport.setSportId(sportDto.getSportId());
        sport.setName(sportDto.getName());

        if (sportDto.getSportId() != null) {
            sport.setTournaments(tournamentRepository.findBySportId(sportDto.getSportId()));
        }
        return sport;
    }

    public SportDTO convertToDto(Sport sport) {
        SportDTO sportDTO = modelMapper.map(sport, SportDTO.class);

        sportDTO.setSportId(sport.getSportId());
        sportDTO.setName(sport.getName());
        return sportDTO;
    }

    public List<Sport> findAll() {
        List<Sport> copy = new ArrayList<>();

        for (Sport sport : sportRepository.findAll()) {
            copy.add(sport);
        }
        return copy;
    }
}
