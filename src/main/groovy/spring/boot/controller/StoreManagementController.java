package spring.boot.controller;


import spring.boot.model.Store;
import spring.boot.service.StoreManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StoreManagementController extends BaseController
{

    @Autowired
    StoreManagementService storeService;

    @RequestMapping(value = "/loadstore", method = RequestMethod.GET)
    public String storeLoad(Model model) {
        model.addAttribute("store", new Store());
        return "store";
    }


    @RequestMapping(value = "/getallstores", method = RequestMethod.GET)
    public String getAllStores(Model model) {
        model.addAttribute("stores", storeService.getAllStores());
        return "storelist";
    }

    @RequestMapping(value = "/addstore", method = RequestMethod.POST)
    public String storeAdd(@ModelAttribute Store store, Model model) {
        Store addedStore = storeService.addStore(store);
        model.addAttribute("stores", storeService.getAllStores());
        return "storelist";
    }

    @RequestMapping(value = "/deletestore/{id}", method = RequestMethod.GET)
    public String storeDelete(@PathVariable Long id, Model model) {

        storeService.deleteStore(id);
        model.addAttribute("stores", storeService.getAllStores());
        return "storelist";
    }

    @RequestMapping(value = "/updatestore", method = RequestMethod.POST)
    public String storeUpdate(@ModelAttribute Store store, Model model) {
        storeService.updateStore(store);
        model.addAttribute("stores", storeService.getAllStores());
        return "storelist";
    }

    @RequestMapping(value = "/editstore/{id}", method = RequestMethod.GET)
    public String storeEdit(@PathVariable Long id, Model model) {
        model.addAttribute("store", storeService.getStore(id));
        return "editstore";
    }
}