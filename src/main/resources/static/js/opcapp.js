var app = angular.module("app",['ngRoute','jlareau.pnotify','chart.js','ngProgress','ui.bootstrap']);

function listar_plcs($http,$scope,baseUrl){
	  
	$http.get(baseUrl + "/listar-dispositivos-server").success(function (data) {
		{ $scope.jsonplcsActivas = data;
		  $scope.plcsActivas = $scope.jsonplcsActivas;
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

function listar_productos($http,$scope,baseUrl){	
	$scope.productos = [];
	$http.get(baseUrl + "/listar-productos").success(function (data) {      
		$scope.productos = data;        
		iniciar_tabla('#tbprod')

	});
	
}

function listar_marcas($http,$scope,baseUrl){
	$http.get(baseUrl + "/listar-marcas").success(function (data) {
		$scope.marcas = data;
		iniciar_tabla('#tbmarca')
        
	});	
}

function listar_maquinas($http,$scope,baseUrl){
	$http.get(baseUrl + "/listar-maquinas").success(function (data) {		
		  $scope.maquinas = data;
		  iniciar_tabla('#tbmaquinas')
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
	
	
    $scope.tipoTurno = [{
        idTipoTurno: "D",
        descTipoTurno: "Diurno"
      }, {
    	  idTipoTurno: "M",
    	  descTipoTurno: "Mixto"
      }, {
    	  idTipoTurno: "N",
    	  descTipoTurno: "Nocturno"
      }];	
    
    $scope.tipoSecuencia = [{
        idTipoSecuencia: "1",
        descTipoSecuencia: "1"
      }, {
    	idTipoSecuencia: "2",
    	descTipoSecuencia: "2"
      }, {
    	idTipoSecuencia: "3",
    	descTipoSecuencia: "3"
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
	
	
	var arra = null;
	

	$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
		  $scope.tagDispo = result.data;
		  return $http.get(baseUrl +'/buscarTag?idDispositivo='+$scope.tagDispo.idDispositivo+'&posTag=1')
	}).then(function(result){
		  $scope.selectTag = result.data;
		  
		  return $http.get(baseUrl +'/verificarArranque?idDispositivo='+$scope.tagDispo.idDispositivo)
	}).then(function(result){
		  $scope.tag1 = result.data;
		  
		  if ($scope.tag1.arrancado == "0"){
			  $scope.descDet = "No";
		  }
			  else{
				  $scope.descDet = "Si";	  
			  
		  }
		  		  
		  //alert($scope.tag1.arrancado);
		 // alert(baseUrl +'/valorDetTag?idTag='+$scope.tag1.tag.idTag+'&valor=0');
		  
/*		  return $http.get(baseUrl +'/valorDetTag?idTag='+$scope.tag1.tag.idTag+'&valor='+$scope.tag1.arrancado)
	}).then(function(result){
		  alert("pase");
		  $scope.descDet = result.data;	
		  
		  //console.log($scope.Tag1); */
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

function buscar_valor_velocidad($http,$scope,baseUrl){
	

	$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
		  $scope.tagDispo3 = result.data;
		  return $http.get(baseUrl +'/velocidadActual?idDispositivo='+$scope.tagDispo3.idDispositivo)
	}).then(function(result){
		  $scope.tag3 = result.data;
		  return $http.get(baseUrl +'/velocidadSeteada?idDispositivo='+$scope.tagDispo3.idDispositivo)
	}).then(function(result){
		  $scope.velSet = result.data;
		  $scope.titVel = ' Velocidad Meta : ';
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
		  
		  return $http.get(baseUrl +'/verificarParada?idDispositivo='+$scope.tagDispo5.idDispositivo)
	}).then(function(result){
		  $scope.tag5 = result.data;
		  console.log($scope.Tag5); 
		  
		  if ($scope.tag5.parado != "0"){
			  $scope.descDet5 = "Si"
		  }else{
			  
			  $scope.descDet5 = "No"
		  }
		  
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

function iniciar_progressBars($http,$scope,baseUrl,ngProgressFactory){
	
	$scope.pg1 = ngProgressFactory.createInstance();
	$scope.pg1.setParent(document.getElementById('parametro_1'));
	$scope.pg1.setColor('#66CDAA');
	$scope.pg1.setAbsolute();
	
	$scope.pg2 = ngProgressFactory.createInstance();
	$scope.pg2.setParent(document.getElementById('parametro_2'));
	$scope.pg2.setColor('#66CDAA');
	$scope.pg2.setAbsolute();
	
	$scope.pg3 = ngProgressFactory.createInstance();
	$scope.pg3.setParent(document.getElementById('parametro_3'));
	$scope.pg3.setColor('#66CDAA');
	$scope.pg3.setAbsolute();
	
	$scope.pg4 = ngProgressFactory.createInstance();
	$scope.pg4.setParent(document.getElementById('parametro_4'));
	$scope.pg4.setColor('#66CDAA');
	$scope.pg4.setAbsolute();
}

function buscar_ultimosTurnos($http,$scope,baseUrl,ngProgressFactory,id){
	
    $http.get(baseUrl + '/EficienciaTurnoDiaMaq?idDispo='+id)
	.success(function(result){
		{
			$scope.prTurnos = result;
			if ($scope.prTurnos.length > 0){
				$scope.titulo1 = $scope.prTurnos[0][3] + " Turno " + $scope.prTurnos[0][2];
				$scope.pg1.set($scope.prTurnos[0][6]);
				$scope.pg1.setHeight('10px');
				$scope.pr1 = $scope.prTurnos[0][6];

				$scope.titulo2 = $scope.prTurnos[1][3] + " Turno " + $scope.prTurnos[1][2];
				$scope.pg2.set($scope.prTurnos[1][6]);
				$scope.pg2.setHeight('10px');
				$scope.pr2 = $scope.prTurnos[1][6];
				
				$scope.titulo3 = $scope.prTurnos[2][3] + " Turno " + $scope.prTurnos[2][2];
				$scope.pg3.set($scope.prTurnos[2][6]);
				$scope.pg3.setHeight('10px');
				$scope.pr3 = $scope.prTurnos[2][6];	
				
				$scope.titulo4 = $scope.prTurnos[3][3] + " Turno " + $scope.prTurnos[3][2];
				$scope.pg4.set($scope.prTurnos[3][6]);
				$scope.pg4.setHeight('10px');
				$scope.pr4 = $scope.prTurnos[3][6];						
				
			}
				
        
		} 
	});	
	
		
}

function iniciarGraficoPrMP($http,$scope,$filter,baseUrl){
    $scope.labels = ['Enero','Febrero'];
    $scope.series = ['Valor Pr'];
    //$scope.data = [7,10];
    
    $scope.labels = [$filter('date')(new Date()-6, 'yyyy-MM-dd'), $filter('date')(new Date()-5, 'yyyy-MM-dd'),
                     $filter('date')((new Date()-4), 'yyyy-MM-dd'), $filter('date')(new Date()-3, 'yyyy-MM-dd'),
                    		 $filter('date')(new Date()-2, 'yyyy-MM-dd'), $filter('date')(new Date()-1, 'yyyy-MM-dd'), $filter('date')(new Date(), 'yyyy-MM-dd')];
    $scope.series = ['Valor Pr'];
    $scope.data = [
      [0, 0, 0, 0, 0, 0, 0]
    ];  
    
    $scope.onClick = function (points, evt) {
        console.log(points, evt);
      };
      
      $scope.onHover = function (points) {
        if (points.length > 0) {
          console.log('Point', points[0].value);
        } else {
          console.log('No point');
        }
      };
      $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }];

      $scope.options = {
        scales: {
          yAxes: [
            {
              id: 'y-axis-1',
              type: 'linear',
              display: true,
              position: 'left'
            }
          ]
        }
      };
}

function iniciarGraficos123PrMP($http,$scope,$filter,baseUrl){
	
	  $scope.labelsGr1MP = ['Planeados','No Planeados'];
	  $scope.dataGr1MP = [0,0];
	  
	  $scope.labelsGr2MP = ['Planeados','No Planeados'];
	  $scope.dataGr2MP = [0,0];
	  
	  $scope.labelsGr3MP = ['Lunes','Martes','Miercoles','Jueves','Viernes'];
	  $scope.dataGr3MP = [0,0,0,0];
	  $scope.seriesGr3MP = ['Planeados'];
}

function graficoPrMP($http,$scope,baseUrl,notificationService,id){
      
			$scope.series = ['Valor Pr'];
			
		   // $scope.labels = ['Lunes','Martes','Miercoles','Jueves','Viernes','Sabado'];
			
            $http.get(baseUrl + '/graficoEficienciaMaquinaDia?idDispo='+id)
			.success(function(result){
				{
				
				$scope.graficoPrMP = result;
				$scope.labels = [];
				$scope.data = [];
				$scope.dataAux = [];
				
		        for (var i = 0; i < $scope.graficoPrMP.length; i++) {
		                
		               // notificationService.error($scope.graficoPrMP[0][2]);
		                $scope.labels.push($scope.graficoPrMP[i][1]);
		                $scope.dataAux.push($scope.graficoPrMP[i][4]);
		                
		         
		        }
		        
		        $scope.data.push($scope.dataAux);
		        
				} 
			});

            $scope.onClick = function (points, evt) {
                console.log(points, evt);
              };
              
              $scope.onHover = function (points) {
                if (points.length > 0) {
                  console.log('Point', points[0].value);
                } else {
                  console.log('No point');
                }
              };
              $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }];

              $scope.options = {
                scales: {
                  yAxes: [
                    {
                      id: 'y-axis-1',
                      type: 'linear',
                      display: true,
                      position: 'left'
                    }
                  ]
                }
              };
   	
	
}



function grafico1MP($http,$scope,baseUrl,id,idSucursal,idTurno,inicio,fin,t){
	
	
  $scope.labelsGr1MP = [];
  $scope.dataGr1MP = [];
	
  if (t == 60){
	
   $http.get(baseUrl + '/graficosTipoParadasMp?filtro=Dispositivo&grupo=Turno&idSucursal='+idSucursal+'&idDispositivo='+id+'&idTurno='+idTurno+'&inicio='+inicio+'&fin='+fin)
	.success(function(result){
		$scope.grParadas1MP = result;
		$scope.labelsGr1MP = [];
		$scope.dataGr1MP = [];
		if ($scope.grParadas1MP != null){
	        for (var i = 0; i < $scope.grParadas1MP.length; i++) {
                
	               // notificationService.error($scope.graficoPrMP[0][2]);
	                $scope.labelsGr1MP.push($scope.grParadas1MP[i][1]);
	                $scope.dataGr1MP.push($scope.grParadas1MP[i][0]);
	                
	         
	        }	
		}else
			{
		}

	});  
    
  }   
}

function grafico2MP($http,$scope,baseUrl,id,idSucursal,idTurno,inicio,fin,t){
	  
	//$scope.labelsGr2MP = ["Dosificador", "Cremallera","Dispensador","Tira","Carril"];
	//$scope.dataGr2MP = [300, 500,150,200,340];
	
	$scope.labelsGr2MP = [];
	$scope.dataGr2MP = [];

	 // if (t == 60){
			
		   $http.get(baseUrl + '/graficosCausaFallaParadasMp?filtro=Dispositivo&grupo=Turno&idSucursal='+idSucursal+'&idDispositivo='+id+'&idTurno='+idTurno+'&inicio='+inicio+'&fin='+fin)
			.success(function(result){
				$scope.grParadas2MP = result;
				$scope.labelsGr2MP = [];
				$scope.dataGr2MP = [];
				if ($scope.grParadas2MP != null){
			        for (var i = 0; i < $scope.grParadas2MP.length; i++) {
		                
			               // notificationService.error($scope.graficoPrMP[0][2]);
			                $scope.labelsGr2MP.push($scope.grParadas2MP[i][1]);
			                $scope.dataGr2MP.push($scope.grParadas2MP[i][0]);
			                
			         
			        }	
				}else
					{
				}

			});  
		    
	//	  } 	
	
}

function grafico3MP($http,$scope,baseUrl,id,idSucursal,idTurno,inicio,fin,t){
	  

	/*
	  $scope.labelsGr3MP= ['Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado', 'Domingo'];
	  $scope.seriesGr3MP = ['Series A', 'Series B'];

	  $scope.dataGr3MP = [
	    [65, 59, 80, 81, 56, 55, 40],
	    [28, 48, 40, 19, 86, 27, 90]
	  ];  */
	  
		
         $http.get(baseUrl + '/graficosTipoParadasSemanalMp?filtro=Dispositivo&grupo=Dia&idSucursal='+idSucursal+'&idDispositivo='+id+'&idTurno='+idTurno+'&inicio='+inicio+'&fin='+fin)
			.success(function(result){
				{
				
              	
				$scope.grafico3MP = result;
				
				//Si hay mas de 2 tipos de Paradas se divide entre el nro de tipos
				var mitad = (($scope.grafico3MP.length))/2;
				var label = mitad;
				
				$scope.labelsGr3MP = [];
				$scope.dataGr3MP = [];
				$scope.seriesGr3MP =[];
				$scope.dataAux = [];
				
		        for (var i = 0; i < $scope.grafico3MP.length; i++) {
		                
		               // notificationService.error($scope.graficoPrMP[0][2]);
		        	    
		                
		                $scope.dataAux.push($scope.grafico3MP[i][0]);
		                
		                if ((i == 0) || (i == mitad)){
		                	$scope.seriesGr3MP.push($scope.grafico3MP[i][1]);
		                	
		                }
		                
		                if (i == (mitad-1)){
		                	$scope.dataGr3MP.push($scope.dataAux);
		                	$scope.dataAux = [];
		                	mitad = mitad + mitad;
		                }
		                
		                if (i <= (label -1))	{
		                	$scope.labelsGr3MP.push($scope.grafico3MP[i][4]);
		                }
		                
		         
		        }
		        
		        $scope.dataGr3MP.push($scope.dataAux);
		        
				} 
			});  
	  
}

function graficoPrGeneral($http,$scope,baseUrl,notificationService){
    
	$scope.seriesGen = ['Valor Pr'];
	
   // $scope.labels = ['Lunes','Martes','Miercoles','Jueves','Viernes','Sabado'];
	
    $http.get(baseUrl + '/graficoEficienciaGeneral')
	.success(function(result){
		{
		
		$scope.graficoPrGen = result;
		$scope.labelsGen = [];
		$scope.dataGen = [];
		$scope.dataAuxGen = [];
		
        for (var i = 0; i < $scope.graficoPrGen.length; i++) {
                
               // notificationService.error($scope.graficoPrMP[0][2]);
                $scope.labelsGen.push($scope.graficoPrGen[i][4]);
                $scope.dataAuxGen.push($scope.graficoPrGen[i][3]);
                
         
        }
        
        $scope.dataGen.push($scope.dataAuxGen);
        
		} 
	});

    $scope.onClick = function (points, evt) {
        console.log(points, evt);
      };
      
      $scope.onHover = function (points) {
        if (points.length > 0) {
          console.log('Point', points[0].value);
        } else {
          console.log('No point');
        }
      };
      $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }];

      $scope.options = {
        scales: {
          yAxes: [
            {
              id: 'y-axis-1',
              type: 'linear',
              display: true,
              position: 'left'
            }
          ]
        }
      };


}


function eficienciaTurno($http,$scope,baseUrl){
	

	$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
		  $scope.prDispo = result.data;
		  return $http.get(baseUrl +'/EficienciaActual?idDispositivo='+$scope.prDispo.idDispositivo+'&idTurno='+$scope.turnoActual.idTurno)
	}).then(function(result){
		  $scope.eficiencia = result.data;
	})	  

}

function indicadoresGenerales($http,$scope,baseUrl){
	
	$scope.ind1 = 0;
	$scope.ind2 = 0;
	$scope.ind3 = 0;
	$scope.ind4 = 0;

	$http.get(baseUrl +'/eficienciaGeneral').success(function (data) {
		{ 
		  $scope.ind1 = "%" + data;
        }
	});
	
	$http.get(baseUrl +'/eficienciaGeneralDiaAnterior').success(function (data) {
		{ 
		  $scope.ind2 = "%" + data;
        }
	});	
	
	$http.get(baseUrl +'/eficienciaGeneralTurnoAnterior').success(function (data) {
		{ 
		  $scope.ind3 = "%" + data;
        }
	});	
	
	$http.get(baseUrl +'/eficienciaGeneralTurnoActual').success(function (data) {
		{ 
		  $scope.ind4 = "%" + data;
        }
	});		
	
}

function topMaquinasProduccion($http,$scope,baseUrl){
	
	$scope.topMaquinas = []

	$http.get(baseUrl +'/topProduccion').success(function (data) {
		{ 
			$scope.topMaquinas = data;
			
			if ($scope.topMaquinas.length > 0){
				
				$scope.desc1 = $scope.topMaquinas[0][6] ;
				$scope.tot1 =  $scope.topMaquinas[0][1];
				$scope.tit1 = ". Unidades Producidas"
				
				if ($scope.topMaquinas.length > 1){
					$scope.desc2 = $scope.topMaquinas[1][6];
					$scope.tot2 =  $scope.topMaquinas[1][1];	
					$scope.tit2 = ". Unidades Producidas"
				}
				if ($scope.topMaquinas.length > 2){
					$scope.desc3 = $scope.topMaquinas[2][6];
					$scope.tot3 =  $scope.topMaquinas[2][1];	
					$scope.tit3 = ". Unidades Producidas"
				}	
				if ($scope.topMaquinas.length > 3){
					$scope.desc4 = $scope.topMaquinas[3][6];
					$scope.tot4 =  $scope.topMaquinas[3][1];
					$scope.tit4 = ". Unidades Producidas"
				}		
				
				
			}			
        }
	});
	

}

function graficoProduccionMensual($http,$scope,baseUrl,notificationService){
    
	$scope.seriestotMens = ['Total'];
	
   // $scope.labels = ['Lunes','Martes','Miercoles','Jueves','Viernes','Sabado'];
	
    $http.get(baseUrl + '/produccionMensual')
	.success(function(result){
		{
		
		$scope.graficototMens = result;
		$scope.labelstotMens = [];
		$scope.datatotMens = [];
		$scope.dataAuxtotMens = [];
		
        for (var i = 0; i < $scope.graficototMens.length; i++) {
                
               // notificationService.error($scope.graficoPrMP[0][2]);
                $scope.labelstotMens.push($scope.graficototMens[i][1]);
                $scope.dataAuxtotMens.push($scope.graficototMens[i][0]);
                
         
        }
        
        $scope.datatotMens.push($scope.dataAuxtotMens);
        
		} 
	});

    $scope.onClick = function (points, evt) {
        console.log(points, evt);
      };
      
      $scope.onHover = function (points) {
        if (points.length > 0) {
          console.log('Point', points[0].value);
        } else {
          console.log('No point');
        }
      };
      $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }];

      $scope.options = {
        scales: {
          yAxes: [
            {
              id: 'y-axis-1',
              type: 'linear',
              display: true,
              position: 'left'
            }
          ]
        }
      };


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
	
	$http.get(baseUrl +'/CausaFalla?parea='+idArea+'&psistema='+idSistema+'&psubSistema='+idSubSistema+'&idMaquina='+idMaquina).success(function (data) {
       
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
    var theme = {
            color: [
                '#26B99A', '#34495E', '#BDC3C7', '#3498DB',
                '#9B59B6', '#8abb6f', '#759c6a', '#bfd3b7'
            ],

            title: {
                itemGap: 8,
                textStyle: {
                    fontWeight: 'normal',
                    color: '#408829'
                }
            },

            dataRange: {
                color: ['#1f610a', '#97b58d']
            },

            toolbox: {
                color: ['#408829', '#408829', '#408829', '#408829']
            },

            tooltip: {
                backgroundColor: 'rgba(0,0,0,0.5)',
                axisPointer: {
                    type: 'line',
                    lineStyle: {
                        color: '#408829',
                        type: 'dashed'
                    },
                    crossStyle: {
                        color: '#408829'
                    },
                    shadowStyle: {
                        color: 'rgba(200,200,200,0.3)'
                    }
                }
            },

            dataZoom: {
                dataBackgroundColor: '#eee',
                fillerColor: 'rgba(64,136,41,0.2)',
                handleColor: '#408829'
            },
            grid: {
                borderWidth: 0
            },

            categoryAxis: {
                axisLine: {
                    lineStyle: {
                        color: '#408829'
                    }
                },
                splitLine: {
                    lineStyle: {
                        color: ['#eee']
                    }
                }
            },

            valueAxis: {
                axisLine: {
                    lineStyle: {
                        color: '#408829'
                    }
                },
                splitArea: {
                    show: true,
                    areaStyle: {
                        color: ['rgba(250,250,250,0.1)', 'rgba(200,200,200,0.1)']
                    }
                },
                splitLine: {
                    lineStyle: {
                        color: ['#eee']
                    }
                }
            },
            timeline: {
                lineStyle: {
                    color: '#408829'
                },
                controlStyle: {
                    normal: {color: '#408829'},
                    emphasis: {color: '#408829'}
                }
            },

            k: {
                itemStyle: {
                    normal: {
                        color: '#68a54a',
                        color0: '#a9cba2',
                        lineStyle: {
                            width: 1,
                            color: '#408829',
                            color0: '#86b379'
                        }
                    }
                }
            },
            map: {
                itemStyle: {
                    normal: {
                        areaStyle: {
                            color: '#ddd'
                        },
                        label: {
                            textStyle: {
                                color: '#c12e34'
                            }
                        }
                    },
                    emphasis: {
                        areaStyle: {
                            color: '#99d2dd'
                        },
                        label: {
                            textStyle: {
                                color: '#c12e34'
                            }
                        }
                    }
                }
            },
            force: {
                itemStyle: {
                    normal: {
                        linkStyle: {
                            strokeColor: '#408829'
                        }
                    }
                }
            },
            chord: {
                padding: 4,
                itemStyle: {
                    normal: {
                        lineStyle: {
                            width: 1,
                            color: 'rgba(128, 128, 128, 0.5)'
                        },
                        chordStyle: {
                            lineStyle: {
                                width: 1,
                                color: 'rgba(128, 128, 128, 0.5)'
                            }
                        }
                    },
                    emphasis: {
                        lineStyle: {
                            width: 1,
                            color: 'rgba(128, 128, 128, 0.5)'
                        },
                        chordStyle: {
                            lineStyle: {
                                width: 1,
                                color: 'rgba(128, 128, 128, 0.5)'
                            }
                        }
                    }
                }
            },
            gauge: {
                startAngle: 225,
                endAngle: -45,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: [[0.2, '#86b379'], [0.8, '#68a54a'], [1, '#408829']],
                        width: 8
                    }
                },
                axisTick: {
                    splitNumber: 10,
                    length: 12,
                    lineStyle: {
                        color: 'auto'
                    }
                },
                axisLabel: {
                    textStyle: {
                        color: 'auto'
                    }
                },
                splitLine: {
                    length: 18,
                    lineStyle: {
                        color: 'auto'
                    }
                },
                pointer: {
                    length: '90%',
                    color: 'auto'
                },
                title: {
                    textStyle: {
                        color: '#333'
                    }
                },
                detail: {
                    textStyle: {
                        color: 'auto'
                    }
                }
            },
            textStyle: {
                fontFamily: 'Arial, Verdana, sans-serif'
            }
        };
        
        var echartPie = echarts.init(document.getElementById('echart_pie'), theme);

        echartPie.setOption({
          tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          legend: {
            x: 'center',
            y: 'bottom',
            data: ['Direct Access', 'E-mail Marketing', 'Union Ad', 'Video Ads', 'Search Engine']
          },
          toolbox: {
            show: true,
            feature: {
              magicType: {
                show: true,
                type: ['pie', 'funnel'],
                option: {
                  funnel: {
                    x: '25%',
                    width: '50%',
                    funnelAlign: 'left',
                    max: 1548
                  }
                }
              },
              restore: {
                show: true,
                title: "Restore"
              },
              saveAsImage: {
                show: true,
                title: "Save Image"
              }
            }
          },
          calculable: true,
          series: [{
            name: '访问来源',
            type: 'pie',
            radius: '55%',
            center: ['50%', '48%'],
            data: [{
              value: 335,
              name: 'Direct Access'
            }, {
              value: 310,
              name: 'E-mail Marketing'
            }, {
              value: 234,
              name: 'Union Ad'
            }, {
              value: 135,
              name: 'Video Ads'
            }, {
              value: 1548,
              name: 'Search Engine'
            }]
          }]
        });

        var dataStyle = {
          normal: {
            label: {
              show: false
            },
            labelLine: {
              show: false
            }
          }
        };

        var placeHolderStyle = {
          normal: {
            color: 'rgba(0,0,0,0)',
            label: {
              show: false
            },
            labelLine: {
              show: false
            }
          },
          emphasis: {
            color: 'rgba(0,0,0,0)'
          }
        };	
        
    });        
	
    angular.element(document).ready(function() {
        //define chart clolors ( you maybe add more colors if you want or flot will add it automatic )
        var chartColours = ['#96CA59', '#3F97EB', '#72c380', '#6f7a8a', '#f7cb38', '#5a8022', '#2c7282'];

        //generate random number for charts
        randNum = function() {
          return (Math.floor(Math.random() * (1 + 40 - 20))) + 20;
        };

        var d1 = [];
        //var d2 = [];

        //here we generate data for chart
        for (var i = 0; i < 30; i++) {
          d1.push([new Date(Date.today().add(i).days()).getTime(), randNum() + i + i + 10]);
          //    d2.push([new Date(Date.today().add(i).days()).getTime(), randNum()]);
        }

        var chartMinDate = d1[0][0]; //first day
        var chartMaxDate = d1[20][0]; //last day

        var tickSize = [1, "day"];
        var tformat = "%d/%m/%y";

        //graph options
        var options = {
          grid: {
            show: true,
            aboveData: true,
            color: "#3f3f3f",
            labelMargin: 10,
            axisMargin: 0,
            borderWidth: 0,
            borderColor: null,
            minBorderMargin: 5,
            clickable: true,
            hoverable: true,
            autoHighlight: true,
            mouseActiveRadius: 100
          },
          series: {
            lines: {
              show: true,
              fill: true,
              lineWidth: 2,
              steps: false
            },
            points: {
              show: true,
              radius: 4.5,
              symbol: "circle",
              lineWidth: 3.0
            }
          },
          legend: {
            position: "ne",
            margin: [0, -25],
            noColumns: 0,
            labelBoxBorderColor: null,
            labelFormatter: function(label, series) {
              // just add some space to labes
              return label + '&nbsp;&nbsp;';
            },
            width: 40,
            height: 1
          },
          colors: chartColours,
          shadowSize: 0,
          tooltip: true, //activate tooltip
          tooltipOpts: {
            content: "%s: %y.0",
            xDateFormat: "%d/%m",
            shifts: {
              x: -30,
              y: -50
            },
            defaultTheme: false
          },
          yaxis: {
            min: 0
          },
          xaxis: {
            mode: "time",
            minTickSize: tickSize,
            timeformat: tformat,
            min: chartMinDate,
            max: chartMaxDate
          }
        };
        var plot = $.plot($("#placeholder33x"), [{
          label: "Valor PR",
          data: d1,
          lines: {
            fillColor: "rgba(150, 202, 89, 0.12)"
          }, //#96CA59 rgba(150, 202, 89, 0.42)
          points: {
            fillColor: "#fff"
          }
        }], options);
      });
   
	
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
	    templateUrl: "modo_monitoreo.html",
	    controller: "MonitoreoController"
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
	    templateUrl: "modo_monitoreo.html",
	    controller: "MonitoreoController"
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
	  $routeProvider.when('/productos', {
		    templateUrl: "productos.html",
		    controller: "ListadoProductosController"
		  });
	  $routeProvider.when('/marcas', {
		    templateUrl: "marcas.html",
		    controller: "ListadoMarcasController"
		  });
	  $routeProvider.when('/maquinas', {
		    templateUrl: "maquinas.html",
		    controller: "ListadoMaquinasController"
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



app.config(['notificationServiceProvider', function(notificationServiceProvider) {

	notificationServiceProvider

		.setDefaults({
            styling: 'bootstrap3',
			delay: 2500,
			buttons: {
				closer: false,
				closer_hover: false,
				sticker: false,
				sticker_hover: false
			},
			type: 'error'
		})

		// Configure a stack named 'bottom_right' that append a call 'stack-bottomright'
		.setStack('bottom_right', 'stack-bottomright', {
			dir1: 'up',
			dir2: 'left',
			firstpos1: 25,
			firstpos2: 25
		})

		// Configure a stack named 'top_left' that append a call 'stack-topleft'
		.setStack('top_left', 'stack-topleft', {
			dir1: 'down',
			dir2: 'right',
			push: 'top'
		})

	;

}]);

app.constant("baseUrl", ".");
app.config(['baseUrl', 'remoteResourceProvider',
  function(baseUrl, remoteResourceProvider) {
    remoteResourceProvider.setBaseUrl(baseUrl);
  }
]);


app.config(['ChartJsProvider', function (ChartJsProvider) {
    // Configure all charts
    ChartJsProvider.setOptions({
      chartColors: ['#96CA59', '#3F97EB', '#72c380', '#6f7a8a', '#f7cb38', '#5a8022', '#2c7282'],
      responsive: true
    });
    // Configure all line charts
    ChartJsProvider.setOptions('line', {
      showLines: true
    });
    
    ChartJsProvider.setOptions('doughnut', {
        legend: true
      });    
  }]);


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
		  //alert(input);	 
		  if(input == null){ 
			  
			  return ""; 
			  
		  } 
		 
		  var _date = $filter('date')(new Date(input), 'HH:mm:ss');
		  alert(_date);
		  return _date;

		 };
		});


app.filter('fecActual',['$filter',  function($filter) {
    return function() {
        return $filter('date')(new Date(), 'yyyy-MM-dd');
    	//return $filter('date')(new Date(), 'HH:mm:ss');
    };
}])

//----------------------------------- Sucursales ----------------------------------------------------------//

app.controller("ListadoSucursalesController", ['$scope', 'remoteResource',function($scope, remoteResource) {
    $scope.sucursales = [];

    remoteResource.list(function(sucursales) {
      $scope.sucursales = sucursales;
    }, function(data, status) {
      alert("Ha fallado la petición. Estado HTTP:" + status);
    });

}]);

//----------------------------------- Productos ----------------------------------------------------------//


app.controller("ListadoProductosController", ['$scope','$http','$filter','$uibModal',function($scope, $http,$filter,$uibModal) {
	$scope.visible = "N";
	$scope.index = 0;
	$http.defaults.headers.post["Content-Type"] = "application/json";
	var baseUrl = ".";
	listar_productos($http, $scope, baseUrl);
	
	$scope.insertarProducto = function(){   	
    	
    		$http.post(baseUrl + '/nuevo-producto?desc='+$scope.txtDesc).
    		success(function(response){
    			$('#crear').modal('hide');
    			$http.get(baseUrl + "/listar-productos").success(function (data) {      
					$scope.productos = data;
    			});
    			$scope.txtDesc=null;
  
    		});

    	

    }
	
	$scope.borrar = function (index) {

	    $scope.index = index;
	    
	    $scope.modalInstance = $uibModal.open({
	    	ariaLabelledBy: 'modal-title',
	        ariaDescribedBy: 'modal-body',
	    	templateUrl: 'borrar.html',
	        scope: $scope,
	        size: 'sm'	        
	    });
	};

	$scope.editar = function (index) {
		$scope.indProd = index;
		$http.get(baseUrl + '/consultar-producto?idProducto='+$scope.productos[$scope.indProd].idProducto).
		success(function(data){
				$scope.modifProd = data;
				$scope.txtDescProducto = $scope.modifProd.descProducto;
				$scope.txtEstatus = $scope.modifProd.statProducto
			});
	};
	
	$scope.cancel = function () {		
		$scope.modalInstance.dismiss('cancel');
	};
	
	
	$scope.removerProducto = function(index){    	
			
		
		$http.put(baseUrl + '/eliminar-producto?idProducto='+$scope.productos[index].idProducto+'&statProducto=1').
		success(function(response){
				$http.get(baseUrl + "/listar-productos").success(function (data) {      
				$scope.productos = data; 

			});

			});
		$scope.modalInstance.close();
		
	}
	
		$scope.editarProducto = function(index){
				$http.put(baseUrl + '/editar-producto?idProducto='+$scope.productos[$scope.index].idProducto+'&descProducto='+$scope.txtDescProducto
						+'&statProducto='+$scope.txtEstatus).
				success(function(response){
						$('#editar').modal('hide');
						$http.get(baseUrl + "/listar-productos").success(function (data) {      
							$scope.productos = data;
							
					});
		
					});
				
			}
		
		
	
	
	
}]);



//----------------------------------- Marcas ----------------------------------------------------------//


app.controller("ListadoMarcasController", ['$scope','$http','$filter',function($scope, $http,$filter) {
	var baseUrl = ".";
	listar_marcas($http, $scope, baseUrl);
	
	$scope.insertarMarca = function(){	
    	
    	if ($scope.txtDesc==""){
    		alert("Campo en blanco");
    	}
    	else{
    		$http.get(baseUrl + '/nueva-marca?desc='+$scope.txtDesc).
    		success(function(data){
    			alert("Marca agregada");
    			//
    			//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
    			listar_marcas($http,$scope,baseUrl);
    			//refrescar_tabla('#tbdata');
    			$scope.txtDesc=null;
  
    		});

    	}

    }
	
	$scope.removerMarca = function(index){    	
		 
		$scope.marcas.splice(index, 1 );		
		//alert($scope.dispositivos[index].idDispositivo);
		$http.get(baseUrl + '/eliminar-marca?id='+$scope.marcas[index].idMarca+'&statReg=1').
		success(function(data){
			alert("Producto Eliminado");
			//
			//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
			listar_marcas($http,$scope,baseUrl);
			//refrescar_tabla('#tbdata');

		});		
	}
}]);

//-------------------------------------------------------------------------------------------------------//
//----------------------------------- MAQUINAS ----------------------------------------------------------//
//-------------------------------------------------------------------------------------------------------//


app.controller("ListadoMaquinasController", ['$scope','$http','$filter',function($scope, $http,$filter) {
	var baseUrl = ".";
	listar_maquinas($http, $scope, baseUrl);
	listar_productos($http,$scope,baseUrl);
	
	$scope.insertarMaquina = function(){	
    	
    	if ($scope.txtNombre==""){
    		alert("Campo en blanco");
    	}
    	else{
    		$http.get(baseUrl + '/nueva-maquina?nombre='+$scope.txtNombre+'&idProducto='+$scope.setProducto+'&velocidad='+$scope.txtVelocidad+'&tiempoHambre='+$scope.txtTiempoHambre).
    		success(function(data){
    			alert("Maquina agregada");
    			//
    			//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
    			listar_maquinas($http,$scope,baseUrl);
    			//refrescar_tabla('#tbdata');
    			$scope.txtDesc=null;
    			$scope.setProducto = null;
  
    		});

    	}

    }
	
	$scope.removerMaquina = function(index){    	
		 
		$scope.marcas.splice(index, 1 );		
		//alert($scope.dispositivos[index].idDispositivo);
		$http.get(baseUrl + '/eliminar-maquina?id='+$scope.marcas[index].idMarca+'&statReg=1').
		success(function(data){
			alert("Maquina Eliminada");
			//
			//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
			listar_maquinas($http,$scope,baseUrl);
			//refrescar_tabla('#tbdata');

		});		
	}

	$scope.modificarMaquina = function(index){    	
		 
		$scope.txtNombre = null;
		$scope.setProducto = null;
		listar_productos($http,$scope,baseUrl);
		$scope.indMaq = index;
		$http.get(baseUrl + '/consultar-maquina?idMaquina='+$scope.maquinas[$scope.indMaq].idMaquina).
		success(function(data){
			//alert("Maquina Eliminada");
			//
			$scope.maquinaModif = data;
			$scope.txtNombre = data.nombre;
			$scope.txtVelocidad = data.velocidad;
			$scope.txtTiempoHambre = data.tiempoHambre;
			$scope.setProducto = data.producto.idProducto;

		});	
	}	
	
	
	$scope.actualizarMaquina = function(){    	
		 
	
		$http.get(baseUrl + '/actualizar-ProductoMaquina?nombre='+$scope.txtNombre+'&idMaquina='+$scope.maquinas[$scope.indMaq].idMaquina+'&idProducto='+$scope.setProducto+'&velocidad='+$scope.txtVelocidad+'&tiempoHambre='+$scope.txtTiempoHambre).
		success(function(data){
			alert("Maquina Actualizada");
			listar_maquinas($http,$scope,baseUrl);

		});	
	}		

	
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

app.controller("TagsController", ['$scope','$http','notificationService',function($scope, $http,notificationService) {
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
    	
		notificationService.notify(
				{
					title: 'Solicitud de Confirmación',
					text: 'Esta seguro de dejar de Monitorear la Tag?',
					hide: false,
					confirm: {
					confirm: true
				},
				buttons: {
					closer: false,
					sticker: false
				},
				history: {
					history: false
				}
			}).get().on('pnotify.confirm', function() {
				
				$http.get(baseUrl + '/eliminar-tag?idTag='+$scope.tagsdispo[index].idTag+'&stat=1').
				success(function(data){
					$scope.tagsdispo.splice( index, 1 );
					notificationService.success('Tag ha sido Eliminada Satisfactoriamente')
					//alert("Tag Eliminada");
					//
					//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
					listar_dispositivos($http,$scope,baseUrl);
					//refrescar_tabla('#tbdata');

				});	
				
			}).on('pnotify.cancel', function() {
				//alert('Oh ok. Chicken, I see.');
			});
    	
 		
		//alert($scope.dispositivos[index].idDispositivo);
	
	}
     
    
}]);

//-------------------------------------------------------------------------------------------------------------//
//----------------------------------- MODO PRODUCCION ---------------------------------------------------------//
//-------------------------------------------------------------------------------------------------------------//

app.controller("TableroController", ['$scope','$http','$timeout','$rootScope','notificationService','$filter','ngProgressFactory',function($scope, $http,$timeout,$rootScope,notificationService,$filter,ngProgressFactory) {
	var baseUrl = ".";
	listar_maquinas($http,$scope,baseUrl);
	var idSucursal = 1;
	inicio = '01/01/1970';
	fin = '01/01/1970';
	iniciar_elementos();
	limpiar_tags($http,$scope,baseUrl);
	buscar_turno_actual($http,$scope,baseUrl);
	iniciarGraficoPrMP($http,$scope,$filter,baseUrl);
	//iniciarGraficos123PrMP($http,$scope,$filter,baseUrl);
	iniciar_progressBars($http,$scope,baseUrl,ngProgressFactory);
	$scope.temporizador = 59;
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

		//$scope.progressbar.start();
		
		$scope.tagDispo = [];
		$scope.tagDispo2 = [];
		$scope.conectado = null;
		$rootScope.selLineaG = $scope.selLinea;
		buscar_turno_actual($http,$scope,baseUrl);
		$scope.temporizador = $scope.temporizador + 1;
		

		
		//alert("Seleccciom :"+$scope.selLinea);
		   //buscarDispoMaquina($http,$scope,baseUrl)

		$http.get(baseUrl + "/buscarPorMaquina?idMaquina="+$scope.selLinea).then(function (result) {
			  $scope.tagDispo2 = result.data;
			  
			  if ($scope.tagDispo2.idDispositivo == null ){
				   limpiar_tags($http,$scope,baseUrl);
				   //alert($scope.tag1Vacio);
			  }else{
				  graficoPrMP($http,$scope,baseUrl,notificationService,$scope.tagDispo2.idDispositivo);
				  buscar_ultimosTurnos($http,$scope,baseUrl,ngProgressFactory,$scope.tagDispo2.idDispositivo);
				  grafico1MP($http,$scope,baseUrl,$scope.tagDispo2.idDispositivo,idSucursal,$scope.turnoActual.idTurno,inicio,fin,$scope.temporizador);
				  grafico2MP($http,$scope,baseUrl,$scope.tagDispo2.idDispositivo,idSucursal,$scope.turnoActual.idTurno,inicio,fin,$scope.temporizador);
				  grafico3MP($http,$scope,baseUrl,$scope.tagDispo2.idDispositivo,idSucursal,$scope.turnoActual.idTurno,inicio,fin,$scope.temporizador);
			  }
			  
			  
			  return $http.get(baseUrl +'/verificar-conexion?id='+$scope.tagDispo2.idDispositivo)
		}).then(function(result){
			  $scope.conectado = result.data;
			  //impiar_tags($http,$scope,baseUrl);
				if ($scope.conectado == "1"){
					
					
					   buscar_valor_tag1($http,$scope,baseUrl);
					   //buscar_valor_tag2($http,$scope,baseUrl);
					   //buscar_valor_tag3($http,$scope,baseUrl);
					   buscar_valor_velocidad($http,$scope,baseUrl);
					   buscar_valor_tag4($http,$scope,baseUrl);
					   buscar_valor_tag5($http,$scope,baseUrl);
					   //buscar_valor_tag6($http,$scope,baseUrl); 
					   eficienciaTurno($http,$scope,baseUrl);
						if (($scope.temporizador) == 60){
							$scope.temporizador = 0;
						}

					   $timeout($scope.activateRealtime, 1000);	
					
					}
					else if ($scope.conectado == "0"){
						//alert("No hay hay conexion con la Linea");
						notificationService.error('No hay hay conexion con la Linea');
						limpiar_tags($http,$scope,baseUrl);
						
					}
					else if ($scope.conectado == "0"){
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
//-----------------------------------------  MODO MONITOREO -----------------------------------------------------------//
//-------------------------------------------------------------------------------------------------------------//

app.controller("MonitoreoController", ['$scope','$http','$timeout','$rootScope','notificationService','$filter','ngProgressFactory',function($scope, $http,$timeout,$rootScope,notificationService,$filter,ngProgressFactory) {
	
	var baseUrl = ".";
	indicadoresGenerales($http,$scope,baseUrl);
	graficoPrGeneral($http,$scope,baseUrl,notificationService);
	topMaquinasProduccion($http,$scope,baseUrl);
	graficoProduccionMensual($http,$scope,baseUrl,notificationService);
	
	
}]);

//-------------------------------------------------------------------------------------------------------------//
//-----------------------------------------  TURNOS -----------------------------------------------------------//
//-------------------------------------------------------------------------------------------------------------//

app.controller("TurnosController", ['$scope','$http','$filter',function($scope, $http,$filter) {
	var baseUrl = ".";
    var idSucursal = 1;	
    buscarSucursal($http,$scope,baseUrl,idSucursal);
    cargarValores($http,$scope,baseUrl);
    
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
	    		//var inicio = $filter('time')($scope.txtInicio+':00');
	    		var inicio = $filter('time')($scope.txtInicio);
	    		var fin = $filter('time')($scope.txtFin);
	    		//$scope.txtInicio+':00'+  asi se pasaba el parametyro
	    		//$scope.txtFin+':00'  
		    		$http.get(baseUrl + '/nuevo-turno?desc='+$scope.txtDesc+'&inicio='+inicio+'&fin='+fin+'&idSucursal='+idSucursal+'&tipoTurno='+$scope.cboTipoTurno+'&secuencia='+$scope.txtOrden).
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

app.controller("FallasController", ['$scope','$http','$timeout','notificationService',function($scope, $http,$timeout,notificationService) {
	
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
			listar_Areas($http,$scope,baseUrl,$scope.selMaq);
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
			//$scope.cboArea = $scope.parada.area.descArea;
			//$scope.cboSistema = $scope.parada.area.descSistema;
			//$scope.cboSubSistema = $scope.parada.area.descSubSistema;
			
			
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
		listar_CausaFallas($http,$scope,baseUrl,$scope.cboArea,$scope.cboSistema,$scope.cboSubSistema,$scope.selMaq);
	}		
	
	$scope.actualizarParada = function(index){
		//$scope.indice = index;
		alert("Indice :" + $scope.indice);
		 
		
		$http.get(baseUrl + '/actParada?idActividad='+$scope.fallasAct[$scope.indice].idActividadTag+'&idArea='+$scope.cboArea+'&idSistema='+$scope.cboSistema+
				  '&idSubsistema='+$scope.cboSubSistema+'&idCausaFalla='+$scope.cboCausaFalla+'&comen='+$scope.txtComen).
		success(function(data){
			//alert("Parada  Actualizada con Exito");
			notificationService.success('Parada  Actualizada con Exito');
			$scope.resultado = data;
			//
			//$scope.dispositivos.push({ 'descripcion':$scope.dispoPlc, 'marca': $scope.dispoMarca, 'maquina':$scope.dispoMaquina, 'statDispositivo':0 });
			$scope.cboArea = null;
			$scope.cboSistema = null;
			$scope.cboSubSistema = null;
			$scope.txtComen = null;
			//refrescar_tabla('#tbdata');

			$http.get(baseUrl + '/buscarPorMaquina?idMaquina='+$scope.selMaq).then(function (result) {
				$scope.maq = result.data;
				//alert($scope.maq.idDispositivo);
				return $http.get(baseUrl + '/Fallas?idDispositivo='+$scope.maq.idDispositivo)
			}).then(function(result){
				$scope.fallas = result.data;
					$scope.fallasAct = $scope.fallas;
			
			}) ///hasta aqui	*/		
			
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

//--------------------------------------------------------------------------------------------------------//
//------------------------------------ GRAFICOS ----------------------------------------------------------//
//--------------------------------------------------------------------------------------------------------//

app.controller("GraficoPrMP", ['$scope','$http','$timeout','$rootScope','$filter',function($scope, $http,$timeout,$rootScope,$filter) {
	var baseUrl = ".";
    var idSucursal = 1;
    $scope.labels = ['Enero','Febrero'];
    $scope.series = ['Valor Pr'];
    $scope.data = [7,10];
    
    $scope.labels = [$filter('date')(new Date()-6, 'yyyy-MM-dd'), $filter('date')(new Date()-5, 'yyyy-MM-dd'),
                     $filter('date')((new Date()-4), 'yyyy-MM-dd'), $filter('date')(new Date()-3, 'yyyy-MM-dd'),
                    		 $filter('date')(new Date()-2, 'yyyy-MM-dd'), $filter('date')(new Date()-1, 'yyyy-MM-dd'), $filter('date')(new Date(), 'yyyy-MM-dd')];
    $scope.series = ['Valor Pr'];
    $scope.data = [
      [0, 0, 0, 0, 0, 0, 0]
    ];  
    
    $scope.onClick = function (points, evt) {
        console.log(points, evt);
      };
      
      $scope.onHover = function (points) {
        if (points.length > 0) {
          console.log('Point', points[0].value);
        } else {
          console.log('No point');
        }
      };
      $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];

      $scope.options = {
        scales: {
          yAxes: [
            {
              id: 'y-axis-1',
              type: 'linear',
              display: true,
              position: 'left'
            },
            {
              id: 'y-axis-2',
              type: 'linear',
              display: true,
              position: 'right'
            }
          ]
        }
      };  
      
    
    //$scope.actGraficoPrMp = function(){  
      
      $timeout(function(){
    	
    	alert("Entrando");
        
    /*	if ($rootScope.selLinea != null){
    	     
    		
    		alert("hay linea");
			$http.get(baseUrl + '/buscarPorMaquina?idMaquina='+$rootScope.selLineaG).then(function (result) {
				$scope.maq = result.data;
				//alert($scope.maq.idDispositivo);
				return $http.get(baseUrl + '/graficoEficienciaMaquinaDia?idDispo='+$scope.maq.idDispositivo)
			}).then(function(result){
				$scope.graficoPrMP = result.data;
		        for (var i = 0; i < $scope.graficoPrMP; i++) {
		            
		                $scope.labels.push($scope.graficoPrMP[i].RESULT.ROWS[i][2]);
		                $scope.data.push($scope.graficoPrMP[i].RESULT.ROWS[i][3]);
		                
		         
		        }
					
			})	
    	}	*/
			
    	
	},3000);
    
    //$timeout($scope.actGraficoPrMp, 3000);

}]);

app.controller("MainController", ['$scope',function($scope) {
	/* angular.element(document).ready( function () {
         dTable = $('#tbdata')
         dTable.DataTable();
     }); */
}]);

