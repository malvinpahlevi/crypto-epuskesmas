(function() {

	var app = angular.module('je-masterdata-obat-controller', [
			'je-masterdata-obat-service',
			'ui.bootstrap', 'dialogs' ]);

	app.controller('ObatListController', [ '$scope', '$location',
			'$dialogs', 'ObatListFactory','ObatEditFactory',
			function($scope, $location, $dialogs, ObatListFactory, ObatEditFactory) {

				$scope.currentPage = 1;
				$scope.totalObat = 0;
				$scope.pageSize = 5;

				$scope.pagination = {
					current : 1
				};

				//ordering
				$scope.predicate='id_obat';
                                $scope.reverse=false;
	            
				$scope.pageChanged = function(newPage) {
					$scope.gridPromise = ObatListFactory.query({
						activePage : newPage,
						order : $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
					}, function(data) {
						$scope.obatList = data.list;
                                                
						notif($dialogs, data.status, 'List');
						$scope.totalObat = data.total;
					});
				};
				$scope.pageChanged(1);

				$scope.search = function() {
					$scope.currentPage = 1;
					$scope.pageChanged(1);
				};

				$scope.create = function() {
					$location.path('/masterdata/obat/new');
				};
				
                                $scope.remove = function (obat) {
		        	dlg = $dialogs.confirm('Konfirmasi','Apakah anda ingin hapus Obat : ' + obat.obat_nama + '. ' +'ID Obat : ' + obat.id_obat );
		        	dlg.result.then(function(btn){
		        		ObatEditFactory.remove({id_obat: obat.id_obat}, function(data){
		        			notif($dialogs, data.status, 'Hapus');
		            		$scope.search();
		    			});
		            	
		            },function(btn){
		              //canceled
		            });
		            
		        };
		        
			    $scope.edit = function (obat) {
		            $location.path('/masterdata/obat/' + obat.id_obat + '/edit');
		        };
		        
		        $scope.detail = function (obat) {
		            $location.path('/masterdata/obat/' + obat.id_obat + '/detail');
		        };

			} ]);

	app.controller('ObatCreateController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'ObatListFactory',
			function($scope, $routeParams, $location,$dialogs,
					ObatListFactory) {

				$scope.title = "Tambah Data Obat";
				$scope.isEdit = false;
                                
				$scope.obat = {
				};
                                
				$scope.save = function() {
                                                                        
					ObatListFactory.create({
						obat : $scope.obat
					}, function(data) {
						notif($dialogs, data.status, 'Simpan');
						$location.path('/masterdata/obat');
					});

				};

				$scope.cancel = function() {
					$location.path('/masterdata/obat');
				};

			} ]);
	
	
	app.controller('ObatEditController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'ObatEditFactory',
			'ObatListFactory',
			function($scope, $routeParams, $location, $dialogs,ObatEditFactory,
					ObatListFactory) {

				$scope.title = "Ubah Data Obat";
				$scope.isEdit = true;
                                

				ObatEditFactory.show({
					id_obat : $routeParams.id_obat
				}, function(dataObat) {
					$scope.obat = dataObat.obat;
                                        
				});

				$scope.save = function() {
                                                                        
					ObatEditFactory.update({
						id_obat : $scope.obat.id_obat,
						obat : $scope.obat
					}, function(dataObat) {
						notif($dialogs, dataObat.status, 'Simpan');
						$location.path('/masterdata/obat');
					});

				};

				$scope.cancel = function() {
					$location.path('/masterdata/obat');
				};

			} ]);
	
	app.controller('ObatDetailController', [
	'$scope',
	'$routeParams',
	'$location',
	'ObatEditFactory',
	'ObatListFactory',
	function($scope, $routeParams, $location, ObatEditFactory,
			ObatListFactory) {

		$scope.title = "Info Detail Obat";

		ObatEditFactory.show({
			id_obat : $routeParams.id_obat
		}, function(data) {
			$scope.obatList = data.obat
		});


		$scope.cancel = function() {
			$location.path('/masterdata/obat');
		};

	} ]);

	
	function notif($dialogs, status, result) {

		switch (status) {
		case 'OK':
			if (result == 'Simpan') {
				dlg = $dialogs.notify('Informasi', 'Data Sukses Disimpan');
			} else if (result == 'Hapus') {
				dlg = $dialogs.notify('Informasi', 'Data Sukses Dihapus');
			}
			break;
		case '500':
			if (result == 'Simpan') {
				dlg = $dialogs.error('Data Gagal Disimpan');
			} else if (result == 'Hapus') {
				dlg = $dialogs.error('Data Gagal Dihapus');
			}
			break;
		}
	}
        
       
	
})();
