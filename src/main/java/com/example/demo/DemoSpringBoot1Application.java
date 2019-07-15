package com.example.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.security.auth.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RestController
@EnableSwagger2
public class DemoSpringBoot1Application {

	private static final Logger logger = LoggerFactory.getLogger(DemoSpringBoot1Application.class);
	static java.util.Properties props = new java.util.Properties();
	// Properties props = null;
	static Connection connection = null;
	static Domain domain = null;
	static ObjectStore objectstore = null;

	public static Connection getConnection() throws IOException {

		FileInputStream file = null;
	
		System.setProperty("wasp.location", "C:\\Nagendra\\wsi");
		System.setProperty("filenet.pe.bootstrap.ceuri", "http://172.16.8.125:9080/wsi/FNCEWS40MTOM");
		Connection connection = Factory.Connection.getConnection("http://172.16.8.125:9080/wsi/FNCEWS40MTOM");
		UserContext userContext = UserContext.get();
		Subject subject = userContext.createSubject(connection, "p8admin", "mits123$", "FileNetP8WSI");
		userContext.pushSubject(subject);
		System.out.println("CE Connection Establised Sucessfully");
		return connection;
	}

	public static Domain getDomain() throws IOException {
	Connection connection2 = getConnection();
		Domain domain = Factory.Domain.fetchInstance(connection2, "fndn", null);
		return domain;
	}

	public static ObjectStore getObjectStore() throws IOException {
		Domain domain2 = getDomain();		
		ObjectStore objectstore = Factory.ObjectStore.fetchInstance(domain2, "CMTOS", null);
		return objectstore;
	}

	@GetMapping("/index")
//	@RequestMapping(method = RequestMethod.GET, value = "/api/javainuse")
	public ModelAndView indexPage() {
		return new ModelAndView("index");
	}
	
	/*
	 * @PostMapping("/validate") public ModelAndView validate(ModelMap
	 * map, @RequestParam("name") String name) { if (name == null || name.isEmpty()
	 * ) { map.addAttribute("errorMessage", "Name should not be empty"); return new
	 * ModelAndView("index", map); } map.addAttribute("name", name); return new
	 * ModelAndView("welcome", map); }
	 */
	
	@GetMapping("/Name/{name}")
	public String getName1(@PathVariable String name) {
		System.out.println("getName api is called {}" + name);
		
		try {
			/*
			 * DemoSpringBoot1Application connectToFilenet = new
			 * DemoSpringBoot1Application(); ObjectStore objectStore2 =
			 * connectToFilenet.getObjectStore(); System.out.println(objectStore2 +
			 * "...objectStore2...");
			 * 
			 * Folder folderInstance = Factory.Folder.createInstance(objectStore2,
			 * "AO_AccountOpening"); Folder get_RootFolder = objectStore2.get_RootFolder();
			 * folderInstance.getProperties().putValue("AO_FirstName", "Nag");
			 * folderInstance.getProperties().putValue("AO_LastName", "Chinna");
			 * folderInstance.save(RefreshMode.REFRESH);
			 * System.out.println("Properties r Updated Successfully.,,");
			 */
			name = "Properties r Updated Successfully..!";
			
			
			//name="Success";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			System.out.println("..error.."+e.getMessage());
			name=e.getMessage();
			
		}
		
	return name;	
	}
	

	
	@GetMapping("/Name/{name}/empId/{empid}/department/{dept}")
	///empName/{empname1}/empId/{empId1}/department/{department1}
	public String getName1(@PathVariable String name,@PathVariable String empid, @PathVariable String dept) throws JSONException {
		System.out.println("getName api is called {____________}" + name);
		logger.info("getName api is called {____________}" + name);
		JSONArray array=new JSONArray();
		JSONObject object=new JSONObject();	
		try {

		//	name = null;
			DemoSpringBoot1Application connectToFilenet = new DemoSpringBoot1Application();
			Connection connection2 = connectToFilenet.getConnection();
			System.out.println(connection2 + "...connection2...");
			logger.info(connection2 + "...connection2...");
			Domain domain2 = connectToFilenet.getDomain();
			System.out.println(domain2.get_Name() + "...domain2...");
			logger.info(domain2.get_Name() + "...domain2...");
			ObjectStore objectStore2 = connectToFilenet.getObjectStore();
			System.out.println(objectStore2 + "...objectStore2...");
			logger.info(objectStore2 + "...objectStore2...");
			

			 Folder folderInstance = Factory.Folder.createInstance(objectStore2, "AO_Employee");
				Folder get_RootFolder = objectStore2.get_RootFolder();
				folderInstance.getProperties().putValue("TE_EmployeeCode", "123");
				folderInstance.getProperties().putValue("AO_Comments", "Chinna");
				folderInstance.getProperties().putValue("AO_EmailID", "chinna_123");
				folderInstance.getProperties().putValue("TE_EmployeeDept", "123");
				folderInstance.getProperties().putValue("AO_FirstName", "Chinna");
			//	folderInstance.getProperties().putValue("AO_EmailID", "chinna_123");
				folderInstance.save(RefreshMode.REFRESH);
			System.out.println("Properties r Updated Successfully.,,");
			logger.info("Properties r Updated Successfully.,,");
			//name = "Properties r Updated Successfully..!";
			
			object.put("requestId","REQ0000010");
			object.put("empName",name);
			object.put("empId",empid);
			object.put("caseId",folderInstance.get_Name());
			object.put("caseStatus","Success");
			
			array.put(object); 
			System.out.println(array.toString());
			logger.info("...array.toString()..."+array.toString());
			return array.toString();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error in catch block..!" + e.getMessage());
			name = "Fail"+e.getMessage();
			logger.info("catch block error-"+name);
			
			
			object.put("caseStatus","Fail");
			object.put("errorDetails",name);
			//object.put("empId",empid);
		
			array.put(object); 
			System.out.println(array.toString());
			return array.toString();
			
		//	return name;
		}

		// logger.info("message {}", this.message);
		//return name;
		// return "<h1>Success</h1>";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoSpringBoot1Application.class, args);
		logger.info("inside the main method..!");
	}

}
