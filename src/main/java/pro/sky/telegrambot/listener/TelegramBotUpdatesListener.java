package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger LOGGER = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            LOGGER.info("Processing update: {}", update);
            // Process your updates here

            if (update.callbackQuery() != null) {//
                if (update.callbackQuery().data().equals("stage1"))//
                    giveInfoAboutShelter(update);//

                if (update.callbackQuery().data().equals("stage2"))//
                    printInstructionsAboutPetFromShelter(update);//

                if (update.callbackQuery().data().equals("stage3"))//
                    getPetReport(update);//

                if(update.callbackQuery().data().equals("callVolunteer")){//
                    callVolunteer(update);//
                }//
            }//

            if (update.message() != null) {//
                // старт//
                if (update.message().text().equals("/start"))//
                    selectRequest(update);//
            }//
        });//
        return UpdatesListener.CONFIRMED_UPDATES_ALL;//
    }

    private void selectRequest(Update update) {
        telegramBot.execute(new SendMessage(update.message().chat().id(),
                "Приветствую! Я бот, помогаю взаимодействовать с приютами для собачек!"));

        SendMessage sm = new SendMessage(update.message().chat().id(),
                "Какой у вас вопрос?");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.addRow(new InlineKeyboardButton("Узнать информацию о приюте").callbackData("stage1"));
        markup.addRow(new InlineKeyboardButton("Как взять животное из приюта").callbackData("stage2"));
        markup.addRow(new InlineKeyboardButton("Прислать отчет о питомце").callbackData("stage3"));
        markup.addRow(new InlineKeyboardButton("Позвать волонтёра").callbackData("callVolunteer"));

        sm.replyMarkup(markup);
        telegramBot.execute(sm);
    }

    private void giveInfoAboutShelter(Update update){ // здесь этап 1

    }
    private void printInstructionsAboutPetFromShelter(Update update){ // здесь этап 2

    }
    private void getPetReport(Update update){ // здесь этап 3

    }
    private void callVolunteer(Update update){ // позвать волонтёра

    }

}
