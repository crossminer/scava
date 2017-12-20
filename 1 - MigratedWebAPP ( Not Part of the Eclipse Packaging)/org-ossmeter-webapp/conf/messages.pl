# Override default Play's validation messages

# --- Constraints
constraint.required=Wymagane
constraint.min=Minimalna wartość: {0}
constraint.max=Maksymalna wartość: {0}
constraint.minLength=Minimalna długość: {0}
constraint.maxLength=Maksymalna długość: {0}
constraint.email=Email

# --- Formats
format.date=Data (''{0}'')
format.numeric=Numeryczny
format.real=Real

# --- Errors
error.invalid=Niepoprawna wartość
error.required=To pole jest wymagane
error.number=Wymagana wartość numeryczna
error.real=Real number value expected
error.min=Musi być większe lub równe niż {0}
error.max=Musi być mniejsze lub równe niż {0}
error.minLength=Minimalna długość {0}
error.maxLength=Maksymalna długość {0}
error.email=Wymagany poprawny adres e-mail
error.pattern=Must satisfy {0}

### --- ossmeter START

# ossmeter: Initial translations

ossmeter.accounts.link.success=Konto został podłączone
ossmeter.accounts.merge.success=Konta zostały złączone

ossmeter.verify_email.error.already_validated=Twój adres został już zweryfikowany.
ossmeter.verify_email.error.set_email_first=Najpierw musisz podać adres e-mail.
ossmeter.verify_email.message.instructions_sent=Instrukcje dotyczące weryfikacji adresu zostały wysłane na adres {0}.
ossmeter.verify_email.success=Adres e-mail  ({0}) został poprawnie zweryfikowany.

ossmeter.reset_password.message.instructions_sent=Instrukcje dotyczące przywracania hasła zostały wysłane na adres {0}.
ossmeter.reset_password.message.email_not_verified=Twoje konto nie zostało jeszcze zweryfikowane. Na wskazany adres zostały wysłane instrukcje dotyczące weryfikacji. Dopiero po weryfikacji spróbuj przywrócić hasło w razie potrzeby.
ossmeter.reset_password.message.no_password_account=Dla tego konta nie ustawiono jeszcze możliwości logowania za pomocą hasła.
ossmeter.reset_password.message.success.auto_login=Twoje hasło zostało przywrócone.
ossmeter.reset_password.message.success.manual_login=Twoje hasło zostało przywrócone. Zaloguj się ponownie z użyciem nowego hasła.

ossmeter.change_password.error.passwords_not_same=Hasła nie są takie same.
ossmeter.change_password.success=Hasło zostało zmienione.

ossmeter.password.signup.error.passwords_not_same=Hasła nie są takie same.
ossmeter.password.login.unknown_user_or_pw=Nieznany użytkownik lub złe hasło.

ossmeter.password.verify_signup.subject=OSSMETER: Zakończ rejestrację
ossmeter.password.verify_email.subject=OSSMETER: Potwierdź adres e-mail
ossmeter.password.reset_email.subject=OSSMETER: Jak ustalić nowe hasło

# ossmeter: Additional translations

ossmeter.login.email.placeholder=Twój adres e-mail
ossmeter.login.password.placeholder=Podaj hasło
ossmeter.login.password.repeat=Powtórz hasło
ossmeter.login.title=Logowanie
ossmeter.login.password.placeholder=Hasło
ossmeter.login.now=Zaloguj się
ossmeter.login.forgot.password=Nie pamiętasz hasła?
ossmeter.login.oauth=lub zaloguj się z innym dostawcą:
ossmeter.login.basic=

ossmeter.signup.title=Rejestracja
ossmeter.signup.name=Imię i nazwisko
ossmeter.signup.now=Zarejestruj się
ossmeter.signup.oauth=lub zarejestruj się z innym dostawcą:

ossmeter.verify.account.title=Wymagana weryfikacja adresu e-mail
ossmeter.verify.account.before=Zanim ustawisz nowe hasło
ossmeter.verify.account.first=musisz zweryfikować swój adres e-mail.

ossmeter.change.password.title=Zmień hasło
ossmeter.change.password.cta=Zmień moje hasło

ossmeter.merge.accounts.title=Złącz konta
ossmeter.merge.accounts.question=Czy chcesz połączyć aktualne konto ({0}) z kontem: {1}?
ossmeter.merge.accounts.true=Tak, połącz oba konta
ossmeter.merge.accounts.false=Nie, opuść bieżącą sesję i zaloguj się jako nowy użytkownik
ossmeter.merge.accounts.ok=OK

ossmeter.link.account.title=Dołącz konto
ossmeter.link.account.question=Czy chcesz dołączyć konto ({0}) do swojego aktualnego konta użytkownika?
ossmeter.link.account.true=Tak, dołącz to konto
ossmeter.link.account.false=Nie, wyloguj mnie i utwórz nowe konto użytkownika
ossmeter.link.account.ok=OK

# ossmeter: Signup folder translations

ossmeter.verify.email.title=Potwierdź adres e-mail
ossmeter.verify.email.requirement=Musisz potwierdzić swój adres e-mail, aby korzytać z OSSMETER
ossmeter.verify.email.cta=Na wskazany adres została przesłana informacja. Skorzystaj z dołączonego do niej linku aby aktywować konto.

ossmeter.password.reset.title=Przywróć hasło
ossmeter.password.reset.cta=Przywróć moje hasło

ossmeter.password.forgot.title=Nie pamiętam hasła
ossmeter.password.forgot.cta=Prześlij instrukcję dot. przywracania hasła

ossmeter.oauth.access.denied.title=Dostęp OAuth zabroniony
ossmeter.oauth.access.denied.explanation=Jeśli chcesz używać OSSMETER za pomocą OAuth, musisz zaakceptować połączenie.
ossmeter.oauth.access.denied.alternative=Jeśli wolisz tego nie robić możesz również
ossmeter.oauth.access.denied.alternative.cta=zarejestrować się podając nazwę użytkownika i hasło

ossmeter.token.error.title=Błąd tokena
ossmeter.token.error.message=Podany token stracił ważność lub nie istnieje.

ossmeter.user.exists.title=Użytkownik istnieje
ossmeter.user.exists.message=Ten użytkownik już istnieje.

# ossmeter: Navigation
ossmeter.navigation.profile=Profil
ossmeter.navigation.link_more=Dołącz więcej dostawców
ossmeter.navigation.logout=Wyloguj się
ossmeter.navigation.login=Zaloguj się
ossmeter.navigation.home=Strona główna
ossmeter.navigation.admin=Strona zastrzeżona
ossmeter.navigation.signup=Zarejestruj się

# ossmeter: Handler
ossmeter.handler.loginfirst=Musisz się zalogować, aby uzyskać dostęp do strony ''{0}''

# ossmeter: Profile
ossmeter.profile.title=Profil użytkownika
ossmeter.profile.mail=Nazywasz się {0} a twój e-mail to {1}!
ossmeter.profile.unverified=Niezweryfikowany - kliknij
ossmeter.profile.verified=zweryfikowany
ossmeter.profile.providers_many=Dostawcy podłączeni do Twojego konta ({0}):
ossmeter.profile.providers_one = Jedyny dostawca podłączony do Twojego konta:
ossmeter.profile.logged=Do obecnego zalogowania użyto:
ossmeter.profile.session=ID tego konta to {0} a jego sesja wygaśnie {1}
ossmeter.profile.session_endless=ID tego konta to {0}, jego sesja nie wygasa automatycznie
ossmeter.profile.password_change=Zmień lub ustaw hasło dla tego konta

# ossmeter - przykład: Index page
ossmeter.index.title=Witaj w OSSMETER
ossmeter.index.intro=OSSMETER
ossmeter.index.intro_2=Oto szablon prostej aplikacji wykorzystującej OSSMETER.
ossmeter.index.intro_3=Skorzystaj z powyższej nawigacji aby przetestować działanie autentykacji.
ossmeter.index.heading=Nagłówek
ossmeter.index.details=Szczegóły

# ossmeter - przykład: Restricted page
ossmeter.restricted.secrets=Tajemnice, tajemnice, tajemnice... Wszędzie tajemnice!

### --- ossmeter END