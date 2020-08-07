(function() {

	var app = angular.module('je-masterdata-rumahsakit-controller', [
			'je-masterdata-rumahsakit-service',
			'ui.bootstrap', 'dialogs' ]);

	app.controller('RumahSakitListController', [ '$scope', '$location',
			'$dialogs', 'RumahSakitListFactory','RumahSakitEditFactory',
			function($scope, $location, $dialogs, RumahSakitListFactory,RumahSakitEditFactory) {

				$scope.currentPage = 1;
				$scope.totalRumahsakit = 0;
				$scope.pageSize = 5;

				$scope.pagination = {
					current : 1
				};

				//ordering
				$scope.predicate='kode';
                                $scope.reverse=false;
	            
				$scope.pageChanged = function(newPage) {
					$scope.gridPromise = RumahSakitListFactory.query({
						activePage : newPage,
						order : $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
					}, function(data) {
						$scope.rumahsakitList = data.list;
						notif($dialogs, data.status, 'List');
						$scope.totalRumahsakit = data.total;
					});
				};
				$scope.pageChanged(1);

				$scope.search = function() {
					$scope.currentPage = 1;
					$scope.pageChanged(1);
				};

				$scope.create = function() {
					$location.path('/masterdata/rumahsakit/new');
				};
				
			   $scope.remove = function (rumahsakit) {
		        	dlg = $dialogs.confirm('Konfirmasi','Apakah anda ingin hapus Rumah Sakit : ' + rumahsakit.nama);
		        	dlg.result.then(function(btn){
		        		RumahSakitEditFactory.remove({kode: rumahsakit.kode}, function(data){
		        			notif($dialogs, data.status, 'Hapus');
		            		$scope.search();
		    			});
		            	
		            },function(btn){
		              //canceled
		            });
		            
		        };
		        
			    $scope.edit = function (rumahsakit) {
		            $location.path('/masterdata/rumahsakit/' + rumahsakit.kode + '/edit');
		        };
		        
		        $scope.detail = function (rumahsakit) {
		            $location.path('/masterdata/rumahsakit/' + rumahsakit.kode + '/detail');
		        };

			} ]);

	app.controller('RumahSakitCreateController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'RumahSakitListFactory',
			function($scope, $routeParams, $location,$dialogs,
					RumahSakitListFactory) {

				$scope.title = "Buat Baru Rumah Sakit";
				$scope.isEdit = false;

				$scope.rumahsakit = {
			
				};
				$scope.save = function() {
					RumahSakitListFactory.create({
						rumahsakit : $scope.rumahsakit
					}, function(data) {
						notif($dialogs, data.status, 'Simpan');
						$location.path('/masterdata/rumahsakit');
					});

				};

				$scope.cancel = function() {
					$location.path('/masterdata/rumahsakit');
				};

			} ]);
	
	
	app.controller('RumahSakitEditController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'RumahSakitEditFactory',
			'RumahSakitListFactory',
			function($scope, $routeParams, $location, $dialogs,RumahSakitEditFactory,
					RumahSakitListFactory) {

				$scope.title = "Ubah Rumah Sakit";
				$scope.isEdit = true;

				RumahSakitEditFactory.show({
					kode : $routeParams.kode
				}, function(data) {
					$scope.rumahsakit = data.rumahsakit;
				});

				$scope.save = function() {
					RumahSakitEditFactory.update({
						kode : $scope.rumahsakit.kode,
						rumahsakit : $scope.rumahsakit
					}, function(data) {
						notif($dialogs, data.status, 'Simpan');
						$location.path('/masterdata/rumahsakit');
					});

				};

				$scope.cancel = function() {
					$location.path('/masterdata/rumahsakit');
				};

			} ]);
	
	app.controller('RumahSakitDetailController', [
	'$scope',
	'$routeParams',
	'$location',
	'RumahSakitEditFactory',
	'RumahSakitListFactory',
	function($scope, $routeParams, $location, RumahSakitEditFactory,
			RumahSakitListFactory) {

		$scope.title = "Info Detail Rumah Sakit";

		RumahSakitEditFactory.show({
			kode : $routeParams.kode
		}, function(data) {
			$scope.rumahsakit = data.rumahsakit;
		});


		$scope.cancel = function() {
			$location.path('/masterdata/rumahsakit');
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
