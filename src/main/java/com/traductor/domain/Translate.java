package com.traductor.domain;

import com.amazonaws.services.translate.model.TranslateTextRequest;

import javax.persistence.Entity;

public class Translate {

    private String text;

    private Language sourceLanguage;

    private String translatedText;

    private Language targetLanguage;

    private TranslateTextRequest request;

    public Translate() {
    }

    public Translate(String text, Language sourceLanguage, String translatedText, Language targetLanguage) {
        this.text = text;
        this.sourceLanguage = sourceLanguage;
        this.translatedText = translatedText;
        this.targetLanguage = targetLanguage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Language getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(Language sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public Language getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(Language targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public TranslateTextRequest getRequest() {
        this.request = new TranslateTextRequest()
                .withText(text)
                .withSourceLanguageCode(this.sourceLanguage.getLangCode())
                .withTargetLanguageCode(this.targetLanguage.getLangCode());
        return request;
    }

    public void setRequest(TranslateTextRequest request) {
        this.request = request;
    }
}
