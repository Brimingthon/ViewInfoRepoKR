package org.project.util.constants;

import static org.project.util.constants.Constants.DEFAULT_OFFSET;

public class Messages {
    public static String LINE_BREAK = "\n\n";
    public static String joinMessages(String...messages) {
        StringBuilder builder = new StringBuilder(messages[DEFAULT_OFFSET]);

        for (int i = 1; i < messages.length; i++) {
            builder.append(LINE_BREAK).append(messages[i]);
        }

        return builder.toString();
    }
    public static String EXCEPTION_MESSAGE = "Упс, щось трапилось \uD83D\uDE23 і бот нажаль не може вам відповісти. " + "Будь ласка зв'яжиться з нашою підтримкою і вони вам допоможуть! ✌";
    public static String GENERAL_GREETING = "Вітаємо вас у боті!";
    public static String GREETING = "Вітаємо у головному меню.\uD83D\uDE8F";
    public static String WRONG_ROUTE_DATA_PROVIDED = "Ввід інформації вручну не підтримується на цьому етапі❌";
}
