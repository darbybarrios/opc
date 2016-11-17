var app = angular.module("app",['ngRoute']);

function listar_plcs($http,$scope,baseUrl){
	  
	$http.get(baseUrl + "/listar-dispositivos-server").success(function (data) {
		{ $scope.jsonplcsActivas = data;
		  $scope.plcsActivas = $scope.jsonplcsActivas;
        }
	});   
	
	}

function listar_marcas($http,$scope,baseUrl){
	$http.get(baseUrl + "/listar-marcas").success(function (data) {
		{ $scope.jsonmarcas = data;
		  $scope.marcas = $scope.jsonmarcas;
        }
	});	
}

function listar_maquinas($http,$scope,baseUrl){
	$http.get(baseUrl + "/listar-maquinas").success(function (data) {
		{ $scope.jsonmaquinas = data;
		  $scope.maquinas = $scope.jsonmaquinas;
        }
	});	
}

function listar_dispositivos($http,$scope,baseUrl){
	$scope.jsondispositivos = []; 
	$scope.dispositivos = [];
	$http.get(baseUrl + "/listar-dispositivos").success(function (data) {
		
       
		{ $scope.jsondispositivos = data;
		  $scope.dispositivos = $scope.jsondispositivos;
        }
		iniciar_tabla('#tbdispo')

	});
	
}

function iniciar_tabla(nombre){
		angular.element(document).ready( function () {
        dTable = $(nombre).DataTable({responsive: true});
        //dTable.ajax.reload();
        
		});
}

function listar_tags_dispo_server($http,$scope,baseUrl,id){
	
	
	$http.get(baseUrl +'/listar-tags-dispo?idDispositivo='+id).success(function (data) {
		
	       
		{ 
		  $scope.tags = data;
        }
		
	});	
}

function cargarValores($http,$scope,baseUrl){
	$scope.unidadMedida = [];
	$scope.tipoValor = [];
	$scope.tipoInfo = [];
	
	$http.get(baseUrl +'/listar-unidades').success(function (data) {
		{ 
		  $scope.unidadMedida = data;
        }
		
	});	
	
	$http.get(baseUrl +'/listar-valores').success(function (data) {
       
		{ 
		  $scope.tipoValor = data;
        }
		
	});	
	
    $scope.tipoInfo = [{
        idTipoInfo: "1",
        descripcion: "Status Linea"
      }, {
    	  idTipoInfo: "2",
        descripcion: "Falla"
      }, {
    	  idTipoInfo: "3",
         descripcion: "Motivo Falla"
      }, {
          idTipoInfo: "4",
          descripcion: "Produccion"
      }, {
          idTipoInfo: "5",
          descripcion: "Velocidad"
      }, {
          idTipoInfo: "6",
          descripcion: "Otro"
        }];	
	
	
	
	
	
}

function listar_tags($http,$scope,baseUrl,id){

	$http.get(baseUrl +'/listar-tags?idDispositivo='+id).success(function (data) {
		
       
		{ 
		  $scope.tagsdispo = data;
        }
		
	angular.element(document).ready( function () {
        dTable = $('#tbdata')
        dTable.DataTable();
        
		});
	});
	
}


function buscarDispoMaquina($http,$scope,baseUrl){
	
	$scope.tagDispo = [];	
	$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).success(function (data) {
		{ 
			   $scope.tagDispo = data;
			   alert("Dispo : " + $scope.tagDispo.idDispositivo);
	
		  
        }
	});	
	
}

function buscarSucursal($http,$scope,baseUrl,id){
	
	$scope.tagDispo = [];	
	$http.get(baseUrl + "/buscar-sucursal?idSuc="+id).success(function (data) {
		{ 
			   $scope.sucursal = data;
			   
	
		  
        }
	});	
	
}

function limpiar_tags($http,$scope,baseUrl){
	$scope.selectTag = null;
	$scope.tag1 = [];
	$scope.descDet = "-";
	//$scope.descDet = null;
	$scope.selectTag2 = null;
	$scope.tag2 = [];
	$scope.selectTag3 = null;
	$scope.tag3 = [];
	$scope.selectTag4 = null;
	$scope.tag4 = [];
	$scope.selectTag5 = null;
	$scope.tag5 = [];
	$scope.selectTag6 = null;
	$scope.tag6 = [];	
}

function buscar_valor_tag1($http,$scope,baseUrl){
	

	$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
		  $scope.tagDispo = result.data;
		  return $http.get(baseUrl +'/buscarTag?idDispositivo='+$scope.tagDispo.idDispositivo+'&posTag=1')
	}).then(function(result){
		  $scope.selectTag = result.data;
		  
		  return $http.get(baseUrl +'/Tablero_Tag1?idDispositivo='+$scope.tagDispo.idDispositivo+'&posTag=1')
	}).then(function(result){
		  $scope.tag1 = result.data;
		  
		  return $http.get(baseUrl +'/valorDetTag?idTag='+$scope.tag1.tag.idTag+'&valor='+$scope.tag1.valor)
	}).then(function(result){
		  $scope.descDet = result.data;		  
		  //console.log($scope.Tag1); 
	})	  

	
		

}

function buscar_valor_tag2($http,$scope,baseUrl){
	

	$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
		  $scope.tagDispo2 = result.data;
		  return $http.get(baseUrl +'/buscarTag?idDispositivo='+$scope.tagDispo2.idDispositivo+'&posTag=2')
	}).then(function(result){
		  $scope.selectTag2 = result.data;
		  
		  return $http.get(baseUrl +'/Tablero_Tag1?idDispositivo='+$scope.tagDispo2.idDispositivo+'&posTag=2')
	}).then(function(result){
		  $scope.tag2 = result.data;
		  console.log($scope.Tag2); 
	})	  

}

function buscar_valor_tag3($http,$scope,baseUrl){
	

	$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
		  $scope.tagDispo3 = result.data;
		  return $http.get(baseUrl +'/buscarTag?idDispositivo='+$scope.tagDispo3.idDispositivo+'&posTag=3')
	}).then(function(result){
		  $scope.selectTag3 = result.data;
		  
		  return $http.get(baseUrl +'/Tablero_Tag1?idDispositivo='+$scope.tagDispo3.idDispositivo+'&posTag=3')
	}).then(function(result){
		  $scope.tag3 = result.data;
		  console.log($scope.Tag3); 
	})	  

}

function buscar_valor_tag4($http,$scope,baseUrl){
	

	$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
		  $scope.tagDispo4 = result.data;
		  return $http.get(baseUrl +'/buscarTag?idDispositivo='+$scope.tagDispo4.idDispositivo+'&posTag=4')
	}).then(function(result){
		  $scope.selectTag4 = result.data;
		  
		  return $http.get(baseUrl +'/Tablero_Tag1?idDispositivo='+$scope.tagDispo4.idDispositivo+'&posTag=4')
	}).then(function(result){
		  $scope.tag4 = result.data;
		  console.log($scope.Tag4); 
	})	  

}

function buscar_valor_tag5($http,$scope,baseUrl){
	

	$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
		  $scope.tagDispo5 = result.data;
		  return $http.get(baseUrl +'/buscarTag?idDispositivo='+$scope.tagDispo5.idDispositivo+'&posTag=5')
	}).then(function(result){
		  $scope.selectTag5 = result.data;
		  
		  return $http.get(baseUrl +'/Tablero_Tag1?idDispositivo='+$scope.tagDispo5.idDispositivo+'&posTag=5')
	}).then(function(result){
		  $scope.tag5 = result.data;
		  console.log($scope.Tag5); 
		  return $http.get(baseUrl +'/valorDetTag?idTag='+$scope.tag5.tag.idTag+'&valor='+$scope.tag5.valor)
	}).then(function(result){
		  $scope.descDet5 = result.data;			  
	})	  

}

function buscar_valor_tag6($http,$scope,baseUrl){
	

	$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
		  $scope.tagDispo6 = result.data;
		  return $http.get(baseUrl +'/buscarTag?idDispositivo='+$scope.tagDispo6.idDispositivo+'&posTag=6')
	}).then(function(result){
		  $scope.selectTag6 = result.data;
		  
		  return $http.get(baseUrl +'/Tablero_Tag1?idDispositivo='+$scope.tagDispo6.idDispositivo+'&posTag=6')
	}).then(function(result){
		  $scope.tag6 = result.data;
		  console.log($scope.Tag6); 
	})	  

}

function eficienciaTurno($http,$scope,baseUrl){
	

	$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
		  $scope.prDispo = result.data;
		  return $http.get(baseUrl +'/Pr_Actual?idDispositivo='+$scope.prDispo.idDispositivo+'&idTurno='+$scope.turnoActual.idTurno)
	}).then(function(result){
		  $scope.eficiencia = result.data;
	})	  

}


function buscar_fallas($http,$scope,baseUrl,id){
	
	$scope.fallas = [];	
	$scope.nroReg = 0;
	var nroReg;
	
	$http.get(baseUrl + '/buscarPorMaquina?idMaquina='+id).then(function (result) {
		$scope.maq = result.data;
		//alert($scope.maq.idDispositivo);
		return $http.get(baseUrl + '/Fallas?idDispositivo='+$scope.maq.idDispositivo)
	}).then(function(result){
		$scope.fallas = result.data;
		$scope.nroReg = $scope.fallas.length;
		
		return $scope.nroReg;
	})
	


}

function listar_Areas($http,$scope,baseUrl,idMaquina){
	
	$http.get(baseUrl +'/Areas?idMaquina='+idMaquina).success(function (data) {
		{ 
		  $scope.areas = data;
        }
		
	});	
}

//Controlador ActividadTag
function listar_Sistemas($http,$scope,baseUrl,idArea,idMaquina){
	
	$http.get(baseUrl +'/Sistemas?parea='+idArea+'&idMaquina='+idMaquina).success(function (data) {
       
		{ 
		  $scope.sistemas = data;
        }
		
	});	
}

function listar_SubSistemas($http,$scope,baseUrl,idArea,idSistema,idMaquina){
	
	$http.get(baseUrl +'/SubSistemas?parea='+idArea+'&psistema='+idSistema+'&idMaquina='+idMaquina).success(function (data) {
       
		{ 
		  $scope.subsistemas = data;
        }
		
	});	
}

function listar_CausaFallas($http,$scope,baseUrl,idArea,idSistema,idSubSistema,idMaquina){
	
	$http.get(baseUrl +'/CausaFalla?parea='+idArea+'&psistema='+idSistema+'&psubsistema='+idSubSistema+'&idMaquina='+idMaquina).success(function (data) {
       
		{ 
		  $scope.causafallas = data;
        }
		
	});	
}

function buscar_turno_actual($http,$scope,baseUrl){
	$http.get(baseUrl +'/turno-actual').success(function (data) {
	       
		{ 
		  $scope.turnoActual = data;
        }
		
	});		
}


function RemoteResource($http, baseUrl) {
  this.get = function(fnOK, fnError) {
    $http({
      method: 'GET',
      url: baseUrl + '/sucursal'
    }).success(function(data, status, headers, config) {
      fnOK(data);
    }).error(function(data, status, headers, config) {
      fnError(data, status);
    });
  }
  this.list = function(fnOK, fnError) {
    $http({
      method: 'GET',
      url: baseUrl + '/listar-sucursales'
    }).success(function(data, status, headers, config) {
      fnOK(data);
    }).error(function(data, status, headers, config) {
      fnError(data, status);
    });
  }
}


function RemoteResourceProvider() {
  var _baseUrl;
  this.setBaseUrl = function(baseUrl) {
    _baseUrl = baseUrl;
  }
  this.$get = ['$http',
    function($http) {
      return new RemoteResource($http, _baseUrl);
    }
  ];
}


function iniciar_elementos(){
	
	angular.element(document).ready(function() {
        var data1 = [
          [gd(2012, 1, 1), 17],
          [gd(2012, 1, 2), 74],
          [gd(2012, 1, 3), 6],
          [gd(2012, 1, 4), 39],
          [gd(2012, 1, 5), 20],
          [gd(2012, 1, 6), 85],
          [gd(2012, 1, 7), 7]
        ];

        var data2 = [
          [gd(2012, 1, 1), 82],
          [gd(2012, 1, 2), 23],
          [gd(2012, 1, 3), 66],
          [gd(2012, 1, 4), 9],
          [gd(2012, 1, 5), 119],
          [gd(2012, 1, 6), 6],
          [gd(2012, 1, 7), 9]
        ];
        $("#canvas_dahs").length && $.plot($("#canvas_dahs"), [
          data1, data2
        ], {
          series: {
            lines: {
              show: false,
              fill: true
            },
            splines: {
              show: true,
              tension: 0.4,
              lineWidth: 1,
              fill: 0.4
            },
            points: {
              radius: 0,
              show: true
            },
            shadowSize: 2
          },
          grid: {
            verticalLines: true,
            hoverable: true,
            clickable: true,
            tickColor: "#d5d5d5",
            borderWidth: 1,
            color: '#fff'
          },
          colors: ["rgba(38, 185, 154, 0.38)", "rgba(3, 88, 106, 0.38)"],
          xaxis: {
            tickColor: "rgba(51, 51, 51, 0.06)",
            mode: "time",
            tickSize: [1, "day"],
            //tickLength: 10,
            axisLabel: "Date",
            axisLabelUseCanvas: true,
            axisLabelFontSizePixels: 12,
            axisLabelFontFamily: 'Verdana, Arial',
            axisLabelPadding: 10
          },
          yaxis: {
            ticks: 8,
            tickColor: "rgba(51, 51, 51, 0.06)",
          },
          tooltip: false
        });

        function gd(year, month, day) {
          return new Date(year, month - 1, day).getTime();
        }
      });	
	
	angular.element(document).ready(function() {
	      var cb = function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	        $('#reportrange_right span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
	      };

	      var optionSet1 = {
	        startDate: moment().subtract(29, 'days'),
	        endDate: moment(),
	        minDate: '01/01/2012',
	        maxDate: '12/31/2015',
	        dateLimit: {
	          days: 60
	        },
	        showDropdowns: true,
	        showWeekNumbers: true,
	        timePicker: false,
	        timePickerIncrement: 1,
	        timePicker12Hour: true,
	        ranges: {
	          'Today': [moment(), moment()],
	          'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
	          'Last 7 Days': [moment().subtract(6, 'days'), moment()],
	          'Last 30 Days': [moment().subtract(29, 'days'), moment()],
	          'This Month': [moment().startOf('month'), moment().endOf('month')],
	          'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
	        },
	        opens: 'right',
	        buttonClasses: ['btn btn-default'],
	        applyClass: 'btn-small btn-primary',
	        cancelClass: 'btn-small',
	        format: 'MM/DD/YYYY',
	        separator: ' to ',
	        locale: {
	          applyLabel: 'Submit',
	          cancelLabel: 'Clear',
	          fromLabel: 'From',
	          toLabel: 'To',
	          customRangeLabel: 'Custom',
	          daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
	          monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
	          firstDay: 1
	        }
	      };

	      $('#reportrange_right span').html(moment().subtract(29, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));

	      $('#reportrange_right').daterangepicker(optionSet1, cb);

	      $('#reportrange_right').on('show.daterangepicker', function() {
	        console.log("show event fired");
	      });
	      $('#reportrange_right').on('hide.daterangepicker', function() {
	        console.log("hide event fired");
	      });
	      $('#reportrange_right').on('apply.daterangepicker', function(ev, picker) {
	        console.log("apply event fired, start/end dates are " + picker.startDate.format('MMMM D, YYYY') + " to " + picker.endDate.format('MMMM D, YYYY'));
	      });
	      $('#reportrange_right').on('cancel.daterangepicker', function(ev, picker) {
	        console.log("cancel event fired");
	      });

	      $('#options1').click(function() {
	        $('#reportrange_right').data('daterangepicker').setOptions(optionSet1, cb);
	      });

	      $('#options2').click(function() {
	        $('#reportrange_right').data('daterangepicker').setOptions(optionSet2, cb);
	      });

	      $('#destroy').click(function() {
	        $('#reportrange_right').data('daterangepicker').remove();
	      });

	    });



	  angular.element(document).ready(function() {
	      var cb = function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	        $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
	      };

	      var optionSet1 = {
	        startDate: moment().subtract(29, 'days'),
	        endDate: moment(),
	        minDate: '01/01/2012',
	        maxDate: '12/31/2015',
	        dateLimit: {
	          days: 60
	        },
	        showDropdowns: true,
	        showWeekNumbers: true,
	        timePicker: false,
	        timePickerIncrement: 1,
	        timePicker12Hour: true,
	        ranges: {
	          'Today': [moment(), moment()],
	          'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
	          'Last 7 Days': [moment().subtract(6, 'days'), moment()],
	          'Last 30 Days': [moment().subtract(29, 'days'), moment()],
	          'This Month': [moment().startOf('month'), moment().endOf('month')],
	          'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
	        },
	        opens: 'left',
	        buttonClasses: ['btn btn-default'],
	        applyClass: 'btn-small btn-primary',
	        cancelClass: 'btn-small',
	        format: 'MM/DD/YYYY',
	        separator: ' to ',
	        locale: {
	          applyLabel: 'Submit',
	          cancelLabel: 'Clear',
	          fromLabel: 'From',
	          toLabel: 'To',
	          customRangeLabel: 'Custom',
	          daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
	          monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
	          firstDay: 1
	        }
	      };
	      $('#reportrange span').html(moment().subtract(29, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
	      $('#reportrange').daterangepicker(optionSet1, cb);
	      $('#reportrange').on('show.daterangepicker', function() {
	        console.log("show event fired");
	      });
	      $('#reportrange').on('hide.daterangepicker', function() {
	        console.log("hide event fired");
	      });
	      $('#reportrange').on('apply.daterangepicker', function(ev, picker) {
	        console.log("apply event fired, start/end dates are " + picker.startDate.format('MMMM D, YYYY') + " to " + picker.endDate.format('MMMM D, YYYY'));
	      });
	      $('#reportrange').on('cancel.daterangepicker', function(ev, picker) {
	        console.log("cancel event fired");
	      });
	      $('#options1').click(function() {
	        $('#reportrange').data('daterangepicker').setOptions(optionSet1, cb);
	      });
	      $('#options2').click(function() {
	        $('#reportrange').data('daterangepicker').setOptions(optionSet2, cb);
	      });
	      $('#destroy').click(function() {
	        $('#reportrange').data('daterangepicker').remove();
	      });
	    });


	 
	  angular.element(document).ready(function() {
	      $('#single_cal1').daterangepicker({
	        singleDatePicker: true,
	        calender_style: "picker_1"
	      }, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	      });
	      $('#single_cal2').daterangepicker({
	        singleDatePicker: true,
	        calender_style: "picker_2"
	      }, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	      });
	      $('#single_cal3').daterangepicker({
	        singleDatePicker: true,
	        calender_style: "picker_3"
	      }, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	      });
	      $('#single_cal4').daterangepicker({
	        singleDatePicker: true,
	        calender_style: "picker_4"
	      }, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	      });
	    });
	 

	 
	  angular.element(document).ready(function() {
	      $('#reservation').daterangepicker(null, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	      });
	    });
	  
	  //Nuevo en la Oficina
	  
	  angular.element(document).ready(function() {
	         var handleDataTableButtons = function() {
	           if ($("#tbfmeca1").length) {
	             $("#tbfmeca1").DataTable({
	               dom: "Bfrtip",
	               buttons: [
	                 {
	                   extend: "copy",
	                   className: "btn-sm"
	                 },
	                 {
	                   extend: "csv",
	                   className: "btn-sm"
	                 },
	                 {
	                   extend: "excel",
	                   className: "btn-sm"
	                 },
	                 {
	                   extend: "pdfHtml5",
	                   className: "btn-sm"
	                 },
	                 {
	                   extend: "print",
	                   className: "btn-sm"
	                 },
	               ],
	               responsive: true
	             });
	           }
	         };

	         TableManageButtons = function() {
	           "use strict";
	           return {
	             init: function() {
	               handleDataTableButtons();
	             }
	           };
	         }();

	         $('#datatable1').dataTable();
	         $('#datatable-keytable').DataTable({
	           keys: true
	         });

	         //$('#tbfmeca1').DataTable();

	         $('#datatable-scroller').DataTable({
	           ajax: "js/datatables/json/scroller-demo.json",
	           deferRender: true,
	           scrollY: 380,
	           scrollCollapse: true,
	           scroller: true
	         });

	         var table = $('#datatable-fixed-header').DataTable({
	           fixedHeader: true
	         });

	         TableManageButtons.init();
	       });	  
	  
}


app.provider("remoteResource", RemoteResourceProvider);

app.config(['$routeProvider',function($routeProvider) {
	  
	  $routeProvider.when('/', {
	    templateUrl: "modo_produccion.html",
	    controller: "TableroController"
	  });
	
	  $routeProvider.when('/modo-produccion', {
	    templateUrl: "modo_produccion.html",
	    controller: "TableroController"
	  });
	  
	  $routeProvider.when('/modo-fallas', {
	    templateUrl: "modo_fallas.html",
	    controller: "FallasController"
	  });
	  
	  $routeProvider.when('/modo-monitoreo', {
	    templateUrl: "construccion.html",
	   // controller: "Pagina3Controller"
	  }); 
	  $routeProvider.when('/agregar-tags', {
		    templateUrl: "tags.html",
		    controller: "TagsController"
		  }); 
	  $routeProvider.when('/listar-tags', {
		    templateUrl: "construccion.html",
		   // controller: "Pagina3Controller"
		  }); 
	  $routeProvider.when('/agregar-dispositivo', {
		    templateUrl: "dispositivos.html",
		    controller: "DispositivosController"
		  }); 
	  $routeProvider.when('/listar-dispositivo', {
		    templateUrl: "construccion.html",
		   // controller: "Pagina3Controller"
		  });
	  $routeProvider.when('/sucursales', {
		    templateUrl: "sucursales.html",
		    controller: "ListadoSucursalesController"
		  });
	  $routeProvider.when('/marcas', {
		    templateUrl: "construccion.html",
		   // controller: "Pagina3Controller"
		  });
	  $routeProvider.when('/maquinas', {
		    templateUrl: "construccion.html",
		   // controller: "Pagina3Controller"
		  });	  
	  $routeProvider.when('/turnos', {
		    templateUrl: "turnos.html",
		    controller: "TurnosController"
		  });
	  $routeProvider.when('/usuarios', {
		    templateUrl: "construccion.html",
		   // controller: "Pagina3Controller"
		  });
	  $routeProvider.when('/Fmecas por Fecha', {
		    templateUrl: "Consulta_Fmecas_1.html",
		    controller: "ConsultasController"
		  });	  
	  
	  $routeProvider.otherwise({
	        redirectTo: '/'
	  });   

	}]);

app.constant("baseUrl", ".");
app.config(['baseUrl', 'remoteResourceProvider',
  function(baseUrl, remoteResourceProvider) {
    remoteResourceProvider.setBaseUrl(baseUrl);
  }
]);


app.filter('songTime',function(){

    return function (s) {
        var ms = s % 1000;
        s = (s - ms) / 1000;
        var secs = s % 60;
        s = (s - secs) / 60;
        var mins = s % 60;
        var hrs = (s - mins) / 60;
        
        if (secs < 10){
        	secs = '0' + secs;
        }
        
        if (mins < 10){
        	mins = '0' + mins;
        }
        
        if (hrs < 10){
        	hrs = '0' + hrs;
        }        
      

        return hrs + ':' + mins + ':' + secs;        
    };
});

app.filter('time', function($filter)
		{
		 return function(input)
		 {
		  alert(input);	 
		  if(input == null){ 
			  
			  return ""; 
			  
		  } 
		 
		  var _date = $filter('date')(new Date(input), 'HH:mm:ss');
		  alert(_date);
		  return _date;

		 };
		});

//----------------------------------- Sucursales ----------------------------------------------------------//

app.controller("ListadoSucursalesController", ['$scope', 'remoteResource',function($scope, remoteResource) {
    $scope.sucursales = [];

    remoteResource.list(function(sucursales) {
      $scope.sucursales = sucursales;
    }, function(data, status) {
      alert("Ha fallado la petición. Estado HTTP:" + status);
    });

}]);

//----------------------------------- Dispositivos ---------------------------------------------------------//

app.controller("DispositivosController", ['$scope','$http',function($scope, $http) {
	$scope.jsonplcsActivas = [];
    $scope.jsonmarcas = [];
    $scope.jsonmaquinas = [];
    var idSucursal = 1;
    
    $http.defaults.headers.post["Content-Type"] = "application/json";
    var baseUrl = ".";
    
    	listar_plcs($http,$scope,baseUrl);
    	listar_dispositivos($http,$scope,baseUrl);
    	listar_marcas($http,$scope,baseUrl);
    	listar_maquinas($http,$scope,baseUrl);

    $scope.insertarDispo = function(){
    	
    	
    	if ($scope.dispoPlc=="" || $scope.dispoMarca =="" || $scope.dispoMaquina == ""){
    		alert("LA CAGASTE");
    	}
    	else{
    		$http.get(baseUrl + '/crear-dispositivo?descripcion='+$scope.dispoPlc+'&idMarca='+$scope.dispoMarca+'&idMaquina='+$scope.dispoMaquina+'&idSucursal='+idSucursal).
    		success(function(data){
    			alert("Dispositivo agregado");
    			//
    			//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
    			listar_dispositivos($http,$scope,baseUrl);
    			//refrescar_tabla('#tbdata');
    			$scope.dispoPlc=null;
    			$scope.dispoMarca=null;
    			$scope.dispoMaquina=null;
  
    		});

    	}

    }
    
    
    $scope.removerDipo = function(index){
    	
    	

		$scope.dispositivos.splice( index, 1 );		
		//alert($scope.dispositivos[index].idDispositivo);
		$http.get(baseUrl + '/eliminar-dispositivo?id='+$scope.dispositivos[index].idDispositivo+'&statReg=1').
		success(function(data){
			alert("Dispositivo Eliminado");
			//
			//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
			listar_dispositivos($http,$scope,baseUrl);
			//refrescar_tabla('#tbdata');

		});		
	}
     
    
}]);

//----------------------------------- Tags ---------------------------------------------------------------//

app.controller("TagsController", ['$scope','$http',function($scope, $http) {
	$scope.selDispo = [];
    var idSucursal = 1;
    
    $http.defaults.headers.post["Content-Type"] = "application/json";
    var baseUrl = ".";
    
    listar_dispositivos($http,$scope,baseUrl);
    
    $scope.mostrarTags = function(){
    	listar_tags_dispo_server($http,$scope,baseUrl,$scope.selDispo);
    	listar_tags($http,$scope,baseUrl,$scope.selDispo);
    	cargarValores($http,$scope,baseUrl);
    };	


    $scope.insertarTag = function(){
    	
    	
    	if ($scope.selTag==""){
    		alert("Hay  Algun Campo en Blanco");
    	}
    	else{
    		//alert("Valor Web Wbe: " + $scope.opTablero);
    		
    		if ($scope.txtWeb == null){
    			$scope.txtWeb = '0';
    		}
    		
    		if ($scope.txtBit == null){
    			$scope.txtBit = '0';
    		}
    		
    		$http.get(baseUrl + '/crear-tag?itemId='+$scope.selTag+'&descTag='+$scope.txtDesc+
    				 '&statWeb='+$scope.txtWeb+'&idUnidadMedida='+$scope.selUnidad+'&idTipoValor='+$scope.selValor+
    				 '&escala='+$scope.txtEscala+'&intervalo='+$scope.txtIntervalo+'&valorBit='+$scope.txtBit+'&idDispositivo='+$scope.selDispo+
    				 '&tipoInformacion='+$scope.selInfo).
    		success(function(data){
    			alert("Tag Agregada Exitosamente");
    			$scope.selTag = null;
    			$scope.txtDesc = null;
    			$scope.txtWeb = null;
    			$scope.selUnidad = null;
    			$scope.selValor = null;
    			$scope.txtEscala = null;
    			$scope.txtIntervalo = null;
    			$scope.txtBit = null;
    			listar_tags($http,$scope,baseUrl,$scope.selDispo);
    			
    			
    			
    			
    			
    			//
    			//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
 
  
    		});

    	}

    }
    
    
    $scope.removerTag = function(index){
    	
    	

				
		
		//alert($scope.dispositivos[index].idDispositivo);
		$http.get(baseUrl + '/eliminar-tag?idTag='+$scope.tagsdispo[index].idTag+'&statReg=1').
		success(function(data){
			$scope.tagsdispo.splice( index, 1 );
			alert("Tag Eliminada");
			//
			//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
			listar_dispositivos($http,$scope,baseUrl);
			//refrescar_tabla('#tbdata');

		});		
	}
     
    
}]);

//-------------------------------------------------------------------------------------------------------------//
//----------------------------------- Modo Produccion ---------------------------------------------------------//
//-------------------------------------------------------------------------------------------------------------//

app.controller("TableroController", ['$scope','$http','$timeout',function($scope, $http,$timeout) {
	var baseUrl = ".";
	listar_maquinas($http,$scope,baseUrl);
	
	iniciar_elementos();
	limpiar_tags($http,$scope,baseUrl);
	buscar_turno_actual($http,$scope,baseUrl);
	/*
	
	$scope.tag1valor = "-";
	$scope.tag2valor = "-";
	$scope.tag3valor = "-";
	$scope.tag4valor = "-";
	$scope.tag5valor = "-";
	$scope.tag6valor = "-";
	
	*/
		
//listar_dispositivos($http,$scope,baseUrl);
	
	$scope.activateRealtime = function() {
		
		$scope.tagDispo = [];
		$scope.tagDispo2 = [];
		$scope.conectado = null;
		
		
		//alert("Seleccciom :"+$scope.selLinea);
		   //buscarDispoMaquina($http,$scope,baseUrl)

		$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
			  $scope.tagDispo2 = result.data;
			  
			  if ($scope.tagDispo2.idDispositivo == null ){
				   limpiar_tags($http,$scope,baseUrl);
				   //alert($scope.tag1Vacio);
			  }
			  
			  
			  return $http.get(baseUrl +'/verificar-conexion?id='+$scope.tagDispo2.idDispositivo)
		}).then(function(result){
			  $scope.conectado = result.data;
			  //impiar_tags($http,$scope,baseUrl);
				if ($scope.conectado == "1"){
					
					
					   buscar_valor_tag1($http,$scope,baseUrl);
					   //buscar_valor_tag2($http,$scope,baseUrl);
					   buscar_valor_tag3($http,$scope,baseUrl);
					   buscar_valor_tag4($http,$scope,baseUrl);
					   buscar_valor_tag5($http,$scope,baseUrl);
					   //buscar_valor_tag6($http,$scope,baseUrl); 
					   eficienciaTurno($http,$scope,baseUrl);
					   $timeout($scope.activateRealtime, 1000);	
					
					}
					else if ($scope.conectado == "0"){
						alert("No hay hay conexion con la Linea");
						limpiar_tags($http,$scope,baseUrl);
						
					}
					else if ($scope.conectado == "2"){
						alert("Problemas al Accesar Servidor, Intente mas Tarde");
						limpiar_tags($http,$scope,baseUrl);
					}
					else if ($scope.conectado == null){
						alert("Es Nulo");
					
					}			  
		})		
		

		
		   

	}



}]);

//-------------------------------------------------------------------------------------------------------------//
//-----------------------------------------  TURNOS -----------------------------------------------------------//
//-------------------------------------------------------------------------------------------------------------//

app.controller("TurnosController", ['$scope','$http','$filter',function($scope, $http,$filter) {
	var baseUrl = ".";
    var idSucursal = 1;	
    buscarSucursal($http,$scope,baseUrl,idSucursal);
    
	$http.get(baseUrl + "/listar-turnos").success(function (data) {
		{ 
			$scope.turnos = data;
        }
	});	
	
    $scope.insertarTurno = function(){
    	
    	
    	
    	if ($scope.txtDesc=="" || $scope.txtInicio =="" || $scope.txtFin == ""){
    		alert("Hay Algun Campo en Blanco");
    	}
    	else{
    		
	    		if ($scope.sucursal.turnos > $scope.turnos.length){
	    		var inicio = $filter('time')($scope.txtInicio+':00');
		    		$http.get(baseUrl + '/nuevo-turno?desc='+$scope.txtDesc+'&inicio='+$scope.txtInicio+':00'+'&fin='+$scope.txtFin+':00'+'&idSucursal='+idSucursal).
		    		success(function(data){
		    			alert("Turno agregado");
		    			//
		    			//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
		    			$http.get(baseUrl + "/listar-turnos").success(function (data) {
		    				{ 
		    					$scope.turnos = data;
		    		        }
		    			});	
		    			//refrescar_tabla('#tbdata');
		    			$scope.txtDesc=null;
		    			$scope.txtInicio=null;
		    			$scope.txtFin=null;
		  
		    		});
	    		}
		    		else{
		    			alert('El tope de Turnos de la Sucursal es :'+$scope.sucursal.turnos);
		    		}	
    		
    		

    	}

    }	
	

}]);

//----------------------------------------------------------------------------------------------------------//
//-------------------------------------------  MODO FALLAS -------------------------------------------------//
//----------------------------------------------------------------------------------------------------------//

app.controller("FallasController", ['$scope','$http','$timeout',function($scope, $http,$timeout) {
	
	var baseUrl = ".";
    var idSucursal = 1;
    var total = 0;
    var inicio = 0;
    $scope.fallasAct = [];
	listar_maquinas($http,$scope,baseUrl);
	var id = $scope.selMaq;
	
	
	
	$scope.monitorFallas = function() {
		
		
		//buscar_fallas($http,$scope,baseUrl,$scope.selMaq);
		$http.get(baseUrl + '/buscarPorMaquina?idMaquina='+$scope.selMaq).then(function (result) {
			$scope.maq = result.data;
			//alert($scope.maq.idDispositivo);
			return $http.get(baseUrl + '/Fallas?idDispositivo='+$scope.maq.idDispositivo)
		}).then(function(result){
			$scope.fallas = result.data;
			if (inicio == 0){
				inicio = 1;
				iniciar_tabla('#tbfalla');
			}
			if ($scope.fallas.length > total){
				
				$scope.fallasAct = $scope.fallas;
				total = $scope.fallas.length;
			}			
		})



		$timeout($scope.monitorFallas, 1000);
		
	}


	$scope.editarParada = function(index) {	
		$scope.cboArea = null;
		$scope.cboSistema = null;
		$scope.cboSubSistema = null;
		listar_Areas($http,$scope,baseUrl,$scope.selMaq);
		$scope.indice = index;
		//listar_Sistemas($http,$scope,baseUrl,$scope.cboArea);
		//listar_SubSistemas($http,$scope,baseUrl,$scope.cboArea,$scope.cboSistema);
		$http.get(baseUrl + '/consultarParada?idParada='+$scope.fallasAct[$scope.indice].idActividadTag).
		success(function(data){
			$scope.parada = data;
			$scope.cboArea = $scope.parada.area.descArea;
			$scope.cboSistema = $scope.parada.area.descSistema;
			$scope.cboSubSistema = $scope.parada.area.descSubSistema;
			
			
		});			
	}
	
	$scope.listaSistemas = function(){
		//alert("Pase");
		listar_Sistemas($http,$scope,baseUrl,$scope.cboArea,$scope.selMaq);
	}

	$scope.listaSubSistemas = function(){
		//alert("subsistema : "+ $scope.cboArea + " " + $scope.cboSistema);
		listar_SubSistemas($http,$scope,baseUrl,$scope.cboArea,$scope.cboSistema,$scope.selMaq);
	}	
	
	$scope.listaCausaFallas = function(){
		//alert("subsistema : "+ $scope.cboArea + " " + $scope.cboSistema);
		listar_CausaFallas($http,$scope,baseUrl,$scope.cboArea,$scope.cboSistema,$scope.cboCausaFalla,$scope.selMaq);
	}		
	
	$scope.actualizarParada = function(index){
		
		//alert("Ahy voy");
		
		$http.get(baseUrl + '/actParada?idActividad='+$scope.fallasAct[$scope.indice].idActividadTag+'&idArea='+$scope.cboArea+'&idSistema='+$scope.cboSistema+
				  '&idSubsistema='+$scope.cboSubSistema+'&comen='+$scope.txtComen).
		success(function(data){
			alert("Parada  Actualizada con Exito");
			//
			//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
			$scope.cboArea = null;
			$scope.cboSistema = null;
			$scope.cboSubSistema = null;
			//refrescar_tabla('#tbdata');

			$http.get(baseUrl + '/buscarPorMaquina?idMaquina='+$scope.selMaq).then(function (result) {
				$scope.maq = result.data;
				//alert($scope.maq.idDispositivo);
				return $http.get(baseUrl + '/Fallas?idDispositivo='+$scope.maq.idDispositivo)
			}).then(function(result){
				$scope.fallas = result.data;
					$scope.fallasAct = $scope.fallas;
			
			}) ///hasta aqui			
			
		});		
		
	}
	

	$scope.consultarParada = function(index){
		$http.get(baseUrl + '/actParada?idActividad='+$scope.fallasAct[$scope.indice].idActividadTag).
		success(function(data){
			$scope.parada = data;
			$scope.cboArea = $scope.parada.area.descArea;
			$scope.cboSistema = $scope.parada.area.descSistema;
			$scope.cboSubSistema = $scope.parada.area.descSubSistema;
			
			
		});		
		
	}
	
}]);

//---------------------------------------------------------------------------------------------------------//
//----------------------------------- Consultas ----------------------------------------------------------//
//---------------------------------------------------------------------------------------------------------//

app.controller("ConsultasController", ['$scope','$http','$timeout',function($scope, $http,$timeout) {
	var baseUrl = ".";
    var idSucursal = 1;
    var total = 0;	
	
	listar_maquinas($http,$scope,baseUrl);
	//iniciar_tabla('#tbfmeca1');
	

		
	$scope.buscarConsulta = function() {
		
		
		alert($scope.fecIni);
		alert($scope.fecFin);
		
		//buscar_fallas($http,$scope,baseUrl,$scope.selMaq);
		$http.get(baseUrl + '/buscarPorMaquina?idMaquina='+$scope.selMaq).then(function (result) {
			$scope.maq = result.data;
			//alert($scope.maq.idDispositivo);
			return $http.get(baseUrl + '/Fmecas-Periodo?idDispositivo='+$scope.maq.idDispositivo+'&inicio='+$scope.fecIni+'&fin='+$scope.fecFin)
		}).then(function(result){
			$scope.fallas = result.data;
			if ($scope.fallas.length > total){
				
				$scope.fallasFmeca = $scope.fallas;
				total = $scope.fallas.length;
				iniciar_elementos();
			}			
		})
		
		//$timeout($scope.buscarConsulta, 1000);
		
	}	
		

}]);

app.controller("MainController", ['$scope',function($scope) {
	/* angular.element(document).ready( function () {
         dTable = $('#tbdata')
         dTable.DataTable();
     }); */
}]);

