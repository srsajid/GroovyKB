package hibernate.avengers.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hibernate.avengers.domain.Avenger;

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public interface AvengersDAO {

	public void createAvenger(Avenger avenger);

	public Avenger retrieveAvengerByVillainName(String villainName);

}
