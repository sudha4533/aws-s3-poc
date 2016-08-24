package com.bootcamp.imagestorage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.bootcamp.imagestorage.service.ImageUploadHandlerService;

/**
 * Uploads the file from client to server and sends to service class
 */
@MultipartConfig
public class ImageUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 7908187011456392847L;

	private static final String UUID_STRING = "img_name";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FileItem file = null;

		String uuidValue = "";

		List<FileItem> multiparts;

		try {
			multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

			for (FileItem item : multiparts) {

				if (item.isFormField()) {
					if (item.getFieldName().equalsIgnoreCase(UUID_STRING)) {
						uuidValue = item.getString();
					}
				}
				if (!item.isFormField()) {
					file = item;
				}
			}

		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = null;

		ImageUploadHandlerService imageService = new ImageUploadHandlerService();

		if (file.getSize() != 0) {

			if (imageService.uploadImage(file, uuidValue)) {

				request.setAttribute("message", "Image is uploaded to S3");
				dispatcher = getServletContext().getRequestDispatcher("/imageUpload.jsp");
				dispatcher.forward(request, response);
			} else {

				request.setAttribute("message", "Image is not uploaded to S3");
				dispatcher = getServletContext().getRequestDispatcher("/imageUpload.jsp");
				dispatcher.forward(request, response);

			}
		} else {
			request.setAttribute("message", "Please upload the image");
			dispatcher = getServletContext().getRequestDispatcher("/imageUpload.jsp");
			dispatcher.forward(request, response);

		}
	}

}
