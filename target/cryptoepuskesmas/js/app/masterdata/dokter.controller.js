(function() {

	var app = angular.module('je-masterdata-dokter-controller', [
			'je-masterdata-dokter-service',
			'ui.bootstrap', 'dialogs' ]);
        
        app.controller('DokterListController', [ '$scope', '$location',
			'$dialogs', 'DokterListFactory','DokterEditFactory',
			function($scope, $location, $dialogs, DokterListFactory, DokterEditFactory) {

				$scope.currentPage = 1;
				$scope.totalDokter = 0;
				$scope.pageSize = 5;

				$scope.pagination = {
					current : 1
				};

				//ordering
				$scope.predicate='id_dokter';
                                $scope.reverse=false;
	            
				$scope.pageChanged = function(newPage) {
					$scope.gridPromise = DokterListFactory.query({
						activePage : newPage,
						order : $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
					}, function(data) {
						$scope.dokterList = data.list;
                                                
						notif($dialogs, data.status, 'List');
						$scope.totalDokter = data.total;
					});
				};
				$scope.pageChanged(1);

				$scope.search = function() {
					$scope.currentPage = 1;
					$scope.pageChanged(1);
				};

				$scope.create = function() {
					$location.path('/masterdata/dokter/new');
				};
				
                                $scope.remove = function (dokter) {
		        	dlg = $dialogs.confirm('Konfirmasi','Apakah anda ingin hapus Dokter : ' + dokter.dokter_nama + '. ' +'ID Dokter : ' + dokter.id_dokter );
		        	dlg.result.then(function(btn){
		        		DokterEditFactory.remove({
                                            id_dokter: dokter.id_dokter
                                        }, function(data){
		        			notif($dialogs, data.status, 'Hapus');
		            		$scope.search();
		    			});
		            	
		            },function(btn){
		              //canceled
		            });
		            
		        };
		        
			    $scope.edit = function (dokter) {
		            $location.path('/masterdata/dokter/' + dokter.id_dokter + '/edit');
		        };
		        
		        $scope.detail = function (dokter) {
		            $location.path('/masterdata/dokter/' + dokter.id_dokter + '/detail');
		        };

			} ]);

	app.controller('DokterCreateController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'DokterListFactory',
			function($scope, $routeParams, $location,$dialogs,
					DokterListFactory) {

				$scope.title = "Tambah Data Dokter";
				$scope.isEdit = false;
                                
                                $scope.jkDokter = [
                                    {"id": "Laki - Laki", "name": "Laki - Laki"},
                                    {"id": "Perempuan", "name": "Perempuan"}
                                ];
                                
                                $scope.spDokter = [
                                    {"id": "Spesialis Umum", "name": "Spesialis Umum"},
                                    {"id": "Lainnya", "name": "Lainnya"}
                                ];

				$scope.dokter = {
				};
                                
				$scope.save = function() {
                                                                        
					DokterListFactory.create({
						dokter : $scope.dokter
					}, function(data) {
						notif($dialogs, data.status, 'Simpan');
						$location.path('/masterdata/dokter');
					});

				};

				$scope.cancel = function() {
					$location.path('/masterdata/dokter');
				};

			} ]);
	
	
	app.controller('DokterEditController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'DokterEditFactory',
			'DokterListFactory',
			function($scope, $routeParams, $location, $dialogs,DokterEditFactory,
					DokterListFactory) {

				$scope.title = "Ubah Data Dokter";
				$scope.isEdit = true;
                                
                                $scope.jkDokter = [
                                    {"id": "Laki - Laki", "name": "Laki - Laki"},
                                    {"id": "Perempuan", "name": "Perempuan"}
                                ];
                                
                                $scope.spDokter = [
                                    {"id": "Spesialis Umum", "name": "Spesialis Umum"},
                                    {"id": "Lainnya", "name": "Lainnya"}
                                ];

				DokterEditFactory.show({
					id_dokter : $routeParams.id_dokter
				}, function(dataDokter) {
					$scope.dokter = dataDokter.dokter;
                                        
				});

				$scope.save = function() {
                                                                        
					DokterEditFactory.update({
						id_dokter : $scope.dokter.id_dokter,
						dokter : $scope.dokter
					}, function(dataDokter) {
						notif($dialogs, dataDokter.status, 'Simpan');
						$location.path('/masterdata/dokter');
					});

				};

				$scope.cancel = function() {
					$location.path('/masterdata/dokter');
				};

			} ]);
	
	app.controller('DokterDetailController', [
	'$scope',
	'$routeParams',
	'$location',
	'DokterEditFactory',
	'DokterListFactory',
	function($scope, $routeParams, $location, DokterEditFactory,
			DokterListFactory) {

		$scope.title = "Info Detail Dokter";

		DokterEditFactory.show({
			id_dokter : $routeParams.id_dokter
		}, function(data) {
			$scope.dokterList = data.dokter;
		});


		$scope.cancel = function() {
			$location.path('/masterdata/dokter');
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
        
        function convertLongToDate(tanggalLong)
        {
        // Convert Long to Date
        var tanggal, date, month, year, dateString;
        tanggal = new Date(tanggalLong);

        date = "" + tanggal.getDate();
        month = "" + (tanggal.getMonth() + 1); //Be careful! January is 0 not 1
        year = tanggal.getFullYear();
        if (month.length < 2)
            month = "0" + month;
        if (date.length < 2)
            date = "0" + date;
        dateString = year + "-" + (month) + "-" + date;
        // Convert selesai

        return dateString;
        }
	
        function convertDateToLong(tanggalDate)
        {
        // Convert date to Long (timestamp)
        var tanggal = new Date(tanggalDate);
        var timeStamp = tanggal.getTime();
        // Convert selesai

        return timeStamp;
        }
	
})();
