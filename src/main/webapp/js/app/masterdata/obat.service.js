(function() {

	var services = angular.module('je-masterdata-obat-service', [ 'ngResource' ]);

	
	services.factory('ObatListFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/obat', {}, {
			query : {
				method : 'GET',
				isArray : false
			},
			create : {
				method : 'POST'
			}
		});

	} ]);
	
	services.factory('ObatEditFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/obat/:id_obat', {}, {
			show : {
				method : 'GET',
				params: {id_obat: '@id_obat'}
			},
			update : {
				method : 'PUT',
				params :{id_obat: '@id_obat'}
			},
			remove : {
				method : 'DELETE',
				params :{id_obat: '@id_obat'}
			}
		});

	} ]);
	
})();