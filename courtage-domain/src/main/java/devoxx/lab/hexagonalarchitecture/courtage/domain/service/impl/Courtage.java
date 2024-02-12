package devoxx.lab.hexagonalarchitecture.courtage.domain.service.impl;

import devoxx.lab.hexagonalarchitecture.courtage.domain.exception.PortefeuilleDejaExistantException;
import devoxx.lab.hexagonalarchitecture.courtage.domain.exception.PortefeuilleNonGereException;
import devoxx.lab.hexagonalarchitecture.courtage.domain.metier.Portefeuille;
import devoxx.lab.hexagonalarchitecture.courtage.domain.port.secondaire.PortefeuilleRepository;
import devoxx.lab.hexagonalarchitecture.courtage.domain.service.CourtageService;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class Courtage implements CourtageService {
	private final PortefeuilleRepository portefeuilleRepository;

    public Courtage(PortefeuilleRepository portefeuilleRepository) {
        this.portefeuilleRepository = portefeuilleRepository;
    }

    @Override
	public Portefeuille creerPortefeuille(String idPortefeuille) throws PortefeuilleDejaExistantException {
		if(existe(idPortefeuille)){
			throw new PortefeuilleDejaExistantException();
		}

		return portefeuilleRepository.sauvegarderPortefeuille(new Portefeuille(idPortefeuille, BigDecimal.ZERO));
	}

	@Override
	public boolean existe(String idPortefeuille) {
		return portefeuilleRepository.existe(idPortefeuille);
	}

	@Override
	public BigDecimal recupererValeur(String idPortefeuille) throws PortefeuilleNonGereException {
		return Optional.ofNullable(portefeuilleRepository.recupererPortefeuille(idPortefeuille))
			.orElseThrow(PortefeuilleNonGereException::new)
			.getValeur();
	}

}
