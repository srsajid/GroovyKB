package springboot.management.store.service;

import java.util.List;

import springboot.management.store.model.Store;

public interface StoreManagementService {
	
	public Store addStore(Store store);
	public List<Store> getAllStores();
	public Store getStore(Long id);
	public Store updateStore(Store store);
	public void deleteStore(Long id);
}
