(function() {

	var services = angular.module('je-transaction-rekammedis-service', [ 'ngResource' ]);

	
	services.factory('RekammedisListFactory', [ '$resource', function($resource) {

		return $resource('service/transaction/rekammedis', {}, {
			query : {
				method : 'GET',
				isArray : false
			},
			create : {
				method : 'POST'
			}
		});

	} ]);
    
	services.factory('RekammedisEditFactory', [ '$resource', function($resource) {

		return $resource('service/transaction/rekammedis/:rekammedis_id', {}, {
			show : {
				method : 'GET',
				params: {rekammedis_id: '@rekammedis_id'}
			},
			update : {
				method : 'PUT',
				params :{rekammedis_id: '@rekammedis_id'}
			},
			remove : {
				method : 'DELETE',
				params :{rekammedis_id: '@rekammedis_id'}
			}
		});

	} ]);
	
})();