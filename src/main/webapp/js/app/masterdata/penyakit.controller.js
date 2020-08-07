(function() {

	var app = angular.module('je-masterdata-penyakit-controller', [
			'je-masterdata-penyakit-service',
			'ui.bootstrap', 'dialogs' ]);

	app.controller('PenyakitListController', [ '$scope', '$location',
			'$dialogs', 'PenyakitListFactory','PenyakitEditFactory',
			function($scope, $location, $dialogs, PenyakitListFactory, PenyakitEditFactory) {

				$scope.currentPage = 1;
				$scope.totalPenyakit = 0;
				$scope.pageSize = 5;

				$scope.pagination = {
					current : 1
				};

				//ordering
				$scope.predicate='id_penyakit';
                                $scope.reverse=false;
	            
				$scope.pageChanged = function(newPage) {
					$scope.gridPromise = PenyakitListFactory.query({
						activePage : newPage,
						order : $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
					}, function(data) {
						$scope.penyakitList = data.list;
                                                
						notif($dialogs, data.status, 'List');
						$scope.totalPenyakit = data.total;
					});
				};
				$scope.pageChanged(1);

				$scope.search = function() {
					$scope.currentPage = 1;
					$scope.pageChanged(1);
				};

				$scope.create = function() {
					$location.path('/masterdata/penyakit/new');
				};
				
                                $scope.remove = function (penyakit) {
		        	dlg = $dialogs.confirm('Konfirmasi','Apakah anda ingin hapus Penyakit : ' + penyakit.penyakit_nama + '. ' +'ID Penyakit : ' + penyakit.id_penyakit + '?');
		        	dlg.result.then(function(btn){
		        		PenyakitEditFactory.remove({id_penyakit: penyakit.id_penyakit}, function(data){
		        			notif($dialogs, data.status, 'Hapus');
		            		$scope.search();
		    			});
		            	
		            },function(btn){
		              //canceled
		            });
		            
		        };
		        
			    $scope.edit = function (penyakit) {
		            $location.path('/masterdata/penyakit/' + penyakit.id_penyakit + '/edit');
		        };
		        
		        $scope.detail = function (penyakit) {
		            $location.path('/masterdata/penyakit/' + penyakit.id_penyakit + '/detail');
		        };

			} ]);

	app.controller('PenyakitCreateController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'PenyakitListFactory',
			function($scope, $routeParams, $location,$dialogs,
					PenyakitListFactory) {

				$scope.title = "Tambah Data Penyakit";
				$scope.isEdit = false;
                                
				$scope.penyakit = {
				};
                                
				$scope.save = function() {
                                                                        
					PenyakitListFactory.create({
						penyakit : $scope.penyakit
					}, function(data) {
						notif($dialogs, data.status, 'Simpan');
						$location.path('/masterdata/penyakit');
					});

				};

				$scope.cancel = function() {
					$location.path('/masterdata/penyakit');
				};

			} ]);
	
	
	app.controller('PenyakitEditController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'PenyakitEditFactory',
			'PenyakitListFactory',
			function($scope, $routeParams, $location, $dialogs,PenyakitEditFactory,
					PenyakitListFactory) {

				$scope.title = "Ubah Data Penyakit";
				$scope.isEdit = true;
                                

				PenyakitEditFactory.show({
					id_penyakit : $routeParams.id_penyakit
				}, function(dataPenyakit) {
					$scope.penyakit = dataPenyakit.penyakit;
                                        
				});

				$scope.save = function() {
                                                                        
					PenyakitEditFactory.update({
						id_penyakit : $scope.penyakit.id_penyakit,
						penyakit : $scope.penyakit
					}, function(dataPenyakit) {
						notif($dialogs, dataPenyakit.status, 'Simpan');
						$location.path('/masterdata/penyakit');
					});

				};

				$scope.cancel = function() {
					$location.path('/masterdata/penyakit');
				};

			} ]);
	
	app.controller('PenyakitDetailController', [
	'$scope',
	'$routeParams',
	'$location',
	'PenyakitEditFactory',
	'PenyakitListFactory',
	function($scope, $routeParams, $location, PenyakitEditFactory,
			PenyakitListFactory) {

		$scope.title = "Info Detail Penyakit";

		PenyakitEditFactory.show({
			id_penyakit : $routeParams.id_penyakit
		}, function(data) {
			$scope.penyakitList = data.penyakit;
		});


		$scope.cancel = function() {
			$location.path('/masterdata/penyakit');
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
