Feature: Roles patrón RBAC

  Scenario Outline: Roles de los usuarios de la aplicacion
    When accedo con el token de usuario <token>
    And a recursos del rol <rol>
    Then muestro el codigo <codigo>

    Examples: 
      |	rol	|	codigo	|	token	|
      | "USER" | 200 | "admin" |
      | "USER" | 200 | "user" |
			| "ADMIN" | 403 | "user" |
			| "USER" | 401 | "?" |
			| "ADMIN" | 401 | "?" |