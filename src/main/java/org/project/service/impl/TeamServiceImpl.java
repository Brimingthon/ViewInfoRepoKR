package org.project.service.impl;

import lombok.AllArgsConstructor;
import org.project.model.Team;
import org.project.repository.TeamRepository;
import org.project.service.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {
	private final TeamRepository teamRepository;
	@Override
	public Page<Team> getAllTeamByUserId(long userId, Pageable pageable) {
		return teamRepository.findByTelegramUserId(userId, pageable);
	}

}
