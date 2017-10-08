package springboot.management.store.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "Store")
public class Store {

	@Id
	@GeneratedValue(generator="STORE_SEQ")
	@SequenceGenerator(name="STORE_SEQ",sequenceName="STORE_SEQ", allocationSize=1)
	Long storeId;
	

	String storeName;
	
	String storeStreetAddress;
	
	String storeSuburb;

	String storePostcode;

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreStreetAddress() {
		return storeStreetAddress;
	}

	public void setStoreStreetAddress(String storeStreetAddress) {
		this.storeStreetAddress = storeStreetAddress;
	}

	
	public String getStoreSuburb() {
		return storeSuburb;
	}

	public void setStoreSuburb(String storeSuburb) {
		this.storeSuburb = storeSuburb;
	}

	public String getStorePostcode() {
		return storePostcode;
	}

	public void setStorePostcode(String storePostcode) {
		this.storePostcode = storePostcode;
	}

}
