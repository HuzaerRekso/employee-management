package com.huzaer.employeemanagement.component;

import com.huzaer.core.BaseObject;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * This class used to get real message in messages.properties file
 *
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
@Component
public class Message extends BaseObject implements MessageSourceAware {

    private static MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        Message.messageSource = messageSource;
    }

    public static String get(String key, String... params) {
        return messageSource.getMessage(key, params, Locale.ENGLISH);
    }
}
