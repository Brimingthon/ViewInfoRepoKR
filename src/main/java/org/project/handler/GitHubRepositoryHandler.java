package org.project.handler;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.project.model.Phase;
import org.project.model.UserPhase;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Optional;

import static org.project.util.UpdateHelper.getUserIdFromUpdate;
import static org.project.util.UpdateHelper.isUpdateCallbackEqualsHandler;
import static org.project.util.enums.HandlerName.*;

@Component
public class GitHubRepositoryHandler extends UpdateHandler{
	@Override
	public boolean isApplicable(Optional<Phase> phaseOptional, Update update) {
		return isUpdateCallbackEqualsHandler(update, handlerPhase.getHandlerName());
	}
	@Override
	public void handle(UserPhase userPhase, Update update) throws TelegramApiException, IOException {
		long userId = getUserIdFromUpdate(update);

		updateUserPhase(userPhase, handlerPhase);

		deleteRemovableMessagesAndEraseAllFromRepo(userId);
		
		GHRepository repository = null;

		GitHub github = GitHub.connectAnonymously();
		repository = github.getRepository("Brimingthon/crispy-potato");


		String string = String.format("Repository: %s\nDescription: %s\nStars: %d\nForks: %d\nOpen Issues: %d",
				repository.getFullName(),
				repository.getDescription(),
				repository.getStargazersCount(),
				repository.getForks(),
				repository.getOpenIssueCount());
		
		sendRemovableMessage(userId, string);
		
	}

	@Override
	public void initHandler() {
		handlerPhase = getPhaseService().getPhaseByHandlerName(GET_GIT_INFO);
	}


}
