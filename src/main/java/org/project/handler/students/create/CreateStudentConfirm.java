package org.project.handler.students.create;

import org.project.handler.UpdateHandler;
import org.project.model.Phase;
import org.project.model.Student;
import org.project.model.Team;
import org.project.model.UserPhase;
import org.project.repository.TeamRepository;
import org.project.service.StudentService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static org.project.util.UpdateHelper.*;
import static org.project.util.enums.HandlerName.*;

@Component
public class CreateStudentConfirm extends UpdateHandler {
	private final StudentService studentService;
	private final TeamRepository teamRepository;

	public CreateStudentConfirm(StudentService studentService,
	                            TeamRepository teamRepository) {
		this.studentService = studentService;
		this.teamRepository = teamRepository;
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

		Student student = new Student();
		student.setName(userInput);
		student.setTeam(teamRepository.getById(userId));

		studentService.saveStudent(student);
	}

	@Override
	public void initHandler() {
		handlerPhase = getPhaseService().getPhaseByHandlerName(TEAM_CONFIRMED);
	}


}
