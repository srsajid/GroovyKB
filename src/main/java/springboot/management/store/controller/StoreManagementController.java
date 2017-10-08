package springboot.management.store.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;
import springboot.management.store.entities.Post;
import springboot.management.store.entities.Tag;
import springboot.management.store.model.Store;
import springboot.management.store.service.PostService;
import springboot.management.store.service.StoreManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Arrays;

@Controller
public class StoreManagementController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	StoreManagementService storeManagementService;

//	@Autowired
//	StoreService storeService;

	@RequestMapping(value = "/loadstore", method = RequestMethod.GET)
	public String storeLoad(Model model) {
//		storeService.save();
		model.addAttribute("store", new Store());
		return "store";
	}

	
	@RequestMapping(value = "/getallstores", method = RequestMethod.GET)
	public String getAllStores(Model model) {
		model.addAttribute("stores", storeManagementService.getAllStores());
		return "storelist";
	}
	
	@RequestMapping(value = "/addstore", method = RequestMethod.POST)
	public String storeAdd(@ModelAttribute Store store, Model model) {
		Store addedStore = storeManagementService.addStore(store);
		model.addAttribute("stores", storeManagementService.getAllStores());
		return "storelist";
	}
	
	@RequestMapping(value = "/deletestore/{id}", method = RequestMethod.GET)
	public String storeDelete(@PathVariable Long id, Model model) {

		storeManagementService.deleteStore(id);
		model.addAttribute("stores", storeManagementService.getAllStores());
		return "storelist";
	}
	
	@RequestMapping(value = "/updatestore", method = RequestMethod.POST)
	public String storeUpdate(@ModelAttribute Store store, Model model) {
		storeManagementService.updateStore(store);
		model.addAttribute("stores", storeManagementService.getAllStores());
		return "storelist";
	}
	
	@RequestMapping(value = "/editstore/{id}", method = RequestMethod.GET)
	public String storeEdit(@PathVariable Long id, Model model) {
		model.addAttribute("store", storeManagementService.getStore(id));
		return "editstore";
	}

	public void before() {
		elasticsearchTemplate.deleteIndex(Post.class);
		elasticsearchTemplate.createIndex(Post.class);
		elasticsearchTemplate.putMapping(Post.class);
		elasticsearchTemplate.refresh(Post.class);
	}

	public void testSave() throws Exception {
		Tag tag = new Tag();
		tag.setId("1");
		tag.setName("tech");
		Tag tag2 = new Tag();
		tag2.setId("2");
		tag2.setName("elasticsearch");

		Post post = new Post();
		post.setId("1");
		post.setTitle("Bigining with spring boot application and elasticsearch");
		post.setTags(Arrays.asList(tag, tag2));
		postService.save(post);


		Post post2 = new Post();
		post2.setId("2");
		post2.setTitle("Bigining with spring boot application");
		post2.setTags(Arrays.asList(tag));
		postService.save(post2);




	}

	public void testFindOne() throws Exception {

	}

	public void testFindAll() throws Exception {

	}
	public void testFindByTagsName() throws Exception {
		Tag tag = new Tag();
		tag.setId("1");
		tag.setName("tech");
		Tag tag2 = new Tag();
		tag2.setId("2");
		tag2.setName("elasticsearch");

		Post post = new Post();
		post.setId("1");
		post.setTitle("Bigining with spring boot application and elasticsearch");
		post.setTags(Arrays.asList(tag, tag2));
		postService.save(post);



		Post post2 = new Post();
		post2.setId("2");
		post2.setTitle("Bigining with spring boot application");
		post2.setTags(Arrays.asList(tag));
		postService.save(post2);

		Page<Post> posts  = postService.findByTagsName("tech", new PageRequest(0,10));
		Page<Post> posts2  = postService.findByTagsName("tech", new PageRequest(0,10));
		Page<Post> posts3  = postService.findByTagsName("maz", new PageRequest(0,10));

	}

	@ResponseBody
	@RequestMapping(value = "/test")
	public String test() throws Exception {
		testSave();
		testFindByTagsName();
		return "";
	}
}
