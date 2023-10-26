package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.enums;

public enum StatusEnum {
    PENDING("Pendente"),
    ADIMPLENTE("adimplente"),
    CANCELED("cancelado"),
    DEFAULTER("default");
    private final String label;

    StatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
