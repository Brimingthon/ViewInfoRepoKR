package org.project.handler.students;

import org.project.handler.UpdateHandler;
import org.project.model.Phase;
import org.project.model.UserPhase;
import org.project.service.RepoService;
import org.project.service.StudentService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static org.project.util.Keyboards.getBackRepoKeyboard;
import static org.project.util.Keyboards.getMoreRepoInfoKeyboard;
import static org.project.util.UpdateHelper.*;
import static org.project.util.enums.HandlerName.*;

@Component
public class FindCommits extends UpdateHandler {
	private final StudentService studentService;
	private final RepoService repoService;

	public FindCommits(StudentService studentService, RepoService repoService) {
		this.studentService = studentService;
		this.repoService = repoService;
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
		long repoId = getCallbackQueryIdParamFromUpdate(update);

		sendRemovableMessage(userId, repoService.getInfoAboutCommit(repoId), getBackRepoKeyboard(repoId, REPO_DETAILS));

	}

	@Override
	public void initHandler() {
		handlerPhase = getPhaseService().getPhaseByHandlerName(COMMIT_DETAILS);
	}


}
