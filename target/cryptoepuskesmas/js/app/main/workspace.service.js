(function() {

	var services = angular.module('je-main-workspace-service', [ 'ngResource' ]);

	services.factory('WorkspaceLoginInfoFactory', [ '$resource', function($resource) {

		 return $resource('service/security/logininfo', {}, {
			show : {
				method : 'GET'
			}
		});

	} ]);
	
})();