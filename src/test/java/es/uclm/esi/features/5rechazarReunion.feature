Feature: Rechazar

  Scenario Outline: rechazar
  
  When rechazo la reunion 
  Then la respuesta a rechazar debe ser <codigo>
 
  
  Examples:
	| codigo | 
  | 200 | 
