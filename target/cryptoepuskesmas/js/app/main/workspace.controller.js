(function(){
	
	var app = angular.module('je-main-workspace-controller', ['je-main-workspace-service']);

	app.controller('WorkspaceController', ['$scope', '$location', '$routeParams', 'WorkspaceLoginInfoFactory', 
	                                  function($scope, $location, $routeParams, WorkspaceLoginInfoFactory){
		
		WorkspaceLoginInfoFactory.show(function(data){
			$scope.loginInfo = data.loginInfo;
			
		});
		
	}]);
	
	
})();