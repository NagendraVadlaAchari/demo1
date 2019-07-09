package com.example.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.security.auth.Subject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

@SpringBootApplication
@RestController
public class DemoSpringBoot1Application {

	
	static java.util.Properties props = new java.util.Properties();
	// Properties props = null;
	static Connection connection = null;
	static Domain domain = null;
	static ObjectStore objectstore = null;

	public static Connection getConnection() throws IOException {

		FileInputStream file = null;
		/*
		 * try { file = new FileInputStream("C:\\Nagendra\\Nag.properties"); // //
		 * src\main\resources\Nag.properties //file = new //
		 * FileInputStream("\\src\\main\\resources\\Nag.properties"); } catch
		 * (FileNotFoundException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * props.load(file);
		 * 
		 * 
		 * String uri = props.getProperty("URI"); String username =
		 * props.getProperty("username"); String password =
		 * props.getProperty("password"); String domain = props.getProperty("domain");
		 * String objectstore = props.getProperty("objectstore");
		 */ 

		// System.setProperty("java.security.auth.login.config", jaasLogin);
		// C:\Nagendra\wsi
		System.setProperty("wasp.location", "C:\\Nagendra\\wsi");
		System.setProperty("filenet.pe.bootstrap.ceuri", "http://172.16.8.125:9080/wsi/FNCEWS40MTOM");
		Connection connection = Factory.Connection.getConnection("http://172.16.8.125:9080/wsi/FNCEWS40MTOM");
	//	System.setProperty("filenet.pe.bootstrap.ceuri", uri);
		// we are creating connection object, inorder to get connection
		//Connection connection = Factory.Connection.getConnection(uri);

		// we are getting the user context object
		UserContext userContext = UserContext.get();

		// standlone project hence stang name is (null or filenetp8)
		Subject subject = userContext.createSubject(connection, "p8admin", "mits123$", "FileNetP8WSI");
	//	Subject subject = userContext.createSubject(connection, username, password, "FileNetP8WSI");
		userContext.pushSubject(subject);

		System.out.println("CE Connection Establised Sucessfully");

		return connection;
	}

	public static Domain getDomain() throws IOException {

		// System.out.println("Entered into getDomain method");

		Connection connection2 = getConnection();
		Domain domain = Factory.Domain.fetchInstance(connection2, "fndn", null);
	//	Domain domain = Factory.Domain.fetchInstance(connection2, props.getProperty("domain"), null);
		// Domain domain = Factory.Domain.fetchInstance(connection2, null, null);
		// System.out.println("domain name ::::::"+domain.get_Name());

		return domain;
	}

	public static ObjectStore getObjectStore() throws IOException {

		// System.out.println("Entered into getObjectStore");

		Domain domain2 = getDomain();

		
		ObjectStore objectstore = Factory.ObjectStore.fetchInstance(domain2, "CMTOS", null);

		//ObjectStore objectstore = Factory.ObjectStore.fetchInstance(domain2, props.getProperty("objectstore"), null);

		// System.out.println("Object store name:::::"+objectstore.get_Name());

		return objectstore;
	}

	
	@GetMapping("/Name/{name}")
	public String getName1(@PathVariable String name) {
		System.out.println("getName api is called {}" + name);
		
		try {
			DemoSpringBoot1Application connectToFilenet = new DemoSpringBoot1Application();
			ObjectStore objectStore2 = connectToFilenet.getObjectStore();
			System.out.println(objectStore2 + "...objectStore2...");

			Folder folderInstance = Factory.Folder.createInstance(objectStore2, "AO_AccountOpening");
			Folder get_RootFolder = objectStore2.get_RootFolder();
			folderInstance.getProperties().putValue("AO_FirstName", "Nag");
			folderInstance.getProperties().putValue("AO_LastName", "Chinna");
			folderInstance.save(RefreshMode.REFRESH);
			System.out.println("Properties r Updated Successfully.,,");
			name = "Properties r Updated Successfully..!";
			
			
			//name="Success";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			System.out.println("..error.."+e.getMessage());
			name=e.getMessage();
			
		}
		
	return name;	
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoSpringBoot1Application.class, args);
	}

}
