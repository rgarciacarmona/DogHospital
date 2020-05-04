<!DOCTYPE html [
    <!ENTITY nbsp "&#160;"> 
    <!ENTITY copy "&#xA9;"> 
]>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Dog Hospital</title>
<link href="style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="wrapper">
  <div id="header">
    <div id="nav"><a href="index.html">Home</a> &nbsp;|&nbsp; <a href="#">Pets Community</a> &nbsp;|&nbsp; <a href="#">About us</a> &nbsp;|&nbsp; <a href="#">Pet Gallery</a> &nbsp;|&nbsp; <a href="#">Latest News</a> &nbsp;|&nbsp; <a href="#">Contact us</a></div>
    <div id="bg"></div>
  </div>
  <div id="main-content">
    <div id="left-column">
      <div id="logo"><img src="images/big-paw.gif" alt="Pet Logo" width="42" height="45" align="left" /><span class="logotxt1">Pet Lovers</span> <span class="logotxt2">Template</span><br />
      <span style="margin-left:15px;">We are the best!</span></div>
      <h2>Your Dog</h2>
      <p><img src="images/dog.jpg" alt="Dog Template" width="92" height="129" align="left" style="margin-right:10px;margin-bottom:10px;" /> 
        <b>Name: <xsl:value-of select="/dog/@name" /></b><br />
		Breed: <xsl:value-of select="/dog/breed" /><br />
		Weight: <xsl:value-of select="/dog/weight" /><br />
		Admission Date: <xsl:value-of select="/dog/admissionDate" /><br />
		Release Date: <xsl:value-of select="/dog/releaseDate" /><br />
	    Medicines:
		<table>
			<th>Medicine</th>
			<xsl:for-each select="//medicine">
			<tr>
				<td><xsl:value-of select="name" /></td>
			</tr>
			</xsl:for-each>
		</table>
	   </p>
    </div>
    <div id="right-column">
      <div id="main-image"><img src="images/lady.jpg" alt="I love Pets" width="153" height="222" /></div>
      <div class="sidebar">
        <h3>About this Site</h3>
        <p>We're the best dog hospital.</p>
        <h3>Related Links</h3>
        <div class="box">
          <ul>
            <li><a href="http://www.medicine-pet.com/" target="_blank">Pet Medicine</a></li>
            <li><a href="http://pets.yahoo.com/pets/" target="_blank">Yahoo Pets &amp; Animals</a></li>
            <li><a href="http://www.web-designers-directory.org/" target="_blank">Web site Designers</a></li>
            <li><a href="http://www.zebonline.com/" target="_blank">Professional Web Design </a> </li>
            <li><a href="http://www.njoyment.com/" target="_blank">Free Flash Games </a></li>
            <li><a href="http://www.oswd.org/" target="_blank">Open Source Web Design</a> </li>
          </ul>
        </div><a href="http://www.web-designers-directory.org/"></a><a href="http://www.medicine-pet.com/"></a>
      </div>
    </div>
  </div>
  <div id="footer">Copyright &copy; 2006 Your Company Name, All rights reserved.<br />
    <a href="http://validator.w3.org/check?uri=referer" target="_blank">XHTML</a>  |  <a href="http://jigsaw.w3.org/css-validator/check/referer?warning=no&amp;profile=css2" target="_blank">CSS</a>  - Thanks to: <a href="http://www.medicine-pet.com/" target="_blank">Pet Medicine</a> | <span class="crd"><a href="http://www.web-designers-directory.org/">Web site Design</a></span> by : <a href="http://www.web-designers-directory.org/" target="_blank">WDD</a></div>
</div>

</body>
</html>
</xsl:template>
</xsl:stylesheet>