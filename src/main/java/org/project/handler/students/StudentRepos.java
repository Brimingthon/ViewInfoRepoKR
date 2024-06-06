package org.project.handler.students;

import org.project.handler.UpdateHandler;
import org.project.model.Phase;
import org.project.model.Repo;
import org.project.model.Student;
import org.project.model.UserPhase;
import org.project.service.RepoService;
import org.project.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static org.project.util.Keyboards.getAvailableReposKeyboard;
import static org.project.util.Keyboards.getAvailableStudentsKeyboard;
import static org.project.util.UpdateHelper.*;
import static org.project.util.constants.Constants.DEFAULT_OFFSET;
import static org.project.util.constants.Constants.DEFAULT_LIMIT;
import static org.project.util.enums.HandlerName.*;
import static org.springframework.data.domain.PageRequest.of;

@Component
public class StudentRepos extends UpdateHandler {
	private final StudentService studentService;
	private final RepoService repoService;

	public StudentRepos(StudentService studentService, RepoService repoService) {
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
		long studentId = getCallbackQueryIdParamFromUpdate(update);

		if (isUpdateContainsHandler(update, STUDENT_REPOS_NEXT)) {
			int page = getOffsetParamFromUpdateByHandler(update, STUDENT_REPOS_NEXT);
			PageRequest pageRequest = of(page, DEFAULT_LIMIT);

			deleteRemovableMessagesAndEraseAllFromRepo(userId);
			Page<Repo> repos = repoService.getAllReposByStudentId(studentId, pageRequest);

			sendRemovableMessage(userId, "<b>Меню репозиторіїв користувача\nОберіть репозиторій, який би хотіли переглянути!</b>",
					getAvailableReposKeyboard(repos, STUDENT_REPOS_NEXT, REPO_DETAILS,
							LEAD_MENU));

			return;
		}
		Page<Repo> repos = repoService.getAllReposByStudentId(studentId, of(DEFAULT_OFFSET, DEFAULT_LIMIT));

		sendRemovableMessage(userId, "<b>Меню репозиторіїв користувача\nОберіть репозиторій, який би хотіли переглянути!</b>",
				getAvailableReposKeyboard(repos, STUDENT_REPOS_NEXT, REPO_DETAILS,
						LEAD_MENU));
	}

	@Override
	public void initHandler() {
		handlerPhase = getPhaseService().getPhaseByHandlerName(STUDENT_REPOS);
	}


}
