# Cash Machine Java App

Projeto exemplo utilizando spring boot de um caixa eletrônico

# Bakend
O backend fica na pasta back onde tem todo o código spring boot Java 
Os endpoints criados foram:

## Endpoint para gerenciar o cliente:
(GET, PUT, POST e DELETE) localhost:8085/api/v1/customers

## Endpoint to get customer cash withdrawal
(POST) localhost:8085/api/v1/customers/{customerId}/cash/out

# Frontend
O frontend fica na pasta client onde foi criado um projeto angular com adminlte rodando no servidor gulp
para rodar o projeto va até o diretório "client" e execute "npm run dev" ou gere a versão de PRD "npm run production"
