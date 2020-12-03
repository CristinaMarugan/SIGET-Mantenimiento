Feature: Cancelar

Scenario Outline: cancelar
  
When cancelo la reunion 
Then la respuesta debe ser <codigo>
 
  
Examples:
	| codigo | 
  | 200 | 