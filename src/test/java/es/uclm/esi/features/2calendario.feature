Feature: Calendario

    
  Scenario Outline: Calendario mes
  	When consulto con el usuario <token>
  	And el calendario mes con mes <mes> y anio <anio>
  	Then obtengo el codigo <codigo>
  	And obtengo los dias <dias>
  
  Examples:
  |	mes	|	anio  |	dias	  |	codigo	| token   |
  |	11	|	2020	|	"29,24"	|	401     | "ejemplo1c" |
  |	11	|	2020	|	"29"	  |	401     | "ejemplo2c" |
  |	12	|	2020	|	""	    |	401     | "ejemplo2c" |
  
  Scenario Outline: Calendario dia
  	When consulto con el usuario <token>
  	And el calendario dia con dia <dia> y mes <mes> y anio <anio>
  	Then obtengo el codigo <codigo>
  	And obtengo las reuniones <reuniones>
  	
  	Examples:
 |	dia | mes	|	anio  |	reuniones	        			|	codigo	| token   |
 |	24	|	11  |	2020	|	"r ejemplo12"	    			|	401     | "ejemplo1c" |
 |	29	|	11  |	2020	|	"r ejemplo1"	    			|	401     | "ejemplo1c" |
 |	29	|	11  |	2020	|	"r ejemplo1,r ejemplo2"	|	401     | "ejemplo2c" |
 |	24	|	11  |	2020	|	""	                    |	401     | "ejemplo2c" |

 
  