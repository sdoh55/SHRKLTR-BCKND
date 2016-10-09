package calc.service;

import calc.DTO.PlayerDTO;
import calc.entity.*;
import calc.repository.OutcomeRepository;
import calc.repository.PlayerRepository;
import calc.repository.StatsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by clementperez on 9/20/16.
 */
@Service
public class PlayerService{

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private StatsRepository statsRepository;
    @Autowired
    private StatsService statsService;
    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private ModelMapper modelMapper;
/*
    private List<Player> playerFromTournament(Tournament tournament){

    }*/

    public List<Player> findAll(){
        List<Player> copy = new ArrayList<>();

        for (Player player : playerRepository.findAll()) {
            copy.add(player);
        }
        return copy;
    }

    public Player findOne(Long id){
        return playerRepository.findOne(id);
    }

    public Player save(Player player){
        return playerRepository.save(player);
    }

    public Player whoIsLoggedIn(){
        Player player = null; //find the logged in user
        return player;
    }

    public List<Player> findByLastName(String lastName){
        return playerRepository.findByLastName(lastName);
    }

    public Player findByUserName(String lastName){
        return playerRepository.findByUserName(lastName);
    }

    public List<Player> findPlayersInTournament(Tournament tournament){
        List<Player> p = new ArrayList<>();

        List<Stats> stats = tournament.getStats();
        for(Stats s : stats){
            p.add(s.getPlayer());
        }

        return p;
    }


    public Player convertToEntity(PlayerDTO playerDto) throws ParseException {
        Player player = modelMapper.map(playerDto, Player.class);

        player.setPlayerId(playerDto.getPlayerId());
        player.setFirstName(playerDto.getFirstName());
        player.setLastName(playerDto.getLastName());
        player.setFirstName(playerDto.getFirstName());
        player.setUserName(playerDto.getUserName());

        if (playerDto.getPlayerId() != null) {
            player.setStats(statsRepository.findByPlayerId(playerDto.getPlayerId()));
            player.setOutcomes(outcomeService.findByPlayerId(playerDto.getPlayerId()));
        }
        return player;
    }

    public PlayerDTO convertToDto(Player player, Tournament tournament) {
        PlayerDTO playerDTO = modelMapper.map(player, PlayerDTO.class);

        playerDTO.setPlayerId(player.getPlayerId());
        playerDTO.setFirstName(player.getFirstName());
        playerDTO.setLastName(player.getLastName());
        playerDTO.setFirstName(player.getFirstName());
        playerDTO.setUserName(player.getUserName());

        if (player.getPlayerId() != null)
            playerDTO.setStats(statsService.convertToDto(player.getStats(tournament)));

        return playerDTO;
    }

    public PlayerDTO convertToDto(Player player) {
        PlayerDTO playerDTO = modelMapper.map(player, PlayerDTO.class);

        playerDTO.setPlayerId(player.getPlayerId());
        playerDTO.setFirstName(player.getFirstName());
        playerDTO.setLastName(player.getLastName());
        playerDTO.setFirstName(player.getFirstName());
        playerDTO.setUserName(player.getUserName());

        return playerDTO;
    }
}