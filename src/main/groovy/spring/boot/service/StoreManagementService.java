package spring.boot.service;

import java.util.List;
import spring.boot.model.Store;

public interface StoreManagementService {
    public Store addStore(Store store);
    public List getAllStores();
    public Store getStore(Long id);
    public Store updateStore(Store store);
    public void deleteStore(Long id);
}