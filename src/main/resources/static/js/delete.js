var asistentes;

window.onload = function() {
	CargarListaUsers();
}

function CargarListaUsers(){
    llamadaAsistentes();
    var asistentesConvocar = getAsistentes();

    for(var i = 0; i < asistentesConvocar.usuarios.length; i++){ 
        document.getElementById("formularioUsers").insertAdjacentHTML('beforeend',"<label id='reunion' class='list-group-item list-group-item-action'>"+asistentesConvocar.usuarios[i]+"</label>");
    }
}

function getAsistentes(){
	return asistentes;
}

function Eliminar(){
    var usuarios = [];
    var mensaje = confirm("¿Estás seguro de que quieres eliminar este usuario?");
    var select = document.getElementById("arrayUsuarios");
    alert(localStorage.rol);
	for ( var i = 0; i < select.selectedOptions.length; i++) {
		usuarios[i] = select.selectedOptions[i].value;
	}
    var info = {
        type : "eliminar",
        usuario : localStorage.rol,
        "usuarios" : usuarios
    };
    if(mensaje==false){
                alert("Operación cancelada");
            }else{
                alert("Usuario Eliminado");
			    $.ajax({
			        url : '/deleteUser',
			        data : JSON.stringify(info),
			        async : false,
			        type : "post",
			        dataType: 'json',
			        contentType: 'application/json',
			        headers: { 'Authorization': localStorage.getItem("jwt") },
			        success : function(response) {
						window.location.reload();
			        },
			        error : function(response) {
			            console.log('Se produjo un problema al eliminar el usuario');
			        }
			    });
    }
}

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


