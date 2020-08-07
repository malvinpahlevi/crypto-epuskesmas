(function() {

	var services = angular.module('je-masterdata-rumahsakit-service', [ 'ngResource' ]);

	
	services.factory('RumahSakitListFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/rumahsakit', {}, {
			query : {
				method : 'GET',
				isArray : false
			},
			create : {
				method : 'POST'
			}
		});

	} ]);
	
	services.factory('RumahSakitEditFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/rumahsakit/:kode', {}, {
			show : {
				method : 'GET',
				params: {kode: '@kode'}
			},
			update : {
				method : 'PUT',
				params :{kode: '@kode'}
			},
			remove : {
				method : 'DELETE',
				params :{kode: '@kode'}
			}
		});

	} ]);
	
})();