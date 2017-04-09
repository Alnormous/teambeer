angular.module('stats', ["chart.js"])
  .controller('stats', function($scope, $http, $timeout) {
	  
	  $scope.userId = 1;
	  $scope.totals = {};
	  $scope.isPayButtonShown = false;
	  
	  $scope.reload = function () {
	  
		  $http.get("/contacts/donatable", {})
			  .then(function (success) {
			  	if(success.data && success.data.length > 0) {
	                $scope.isPayButtonShown = true;
				}
			  	$scope.contacts = success.data;
			  	console.log($scope.contacts);
			  }, function (fail) {
			  	console.log("WHAT IS LOVE?");
			  });
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
		  			if (e.timeOfTransaction != null)
		  				e.time = new Date(e.timeOfTransaction.year, e.timeOfTransaction.monthValue, e.timeOfTransaction.dayOfMonth, e.timeOfTransaction.hour, e.timeOfTransaction.minute);
		  		});
		  		$scope.expenses = _.sortBy($scope.expenses, 'time').reverse();
		  		$scope.expenses = _.filter($scope.expenses, function(expense) {return expense.description !== 'No beer';});
		  	}, function(fail) {
		  		console.log("Oops");
		  	});
	 	  
	 	  $timeout(function(){
		      $scope.reload();
		    },5000);
	  };
	  
	  $scope.reload();

 	  $scope.pay = function(index) {
 	  	console.log(index);
 	  	var e = $scope.expenses[index];
 	  	console.log(e);
 	  	$http.post("/pay", {
            contactId: $scope.contacts[0].id,
            amount: e.spentOnBeer,
            expenseId: e.id

		}).then(function (success) {
			e.paid = true;
		});
	  };

 	  $scope.updateAmount = function(index) {
 		  console.log(index);
 		  var e = $scope.expenses[index];
 		  console.log(e);
 		  $http.post("/query/expense/" + e.id + "/cost/" + e.spentOnBeer, {});
 	  };
});