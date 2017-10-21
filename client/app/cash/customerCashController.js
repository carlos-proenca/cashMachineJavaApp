angular.module('cashMachineFrontApp').controller('CustomerCashController', [
  '$http',
  'consts',
  'msgs',
  CustomerCashController
])

function CustomerCashController($http, consts, msgs) {
  const vm = this
  const urlBase = `${consts.apiUrl}/customers`;
  vm.isToShowResult = false;
  vm.money = {
    moneyRequested: 0
  };

  vm.getCustomers = () => {
    $http.get(urlBase).then(function(response) {
      vm.customers =  response.data;
    })
  }

  vm.cashOutCustomerMoney = ()  => {
    const urlCashOut = urlBase+"/"+ vm.customerId+"/cash/out";
    $http.post(urlCashOut,  vm.money).then(function(response) {
      vm.customerBanknotes =  response.data;
      vm.isToShowResult = true;
      console.log(JSON.stringify( vm.customerBanknotes));
      msgs.addSuccess('Operação realizada com sucesso!!');
    }).catch(function(response) {
      msgs.addError('Opsss... ocorreu algum erro por favor tente novamente!!');
    })
  }
  vm.getCustomers();
}
