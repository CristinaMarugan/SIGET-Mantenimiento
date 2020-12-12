
var hoy = new Date();

var infoMes;
var detallesReuniones;
var detallesDia = 0;
var identificador = 0;
var asistentes;

function llamadaAsistentes(){
    var info = {
        type : "getAsistentes",
    };
    $.ajax({
        url : '/reunion/getAsistentes',
        data : JSON.stringify(info),
        async : false,
        type : "post",
        dataType: 'json',
        contentType: 'application/json',
        headers: { 'Authorization': localStorage.getItem("jwt") },
        success : function(response) {
			setAsistentes(response);
        },
        error : function(response) {
            console.log('Se produjo un problema en getAsistentes()');
        }
    });
}

function setAsistentes(data){
	asistentes = data;
}

function getAsistentes(){
	return asistentes;
}

function clickInfoReuniones(ID){

    var jsonDia;

    for(var j = 0; j < 31; j++){
    	if(document.getElementById(j) != null){
        	document.getElementById(j).style.border = "2px double #fffafa";
		}
	}
	
    formEnBlanco();
    detallesEnBlanco();

    var hayreu = null;

    for(var k = 0; k < infoMes.reuniones.length; k++){
		if (ID == infoMes.reuniones[k]) {
            reunionesDia(ID,infoMes.mes,infoMes.ano);
            jsonDia = getDetallesReunionDiaC();
			hayreu = true;
		}
	}

	if(hayreu){
    	if(jsonDia == 0){
    		document.getElementById("formularioPreview").insertAdjacentHTML('beforeend',"<div><label>Actualizando...</label></div>");
    	} else {
    	   	var celda = document.getElementById(ID);
    	   	if(celda!=null){
            	celda.style.border = "2px double coral";
            }
    	    for(var i = 0; i < jsonDia.reuniones.length; i++){
    	       	document.getElementById("formularioPreview").insertAdjacentHTML('beforeend',"<div id='reunionYhora'><label id='reunion' "+
    	       	"onclick='mostrarInfoReunion("+jsonDia.reuniones[i].id+","+jsonDia.dia+")'>"+
    	      	jsonDia.reuniones[i].titulo+"</label>"+
    	        "<label id='horasreunion'>"+jsonDia.reuniones[i].hora+"</label><br></div>");
    	    }
    	}
	} else {
		document.getElementById("formularioPreview").insertAdjacentHTML('beforeend',"<div><label>NO HAY REUNIONES</label></div>");
	}
}

function formEnBlanco(){
var borrador = document.getElementById("formularioPreview");
    while (borrador.firstChild){
        borrador.removeChild(borrador.firstChild);
    }
}

function detallesEnBlanco(){
	//Vacía la caja de la derecha cada vez que cambia el día seleccionado
	var tituloB = document.getElementById("titureunion");
    tituloB.setAttribute("value"," ");

    var horaB = document.getElementById("horareunion");
    horaB.setAttribute("value"," ");

    var descripcionB = document.getElementById("descripcion");
    descripcionB.setAttribute("placeholder"," ");

    var asistentesB = document.getElementById("asistentes");
    asistentesB.setAttribute("placeholder"," ");

}

function mostrarInfoReunion(idReunion,diaReunion){
    var jsonMostrar = getDetallesReunionDiaC();
    
    identificador = jsonMostrar.reuniones[idReunion-1].identificador;

    var titulo = document.getElementById("titureunion");
    titulo.setAttribute("value",jsonMostrar.reuniones[idReunion-1].titulo);

    var hora = document.getElementById("horareunion");
    hora.setAttribute("value",jsonMostrar.reuniones[idReunion-1].hora);

    //var descripcion = document.getElementById("descripcion");
    //descripcion.setAttribute("placeholder",jsonMostrar.reuniones[idReunion-1].descripcion);
    $('#descripcion').val(jsonMostrar.reuniones[idReunion-1].descripcion);

    var horaF= document.getElementById("horafinreunion");
    horaF.setAttribute("value",jsonMostrar.reuniones[idReunion-1].horaFin);
    
    var strasistentes = "";
    for(var i = 0; i < jsonMostrar.reuniones[idReunion-1].asistentes.length; i++){
		strasistentes+=jsonMostrar.reuniones[idReunion-1].asistentes[i].usuario+"\n";
    }
    
    var asistentes = document.getElementById("asistentes");
    asistentes.setAttribute("placeholder",strasistentes);
    $('#cajainforeuniones').modal();
}

function getDetallesReuniones(){
    return detallesReuniones;
}

function setDetallesReuniones(data){
    detallesReuniones = data;
}

function getReunionesMes(){
    return infoMes;
}

function setReunionesMes(data){
    infoMes = data;
}

function reunionesMesHoy(){
    var mesActual = hoy.getMonth() + 1;
    var anoActual = hoy.getFullYear();
    var info = {
        type : "PeticionReunionesMes",
        mes : mesActual,
        ano : anoActual
    };
    $.ajax({
        url : '/getCalendarioPersonalMes',
        data : JSON.stringify(info),
        async : false,
        type : "post",
        dataType: 'json',
        headers: { 'Authorization': localStorage.getItem("jwt") },
        contentType: 'application/json',
        success : function(response) {
            setReunionesMes(response);
            localStorage.setItem("rol", response.usuario);
        },
        error : function(response) {
            console.log('Se produjo un problema en reunioesMesHoy()');
        }
    });
}

function reunionesDiaHoy(){ //Pedirá las reuniones del día de hoy, por defecto
    var mesActual = hoy.getMonth() + 1;
    var info = {
        "type" : "PeticionDatosReunion",
        "dia" : hoy.getDate(),
        "mes" : mesActual,
        "ano" : hoy.getFullYear()
    };
    $.ajax({
        url : '/getDetallesReunion',
        data : JSON.stringify(info),
        async : false,
        type : "post",
        dataType: 'json',
        headers: { 'Authorization': localStorage.getItem("jwt") },
        contentType: 'application/json',
        success : function(response) {
            setDetallesReuniones(response);
            setDetallesReunionDiaC(response);
            clickInfoReuniones(hoy.getDate());
        },
        error : function(response) {
            console.log('Se produjo un problema en reunionesDiaHoy()');
        }
    });
}

function getReunionesMesC(){
    return infoMes;
}

function setReunionesMesC(data){
    infoMes = data;
}

function reunionesMes(mesConcreto, anoConcreto){ //Recibirá las reuniones de un mes concreto
	
    var info = {
        type : "PeticionReunionesMes",
        mes : mesConcreto,
        ano : anoConcreto
    };
    $.ajax({
        url : '/getCalendarioPersonalMes',
        async : false,
        data : JSON.stringify(info),
        type : "post",
        dataType: 'json',
        headers: { 'Authorization': localStorage.getItem("jwt") },
        contentType: 'application/json',
        success : function(response) {
            setReunionesMesC(response);
        },
        error : function(response) {
            console.log('Se produjo un problema en reunionesMes()');
        }
    });
}


function cancelar() {
	var info = {
		id : identificador
	};
	$.ajax({
		url : '/reunion/cancelar',
        async : false,
        data : JSON.stringify(info),
        type : "post",
        headers: { 'Authorization': localStorage.getItem("jwt") },
        contentType: 'application/json',
        success : function(response) {
        	recarga();
        },
        error : function(response) {
            console.log('Se produjo un problema cancelando reunion');
        }
    });
}

function aceptarReunion() {
    var info = {
        id: identificador
    }
    $.ajax({
		url : '/reunion/aceptar',
        async : false,
        data : JSON.stringify(info),
        type : "post",
        dataType: 'json',
        headers: { 'Authorization': localStorage.getItem("jwt") },
        contentType: 'application/json',
        success : recarga()
    });
}

function rechazarReunion() {
    var info = {
        id: identificador
    }
    $.ajax({
		url : '/reunion/rechazar',
        async : false,
        data : JSON.stringify(info),
        type : "post",
        dataType: 'json',
        headers: { 'Authorization': localStorage.getItem("jwt") },
        contentType: 'application/json',
        success : recarga()
    });
}

function getDetallesReunionDiaC(){
    return detallesDia;
}

function setDetallesReunionDiaC(data){
    detallesDia = data;
}

function reunionesDia(diaConcreto, mesConcreto, anoConcreto){ //Pedirá las reuniones de un día concreto
	
   	var info = {
        type : "PeticionDatosReunion",
        dia : diaConcreto,
        mes : mesConcreto,
        ano : anoConcreto
    };
    $.ajax({
        url : '/getDetallesReunion',
        async : false,
        data : JSON.stringify(info),
        type : "post",
        dataType: 'json',
        headers: { 'Authorization': localStorage.getItem("jwt") },
        contentType: 'application/json',
        success : function(response) {
            setDetallesReunionDiaC(response);
        },
        error : function(response) {
            console.log('Se produjo un problema en reunionesMes()');
        }
    });
}

function setRol(){
	var usuario = localStorage.getItem("rol");
	var text = 'Usuario: ';
	text+= usuario;
	document.getElementById("ROL").innerHTML= text;
}

function guardarReunion(){
	var asistentes = [];
	var titulo = document.getElementById("tituloConvocar");
	var descripcion = document.getElementById("descripcionConvocar");
	var fecha = document.getElementById("fechaconvocar");
	var hora = document.getElementById("horaconvocar");
	var select = document.getElementById("arrayAsistentes");
	for ( var i = 0; i < select.selectedOptions.length; i++) {
		asistentes[i] = select.selectedOptions[i].value;
	}
	var info = {
        "type" : "ConvocarReunion",
        "titulo" : titulo.value,
        "descripcion" : descripcion.value,
        "hora" : hora.value,
        "fecha" : fecha.value,
        "asistentes" : asistentes
    };
    $.ajax({
        url : '/reunion/convocar',
        data : JSON.stringify(info),
        async : false,
        type : "post",
        dataType: 'json',
        headers: { 'Authorization': localStorage.getItem("jwt") },
        contentType: 'application/json',
        success : function(response) {
        },
        error : function(response) {
            console.log('Se produjo un problema en guardarReunion()');
        }
    });
	
}


function cargar() { 
    llamadaAsistentes();
    var asistentesConvocar = getAsistentes();
    var select = document.getElementById("arrayAsistentes");
    for(var i = 0; i < asistentesConvocar.usuarios.length; i++){ 
        var option = document.createElement("option");
        option.setAttribute("id","asistente");
        option.innerHTML = asistentesConvocar.usuarios[i];
        select.appendChild(option);

    }
    $('#convocar').modal('show');
    
}

function modificar(){
    
    if($('#titureunion').val().length == 0 || $('#descripcion').val().length == 0 || $('#horareunion').val().length == 0 || $('#horafinreunion').val().length == 0) 
        alert("Hay algun campo vacio");
    if($("#horareunion").val() > $("#horafinreunion").val() ){ //la hora de inicio es mayor que la de fin
        alert("La hora de fin no puede ser anterior a la de inicio")
        }
    
    
    else{
        var titulo = document.getElementById("titureunion");
        var descripcion = document.getElementById("descripcion");
        var horaInicio = document.getElementById("horareunion");
        var horaFin = document.getElementById("horafinreunion");
        
        var info = {
            "type" : "modificarReunion",
            "id": identificador,
            "titulo" : titulo.value,
            "descripcion" : descripcion.value,
            "horaInicio" : horaInicio.value,
            "horaFin" : horaFin.value,
            
        };
        $.ajax({
            url : '/reunion/modificar',
            data : JSON.stringify(info),
            async : false,
            type : "post",
            dataType: 'json',
            headers: { 'Authorization': localStorage.getItem("jwt") },
            contentType: 'application/json',
            success : function(response) {
                alert(response);
            },
            error : function(response) {
              
               alert(response.responseText);
            }
        });
    }
}