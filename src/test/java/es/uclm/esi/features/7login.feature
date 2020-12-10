Feature: Login
    
  Scenario: Buen Login
    When Hago login con "manu" y "manu"
    Then el codigo de respuesta debe ser 200

  Scenario: Mal Login
    When Hago login con "manu" y "Admin1233"
    Then el codigo de respuesta debe ser 401

    Scenario: Mal Login 2
    When Hago login con "" y "manu"
    Then el codigo de respuesta debe ser 401

    Scenario: Mal Login 3
    When Hago login con "manu" y ""
    Then el codigo de respuesta debe ser 401    