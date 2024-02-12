package devoxx.lab.hexagonalarchitecture.courtage.domain.port.secondaire;

import devoxx.lab.hexagonalarchitecture.courtage.domain.metier.Portefeuille;

import java.util.HashMap;
import java.util.Map;

public class PortefeuilleRepositoryAdapter implements PortefeuilleRepository{
	private final Map<String, Portefeuille> portefeuilleHashMap = new HashMap<>();
	@Override
	public Portefeuille sauvegarderPortefeuille(Portefeuille portefeuille) {
		portefeuilleHashMap.put(portefeuille.getId(), portefeuille);

		return portefeuilleHashMap.get(portefeuille.getId());
	}

	@Override
	public boolean existe(String idPortefeuille) {
		return portefeuilleHashMap.containsKey(idPortefeuille);
	}

	@Override
	public Portefeuille recupererPortefeuille(String idPortefeuille) {
		return portefeuilleHashMap.get(idPortefeuille);
	}
}
