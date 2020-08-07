(function() {

	var services = angular.module('je-masterdata-penyakit-service', [ 'ngResource' ]);

	
	services.factory('PenyakitListFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/penyakit', {}, {
			query : {
				method : 'GET',
				isArray : false
			},
			create : {
				method : 'POST'
			}
		});

	} ]);
	
	services.factory('PenyakitEditFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/penyakit/:id_penyakit', {}, {
			show : {
				method : 'GET',
				params: {id_penyakit: '@id_penyakit'}
			},
			update : {
				method : 'PUT',
				params :{id_penyakit: '@id_penyakit'}
			},
			remove : {
				method : 'DELETE',
				params :{id_penyakit: '@id_penyakit'}
			}
		});

	} ]);
	
})();