var app = angular.module('nebula', []);

app.controller('control', [ '$scope', '$http', '$interval', '$window', function($scope, $http, $interval, $window) {
	
	    $interval( function(){ $scope.checkVeiculoReady(); }, 5000);
	    
	    $scope.key;
	    $scope.solicitado = false;
	    $scope.showdialog = true;
		$scope.mensagem = "Solicitação não enviada";
		$scope.mensagem2 = "Sem seguro";

		$scope.bloco = { 
			"chave": "1661886325345",
		    "modelo": "",
			"cor": "",
			"interior": "",
			"segurado": false, 
			"chassicriado" : false,
			"pinturacriada" : false,
			"interiorcriado" : false,
			"veiculopronto" : false,
			"construcao_solicitada": false,
			"seguro_solicitado" : false,
			"timestamp": 0
		};
	    
	    $scope.blockchain_ip = "http://127.0.0.1:3546";	    
    	
	    
		$scope.checkVeiculoReady = function() {
		    //TODO CHAMA PERIODICAMENTE A URL RETRIEVE DO BLOCKCHAIN E TESTA SE O VEICULO FOI CONSTRUIDO
			//TODO Se o valor construido for alterado para true, apresenta o botão solicita seguro
			$http.get($scope.blockchain_ip + '/retrieve?chave='+$scope.bloco.chave).then(function(response) {
			//$http.get($scope.blockchain_ip + '/getQueryResultString?construcao_solicitada=false').then(function(response) {
				if (response.data.chave != undefined) {
				   $scope.bloco = response.data;
				}
		    });
			//$http.get($scope.blockchain_ip + '/retrieve?chave='+$scope.bloco.chave).then(function(response) {
			//	if (response.data.chave != undefined) {
			//	   $scope.bloco = response.data;
			//	}
		    //});
			if ($scope.bloco.segurado)
			     $scope.mensagem2 = 'Seu seguro foi aprovado';
    	}  	 
		
    	$scope.modelo = function(modelo) {
		    $scope.bloco.modelo = modelo;
    	} 
		
		$scope.cor = function(cor) {
		    $scope.bloco.cor = cor;
    	}   

		$scope.interior = function(interior) {
		    $scope.bloco.interior = interior;
    	}

    	$scope.createCar = function() {
			
			//TODO TESTA SE OS VALORES modelo, cor e intterior estão setados (não são nulos) e chama a URL do blockchain para inserir no banco
		    $http.get($scope.blockchain_ip + '/create?chave='+$scope.bloco.chave+"&valor="+JSON.stringify($scope.bloco)).then(function(response) {
		       });
			$scope.mensagem = 'Solicitação para construção do veículo enviada';
    	}
    	
    	$scope.solicitaSeguro = function() {
			$scope.bloco.seguro_solicitado = true;
			$http.get($scope.blockchain_ip + '/update?chave='+$scope.bloco.chave+"&valor="+JSON.stringify($scope.bloco)).then(function(response) {
			});
			$scope.mensagem = 'Solicitação enviada para o seguro ';
    	}
		
    	$scope.hasFinished = function() {
			if ($scope.bloco.veiculopronto)
				$scope.mensagem = 'Seu veículo foi construido com sucesso';  
			return $scope.bloco.veiculopronto;
    	}
		
   }]);