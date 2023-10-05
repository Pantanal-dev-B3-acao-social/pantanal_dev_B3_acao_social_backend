package dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.enums;

public enum VisibilityCategoryGroupEnum {
    PUBLIC_INTERNALLY("Público somente para internos da empresa"),
    PUBLIC_EXTERNALLY("Público tambem para externos da empresa"),
    PRIVATE("Privado para todos");

    private final String label;

    VisibilityCategoryGroupEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}