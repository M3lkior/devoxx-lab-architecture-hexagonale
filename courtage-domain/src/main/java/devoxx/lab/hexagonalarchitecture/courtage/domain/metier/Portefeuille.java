package devoxx.lab.hexagonalarchitecture.courtage.domain.metier;

import java.math.BigDecimal;

public class Portefeuille {

	private final String id;
	private BigDecimal valeur = BigDecimal.valueOf(0, 1);

	public Portefeuille(String idPortefeuille, BigDecimal valeur) {
		this.id = idPortefeuille;
		this.valeur = valeur;
	}

	public String getId() {
		return id;
	}

	public BigDecimal getValeur() {
		return valeur;
	}
}
