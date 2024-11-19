package com.java.pojo.issue215;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class AbstractProduct implements Taxable {

	@NonNull
	private String name;

	@Setter
	private double prixHT;

	@Setter
	private boolean imported;

	@Setter
	private double prixTTC;

	@Setter
	private double taxe;

	@Override
	public boolean isExemptedTaxe() {
		return false;
	}
}