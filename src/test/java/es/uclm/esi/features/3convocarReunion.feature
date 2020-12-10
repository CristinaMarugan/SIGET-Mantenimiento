Feature: Convocar

    
  Scenario Outline: convocar reunion
  	Given Me autentico como usuario existente en el sistema y usuario es <usuario> y password es <password>
  	When convoco la reunion y titulo es <titulo>
	  	And dia es <dia>
	  	And mes es <mes>
	  	And ano es <ano>
	  	And hora es <hora>
	  	And descripcion es <descripcion>
	  	And asistentes son <asistentes>
  	Then la respuesta a convocar sera <codigo>
 
  Examples:
  | usuario   | password  | titulo            | dia	|	mes	|	ano	  |	hora    | descripcion       | asistentes                                              |	codigo |
  | "manu"    | "manu"    | "reunion jaime"   |	30  |	12	|	2020	|	"11:55"	|	"reunion jaime"   |	"Elisa:pendiente, pepe:Rechazado, jaime:aceptada"	      |	200    |
  | "Elisa" | "Seguridad2020" | "reunion equipo3" |	9	  |	12	|	2020	|	"11:55"	|	"reunion equipo3" |	"Elisa:pendiente,pepe:Rechazado,jaime:aceptada"	        |	200    |