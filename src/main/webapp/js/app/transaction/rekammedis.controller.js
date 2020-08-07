(function() {

	var app = angular.module('je-transaction-rekammedis-controller', [
			'je-transaction-rekammedis-service',
                        'je-masterdata-pasien-service',
                        'je-masterdata-dokter-service',
                        'je-masterdata-penyakit-service',
			'ui.bootstrap', 'dialogs' ]);

	app.controller('RekammedisListController', [ '$scope', '$location',
			'$dialogs', 'RekammedisListFactory','RekammedisEditFactory',
			function($scope, $location, $dialogs, RekammedisListFactory,RekammedisEditFactory) {

				$scope.currentPage = 1;
				$scope.totalRekammedis = 0;
				$scope.pageSize = 5;

				$scope.pagination = {
					current : 1
				};

				//ordering
				$scope.predicate='rekammedis_id';
                                $scope.reverse=false;
	            
				$scope.pageChanged = function(newPage) {
					$scope.gridPromise = RekammedisListFactory.query({
						activePage : newPage,
						order : $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
					}, function(data) {
						$scope.rekammedisList = data.list;
                                                
                                                for (var i=0; i < $scope.rekammedisList.length; i++){
                                                $scope.rekammedisList[i].rekammedis_tglkunjungan = convertLongToDate($scope.rekammedisList[i].rekammedis_tglkunjungan);
                                                }
                                            
						notif($dialogs, data.status, 'List');
						$scope.totalRekammedis = data.total;
					});
				};
				$scope.pageChanged(1);

				$scope.search = function() {
					$scope.currentPage = 1;
					$scope.pageChanged(1);
				};

				$scope.create = function() {
					$location.path('/transaction/rekammedis/new');
				};
				
                                $scope.remove = function (rekamMedis) {
		        	dlg = $dialogs.confirm('Konfirmasi','Apakah anda ingin hapus Rekam Medis : ' + rekamMedis.pasien.pasien_nama + '. ' +'Rekam Medis ID : ' + rekamMedis.rekammedis_id );
		        	dlg.result.then(function(btn){
		        		RekammedisEditFactory.remove({
                                            rekammedis_id: rekamMedis.rekammedis_id
                                        }, function(data){
		        			notif($dialogs, data.status, 'Hapus');
		            		$scope.search();
		    			});
		            	
		            },function(btn){
		              //canceled
		            });
		            
		        };
		        
			    $scope.edit = function (rekamMedis) {
		            $location.path('/transaction/rekammedis/' + rekamMedis.rekammedis_id + '/edit');
		        };
		        
		        $scope.detail = function (rekamMedis) {
		            $location.path('/transaction/rekammedis/' + rekamMedis.rekammedis_id + '/detail');
		        };

			} ]);

	app.controller('RekammedisCreateController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'RekammedisListFactory',
                        'DokterListFactory',
                        'PasienListFactory',
                        'PenyakitListFactory',
			function($scope, $routeParams, $location,$dialogs,
					RekammedisListFactory, DokterListFactory, PasienListFactory, PenyakitListFactory) {

				$scope.title = "Tambah Data Rekam Medis";
				$scope.isEdit = false;
                                                               
                                //Ambil list data dokter
                                DokterListFactory.query({}, function (data){
                                    $scope.dokterList = data.list;
                                });
                                
                                //Ambil list data Pasien
                                PasienListFactory.query({}, function(data){
                                    $scope.pasienList = data.list;
                                });
                                
                                //Ambil list data penyakit
                                PenyakitListFactory.query({}, function(data){
                                    $scope.penyakitList = data.list;
                                });
                                
				$scope.save = function() {
                                    // Convert date to long (timestamp)
                                    var dataTanggalKunjungan = $scope.rekamMedis.rekammedis_tglkunjungan;
                                    var tanggalKunjungan = new Date(dataTanggalKunjungan);
                                    $scope.rekamMedis.rekammedis_tglkunjungan = tanggalKunjungan.getTime();
                                    // Convert selesai
                                                                        
					RekammedisListFactory.create({
						rekamMedis : $scope.rekamMedis
					}, function(data) {
						notif($dialogs, data.status, 'Simpan');
						$location.path('/transaction/rekammedis');
					});

				};

				$scope.cancel = function() {
					$location.path('/transaction/rekammedis');
				};

			} ]);
	
	
	app.controller('RekammedisEditController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'RekammedisEditFactory',
			'RekammedisListFactory',
                        'PasienListFactory',
                        'DokterListFactory',
                        'PenyakitListFactory',
			function($scope, $routeParams, $location, $dialogs,RekammedisEditFactory,
					RekammedisListFactory,PasienListFactory,DokterListFactory,PenyakitListFactory) {

				$scope.title = "Ubah Data Rekam Medis Pasien";
				$scope.isEdit = true;
                                
                                $scope.dokter = {};
                                
                                //Ambil list data dokter
                                DokterListFactory.query({}, function (data){
                                    $scope.dokterList = data.list;
                                });
                                
                                //Ambil list data Pasien
                                PasienListFactory.query({}, function(data){
                                    $scope.pasienList = data.list;
                                });
                                
                                //Ambil list data penyakit
                                PenyakitListFactory.query({}, function(data){
                                    $scope.penyakitList = data.list;
                                });
                                
                                
				RekammedisEditFactory.show({
					rekammedis_id : $routeParams.rekammedis_id
				}, function(dataRekamMedis) {
					$scope.rekamMedis = dataRekamMedis.rekamMedis;
                                        
                                        // Convert Long to Date
                                        var dataTanggalKunjungan = $scope.rekamMedis.rekammedis_tglkunjungan;
                                        var tanggalKunjungan = new Date(dataTanggalKunjungan);
                                        
                                        var date = "" + tanggalKunjungan.getDate();
                                        var month = "" + (tanggalKunjungan.getMonth() + 1);
                                        var year = tanggalKunjungan.getFullYear();
                                        
                                        if(month.length < 2){
                                            month = "0" + month;
                                        }
                                        
                                        if (date.length < 2)
                                            date = "0" + date;
                                        
                                        var dateString = year + "-" + (month) + "-" + date;
                                        
                                        $scope.rekamMedis.rekammedis_tglkunjungan = dateString;
                                        // Convert selesai
				});

				$scope.save = function() {
                                    // Convert date to long (timestamp)
                                    var dataTanggalKunjungan = $scope.rekamMedis.rekammedis_tglkunjungan;
                                    var tanggalKunjungan = new Date(dataTanggalKunjungan);
                                    $scope.rekamMedis.rekammedis_tglkunjungan = tanggalKunjungan.getTime();
                                    // Convert selesai
                                    
					RekammedisEditFactory.update({
						rekammedis_id : $scope.rekamMedis.rekammedis_id,
						rekamMedis : $scope.rekamMedis
					}, function(dataRekaMedis) {
						notif($dialogs, dataRekaMedis.status, 'Simpan');
						$location.path('/transaction/rekammedis');
					});

				};

				$scope.cancel = function() {
					$location.path('/transaction/rekammedis');
				};

			} ]);
//	
	app.controller('RekammedisDetailController', [
	'$scope',
	'$routeParams',
	'$location',
	'RekammedisEditFactory',
	'RekammedisListFactory',
	function($scope, $routeParams, $location, RekammedisEditFactory,
			RekammmedisListFactory) {

		$scope.title = "Info Detail Rekam Medis Pasien";

		RekammedisEditFactory.show({
			rekammedis_id : $routeParams.rekammedis_id
		}, function(data) {
			$scope.rekammedisList = data.rekamMedis;
                        $scope.rekammedisList.rekammedis_tglkunjungan = convertLongToDate($scope.rekammedisList.rekammedis_tglkunjungan);
                        $scope.rekammedisList.pasien.pasien_tgllahir = convertLongToDate($scope.rekammedisList.pasien.pasien_tgllahir);
		});


		$scope.cancel = function() {
			$location.path('/transaction/rekammedis');
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
