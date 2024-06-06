package org.project.handler.students.create;

import org.project.handler.UpdateHandler;
import org.project.model.*;
import org.project.repository.RepoRepository;
import org.project.repository.TeamRepository;
import org.project.service.RepoService;
import org.project.service.StudentService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static org.project.util.UpdateHelper.*;
import static org.project.util.enums.HandlerName.*;

@Component
public class CreateRepoConfirm extends UpdateHandler {

	private final RepoService repoService;

	public CreateRepoConfirm(RepoService repoService) {

		this.repoService = repoService;
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

		Repo repo = new Repo();
		repo.setName(userInput);

		repoService.saveRepo(repo);
	}

	@Override
	public void initHandler() {
		handlerPhase = getPhaseService().getPhaseByHandlerName(TEAM_CONFIRMED);
	}


}
