//Arrays de datos:
var meses = ["enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre"];
var lasemana = ["Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"]
var diassemana = ["lun","mar","mié","jue","vie","sáb","dom"];

//Tras cargarse la página ...
window.onload = function() {

    //Llamadas iniciales para la petición de información de reuniones
    reunionesMesHoy();
    reunionesDiaHoy();
    setRol();

	//Fecha actual
	var hoy = new Date(); //objeto fecha actual
	/*var diasemhoy = hoy.getDay(); //dia semana actual
	var diahoy = hoy.getDate(); //dia mes actual
	var meshoy = hoy.getMonth(); //mes actual*/
	var annohoy = hoy.getFullYear(); //año actual

	// Elementos del DOM: en cabecera de calendario 
	/*var tit = document.getElementById("titulos"); //cabecera del calendario
	var ant = document.getElementById("anterior"); //mes anterior
	var pos = document.getElementById("posterior"); //mes posterior*/

	// Elementos del DOM en primera fila
	//var f0 = document.getElementById("fila0");

	//Formulario: datos iniciales:
	document.buscar.buscaanno.value = annohoy;

	// Definir elementos iniciales:
	/*var mescal = meshoy; //mes principal
	var annocal = annohoy //año principal*/

	//Iniciar calendario:
	cabecera(); 
	primeralinea();
	escribirdias(); 
}

//FUNCIONES de creación del calendario:
//Cabecera del calendario
function cabecera() {
    tit.innerHTML = meses[mescal]+" de "+annocal;
    var mesant = mescal-1; //mes anterior
    var mespos = mescal+1; //mes posterior

    if(mesant < 0) {mesant = 11;}
    if(mespos > 11) {mespos = 0;}

    ant.innerHTML = meses[mesant]
    pos.innerHTML = meses[mespos]
} 

//Primera línea de tabla: días de la semana.
function primeralinea() {
    for(var i = 0; i < 7; i++) {
        var celda0 = f0.getElementsByTagName("th")[i];
        celda0.innerHTML = diassemana[i]
   }
}

//Rellenar celdas con los días
function escribirdias() {

	reunionesMes(mescal+1,annocal);
	var jsonreuniones = getReunionesMesC();

    //Buscar dia de la semana del dia 1 del mes:
    var primeromes = new Date(annocal,mescal,"1") //buscar primer día del mes
    var prsem = primeromes.getDay() //buscar día de la semana del día 1
    prsem--; //adaptar al calendario español (empezar por lunes)

    if(prsem == -1) {prsem = 6;}
	//Buscar fecha para primera celda:
    var diaprmes = primeromes.getDate() 
    var prcelda = diaprmes-prsem; //restar días que sobran de la semana
    var empezar = primeromes.setDate(prcelda) //empezar= tiempo UNIX 1ª celda
    var diames = new Date() //convertir en fecha
    diames.setTime(empezar); //diames=fecha primera celda.

    //Recorrer las celdas para escribir el día:
    for(var i = 1; i < 7; i++) { //localizar fila
        var fila=document.getElementById("fila"+i);
        for(var j = 0; j < 7; j++) {
            var midia = diames.getDate() 
            var mimes = diames.getMonth()
            var mianno = diames.getFullYear()
	        var celda = fila.getElementsByTagName("td")[j];
            celda.innerHTML = midia;
    	    celda.setAttribute('id',midia);

            //Recuperar estado inicial al cambiar de mes:
            celda.style.backgroundColor = "#fdfefe";
            celda.style.color = "#492736";
            celda.style.border = "2px double #fffafa";

            //Domingos en rojo
            if(j == 6) { 
	            celda.style.color = "#f11445";
            }

            //dias restantes del mes en gris
            if(mimes != mescal) { 
              	celda.setAttribute('id',0);
    	        celda.style.color = "#a0babc";
            }

            //Destacar la fecha actual
            if(mimes == meshoy && midia == diahoy && mianno == annohoy ) {
              	celda.innerHTML = "<cite title='Fecha Actual'>"+ midia +"</cite>";
                //celda.style.border = "thick solid #9370DB";
                celda.style.font = "bold 18pt arial";
                celda.style.color = "#000000";
           	} else {
               	celda.style.font = "normal 14pt arial";
            }

            //Pasar al siguiente día
            midia = midia + 1;
            diames.setDate(midia);

            if(jsonreuniones != 0){
	            for(var ii = 0; ii < jsonreuniones.reuniones.length; ii++){ //Resalta cuando hay una reunión ese día
	              	if(celda.getAttribute("id") == jsonreuniones.reuniones[ii]){
	              		celda.style.backgroundColor = "#CACCD3";
	               	}
	            }
        	}
        }
    }

    formEnBlanco();
    detallesEnBlanco();
}

//Ver mes anterior
function mesantes() {
    var nuevomes = new Date() //nuevo objeto de fecha
    primeromes--; //Restamos un día al 1 del mes visualizado
    nuevomes.setTime(primeromes) //cambiamos fecha al mes anterior 
    /*var mescal = nuevomes.getMonth() //cambiamos las variables que usarán las funciones
    var annocal = nuevomes.getFullYear()*/
    cabecera() //llamada a funcion de cambio de cabecera
    escribirdias() //llamada a funcion de cambio de tabla.
}

//ver mes posterior
function mesdespues() {
    var nuevomes = new Date() //nuevo obejto fecha
    var tiempounix = primeromes.getTime() //tiempo de primero mes visible
    tiempounix = tiempounix+(45*24*60*60*1000) //le añadimos 45 días 
    nuevomes.setTime(tiempounix) //fecha con mes posterior.
    /*var mescal = nuevomes.getMonth() //cambiamos variables
    var annocal = nuevomes.getFullYear()*/
    cabecera() //escribir la cabecera 
    escribirdias() //escribir la tabla
}

//volver al mes actual
function actualizar() {
    /*var mescal = hoy.getMonth(); //cambiar a mes actual
    var annocal = hoy.getFullYear(); //cambiar a año actual */
    cabecera() //escribir la cabecera
    escribirdias() //escribir la tabla
}

//ir al mes buscado
function mifecha() {
    //Recoger dato del año en el formulario
    var mianno = document.buscar.buscaanno.value; 
    //recoger dato del mes en el formulario
    var listameses = document.buscar.buscames;
    var opciones = listameses.options;
    var num = listameses.selectedIndex
    var mimes = opciones[num].value;
    //Comprobar si el año está bien escrito
    if(isNaN(mianno) || mianno < 1) { 
        //año mal escrito: mensaje de error
        alert("El año no es válido:\n debe ser un número mayor que 0")
    } else { //año bien escrito: ver mes en calendario:
        var mife = new Date(); //nueva fecha
        mife.setMonth(mimes); //añadir mes y año a nueva fecha
        mife.setFullYear(mianno);
        /*var mescal = mife.getMonth(); //cambiar a mes y año indicados
        var annocal = mife.getFullYear();*/
        cabecera() //escribir cabecera
        escribirdias() //escribir tabla
    }
}

function recarga() {
    window.location.reload();
}