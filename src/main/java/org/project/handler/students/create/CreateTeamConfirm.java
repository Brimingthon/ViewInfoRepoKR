package org.project.handler.students.create;

import org.project.handler.UpdateHandler;
import org.project.model.Phase;
import org.project.model.Team;
import org.project.model.UserPhase;
import org.project.service.TeamService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Optional;

import static org.project.util.UpdateHelper.*;
import static org.project.util.enums.HandlerName.*;

@Component
public class CreateTeamConfirm extends UpdateHandler {
	private final TeamService teamService;
	public CreateTeamConfirm(TeamService teamService) {
		this.teamService = teamService;
	}

	@Override
	public boolean isApplicable(Optional<Phase> phaseOptional, Update update) {
		return super.isApplicable(phaseOptional, update) || isUpdateCallbackEqualsHandler(update, handlerPhase.getHandlerName());
	}

	@Override
	public void handle(UserPhase userPhase, Update update) throws TelegramApiException {
		long userId = getUserIdFromUpdate(update);

		updateUserPhase(userPhase, handlerPhase);

		deleteRemovableMessagesAndEraseAllFromRepo(userId);

		String userInput = getUserInputFromUpdate(update);

		Team team = new Team();
		team.setTelegramUserId(userId);
		team.setName(userInput);

		teamService.saveTeam(team);
	}

	@Override
	public void initHandler() {
		handlerPhase = getPhaseService().getPhaseByHandlerName(TEAM_CONFIRMED);
	}


}
