package spring.boot.service;

import java.util.ArrayList;
import java.util.List;

import spring.boot.model.Store;
import spring.boot.repo.StoreRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreManagementServiceImpl implements StoreManagementService {

    @Autowired
    StoreRepository storeRepository;

    @Override
    public Store addStore(Store store) {
        if (store == null)
            throw new IllegalArgumentException("Store is null");

        return (Store) storeRepository.save(store);
    }

    @Override
    public Store updateStore(Store store) {
        if (store == null)
            throw new IllegalArgumentException("Store is null");

        Store currentStore = getStore(store.getStoreId());

        if (currentStore == null)
            throw new IllegalArgumentException(
                    "Store doesnot exist with given store id");

        BeanUtils.copyProperties(store, currentStore);

        return (Store) storeRepository.save(currentStore);
    }

    @Override
    public Store getStore(Long id)
    {
        if (id == null)
            throw new IllegalArgumentException("Store Id is null");

        Store st = (Store) storeRepository.findOne(id);

        if (st == null) throw new IllegalArgumentException("Store with given store id does not exist");

        return st;
    }

    @Override
    public List getAllStores() {

        List list = new ArrayList();

        storeRepository.findAll().forEach(list::add);

        return list;
    }

    @Override
    public void deleteStore(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Store Id is null");

        if (getStore(id) != null)
            storeRepository.delete(id);
    }

}
