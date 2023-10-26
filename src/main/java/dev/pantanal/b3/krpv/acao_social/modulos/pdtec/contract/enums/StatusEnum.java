package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.enums;

public enum StatusEnum {
    RUNNING("em andamento"),
    CREATED("criado"),
    DRAFTED("arquivado"),
    PENDING("pendente"),
    CANCELED("cancelado"),
    DONE("processo completo"),
    REJECTED("rejeitado"),
    EXPIRATED("expirated"),
    EXPIRED("expirado");
    private final String label;
//
    StatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
