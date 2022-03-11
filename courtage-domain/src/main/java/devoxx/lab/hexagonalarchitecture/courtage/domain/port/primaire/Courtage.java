package devoxx.lab.hexagonalarchitecture.courtage.domain.port.primaire;

import devoxx.lab.hexagonalarchitecture.courtage.domain.exception.PortefeuilleDejaExistantException;
import devoxx.lab.hexagonalarchitecture.courtage.domain.exception.PortefeuilleNonGereException;
import devoxx.lab.hexagonalarchitecture.courtage.domain.model.Portefeuille;
import devoxx.lab.hexagonalarchitecture.courtage.domain.port.secondaire.PortefeuilleRepository;
import devoxx.lab.hexagonalarchitecture.courtage.domain.port.secondaire.ServiceBourse;

import java.math.BigDecimal;

public class Courtage implements ServiceCourtage {
	private final PortefeuilleRepository portefeuilleRepository;
	private final ServiceBourse serviceBourse;

	public Courtage(PortefeuilleRepository portefeuilleRepository, ServiceBourse serviceBourse) {
		this.portefeuilleRepository = portefeuilleRepository;
		this.serviceBourse = serviceBourse;
	}

	public Portefeuille creerPortefeuille(String nomPortefeuille) throws PortefeuilleDejaExistantException {
		if (gere(nomPortefeuille)) {
			throw new PortefeuilleDejaExistantException();
		}
		Portefeuille nouveauPortefeuille = new Portefeuille(nomPortefeuille);
		this.portefeuilleRepository.sauvegarde(nouveauPortefeuille);
		return nouveauPortefeuille;
	}

	public boolean gere(String nomPortefeuille) {
		return portefeuilleRepository.existe(nomPortefeuille);
	}

	private BigDecimal calculerValeurPortefeuille(Portefeuille portefeuille) {
		return portefeuille.getActions().entrySet().stream()
			.map(entry -> serviceBourse
				// entry.getKey(): nom de l'action
				.recupererCours(entry.getKey())
				// entry.getValue() : nombre d'action
				.multiply(new BigDecimal(entry.getValue())))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public BigDecimal calculerValeurPortefeuille(String nomPortefeuille) throws PortefeuilleNonGereException {
		return calculerValeurPortefeuille(
			portefeuilleRepository.recupere(nomPortefeuille)
				.orElseThrow(PortefeuilleNonGereException::new)
		);
	}

	@Override
	public void ajouteAction(int nombreActions, String nomAction, String nomPortefeuille) throws PortefeuilleNonGereException {
		portefeuilleRepository.recupere(nomPortefeuille)
			.orElseThrow(PortefeuilleNonGereException::new)
			.ajouterAction(nombreActions, nomAction)
		;
	}

	@Override
	public BigDecimal calculerValeurEnsemblePortefeuilles() {
		return portefeuilleRepository.recupereTous().stream()
			.map(this::calculerValeurPortefeuille)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
