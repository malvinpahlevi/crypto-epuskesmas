(function(){
	
	var app = angular.module('je', ['ngRoute', 'datePicker', 'cgBusy', 'je-paging',
	                                 'je-main-workspace-controller',
//	                                 'je-masterdata-rumahsakit-controller',
                                         'je-masterdata-pasien-controller',
                                         'je-masterdata-dokter-controller',
                                         'je-masterdata-penyakit-controller',
                                         'je-masterdata-obat-controller',
                                         'je-transaction-rekammedis-controller',
                                         'je-transaction-resepobat-controller',
                                         
	                                
	                                 'angularUtils.directives.dirPagination']);

	app.config(function($routeProvider) {
		$routeProvider
		.when("/", {
			templateUrl : "template/main/home.html"
		})
                // MASTER-PASIEN
                .when("/masterdata/pasien", {
			templateUrl : "template/masterdata/pasien_list.html",
			controller: "PasienListController"
		})
                .when("/masterdata/pasien/new", {
			templateUrl : "template/masterdata/pasien_edit.html",
			controller: "PasienCreateController"
		})
                .when("/masterdata/pasien/:id_pasien/edit", {
			templateUrl : "template/masterdata/pasien_edit.html",
			controller: "PasienEditController"
		})
                .when("/masterdata/pasien/:id_pasien/detail", {
			templateUrl : "template/masterdata/pasien_detail.html",
			controller: "PasienDetailController"
		})
                // MASTER-DOKTER
                .when("/masterdata/dokter", {
			templateUrl : "template/masterdata/dokter_list.html",
			controller: "DokterListController"
		})
                .when("/masterdata/dokter/new", {
			templateUrl : "template/masterdata/dokter_edit.html",
			controller: "DokterCreateController"
		})
                .when("/masterdata/dokter/:id_dokter/edit", {
			templateUrl : "template/masterdata/dokter_edit.html",
			controller: "DokterEditController"
		})
                .when("/masterdata/dokter/:id_dokter/detail", {
			templateUrl : "template/masterdata/dokter_detail.html",
			controller: "DokterDetailController"
		})
                // MASTER-PENYAKIT
                .when("/masterdata/penyakit", {
			templateUrl : "template/masterdata/penyakit_list.html",
			controller: "PenyakitListController"
		})
                .when("/masterdata/penyakit/new", {
			templateUrl : "template/masterdata/penyakit_edit.html",
			controller: "PenyakitCreateController"
		})
                .when("/masterdata/penyakit/:id_penyakit/edit", {
			templateUrl : "template/masterdata/penyakit_edit.html",
			controller: "PenyakitEditController"
		})
                .when("/masterdata/penyakit/:id_penyakit/detail", {
			templateUrl : "template/masterdata/penyakit_detail.html",
			controller: "PenyakitDetailController"
		})
                // MASTER-OBAT
                .when("/masterdata/obat", {
			templateUrl : "template/masterdata/obat_list.html",
			controller: "ObatListController"
		})
                .when("/masterdata/obat/new", {
			templateUrl : "template/masterdata/obat_edit.html",
			controller: "ObatCreateController"
		})
                .when("/masterdata/obat/:id_obat/edit", {
			templateUrl : "template/masterdata/obat_edit.html",
			controller: "ObatEditController"
		})
                .when("/masterdata/obat/:id_obat/detail", {
			templateUrl : "template/masterdata/obat_detail.html",
			controller: "ObatDetailController"
		})
                // TRANSAKSI-REKAMMEDIS
                .when("/transaction/rekammedis", {
			templateUrl : "template/transaction/rekammedis_list.html",
			controller: "RekammedisListController"
		})
                .when("/transaction/rekammedis/new", {
			templateUrl : "template/transaction/rekammedis_save.html",
			controller: "RekammedisCreateController"
		})
                .when("/transaction/rekammedis/:rekammedis_id/edit", {
			templateUrl : "template/transaction/rekammedis_edit.html",
			controller: "RekammedisEditController"
		})
                .when("/transaction/rekammedis/:rekammedis_id/detail", {
			templateUrl : "template/transaction/rekammedis_detail.html",
			controller: "RekammedisDetailController"
		})
                // TRANSAKSI-RESEPOBAT
                .when("/transaction/resepobat", {
			templateUrl : "template/transaction/resepobat_list.html",
			controller: "ResepobatListController"
		})
                .when("/transaction/resepobat/new", {
			templateUrl : "template/transaction/resepobat_save.html",
			controller: "ResepobatCreateController"
		})
                .when("/transaction/resepobat/:id_resepobat/edit", {
			templateUrl : "template/transaction/resepobat_edit.html",
			controller: "ResepobatEditController"
		})
                .when("/transaction/resepobat/:id_resepobat/detail", {
			templateUrl : "template/transaction/resepobat_detail.html",
			controller: "ResepobatDetailController"
		})
                ;
	});
	
	app.filter('trusted', ['$sce', function ($sce) {
	    return function(url) {
	        return $sce.trustAsResourceUrl(url);
	    };
	}]);
	
})();



