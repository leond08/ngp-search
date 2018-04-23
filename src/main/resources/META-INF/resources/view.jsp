<%@ include file="/init.jsp" %>

<%
String searchUrl = (String) renderRequest.getAttribute("searchResultsUrl");
HashMap initialQueryParameters = (HashMap) renderRequest.getAttribute("initialQueryParameters");
String portletNamespace = (String) renderRequest.getAttribute("portletNamespace");
%>
	<p><%= searchUrl %></p>
	<p><%= initialQueryParameters %></p>
	<p><%=portletNamespace %></p>
	<form id="<%= portletNamespace %>searchForm">
		<input type="text" name="<%= portletNamespace %>q" placeholder="Search.." id="<%= portletNamespace %>search" >
		<input type="button" name="searchButton" id="searchButton" value="Search" >
	</form>
	
	<script>
	
 	$('#searchButton').click(function(e) {
		var query = $("#<%=portletNamespace %>searchForm").serialize();
  		$.ajax({
 			  url: "<%= searchUrl %>",
 			  type: "get", //send it through get method
 			  data: query,
 			  success: function(response) {
 			    console.log(response);
 			  },
 			  error: function(xhr) {
 			    console.log("Error" + xhr);
 			  },
 			 timeout: 3000
 			});
	});
	</script> 
