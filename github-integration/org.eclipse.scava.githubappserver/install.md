#To create the CROSSMINER GitHub App:

- Goto new GitHub App registration page (Settings -> Developer settings -> New GitHub App)
- Set **Webhook URL** to *<CROSSMINER GitHub App Server>*
- Add *Read-only* permission to **Repository contents**
- Subscribe to **Push** events

- Set **crossminer.github_app_id** in `src/main/resources/application.properties` to the *<CROSSMINER GitHub App Id>*
- Build the app server (`$ mvn package`)
- Copy `crossminerappserver-1.0-SNAPSHOT.jar` to the *<CROSSMINER GitHub App Server>*
- Copy the *<CROSSMINER GitHub App private API key>* beside the `crossminerappserver-1.0-SNAPSHOT.jar` as `crossminer-github-app-key.pem`
- Start server (`$ java -Djava.security.egd=file:/dev/urandom -jar /opt/crossminerappserver-1.0-SNAPSHOT.jar`)
