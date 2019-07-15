<html>
<head>
<title>Index</title>


<style>
table, th , td  {
  border: 1px solid grey;
  border-collapse: collapse;
  padding: 5px;
}
table tr:nth-child(odd) {
  background-color: #f1f1f1;
}
table tr:nth-child(even) {
  background-color: #ffffff;
}
</style>


<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
<!-- <script src="jsp/createCase.js" type="text/javascript"></script> -->
<script>

var app = angular.module('caseApp', []);

app.controller('caseCtrl', function($scope,$http) {
//	$scope.empName= "Vaidehi";
	//$scope.empId= "406";
	
	$scope.boolhide=true;
	$scope.boolhide1=true;
	$scope.createCase = function() {

		
	    
		alert("Case is going to create in ICM..!");
//		var restURL = 'http://localhost:8081/Name/'+ $scope.empName +'/empId/'+$scope.empId+'/department/'+$scope.department;
		//var restURL = 'http://localhost:8081/validate';
			//var restURL = 'http://localhost:4004/Name/'+ $scope.empName +'/empId/'+$scope.empId+'/department/'+$scope.department;
		var restURL = 'http://localhost:4002/Name/'+ $scope.empName +'/empId/'+$scope.empId+'/department/'+$scope.department;
		$http.get(restURL).
	     then(function(response) {
	    	/*   console.log("response: ",response);
	    	//  alert("response.data: "+response.data[0].empId);
	    	  alert("response.data.data: "+response.data);
	    	  $scope.boolhide=false;
	    	  $scope.caseInfo = response.data; */

	    	  alert("response.data: "+response.data[0].caseStatus);
	//    	  alert("response.data.data: "+response.data);
	    	  if(response.data[0].caseStatus==="Success"){
//alert("inside the success..!");
		    	  	 $scope.boolhide=false;}
	    	  else{
	    		  $scope.boolhide1=false;
		    	  }

	    	  $scope.caseInfo = response.data;
	    	  
	      });
	  };
});


</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Case Creation</title>

</head>

<body>
   
      <div ng-app="caseApp" ng-controller="caseCtrl">
  		<p>Employee Name : <input type="text" ng-model="empName"></p>
  		<p>Employee Id   : <input type="text" ng-model="empId"></p>
  		<p>Department    : <input type="text" ng-model="department"></p>
  		<button ng-click="createCase()">Create Case</button>
  		  	
  <br><br>
    <table border = 1 ng-hide="boolhide">
    <tr>
    <th> Request Id </th>
    <th> Employee Name </th>
    <th> Employee Id </th>
    <th> Case Id </th>
    <th> Status </th>
    
    </tr>
    
  		<tr ng-repeat="x in caseInfo">
			<td>{{ x.requestId }}</td>
			<td>{{ x.empName }}</td>
    		<td>{{ x.empId }}</td>
			<td>{{ x.caseId }}</td>
			<td>{{ x.caseStatus }}</td>
    		    		
  		</tr>
	</table>
	
	
	  <table border = 1 ng-hide="boolhide1">
    <tr>
    <th> Error Details</th>
    <th> Status </th>
    
    </tr>
    
  		<tr ng-repeat="x in caseInfo">
			<td>{{ x.errorDetails }}</td>
			<td>{{ x.caseStatus }}</td>
    		    		
  		</tr>
	</table>
	
	
	
	
	</div>
   
	</div>
   
   
   
   
   <%--  <font color="red">${errorMessage}</font>
    <form action="/validate"  method="post">
        Name : <input type="text" name="name" />
        <input type="submit" />
    </form> --%>
</body>

</html>
