package com.traductor.domain;

public enum Language {
    Auto("auto"),
    Arabic("ar"),
    Chinese("zh"),
    Czech("cs"),
    Danish("da"),
    Dutch("nl"),
    English("en"),
    Finnish("fi"),
    French("fr"),
    German("de"),
    Hebrew("he"),
    Indonesian("id"),
    Italian("it"),
    Japanese("ja"),
    Korean("ko"),
    Polish("pl"),
    Portuguese("pt"),
    Russian("ru"),
    Spanish("es"),
    Swedish("sv"),
    Turkish("tr");

    private String langCode;

    Language(String langCode) {
        this.langCode = langCode;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }
}
