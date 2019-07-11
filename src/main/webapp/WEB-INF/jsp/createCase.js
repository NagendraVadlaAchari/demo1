var app = angular.module('caseApp', []);

app.controller('caseCtrl', function($scope,$http) {
	$scope.empName= "Vaidehi";
	//$scope.empId= "406";
	
	$scope.createCase = function() {
	    
		alert("on button click 1");
	//	var restURL = 'http://localhost:8081/empName/'+ $scope.empName +'/empId/'+$scope.empId+'/department/'+$scope.department;
		var restURL = 'http://localhost:8081/validate';
		
		$http.get(restURL).
	     then(function(response) {
	    	  console.log("response: ",response);
	    	//  alert("response.data: "+response.data[0].empId);
	    	  alert("response.data.data: "+response.data);
	    	  $scope.caseInfo = response.data;
	      });
	  };
});
