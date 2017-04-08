angular.module('stats', ["chart.js"])
  .controller('stats', function($scope, $http) {
	  
	  $scope.userId = 1;
	  $scope.totals = {};
	  $http.get("/query/beerstats/total/1", {})
	  	.then(function(success) {
	  		$scope.totals = success.data;
	  		$scope.datatotal = [success.data.beerMoney, success.data.totalMoney - success.data.beerMoney];
	  		$scope.labelstotal = ["beer", "not beer"];
	  	}, function(fail) {
	  		console.log("fuck");
	  	});
	  $http.get("/query/beerstats/1", {})
	  	.then(function(success) {
	  		$scope.all = success.data;
	  		$scope.labelsdays = ["Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
	  		$scope.seriesdays = ['Beer Costs', 'Total Costs'];
	  		$scope.datadays = [_.chain(success.data).map(function(bs) { return bs.beerMoney; }).reverse().value(),
	  			_.chain(success.data).map(function(bs) { return bs.totalMoney; }).reverse().value()];
	  	}, function(fail) {
	  		console.log("fuck");
	  	});
 	  $http.get("/query/expenses/1", {})
	  	.then(function(success) {
	  		$scope.expenses = success.data;
	  		_.each($scope.expenses, function(e) {
	  			if (e.timeOfTransaction.length > 4)
	  			e.time = new Date(e.timeOfTransaction[0], e.timeOfTransaction[1], e.timeOfTransaction[2], e.timeOfTransaction[3], e.timeOfTransaction[4]);
	  		});
	  		$scope.expenses = _.sortBy($scope.expenses, 'time'); 
	  	}, function(fail) {
	  		console.log("Oops");
	  	});
 	  
 	  
 	  $scope.updateAmount = function(index) {
 		  console.log(index);
 		  var e = $scope.expenses[index];
 		  console.log(e);
 		  $http.post("/query/expense/" + e.id + "/cost/" + e.spentOnBeer, {});
 	  };
});