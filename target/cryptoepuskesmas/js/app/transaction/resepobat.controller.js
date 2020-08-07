(function() {

	var app = angular.module('je-transaction-resepobat-controller', [
			'je-transaction-resepobat-service',
			'je-transaction-rekammedis-service',
			'ui.bootstrap', 'dialogs' ]);

	app.controller('ResepobatListController', [ '$scope', '$location',
			'$dialogs', 'ResepobatListFactory','ResepobatEditFactory',
			function($scope, $location, $dialogs, ResepobatListFactory, ResepobatEditFactory) {

				$scope.currentPage = 1;
				$scope.totalResepobat = 0;
				$scope.pageSize = 5;

				$scope.pagination = {
					current : 1
				};

				//ordering
				$scope.predicate='id_resepobat';
                                $scope.reverse=false;
	            
				$scope.pageChanged = function(newPage) {
					$scope.gridPromise = ResepobatListFactory.query({
						activePage : newPage,
						order : $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
					}, function(data) {
						$scope.resepobatList = data.list;
                                                
						notif($dialogs, data.status, 'List');
						$scope.totalResepobat = data.total;
					});
				};
				$scope.pageChanged(1);

				$scope.search = function() {
					$scope.currentPage = 1;
					$scope.pageChanged(1);
				};

				$scope.create = function() {
					$location.path('/transaction/resepobat/new');
				};
				
                                $scope.remove = function (resepObat) {
		        	dlg = $dialogs.confirm('Konfirmasi','Apakah anda ingin hapus Resep Obat : ' + resepObat.rekamMedis.pasien.pasien_nama + '. ' + 'ID Resep Obat : ' + resepObat.id_resepobat );
		        	dlg.result.then(function(btn){
		        		ResepobatEditFactory.remove({
                                            id_resepobat : resepObat.id_resepobat
                                        }, function(data){
		        			notif($dialogs, data.status, 'Hapus');
		            		$scope.search();
		    			});
		            	
		            },function(btn){
		              //canceled
		            });
		            
		        };
		        
			    $scope.edit = function (resepObat) {
		            $location.path('/transaction/resepobat/' + resepObat.id_resepobat + '/edit');
		        };
		        
		        $scope.detail = function (resepObat) {
		            $location.path('/transaction/resepobat/' + resepObat.id_resepobat + '/detail');
		        };

			} ]);

	app.controller('ResepobatCreateController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'ResepobatListFactory',
                        'RekammedisListFactory',
                        'ObatListFactory',
			function($scope, $routeParams, $location,$dialogs,
					ResepobatListFactory, RekammedisListFactory, ObatListFactory) {

				$scope.title = "Tambah Data Resep Obat";
				$scope.isEdit = false;
                                                               
                                //Ambil list data RekamMedis
                                RekammedisListFactory.query({}, function(data){
                                    $scope.rekammedisList = data.list;
                                });
                                
                                //Ambil list data Obat
                                ObatListFactory.query({}, function(data){
                                    $scope.obatList = data.list;
                                });
                                
				$scope.save = function() {
                                    
					ResepobatListFactory.create({
						resepObat : $scope.resepObat
					}, function(data) {
						notif($dialogs, data.status, 'Simpan');
						$location.path('/transaction/resepobat');
					});

				};

				$scope.cancel = function() {
					$location.path('/transaction/resepobat');
				};

			} ]);
	
	
	app.controller('ResepobatEditController', [
			'$scope',
			'$routeParams',
			'$location',
			'$dialogs',
			'ResepobatEditFactory',
			'ResepobatListFactory',
                        'RekammedisListFactory',
                        'ObatListFactory',
			function($scope, $routeParams, $location, $dialogs,ResepobatEditFactory,
					ResepobatListFactory, RekammedisListFactory, ObatListFactory) {

				$scope.title = "Ubah Data Resep Obat";
				$scope.isEdit = true;
                                
                                $scope.dokter = {};
                                
                                //Ambil list data RekamMedis
                                RekammedisListFactory.query({}, function(data){
                                    $scope.rekammedisList = data.list;
                                });
                                
                                //Ambil list data Obat
                                ObatListFactory.query({}, function(data){
                                    $scope.obatList = data.list;
                                });
                                
                                
				ResepobatEditFactory.show({
					id_resepobat : $routeParams.id_resepobat
				}, function(dataResepObat) {
					$scope.resepObat = dataResepObat.resepObat;
                                        
                                        // Convert Long to Date
                                        var dataTanggalKunjungan = $scope.resepObat.rekamMedis.rekammedis_tglkunjungan;
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
                                        
                                        $scope.resepObat.rekamMedis.rekammedis_tglkunjungan = dateString;
                                        // Convert selesai
				});

				$scope.save = function() {
                                    // Convert date to long (timestamp)
                                    var dataTanggalKunjungan = $scope.resepObat.rekamMedis.rekammedis_tglkunjungann;
                                    var tanggalKunjungan = new Date(dataTanggalKunjungan);
                                    $scope.resepObat.rekamMedis.rekammedis_tglkunjungan = tanggalKunjungan.getTime();
                                    // Convert selesai
                                    
					ResepobatEditFactory.update({
						id_resepobat : $scope.resepObat.id_resepobat,
						resepObat : $scope.resepObat
					}, function(dataResepObat) {
						notif($dialogs, dataResepObat.status, 'Simpan');
						$location.path('/transaction/resepobat');
					});

				};

				$scope.cancel = function() {
					$location.path('/transaction/resepobat');
				};

			} ]);
	
	app.controller('ResepobatDetailController', [
	'$scope',
	'$routeParams',
	'$location',
	'ResepobatEditFactory',
	'ResepobatListFactory',
	function($scope, $routeParams, $location, ResepobatEditFactory,
			ResepobatListFactory) {

		$scope.title = "Info Detail Resep Obat";

		ResepobatEditFactory.show({
			id_resepobat : $routeParams.id_resepobat
		}, function(data) {
			$scope.resepobatList = data.resepObat;
                        $scope.resepobatList.rekamMedis.rekammedis_tglkunjungan = convertLongToDate($scope.resepobatList.rekamMedis.rekammedis_tglkunjungan);
		});


		$scope.cancel = function() {
			$location.path('/transaction/resepobat');
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
