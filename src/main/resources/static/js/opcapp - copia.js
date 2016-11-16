var app = angular.module("app", ['ngRoute']);

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
		

	});
	
}

function refrescar_tabla(nombre){
		angular.element(document).ready( function () {
        dTable = $(nombre).DataTable();
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
          descripcion: "Produccion"
      }, {
          idTipoInfo: "4",
          descripcion: "Velocidad"
      }, {
          idTipoInfo: "5",
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

function buscar_valor_tag1($http,$scope,baseUrl){
	

	$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
		  $scope.tagDispo = result.data;
		  return $http.get(baseUrl +'/buscarTag?idDispositivo='+$scope.tagDispo.idDispositivo+'&posTag=1')
	}).then(function(result){
		  $scope.selectTag = result.data;
		  
		  return $http.get(baseUrl +'/Tablero_Tag1?idDispositivo='+$scope.tagDispo.idDispositivo+'&posTag=1')
	}).then(function(result){
		  $scope.tag1 = result.data;
		  console.log($scope.Tag1); 
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

function listar_Areas($http,$scope,baseUrl){
	
	$http.get(baseUrl +'/Areas').success(function (data) {
		{ 
		  $scope.areas = data;
        }
		
	});	
}

//Controlador ActividadTag
function listar_Sistemas($http,$scope,baseUrl,idArea){
	
	$http.get(baseUrl +'/Sistemas?parea='+idArea).success(function (data) {
       
		{ 
		  $scope.sistemas = data;
        }
		
	});	
}

function listar_SubSistemas($http,$scope,baseUrl,idArea,idSistema){
	
	$http.get(baseUrl +'/SubSistemas?parea='+idArea+'&psistema='+idSistema).success(function (data) {
       
		{ 
		  $scope.subsistemas = data;
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
    			
    			
    			
    			
    			
    			//
    			//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
 
  
    		});

    	}

    }
    
    
    $scope.removerTag = function(index){
    	
    	

		$scope.tags.splice( index, 1 );		
		//alert($scope.dispositivos[index].idDispositivo);
		$http.get(baseUrl + '/eliminar-tag?id='+$scope.tags[index].idTag+'&statReg=1').
		success(function(data){
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
	
		
//listar_dispositivos($http,$scope,baseUrl);
	
	$scope.activateRealtime = function() {
		
		$scope.tagDispo = "";
		
		
		//alert("Seleccciom :"+$scope.selLinea);
		   //buscarDispoMaquina($http,$scope,baseUrl)
		
		   buscar_valor_tag1($http,$scope,baseUrl);
		   buscar_valor_tag2($http,$scope,baseUrl);
		   buscar_valor_tag3($http,$scope,baseUrl);
		   buscar_valor_tag4($http,$scope,baseUrl);
		   buscar_valor_tag5($http,$scope,baseUrl);
		   buscar_valor_tag6($http,$scope,baseUrl);
		   $timeout($scope.activateRealtime, 1000);	
		   

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
		listar_Areas($http,$scope,baseUrl);
		$scope.indice = index;
		//listar_Sistemas($http,$scope,baseUrl,$scope.cboArea);
		//listar_SubSistemas($http,$scope,baseUrl,$scope.cboArea,$scope.cboSistema);
		
	}
	
	$scope.listaSistemas = function(){
		//alert("Pase");
		listar_Sistemas($http,$scope,baseUrl,$scope.cboArea);
	}

	$scope.listaSubSistemas = function(){
		//alert("subsistema : "+ $scope.cboArea + " " + $scope.cboSistema);
		listar_SubSistemas($http,$scope,baseUrl,$scope.cboArea,$scope.cboSistema);
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
	

	
}]);


app.controller("MainController", ['$scope',function($scope) {

}]);

 /*angular.element(document).ready( function () {
         dTable = $('#tbsucursales')
         dTable.DataTable();
     });*/