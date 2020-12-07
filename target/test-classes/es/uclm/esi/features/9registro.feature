#Feature: Registro usuario

#  Scenario Outline: registro
#    When registro un usuario con usuario <usuario>, pass <pass> y email <mail> 
#    And <opcion> es nuevo para borrarlo
#    Then el mensaje sera <mensaje>
    
    
#    Examples:
#  | usuario          |  pass               |  mail                    | opcion | mensaje |
#  | "admin"          | "usuario_existente" | "e@mail.com"             | "no"   | "Error: Username is already taken!" |
#  | "trabajador1"    | "usuario-existente" | "e@mail.com"             | "no"   | "Error: Username is already taken!" |
#  | "mail_existente" | "patata"            | "admin@admin.com"        | "no"   | "Error: Email is already in use!" |
#  | "mail-existente" | "asasds"            | "trabajador1@admin.com"  | "no"   | "Error: Email is already in use!" |
#  | "mail.existente" | "patata"            | "trabajador2@admin.com"  | "no"   | "Error: Email is already in use!" |
#  | "ejemplo1"       | "pass11"            | "ej1@admin.com"          | "si"   | "User registered successfully!" |
#  | "2ejemplo"       | "2pass2"            | "trabajador2@ej2.com"    | "si"   | "User registered successfully!" |
  