Feature: prueba
 
  Scenario: el cliente llama al saludo
    When the client calls "/tareas/saludo" 
    Then the client receives answer as "test"