package me.coldrain.ninetyminute.repository;


import me.coldrain.ninetyminute.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
