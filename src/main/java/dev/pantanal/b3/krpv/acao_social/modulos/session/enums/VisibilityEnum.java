package dev.pantanal.b3.krpv.acao_social.modulos.session.enums;

public enum VisibilityEnum {
    PUBLIC_INTERNALLY("Público somente para internos da empresa"),
    PUBLIC_EXTERNALLY("Público tambem para externos da empresa"),
    PRIVATE("Privado para todos");

    private final String label;

    VisibilityEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
