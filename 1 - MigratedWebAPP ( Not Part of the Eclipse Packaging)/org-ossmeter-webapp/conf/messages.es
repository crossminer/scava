# Override default Play's validation messages

# --- Constraints
constraint.required=Obligatorio
constraint.min=Valor mínimo: {0}
constraint.max=Valor máximo: {0}
constraint.minLength=Longitud mínima: {0}
constraint.maxLength=Longitud máxima: {0}
constraint.email=Email

# --- Formats
format.date=Date (''{0}'')
format.numeric=Numérico
format.real=Real

# --- Errors
error.invalid=Valor incorrecto
error.required=Este campo es obligatorio
error.number=Se esperaba un valor numérico
error.real=Se esperaba un numero real
error.min=Debe ser mayor o igual que {0}
error.max=Debe ser menor o igual que {0}
error.minLength=La longitud mínima es de {0}
error.maxLength=La longitud máxima es de {0}
error.email=Se requiere un email válido
error.pattern=Debe satisfacer {0}

### --- ossmeter START

# ossmeter: Initial translations

ossmeter.accounts.link.success=Cuenta enlazada correctamente
ossmeter.accounts.merge.success=Cuentas unificadas correctamente

ossmeter.verify_email.error.already_validated=Su email ya ha sido validado
ossmeter.verify_email.error.set_email_first=Primero debe dar de alta un email.
ossmeter.verify_email.message.instructions_sent=Las instrucciones para validar su cuenta han sido enviadas a {0}.
ossmeter.verify_email.success=La dirección de email ({0}) ha sido verificada correctamente.

ossmeter.reset_password.message.instructions_sent=Las instrucciones para restablecer su contraseña han sido enviadas a {0}.
ossmeter.reset_password.message.email_not_verified=Su cuenta aún no ha sido validada. Se ha enviado un email incluyedo instrucciones para su validación. Intente restablecer la contraseña una vez lo haya recibido.
ossmeter.reset_password.message.no_password_account=Su usuario todavía no ha sido configurado para utilizar contraseña.
ossmeter.reset_password.message.success.auto_login=Su contraseña ha sido restablecida.
ossmeter.reset_password.message.success.manual_login=Su contraseña ha sido restablecida. Intente volver a entrar utilizando su nueva contraseña.

ossmeter.change_password.error.passwords_not_same=Las contraseñas no coinciden.
ossmeter.change_password.success=La contraseña ha sido cambiada correctamente.

ossmeter.password.signup.error.passwords_not_same=Las contraseñas no coinciden.
ossmeter.password.login.unknown_user_or_pw=Usuario o contraseña incorrectos.

ossmeter.password.verify_signup.subject=OSSMETER: Complete su registro
ossmeter.password.verify_email.subject=OSSMETER: Confirme su dirección de email
ossmeter.password.reset_email.subject=OSSMETER: Cómo restablecer su contraseña

# ossmeter: Additional translations

ossmeter.login.email.placeholder=Su dirección de email
ossmeter.login.password.placeholder=Elija una contraseña
ossmeter.login.password.repeat=Repita la contraseña elegida
ossmeter.login.title=Entrar
ossmeter.login.password.placeholder=Contraseña
ossmeter.login.now=Entrar
ossmeter.login.forgot.password=¿Olvidó su contraseña?
ossmeter.login.oauth=entre usando su cuenta con alguno de los siguientes proveedores:
ossmeter.login.basic=o prueba con la autenticación básica HTTP (como example/secret)

ossmeter.signup.title=Registrarse
ossmeter.signup.name=Su nombre
ossmeter.signup.now=Regístrese
ossmeter.signup.oauth=regístrese usando su cuenta con alguno de los siguientes proveedores:

ossmeter.verify.account.title=Es necesario validar su email
ossmeter.verify.account.before=Antes de configurar una contraseña
ossmeter.verify.account.first=valide su email

ossmeter.change.password.title=Cambio de contraseña
ossmeter.change.password.cta=Cambiar mi contraseña

playauthenticate.merge.accounts.title=Unir cuentas
playauthenticate.merge.accounts.question=¿Desea unir su cuenta ({0}) con su otra cuenta: {1}?
playauthenticate.merge.accounts.true=Sí, ¡une estas dos cuentas!
playauthenticate.merge.accounts.false=No, quiero abandonar esta sesión y entrar como otro usuario.
playauthenticate.merge.accounts.ok=OK

ossmeter.link.account.title=Enlazar cuenta
ossmeter.link.account.question=¿Enlazar ({0}) con su usuario?
ossmeter.link.account.true=Sí, ¡enlaza esta cuenta!
ossmeter.link.account.false=No, salir y crear un nuevo usuario con esta cuenta
ossmeter.link.account.ok=OK

# ossmeter: Signup folder translations

ossmeter.verify.email.title=Verifique su email
ossmeter.verify.email.requirement=Antes de usar OSSMETER, debe validar su email.
ossmeter.verify.email.cta=Se le ha enviado un email a la dirección registrada. Por favor, siga el link de este email para activar su cuenta.
ossmeter.password.reset.title=Restablecer contraseña
ossmeter.password.reset.cta=Restablecer mi contraseña

ossmeter.password.forgot.title=Contraseña olvidada
ossmeter.password.forgot.cta=Enviar instrucciones para restablecer la contraseña

ossmeter.oauth.access.denied.title=Acceso denegado por OAuth
ossmeter.oauth.access.denied.explanation=Si quiere usar OSSMETER con OAuth, debe aceptar la conexión.
ossmeter.oauth.access.denied.alternative=Si prefiere no hacerlo, puede también
ossmeter.oauth.access.denied.alternative.cta=registrarse con un usuario y una contraseña.

ossmeter.token.error.title=Error de token
ossmeter.token.error.message=El token ha caducado o no existe.

ossmeter.user.exists.title=El usuario existe
ossmeter.user.exists.message=Otro usario ya está dado de alta con este identificador.

# ossmeter: Navigation
ossmeter.navigation.profile=Perfil
ossmeter.navigation.link_more=Enlazar más proveedores
ossmeter.navigation.logout=Salir
ossmeter.navigation.login=Entrar
ossmeter.navigation.home=Inicio
ossmeter.navigation.admin=Administración
ossmeter.navigation.signup=Dárse de alta

# ossmeter: Handler
ossmeter.handler.loginfirst=Para ver ''{0}'', debe darse primero de alta.

# ossmeter: Profile
ossmeter.profile.title=Perfil de usuario
ossmeter.profile.mail=Su nombre es {0} y su dirección de mail es {1}!
ossmeter.profile.unverified=sin validar - haga click para validar
ossmeter.profile.verified=validada
ossmeter.profile.providers_many=Hay {0} proveedores enlazados con su cuenta:
ossmeter.profile.providers_one = Hay un proveedor enlazado con su cuenta:
ossmeter.profile.logged=Ha entrado con:
ossmeter.profile.session=Su ID de usuario es {0}. Su sesión expirará el {1}.
ossmeter.profile.session_endless=Su ID de usuario es {0}. Su sesión no expirará nunca porque no tiene caducidad.
ossmeter.profile.password_change=Cambie/establezca una contraseña para su cuenta

# ossmeter - sample: Index page
ossmeter.index.title=Bienvenido a OSSMETER
ossmeter.index.intro=Aplicación web OSSMETER
ossmeter.index.intro_2=Bienvenido a OSSMETER
ossmeter.index.intro_3=Explora y compara proyectos y métricas
ossmeter.index.heading=Cabecera
ossmeter.index.projects=Proyectos
ossmeter.index.details=Ver detalles

# ossmeter - sample: Restricted page
ossmeter.restricted.secrets=¡Secretos y más secretos!

# Admin page
ossmeter.admin.users=Administrar usuarios
### --- ossmeter END
