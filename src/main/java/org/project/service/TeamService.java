package org.project.service;

import org.project.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamService {
	Page<Team> getAllTeamByUserId(long userId, Pageable pageable);

    Team saveTeam(Team team);
}
