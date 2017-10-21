angular.module('cashMachineFrontApp').config([
  '$stateProvider',
  '$urlRouterProvider',
  function($stateProvider, $urlRouterProvider) {
    $stateProvider.state('cashout', {
      url: "/cash/out",
      templateUrl: "cash/customerCash.html"
    }).state('customers', {
      url: "/customers",
      templateUrl: "customers/customers.html"
    })

    $urlRouterProvider.otherwise('/cash/out')
}])
