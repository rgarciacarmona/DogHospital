<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
	<html>
		<head><title>This is your dog!</title></head>
		<body>
			<p><b>Name: <xsl:value-of select="/dog/@name" /></b></p>
			<p>Breed: <xsl:value-of select="/dog/breed" /></p>
			<p>Weight: <xsl:value-of select="/dog/weight" /></p>
			<p>Admission Date: <xsl:value-of select="/dog/admissionDate" /></p>
			<p>Release Date: <xsl:value-of select="/dog/releaseDate" /></p>
			<p>Medicines:
				<table>
					<th>Medicine</th>
					<xsl:for-each select="//medicine">
					<tr>
						<td><xsl:value-of select="name" /></td>
					</tr>
					</xsl:for-each>
				</table>
			</p>
		</body>
	</html>
</xsl:template>
</xsl:stylesheet>