<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Image Upload Page</title>
<style type="text/css">
body {
	margin-left: 200px;
	margin-top: 100px;
}
</style>
</head>
<body>
	
	<h3 style="color: red;">
	<%
		if (request.getAttribute("message") != null) {
			out.println(request.getAttribute("message").toString());
		}
	%></h3>
	<h1 style="color: maroon;">Browse image to upload in S3 Bucket</h1>
	<form method="POST" action="upload" enctype="multipart/form-data">
		Image file:<input type="file" name="file" required="required"/> <br> Image title:<input
			type="text" name="img_name" required="required"/> <br> <input type="submit"
			value="Upload" name="upload" id="upload" />
	</form>


</body>
</html>