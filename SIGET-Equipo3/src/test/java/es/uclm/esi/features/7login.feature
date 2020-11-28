Feature: Login

	#Background:
  #  Given baseUri is http://localhost:8080
    
  Scenario: Buen Login
    When Hago login con "admin" y "Admin123"
    Then el codigo de respuesta debe ser 200
    

  Scenario: Mal Login
    When Hago login con "admin" y "Admin1233"
    Then el codigo de respuesta debe ser 401

    Scenario: Mal Login 2
    When Hago login con "" y "Admin1233"
    Then el codigo de respuesta debe ser 401

    Scenario: Mal Login 3
    When Hago login con "admin" y ""
    Then el codigo de respuesta debe ser 401    