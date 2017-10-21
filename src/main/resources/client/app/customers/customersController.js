angular.module('cashMachineFrontApp').controller('CustomerController', [
  '$http',
  'consts',
  'msgs',
  CustomerController
])

function CustomerController($http, consts, msgs) {
  const vm = this
  const urlBase = `${consts.apiUrl}/customers`;
  vm.isToUpdate = false;

  vm.getCustomers = () => {
    $http.get(urlBase).then(function(response) {
      vm.customers =  response.data;
    })
  }

  vm.selectCustomerToUpdate = (customer)  => {
    vm.isToUpdate = true;
    vm.customerSelected =  angular.copy(customer);
  }

  vm.saveOrUpdateCustomer = ()  => {
    if(vm.isToUpdate){
      vm.update();
    } else {
      vm.create();
    }
  }

  vm.create = ()  => {
    $http.post(urlBase,  vm.customerSelected).then(function(response) {
      vm.customerSelected =  {};
      msgs.addSuccess('Operação realizada com sucesso!!');
      vm.getCustomers();
    }).catch(function(response) {
      msgs.addError('Opsss... ocorreu algum erro por favor tente novamente!!');
    })
  }

  vm.update = ()  => {
    const urlBaseWithId = urlBase+"/"+vm.customerSelected.id;
    $http.put(urlBaseWithId,  vm.customerSelected).then(function(response) {
      vm.customerSelected =  {};
      msgs.addSuccess('Operação realizada com sucesso!!');
      vm.getCustomers();
    }).catch(function(response) {
      msgs.addError('Opsss... ocorreu algum erro por favor tente novamente!!');
    })
  }

  vm.remove = (customer)  => {
    const urlBaseWithId = urlBase+"/"+customer.id;
    $http.delete(urlBaseWithId).then(function(response) {
      vm.customerSelected =  {};
      msgs.addSuccess('Operação realizada com sucesso!!')
      vm.getCustomers();
    }).catch(function(response) {
      msgs.addError('Opsss... ocorreu algum erro por favor tente novamente!!')
    })
  }

  vm.getCustomers();
}
