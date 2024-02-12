package devoxx.lab.hexagonalarchitecture.courtage.domain.port.secondaire;


import devoxx.lab.hexagonalarchitecture.courtage.domain.metier.Portefeuille;

public interface PortefeuilleRepository {
	Portefeuille sauvegarderPortefeuille(Portefeuille portefeuille);
	boolean existe(String idPortefeuille);

	Portefeuille recupererPortefeuille(String idPortefeuille);
}
