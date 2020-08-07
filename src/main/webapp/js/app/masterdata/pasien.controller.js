(function() {

	var app = angular.module('je-masterdata-pasien-controller', [
			'je-masterdata-pasien-service',
			'ui.bootstrap', 'dialogs' ]);

	app.controller('PasienListController', [ '$scope', '$location',
			'$dialogs', 'PasienListFactory','PasienEditFactory',
			function($scope, $location, $dialogs, PasienListFactory,PasienEditFactory) {

				$scope.currentPage = 1;
				$scope.totalPasien = 0;
				$scope.pageSize = 5;

				$scope.pagination = {
					current : 1
				};

				//ordering
				$scope.predicate='id_pasien';
                                $scope.reverse=false;
	            
				$scope.pageChanged = function(newPage) {
					$scope.gridPromise = PasienListFactory.query({
						activePage : newPage,
						order : $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
					}, function(data) {
						$scope.pasienList = data.list;
                                                
                                                // Convert Long to Date
                                                for (var i=0; i < $scope.pasienList.length; i++){
                                                    $scope.pasienList[i].pasien_tgllahir = convertLongToDate($scope.pasienList[i].pasien_tgllahir);
                                                }
                                                
                                                
						notif($dialogs, data.status, 'List');
						$scope.totalPasien = data.total;
					});
				};
				$scope.pageChanged(1);

				$scope.search = function() {
					$scope.currentPage = 1;
					$scope.pageChanged(1);
				};

				$scope.create = function() {
					$location.path('/masterdata/pasien/new');
				};
				
                                $scope.remove = function (pasien) {
		        	dlg = $dialogs.confirm('Konfirmasi','Apakah anda ingin hapus Pasien : ' + pasien.pasien_nama + '. ' +'ID pasien : ' + pasien.id_pasien + '?');
		        	dlg.result.then(function(btn){
		        		PasienEditFactory.remove({id_pasien: pasien.id_pasien}, function(data){
		        			notif($dialogs, data.status, 'Hapus');
		            		$scope.search();
		    			});
		            	
		            },function(btn){
		              //canceled
		            });
		            
		        };
		        
			    $scope.edit = function (pasien) {
		            $location.path('/masterdata/pasien/' + pasien.id_pasien + '/edit');
		        };
		        
		        $scope.detail = function (pasien) {
		            $location.path('/masterdata/pasien/' + pasien.id_pasien + '/detail');
		        };

			} ]);

	app.controller('PasienCreateController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'PasienListFactory',
			function($scope, $routeParams, $location,$dialogs,
					PasienListFactory) {

				$scope.title = "Tambah Data Pasien";
				$scope.isEdit = false;
                                
                                $scope.jkPasien = [
                                    {"id": "Laki - Laki", "name": "Laki - Laki"},
                                    {"id": "Perempuan", "name": "Perempuan"}
                                ];
                                
                                $scope.agamaPasien = [
                                    {"id": "Islam", "name": "Islam"},
                                    {"id": "Budha", "name": "Budha"},
                                    {"id": "Kristen", "name": "Kristen"},
                                    {"id": "Hindu", "name": "Hindu"},
                                    {"id": "Konghucu", "name": "Konguchu"}
                                ];
                                
                                $scope.jnAsuransi = [
                                    {"id": "PBI", "name": "PBI"},
                                    {"id": "Non-PBI", "name": "Non-PBI"},
                                    {"id": "Tidak Ada", "name": "Tidak Ada"}
                                ];
                                
                                $scope.golDarah = [
                                    {"id": "A", "name": "A"},
                                    {"id": "B", "name": "B"},
                                    {"id": "AB", "name": "AB"},
                                    {"id": "O", "name": "O"},
                                    {"id": "Belum Diketahui", "name": "Belum Diketahui"}
                                ];

				$scope.pasien = {
				};
                                
				$scope.save = function() {
                                    
                                    // Convert date to Long (timestamp)
                                    var dataTanggalLahir = $scope.pasien.pasien_tgllahir;
                                    var tanggalLahir = new Date(dataTanggalLahir);
                                    $scope.pasien.pasien_tgllahir = tanggalLahir.getTime();
                                    // Convert selesai
                                    
                                    var dataPasien = {
                                      "data": $scope.pasien   
                                    };
                                                                        
					PasienListFactory.create({
						pasien : $scope.pasien
					}, function(data) {
						notif($dialogs, data.status, 'Simpan');
						$location.path('/masterdata/pasien');
					});

				};

				$scope.cancel = function() {
					$location.path('/masterdata/pasien');
				};

			} ]);
	
	
	app.controller('PasienEditController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'PasienEditFactory',
			'PasienListFactory',
			function($scope, $routeParams, $location, $dialogs,PasienEditFactory,
					PasienListFactory) {

				$scope.title = "Ubah Data Pasien";
				$scope.isEdit = true;
                                
                                $scope.jkPasien = [
                                    {"id": "Laki - Laki", "name": "Laki - Laki"},
                                    {"id": "Perempuan", "name": "Perempuan"}
                                ];
                                
                                $scope.agamaPasien = [
                                    {"id": "Islam", "name": "Islam"},
                                    {"id": "Budha", "name": "Budha"},
                                    {"id": "Kristen", "name": "Kristen"},
                                    {"id": "Hindu", "name": "Hindu"},
                                    {"id": "Konghucu", "name": "Konguchu"}
                                ];
                                
                                $scope.jnAsuransi = [
                                    {"id": "PBI", "name": "PBI"},
                                    {"id": "Non-PBI", "name": "Non-PBI"},
                                    {"id": "Tidak Ada", "name": "Tidak Ada"}
                                ];
                                
                                $scope.golDarah = [
                                    {"id": "A", "name": "A"},
                                    {"id": "B", "name": "B"},
                                    {"id": "AB", "name": "AB"},
                                    {"id": "O", "name": "O"},
                                    {"id": "Belum Diketahui", "name": "Belum Diketahui"}
                                ];

				PasienEditFactory.show({
					id_pasien : $routeParams.id_pasien
				}, function(dataPasien) {
					$scope.pasien = dataPasien.pasien;
                                        
                                        // Convert Long to Date
                                        var dataTanggalLahir = $scope.pasien.pasien_tgllahir;
                                        var tanggalLahir = new Date(dataTanggalLahir);
                                        
                                        var date = "" + tanggalLahir.getDate();
                                        var month = "" + (tanggalLahir.getMonth() + 1);
                                        var year = tanggalLahir.getFullYear();
                                        
                                        if(month.length < 2){
                                            month = "0" + month;
                                        }
                                        
                                        if (date.length < 2)
                                            date = "0" + date;
                                        
                                        var dateString = year + "-" + (month) + "-" + date;
                                        
                                        $scope.pasien.pasien_tgllahir = dateString;
                                        // Convert selesai
				});

				$scope.save = function() {
                                    // Convert date to long (timestamp)
                                    var dataTanggalLahir = $scope.pasien.pasien_tgllahir;
                                    var tanggalLahir = new Date(dataTanggalLahir);
                                    $scope.pasien.pasien_tgllahir = tanggalLahir.getTime();
                                    // Convert selesai
                                    
//                                    var dataPasien = {
//                                        "data": $scope.pasien 
//                                            };
                                    
					PasienEditFactory.update({
						id_pasien : $scope.pasien.id_pasien,
						pasien : $scope.pasien
					}, function(dataPasien) {
						notif($dialogs, dataPasien.status, 'Simpan');
						$location.path('/masterdata/pasien');
					});

				};

				$scope.cancel = function() {
					$location.path('/masterdata/pasien');
				};

			} ]);
	
	app.controller('PasienDetailController', [
	'$scope',
	'$routeParams',
	'$location',
	'PasienEditFactory',
	'PasienListFactory',
	function($scope, $routeParams, $location, PasienEditFactory,
			PasienListFactory) {

		$scope.title = "Info Detail Pasien";

		PasienEditFactory.show({
			id_pasien : $routeParams.id_pasien
		}, function(data) {
			$scope.pasienList = data.pasien;
                        $scope.pasienList.pasien_tgllahir = convertLongToDate($scope.pasienList.pasien_tgllahir);
		});


		$scope.cancel = function() {
			$location.path('/masterdata/pasien');
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
