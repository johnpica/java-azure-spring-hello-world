package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/")
	String sayHello() {
		String string = createManagedIdentityCredential();
		return string + " Hello, World!!!";
	}

	/**
	 * Authenticate with a System Assigned Managed identity.
	 */
	public String createManagedIdentityCredential() {
		ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder().build();

		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
				.endpoint("https://jplearn1.blob.core.windows.net/blob-container")
				.credential(managedIdentityCredential)
				.buildClient();

		System.out.println("\nListing1 blobs...");
		// Get a reference to a blob
		// Create the container and return a container client object
		// BlobContainerClient blobContainerClient =
		// blobServiceClient.createBlobContainer("blob-container");
		//BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient("mycontainer");
		BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
				.endpoint("https://jplearn1.blob.core.windows.net/blob-container")
				.credential(managedIdentityCredential)
				.buildClient();

		System.out.println("\nListing blobs1...");
		// List the blob(s) in the container.
		StringBuffer stringBuffer = new StringBuffer("test ");
		for (BlobItem blobItem : blobContainerClient.listBlobs()) {
			System.out.println("\t" + blobItem.getName());
			stringBuffer.append(blobItem.getName());
		}
		return stringBuffer.toString();
	}
}
