(function() {

	var services = angular.module('je-transaction-resepobat-service', [ 'ngResource' ]);

	
	services.factory('ResepobatListFactory', [ '$resource', function($resource) {

		return $resource('service/transaction/resepobat', {}, {
			query : {
				method : 'GET',
				isArray : false
			},
			create : {
				method : 'POST'
			}
		});

	} ]);
    
	services.factory('ResepobatEditFactory', [ '$resource', function($resource) {

		return $resource('service/transaction/resepobat/:id_resepobat', {}, {
			show : {
				method : 'GET',
				params: {id_resepobat: '@id_resepobat'}
			},
			update : {
				method : 'PUT',
				params :{id_resepobat: '@id_resepobat'}
			},
			remove : {
				method : 'DELETE',
				params :{id_resepobat: '@id_resepobat'}
			}
		});

	} ]);
	
})();