package hibernate.avengers.service;

import hibernate.avengers.domain.Avenger;

public interface AvengerService {

	public void createAvenger(Avenger avenger);
	
	public Avenger retrieveAvengerByVillainName(String villainName);
}
