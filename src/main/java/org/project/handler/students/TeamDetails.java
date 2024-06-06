package org.project.handler.students;

import org.project.handler.UpdateHandler;
import org.project.model.Phase;
import org.project.model.Student;
import org.project.model.UserPhase;
import org.project.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static org.project.util.Keyboards.getAvailableStudentsKeyboard;
import static org.project.util.UpdateHelper.*;
import static org.project.util.constants.Constants.DEFAULT_OFFSET;
import static org.project.util.constants.Constants.DEFAULT_LIMIT;
import static org.project.util.enums.HandlerName.*;
import static org.springframework.data.domain.PageRequest.of;

@Component
public class TeamDetails extends UpdateHandler {
	private final StudentService studentService;

	public TeamDetails(StudentService studentService) {
		this.studentService = studentService;
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
		long teamId = getCallbackQueryIdParamFromUpdate(update);


		if (isUpdateContainsHandler(update, TEAM_DETAILS_NEXT)) {
			int page = getOffsetParamFromUpdateByHandler(update, TEAM_DETAILS_NEXT);
			PageRequest pageRequest = of(page, DEFAULT_LIMIT);

			deleteRemovableMessagesAndEraseAllFromRepo(userId);
			Page<Student> students = studentService.getAllStudentsByTeamId(teamId, pageRequest);

			sendRemovableMessage(userId, "<b>Деталі команди\nОберіть користувача, чиї репориторії бажаєте переглянути!</b>",
					getAvailableStudentsKeyboard(students, TEAM_DETAILS_NEXT, STUDENT_REPOS,
							LEAD_MENU));

			return;
		}
		Page<Student> students = studentService.getAllStudentsByTeamId(teamId, of(DEFAULT_OFFSET, DEFAULT_LIMIT));

		sendRemovableMessage(userId, "<b>Деталі команди\nОберіть користувача, чиї репориторії бажаєте переглянути!</b>",
				getAvailableStudentsKeyboard(students, TEAM_DETAILS_NEXT, STUDENT_REPOS,
						LEAD_MENU));
	}

	@Override
	public void initHandler() {
		handlerPhase = getPhaseService().getPhaseByHandlerName(TEAM_DETAILS);
	}


}
