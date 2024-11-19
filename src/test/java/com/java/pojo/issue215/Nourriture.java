package com.java.pojo.issue215;

import lombok.Builder;

public class Nourriture extends AbstractProduct {
    @Builder
    private Nourriture(final String name, final double prixHT, final boolean imported) {
        super(name);
        super.setPrixHT(prixHT);
        super.setImported(imported);
    }

    @Override
    public void applyTaxe() {
        setPrixTTC(getPrixHT());
    }

    @Override
    public boolean isExemptedTaxe() {
        return true;
    }
}