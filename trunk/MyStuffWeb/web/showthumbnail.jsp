<%@page import="java.io.*"%>
<%@page import="edu.byu.isys413.views.*"%>
<%@page import="edu.byu.isys413.cbb54.intex2kb.data.*"%>
<%@page import="java.util.*"%>
<%
photoBackupBO pb = PhotoDAO.getInstance().read(request.getParameter("id"));

response.setContentType(pb.getFiletype());
System.out.println("mime type = " + pb.getFiletype());
// it is important that you do not have any white space in here
// (in the HTML part of the file -- it's ok in the script area),
// because it will be sent to the browser.  so I have no leading
// or trailing white space lines.  all that goes to the browser
// should be the bytes of the file

// first, notice how I've changed the contentType above to
// image/gif.  this tells the browser that i'm sending it a gif
// image.  you can look up other types online: image/png, image/jpg,
// etc.

// first open the file.  i'm loading the file off of the disk
// here, but you could just as easily load it from your db using
// an sql query.  the inputstream could come from the ResultSet
// object
//InputStream istream = pb.getThumbnail(pb.getId()); // didn't get jmagick working'
if (pb.getFiletype().equalsIgnoreCase("image/jpeg") | pb.getFiletype().equalsIgnoreCase("image/png") | pb.getFiletype().equalsIgnoreCase("image/gif")){
    istream = new FileInputStream("/images/emblem-photos.png");
    System.out.println("using photo icon");
}else if (pb.getFiletype().equalsIgnoreCase("application/msword")) {
    istream = new FileInputStream("/images/x-office-spreadsheet.png");
    System.out.println("using office icon");
}else {
    istream = new FileInputStream("/images/text-x-generic.png");
    System.out.println("using text icon");
}

// get the outputstream.  this goes directly to the user's browser.
// note how i get the real stream from the response, not the decorated
// one that is already in the "out" variable (which expects text not
// file bytes).
OutputStream ostream = response.getOutputStream();

// now I do a simple copy routine of the bytes from the istream to
// the bytes of the ostream
byte[] buffer = new byte[1024]; // go in 1K chunks
int numread = 0;
while ((numread = istream.read(buffer)) >= 0) {
    ostream.write(buffer, 0, numread);
}
ostream.flush();

// to actually show this graphic on a page, you'd need a *separate*
// jsp file that holds the html.  on that page somewhere, you'd
// say:
//   <img src="/ShowImage.jsp">
//
// if your ShowImage.jsp took a parameter (which mine here doesn't
// since mine is hard coded), you could write your image tag this
// way to pull something from our database:
//   <img src="/ShowImage.jsp?id=152342abcd234ef141">

// and just like that you can show any image out of the DB
%>