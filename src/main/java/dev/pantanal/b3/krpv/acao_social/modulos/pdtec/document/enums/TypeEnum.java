package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.enums;

public enum TypeEnum {

    SIGN("assinar"),
    COMPLEMENTARY("complementar");
    private final String label;
    //
    TypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
