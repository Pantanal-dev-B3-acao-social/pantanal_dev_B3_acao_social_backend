package dev.pantanal.b3.krpv.acao_social.modulos.session.enums;

public enum StatusEnum {
        OCCURRING("Ocorrendo"),
        PENDING("Pendente"),
        OPEN("Aberto "),
        CLOSED("Fechado");

    private final String label;

    StatusEnum(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
