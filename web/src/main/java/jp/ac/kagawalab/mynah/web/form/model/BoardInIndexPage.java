package jp.ac.kagawalab.mynah.web.form.model;

import lombok.Value;

import java.util.Calendar;

@Value
public class BoardInIndexPage {
    int number;
    String topic;
    Calendar dataPublishedFrom;
    Calendar dataPublishedTo;
}
