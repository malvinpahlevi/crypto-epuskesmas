(function() {

	var services = angular.module('je-masterdata-dokter-service', [ 'ngResource' ]);

	
	services.factory('DokterListFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/dokter', {}, {
			query : {
				method : 'GET',
				isArray : false
			},
			create : {
				method : 'POST'
			}
		});

	} ]);
	
	services.factory('DokterEditFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/dokter/:id_dokter', {}, {
			show : {
				method : 'GET',
				params: {id_dokter: '@id_dokter'}
			},
			update : {
				method : 'PUT',
				params :{id_dokter: '@id_dokter'}
			},
			remove : {
				method : 'DELETE',
				params :{id_dokter: '@id_dokter'}
			}
		});

	} ]);
	
})();