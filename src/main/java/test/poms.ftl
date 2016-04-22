<#if dep?? && dep=="Y">
<dependency>
	<groupId>${groupId}</groupId>
	<artifactId>${artifactId}</artifactId>
	<version>${version}</version>
	<!-- ${description} -->
</dependency>
<#else>
<project>
  <modelVersion>1.0.0</modelVersion>
  <groupId>${groupId}</groupId>
  <artifactId>${artifactId}</artifactId>
  <version>${version}</version>
  <description>${description}</description>
</project>
</#if>