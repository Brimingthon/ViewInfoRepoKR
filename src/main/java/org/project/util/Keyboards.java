package org.project.util;

import org.project.model.Repo;
import org.project.model.Student;
import org.project.model.Team;
import org.project.util.enums.HandlerName;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static java.util.List.of;
import static org.project.util.constants.Buttons.MAIN_MENU;
import static org.project.util.constants.Buttons.*;
import static org.project.util.constants.Constants.DEFAULT_SEPARATOR;
import static org.project.util.constants.Constants.START_GLOBAL_COMMAND;
import static org.project.util.enums.HandlerName.*;

public class Keyboards {

    public static InlineKeyboardMarkup getConfirmationKeyboard(HandlerName agree, HandlerName decline) {
        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        firstRow.add(InlineKeyboardButton.builder().text(DECLINE).callbackData(decline.name()).build());
        firstRow.add(InlineKeyboardButton.builder().text(CONFIRM).callbackData(agree.name()).build());

        return new InlineKeyboardMarkup(of(firstRow));
    }

    public static InlineKeyboardMarkup getConfirmationKeyboard(String agree, String decline) {
        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        firstRow.add(InlineKeyboardButton.builder().text(DECLINE).callbackData(decline).build());
        firstRow.add(InlineKeyboardButton.builder().text(CONFIRM).callbackData(agree).build());

        return new InlineKeyboardMarkup(of(firstRow));
    }

    public static InlineKeyboardMarkup getMainMenuKeyboard() {
        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        firstRow.add(InlineKeyboardButton.builder().text("До головного меню").callbackData(LEAD_MENU.name()).build());

        return new InlineKeyboardMarkup(of(firstRow));
    }

    public static InlineKeyboardMarkup getAvailableServicesKeyboard() {
        InlineKeyboardButton cancelRegistration = InlineKeyboardButton.builder().text(MAIN_MENU)
                .callbackData(START_GLOBAL_COMMAND).build();

        InlineKeyboardButton startRegistration = InlineKeyboardButton.builder().text(START_REGISTRATION)
                .callbackData(GET_FIRST_NAME.name()).build();

        return new InlineKeyboardMarkup(of(of(cancelRegistration, startRegistration)));
    }

    private static String buildCallbackFromHandlerAndIdParam(HandlerName handlerName, int idParam) {
        return String.join(DEFAULT_SEPARATOR, handlerName.name(), String.valueOf(idParam));
    }

    private static String buildCallbackFromHandlerAndIdParam(HandlerName handlerName, long idParam) {
        return String.join(DEFAULT_SEPARATOR, handlerName.name(), String.valueOf(idParam));
    }

    private static String buildCallbackFromHandlerAndIdParam(HandlerName handlerName, String idParam) {
        return String.join(DEFAULT_SEPARATOR, handlerName.name(), idParam);
    }

    public static ReplyKeyboardRemove getRemoveKeyboard() {
        return ReplyKeyboardRemove.builder().removeKeyboard(true).build();
    }

    public static InlineKeyboardMarkup getMenuKeyboard() {
        List<InlineKeyboardButton> first =
                of(InlineKeyboardButton.builder().text("Перейти до меню груп").callbackData(TEAM_MENU.name()).build());
        List<InlineKeyboardButton> second =
                of(InlineKeyboardButton.builder().text("Знайти інформацію про репозиторій").callbackData(SEARCH_GIT.name()).build());
        return new InlineKeyboardMarkup(of(first, second));
    }

    public static InlineKeyboardMarkup getAvailableTeamsKeyboard(Page<Team> teams, HandlerName navigationCallback,
                                                                 HandlerName teamCallback, HandlerName backCallback) {
        List<Team> teamsList = teams.toList();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        if (hasPreviousPage(teams)) {
            rows.add(of(InlineKeyboardButton.builder().text("Попередня сторінка")
                    .callbackData(buildCallbackFromHandlerAndIdParam(navigationCallback, teams.getNumber() - 1)).build()));
        }

        for (Team value : teamsList) {
            rows.add(new ArrayList<>(of(getTeamButton(value, teamCallback))));
        }

        if (hasNextPage(teams)) {
            rows.add(of(InlineKeyboardButton.builder().text("Наступна сторінка")
                    .callbackData(buildCallbackFromHandlerAndIdParam(navigationCallback, teams.getNumber() + 1)).build()));
        }

        rows.add(of(InlineKeyboardButton.builder().text("Попереднє меню").callbackData(backCallback.name()).build()));
        rows.add(of(InlineKeyboardButton.builder().text("Створити групу").callbackData(TEAM_CREATION.name()).build()));

        return new InlineKeyboardMarkup(rows);
    }
    private static InlineKeyboardButton getTeamButton(Team team, HandlerName teamCallback) {
        return InlineKeyboardButton.builder().text(team.getName())
                .callbackData(buildCallbackFromHandlerAndIdParam(teamCallback, team.getId())).build();
    }

    public static InlineKeyboardMarkup getAvailableStudentsKeyboard(Page<Student> students, HandlerName navigationCallback,
                                                                    HandlerName studentCallback, HandlerName backCallback) {
        List<Student> studentsList = students.toList();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        if (hasPreviousPage(students)) {
            rows.add(of(InlineKeyboardButton.builder().text("Попередня сторінка")
                    .callbackData(buildCallbackFromHandlerAndIdParam(navigationCallback, students.getNumber() - 1)).build()));
        }

        for (Student value : studentsList) {
            rows.add(new ArrayList<>(of(getStudentButton(value, studentCallback))));
        }

        if (hasNextPage(students)) {
            rows.add(of(InlineKeyboardButton.builder().text("Наступна сторінка")
                    .callbackData(buildCallbackFromHandlerAndIdParam(navigationCallback, students.getNumber() + 1)).build()));
        }

        rows.add(of(InlineKeyboardButton.builder().text("Попереднє меню").callbackData(backCallback.name()).build()));
        rows.add(of(InlineKeyboardButton.builder().text("Додати користувача").callbackData(TEAM_CREATION.name()).build()));

        return new InlineKeyboardMarkup(rows);
    }
    private static InlineKeyboardButton getStudentButton(Student student, HandlerName studentCallback) {
        return InlineKeyboardButton.builder().text(student.getName()+" "+ student.getSurname())
                .callbackData(buildCallbackFromHandlerAndIdParam(studentCallback, student.getId())).build();
    }

    public static InlineKeyboardMarkup getAvailableReposKeyboard(Page<Repo> repos, HandlerName navigationCallback,
                                                                 HandlerName repocallback, HandlerName backCallback) {
        List<Repo> repoList = repos.toList();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        if (hasPreviousPage(repos)) {
            rows.add(of(InlineKeyboardButton.builder().text("Попередня сторінка")
                    .callbackData(buildCallbackFromHandlerAndIdParam(navigationCallback, repos.getNumber() - 1)).build()));
        }

        for (Repo value : repoList) {
            rows.add(new ArrayList<>(of(getRepoButton(value, repocallback))));
        }

        if (hasNextPage(repos)) {
            rows.add(of(InlineKeyboardButton.builder().text("Наступна сторінка")
                    .callbackData(buildCallbackFromHandlerAndIdParam(navigationCallback, repos.getNumber() + 1)).build()));
        }

        rows.add(of(InlineKeyboardButton.builder().text("Попереднє меню").callbackData(backCallback.name()).build()));
        rows.add(of(InlineKeyboardButton.builder().text("Додати існуючий репозиторій").callbackData(REPO_CREATION.name()).build()));

        return new InlineKeyboardMarkup(rows);
    }
    private static InlineKeyboardButton getRepoButton(Repo repo, HandlerName repoCallback) {
        return InlineKeyboardButton.builder().text(repo.getName())
                .callbackData(buildCallbackFromHandlerAndIdParam(repoCallback, repo.getId())).build();
    }

    public static InlineKeyboardMarkup getMoreRepoInfoKeyboard(long repo, HandlerName commitCallback,
                                                               HandlerName branchCallback) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(of(InlineKeyboardButton.builder().text("Переглянути коміти")
                .callbackData(buildCallbackFromHandlerAndIdParam(commitCallback, repo)).build()));
        rows.add(of(InlineKeyboardButton.builder().text("Переглянути гілки")
                .callbackData(buildCallbackFromHandlerAndIdParam(branchCallback, repo)).build()));
        rows.add(of(InlineKeyboardButton.builder().text("Попереднє меню")
                .callbackData(buildCallbackFromHandlerAndIdParam(branchCallback, repo)).build()));
        return new InlineKeyboardMarkup(rows);
    }

    public static InlineKeyboardMarkup getBackRepoKeyboard(long repo, HandlerName backCallback) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(of(InlineKeyboardButton.builder().text("Повернутись")
                .callbackData(buildCallbackFromHandlerAndIdParam(backCallback, repo)).build()));
        return new InlineKeyboardMarkup(rows);
    }
    private static boolean hasPreviousPage(Page<?> page) {
        return !page.isFirst() && page.getTotalPages() > 1;
    }

    private static boolean hasNextPage(Page<?> page) {
        return !page.isLast() && page.getTotalPages() > 1;
    }


}
