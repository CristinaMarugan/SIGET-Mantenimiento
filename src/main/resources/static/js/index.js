$(document).ready(function() {
    $("#mostrarLogin").click(function() {
        $("#divLogin").show();
        $("#divRegistro").hide()
    }),
    $("#mostrarRegistro").click(function() {
        $("#divLogin").hide();
        $("#divRegistro").show()
    })
});

function SignUp() {
    var info = {
        type : "Register",
        username : $("#SignUpName").val(),
        email : $("#SignUpEmail").val(),
        password : $("#pwd1").val(),
        
    };
    var data = {
            data : JSON.stringify(info),
            url : "api/auth/signup",
            type : "post",
            contentType: 'application/json',
            success : function() {
                alert("Registro realizado");
            },
            error : function(response) {
                alert("Error en el registro del usuario");
            }
        };
        $.ajax(data);
}

function LogIn() {
    var info = {
        type : "Login",
        username : $("#loginUsername").val(),
        password : $("#loginUserPassword").val()
    };
    
    var data = {
        data : JSON.stringify(info),
        url : "api/auth/signin",
        type : "post",
        contentType: 'application/json',
        success : function(response) {
        localStorage.setItem('jwt','Bearer '+response.accessToken);//guardo el token que luego habra que enviar en el header de las peticiones

        localStorage.setItem('accessToken','acesoAlToken');
        window.location.href="calendario.html";
        },
        error : function(response) {
            alert("Error en la autenticacion del usuario");
        }
    };
    $.ajax(data);
    console.log(data);
}



function requestToken() {
    alert("En requestToken");
}