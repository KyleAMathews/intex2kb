<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%String title = "Index";%>
<html>
    <head><TITLE>MyStuff.com: <%out.write(title);%></TITLE>
        <link rel="StyleSheet" type="text/css" media="all" href="style.css" />
        <link rel="stylesheet" href="thickbox.css" type="text/css" media="screen" />
        <script type="text/javascript" src="jquery.js"></script>
        <script type="text/javascript" src="jq-corner.js"></script>
        <script type="text/javascript" src="thickbox.js"></script>
        <script type="text/javascript">
	$(function(){	// shorthand for $(document).ready() BTW
        $("#navigation ul li a").corner();
	$("#bigcontainer").corner();
	});
$(document).ready(function(){
   // Your code here
 });
        </script>
    </head>
    
    <BODY>
        <div id="bigcontainer">
            <div id="container">
                <div id="header">
                    <a id="nolink" id="logo" alt="Home" href="index.jsp">
                        <div id="logoHeader">
                            <img src="mystuff.png" />
                            <!--<h1>MyStuff.com</h1>-->
                        </div>
                    </a>
                </div><!--end header-->
                   
                <div id="navigation">
                    <ul>
                        <LI style="margin-top: 0;"><a href="login.jsp?KeepThis=true&TB_iframe=true&height=200&width=250" class="thickbox">Login</a></LI>
                        <LI><a href="">Sale</a></LI>
                        <LI><a href="">Rental</a></LI>
                        <LI><a href="">Photo</a></LI>
                        <LI><a href="">Backup</a></LI>
                    </ul>
                </div><!--end navigation-->
                   
                <div id="body">
                    <h1><%out.write(title);%></h1>
                    <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Quisque purus. Mauris sed arcu id felis vestibulum luctus. Praesent fringilla nonummy eros. Suspendisse eget ligula eu nisi luctus pulvinar. Morbi feugiat convallis nibh. Quisque suscipit, ligula eget molestie accumsan, quam velit pellentesque libero, id placerat urna ligula feugiat erat. Suspendisse et mauris tincidunt eros fermentum nonummy. Pellentesque sed nulla malesuada turpis pharetra faucibus. Aenean feugiat fringilla orci. Vestibulum dignissim pellentesque magna. Aliquam in sem at justo pretium elementum. Nunc ultricies velit eget urna. Duis massa.</p>
                    <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam consectetuer, odio nec eleifend lacinia, nisl felis convallis tortor, quis sollicitudin nunc quam vitae mi. Vestibulum accumsan. Aliquam eu enim et ipsum vestibulum commodo. Aliquam eros orci, vulputate porttitor, pulvinar egestas, interdum non, quam. Aenean erat mi, tincidunt sed, luctus vitae, posuere vitae, augue. Nulla facilisi. Sed libero arcu, facilisis ac, rutrum quis, lobortis vel, urna. Sed quis ligula eget sem laoreet pretium. Sed felis magna, aliquam sit amet, posuere quis, adipiscing eget, nibh. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Pellentesque tempor fermentum lacus. Aliquam luctus enim aliquet sapien. Nullam id nisi non ligula aliquam lobortis. Aliquam et neque eu eros pretium lacinia. Aenean vulputate lacinia diam. Ut vehicula eleifend magna. Morbi quis lacus.</p>
                    <p>Nunc vitae augue. Morbi porttitor ipsum. Curabitur non urna. Nulla nunc. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In hac habitasse platea dictumst. Suspendisse augue augue, vestibulum auctor, fringilla sed, bibendum et, elit. Sed malesuada. Sed neque. Nulla ligula sem, consequat eu, dapibus eget, sodales vitae, purus.</p>
                    <p>Curabitur sapien purus, lobortis dictum, pulvinar vel, rutrum eget, magna. Integer feugiat sodales nulla. Pellentesque volutpat placerat sapien. Phasellus sollicitudin tellus tincidunt eros. Pellentesque ac nibh in eros consectetuer lacinia. Ut diam sem, mollis eget, accumsan quis, fringilla vel, libero. Quisque fringilla nunc ac metus. Quisque magna nunc, tincidunt at, vestibulum et, nonummy ac, ligula. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nunc ut augue vitae turpis consectetuer mollis. Duis ultricies placerat erat. In sollicitudin congue magna. Donec sit amet mauris. Cras a est vitae augue tempus tempor. Cras volutpat vestibulum erat. Sed metus ligula, semper sed, faucibus vitae, vestibulum quis, mi. Curabitur laoreet placerat arcu. Mauris id augue. Etiam libero. Donec nonummy tortor non velit.</p>
                    <p>Ut laoreet bibendum enim. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed varius faucibus erat. Nam dignissim imperdiet nibh. Integer vel diam eu augue elementum tincidunt. Phasellus placerat, sem ultrices commodo porttitor, turpis ligula lobortis velit, nec dictum dui ante ut metus. Praesent rhoncus blandit tortor. Aliquam pulvinar, ante non malesuada tristique, leo diam mattis justo, eu dictum odio metus ac leo. Nullam fermentum. Quisque euismod dolor vel orci. Maecenas lacus. Aliquam hendrerit, felis vel mollis mattis, dolor odio pretium nisl, pretium scelerisque purus metus ut magna. Etiam sit amet leo a dolor viverra eleifend. Etiam ac quam vel quam blandit sagittis. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas massa. Vestibulum at ante eu sem vulputate suscipit. Nullam lacinia, pede ac pharetra tincidunt, justo nisl pulvinar lorem, sed porta nunc lacus vitae neque.</p>
                    <p>Mauris nonummy placerat odio. In nec erat quis lectus commodo facilisis. Sed nunc diam, iaculis vel, malesuada eu, nonummy quis, risus. Pellentesque id tortor eu augue laoreet mollis. Nullam est nisl, varius at, lacinia eu, viverra ut, nisi. Curabitur non est. Nam feugiat bibendum felis. Suspendisse dui nulla, varius pretium, scelerisque a, volutpat ut, pede. Aenean venenatis bibendum massa. Nulla ut purus ut nunc tincidunt accumsan. Aliquam erat volutpat. Fusce congue. Integer tempus elit vitae eros. Aliquam erat volutpat. Aenean ut quam. Etiam cursus purus et libero. Ut non dolor. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</p>
                    
                </div><!--end body-->
            </div><!--end container-->
            <div id="footer">
                <p>Copyright 2006 MyStuff.com || All rights reserved</p>
            </div><!--end footer-->
               
        </div><!--end bigcontainer-->
    </BODY>
</html>