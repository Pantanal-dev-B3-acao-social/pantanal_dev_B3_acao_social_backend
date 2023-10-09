package dev.pantanal.b3.krpv.acao_social.modulos.ong.enums;

public enum StatusEnum {
        DEFAULTER("Inadimplente"),
        SUPPLIER("Adimplente"),
        SUSPENDED("Suspenso "),
        REGULAR("Regular");

    private final String label;

    StatusEnum(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
