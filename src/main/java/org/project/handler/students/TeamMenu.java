package org.project.handler.students;

import org.project.handler.UpdateHandler;
import org.project.model.Phase;
import org.project.model.Team;
import org.project.model.UserPhase;
import org.project.service.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static org.project.util.Keyboards.getAvailableTeamsKeyboard;
import static org.project.util.UpdateHelper.*;
import static org.project.util.constants.Constants.DEFAULT_OFFSET;
import static org.project.util.constants.Constants.DEFAULT_TEAM_LIMIT;
import static org.project.util.enums.HandlerName.*;
import static org.springframework.data.domain.PageRequest.of;

@Component
public class TeamMenu extends UpdateHandler {
	private final TeamService teamService;

	public TeamMenu(TeamService teamService) {
		this.teamService = teamService;
	}

	@Override
	public boolean isApplicable(Optional<Phase> phaseOptional, Update update) {
		return isUpdateCallbackEqualsHandler(update, handlerPhase.getHandlerName());
	}
	@Override
	public void handle(UserPhase userPhase, Update update) throws TelegramApiException {
		long userId = getUserIdFromUpdate(update);

		updateUserPhase(userPhase, handlerPhase);

		deleteRemovableMessagesAndEraseAllFromRepo(userId);


		if (isUpdateContainsHandler(update, TEAM_MENU_NEXT)) {
			int page = getOffsetParamFromUpdateByHandler(update, TEAM_MENU_NEXT);
			PageRequest pageRequest = of(page, DEFAULT_TEAM_LIMIT);

			deleteRemovableMessagesAndEraseAllFromRepo(userId);
			Page<Team> teams = teamService.getAllTeamByUserId(userId, pageRequest);

			sendRemovableMessage(userId, "chosse",
					getAvailableTeamsKeyboard(teams, TEAM_MENU_NEXT, TEAM_DETAILS,
							LEAD_MENU));

			return;
		}
		Page<Team> teams = teamService.getAllTeamByUserId(userId, of(DEFAULT_OFFSET, DEFAULT_TEAM_LIMIT));

		sendRemovableMessage(userId, "chosse",
				getAvailableTeamsKeyboard(teams, TEAM_MENU_NEXT, TEAM_DETAILS,
						LEAD_MENU));
	}

	@Override
	public void initHandler() {
		handlerPhase = getPhaseService().getPhaseByHandlerName(TEAM_MENU);
	}


}
