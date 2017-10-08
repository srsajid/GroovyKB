//package hibernate.avengers.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//import com.avengers.dao.AvengersDAO;
//import com.avengers.domain.Avenger;
//import com.avengers.service.AvengerService;
//
//@Component("avengerService")
//public class AvengerServiceImpl implements AvengerService{
//
//	@Autowired
//	@Qualifier(value="avengersDAO")
//	private AvengersDAO avengerDAO;
//
//
//	public void createAvenger(Avenger avenger) {
//		avengerDAO.createAvenger(avenger);
//	}
//
//
//	public Avenger retrieveAvengerByVillainName(String villainName) {
//		return avengerDAO.retrieveAvengerByVillainName(villainName);
//	}
//
//}
