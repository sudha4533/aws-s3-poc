package com.bootcamp.imagestorage.service;

import java.io.IOException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * @author Sudha Gunamgari
 */

public class ImageUploadHandlerService {

	private static String bucketName = "com.bootcamp.aws.s3";
	private static String keyName = null;

	public boolean uploadImage(FileItem file, String image_title) throws IOException {

		
		
		AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
		try {

			ObjectMetadata om = new ObjectMetadata();
			om.setContentLength(file.getSize());

			String ext = FilenameUtils.getExtension(file.getName());
            keyName = image_title + '.' + ext;
            
			s3client.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), om));

			return true;

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which " + "means your request made it "
					+ "to Amazon S3, but was rejected with an error response" + " for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which " + "means the client encountered "
					+ "an internal error while trying to " + "communicate with S3, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

		return false;
	}
	
}
