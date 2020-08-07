(function() {

	var services = angular.module('je-masterdata-pasien-service', [ 'ngResource' ]);

	
	services.factory('PasienListFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/pasien', {}, {
			query : {
				method : 'GET',
				isArray : false
			},
			create : {
				method : 'POST'
			}
		});

	} ]);
	
	services.factory('PasienEditFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/pasien/:id_pasien', {}, {
			show : {
				method : 'GET',
				params: {id_pasien: '@id_pasien'}
			},
			update : {
				method : 'PUT',
				params :{id_pasien: '@id_pasien'}
			},
			remove : {
				method : 'DELETE',
				params :{id_pasien: '@id_pasien'}
			}
		});

	} ]);
	
})();