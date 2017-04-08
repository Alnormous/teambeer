angular.module('stats', ["chart.js"])
  .controller('stats', function($scope, $http) {
	  
	  $scope.userId = 1;
	  $scope.totals = {};
	  $scope.data = [];
	  $scope.labels = [];
	  $http.get("/query/beerstats/1/day/1", {})
	  	.then(function(success) {
	  		$scope.totals = success.data;
	  		$scope.data = [success.data.beerMoney, success.data.totalMoney - success.data.beerMoney];
	  		$scope.labels = ["beer", "not beer"];
	  	}, function(fail) {
	  		console.log("fuck");
	  	});
});