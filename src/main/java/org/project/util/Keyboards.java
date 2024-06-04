package org.project.util;

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

        firstRow.add(InlineKeyboardButton.builder().text("go to team menu").callbackData(LEAD_MENU.name()).build());

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
                of(InlineKeyboardButton.builder().text("go to team menu").callbackData(TEAM_MENU.name()).build());
        List<InlineKeyboardButton> second =
                of(InlineKeyboardButton.builder().text("search for git repo").callbackData(TRACKING_ROUTES.name()).build());
        return new InlineKeyboardMarkup(of(first, second));
    }

    public static InlineKeyboardMarkup getAvailableTeamsKeyboard(Page<Team> teams, HandlerName navigationCallback,
                                                                 HandlerName teamCallback, HandlerName backCallback) {
        List<Team> tripsList = teams.toList();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        if (hasPreviousPage(teams)) {
            rows.add(of(InlineKeyboardButton.builder().text("back")
                    .callbackData(buildCallbackFromHandlerAndIdParam(navigationCallback, teams.getNumber() - 1)).build()));
        }

        for (Team value : tripsList) {
            rows.add(new ArrayList<>(of(getTeamButton(value, teamCallback))));
        }

        if (hasNextPage(teams)) {
            rows.add(of(InlineKeyboardButton.builder().text("next")
                    .callbackData(buildCallbackFromHandlerAndIdParam(navigationCallback, teams.getNumber() + 1)).build()));
        }

        rows.add(of(InlineKeyboardButton.builder().text("previous").callbackData(backCallback.name()).build()));
        rows.add(of(InlineKeyboardButton.builder().text("create team").callbackData(TEAM_CREATION.name()).build()));

        return new InlineKeyboardMarkup(rows);
    }
    private static InlineKeyboardButton getTeamButton(Team team, HandlerName teamCallback) {
        return InlineKeyboardButton.builder().text("Dsfsdf")
                .callbackData(buildCallbackFromHandlerAndIdParam(teamCallback, team.getId())).build();
    }

    private static boolean hasPreviousPage(Page<?> page) {
        return !page.isFirst() && page.getTotalPages() > 1;
    }

    private static boolean hasNextPage(Page<?> page) {
        return !page.isLast() && page.getTotalPages() > 1;
    }


}
