Feature: Modificar Reunion

  Scenario Outline: modificar
   When Modifico la reunion <id> con el token de usuario <token> 
    And cambio el titulo a <titulo>
    And cambio la fecha a <fecha>
    And cambio la hora a <hora>
    And el nuevo titulo sera <nuevotitulo>
    And la nueva fecha sera <nuevafecha>
    And la nueva hora sera <nuevahora>
   
    
    Examples:
  | titulo                   | nuevotitulo       				| fecha        |  nuevafecha  | hora    | nuevahora | asistentes                                      | asistentesnuevos                                | codigo | id   | token   |
	| "test"                   | "test"                   | "11-11-2020" | "11-11-2020" | "23:32" |  "23:32"  | "manu:pendiente,Elisa:pendiente"                 | "manu:pendiente,jaime:pendiente"                 | 404    | 1    | "awed"  |