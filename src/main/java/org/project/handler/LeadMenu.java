package org.project.handler;

import org.project.model.Phase;
import org.project.model.UserPhase;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static org.project.util.Keyboards.getMenuKeyboard;
import static org.project.util.UpdateHelper.getUserIdFromUpdate;
import static org.project.util.UpdateHelper.isUpdateCallbackEqualsHandler;
import static org.project.util.enums.HandlerName.LEAD_MENU;

@Component
public class LeadMenu extends UpdateHandler {

    @Override
    public boolean isApplicable(Optional<Phase> phaseOptional, Update update) {
        return isUpdateCallbackEqualsHandler(update, handlerPhase.getHandlerName());
    }

    @Override
    public void handle(UserPhase userPhase, Update update) throws TelegramApiException {
        long userId = getUserIdFromUpdate(update);

        updateUserPhase(userPhase, handlerPhase);

        deleteRemovableMessagesAndEraseAllFromRepo(userId);

        sendRemovableMessage(userId, "<b>Вітаємо у загальному меню!\n Оберіть опцію нижче!</b>", getMenuKeyboard());
    }

    @Override
    public void initHandler() {
        handlerPhase = getPhaseService().getPhaseByHandlerName(LEAD_MENU);
    }
}
