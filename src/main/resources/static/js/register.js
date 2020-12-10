$(document).ready(function() {
	$('input[id=pwd1]').keyup(function() {
		var pswd = $(this).val();
		//length
		if (pswd.length < 8) {
			$('#length').removeClass('valid').addClass('invalid');
		} else {
			$('#length').removeClass('invalid').addClass('valid');
		}
		//letter
		if (pswd.match(/[A-z]/)) {
			$('#letter').removeClass('invalid').addClass('valid');
		} else {
			$('#letter').removeClass('valid').addClass('invalid');
		}
		//validate number
		if (pswd.match(/\d/)) {
			$('#number').removeClass('invalid').addClass('valid');
		} else {
			$('#number').removeClass('valid').addClass('invalid');
		}
		//mayus
		if (pswd.match(/[A-Z]/)) {
			$('#capital').removeClass('invalid').addClass('valid');
		} else {
			$('#capital').removeClass('valid').addClass('invalid');
		}

	}).focus(function() {
		$('#alertPass').show();
	}).blur(function() {
		$('#alertPass').hide();
	});
});

function comprobar() {
	var mail1 = $("#userMail").val();
	var username = $("#userName").val();
	var pass = $("#pwd1").val();
	var pass2 = $("#pwd2").val();
	var mail2 = $("#mail2").val();
	if (false != (validarCampo("userMail") && validarCampo("userName") && validarCampo("pwd1") && validarCampo("mail2") && validarCampo("userCompletName") && validarCampo("userApellidos") && validarCampo("userTelf"))) {
		if (pass !== pass2) {
			alert("Las contraseñas no puede ser distintas");
		} else if (mail1 !== mail2) {
			alert("Los e-mails no pueden ser distintos");
		}
		else if (userTelf.value.length > 9)
			alert("El numero de telefono es demasiado largo");
		else
			register();

	}
}

function validarCampo(campo) { // comprobar que no hay campos vacios
	var valido = new Boolean(true);
	if (document.getElementById(campo).value == '') {
		alert('El campo ' + campo + ' está vacio');
		valido = false;
	}
	return valido;
};


function limpiarCampos() { // resetear todos los campos
	document.getElementById("userCompletName").value = "";
	document.getElementById("userDni").value = "";
	document.getElementById("userName").value = "";
	document.getElementById("userApellidos").value = "";
	document.getElementById("userTelf").value = "";
	document.getElementById("userMail").value = "";
	document.getElementById("pwd1").value = "";
	document.getElementById("pwd2").value = "";
	document.getElementById("mail2").value = "";
};

function register() {

	var info = {
		type: "Register",
		name: userCompletName.value,
		username: userName.value,
		apellidos: userApellidos.value,
		dni: userDni.value,
		tlf: userTelf.value,
		email: userMail.value,
		password: pwd1.value
	};
	var data = {
		data: JSON.stringify(info),
		url: "api/auth/signup",
		type: "post",
		contentType: 'application/json',
		success: function(response) {
			alert("Registro realizado");
		},
		error: function(response) {
			alert("Error en el registro del usuario");


		}
	};
	$.ajax(data);
}