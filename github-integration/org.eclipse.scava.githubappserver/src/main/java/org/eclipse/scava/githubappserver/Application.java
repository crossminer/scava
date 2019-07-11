package org.eclipse.scava.githubappserver;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@RestController
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${crossminer.github_pem}")
    private String pem;

    @Value("${crossminer.github_app_id}")
    private String app_id;

    @RequestMapping("/")
    public String home() {
        return "Hello";
    }

    @RequestMapping(value = "/setup", method = RequestMethod.GET)
    public String setup(@RequestParam("installation_id") String installationId, @RequestParam("setup_action") String setupAction) {
        return "Successful install";
    }

    @RequestMapping("/hook")
    public void hook(@RequestHeader("X-GitHub-Event") String eventType, @RequestBody String body) {
        logger.info("Event: " + eventType);
        logger.info("Body: " + body);
        try {
            if (eventType.equals("push")) {
                ObjectMapper objectMapper = new ObjectMapper();
                Push push = objectMapper.readValue(body, Push.class);
                if (push.getRef().equals("refs/heads/master")) {
                    checkFile(push.getRepository().getOwner().getName(), push.getRepository().getName(), push.getRef());
                }
            } else if (eventType.equals("installation")) {
                ObjectMapper objectMapper = new ObjectMapper();
                Installation installation = objectMapper.readValue(body, Installation.class);
                if (installation.getAction().equals("created")) {
                    for (org.eclipse.scava.githubappserver.Repository r : installation.getRepositories()) {
                        checkFile(r.getOwner().getName(), r.getName(), "refs/heads/master");
                    }
                }
            } else if (eventType.equals("installation_repositories")) {
                ObjectMapper objectMapper = new ObjectMapper();
                InstallationRepositories installationRepositories = objectMapper.readValue(body, InstallationRepositories.class);
                if (installationRepositories.getAction().equals("added")) {
                    for (org.eclipse.scava.githubappserver.Repository r : installationRepositories.getRepositories_added()) {
                        checkFile(r.getOwner().getName(), r.getName(), "refs/heads/master");
                    }
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void checkFile(String owner, String repo, String branch) {
        try {
            logger.info("Checking: " + owner + " " + repo + " " + branch);
            GitHubClient client = new GitHubClient();
            client.setOAuth2Token(getToken());
            RepositoryService rs = new RepositoryService();
            ContentsService conts = new ContentsService();
            Repository rep = rs.getRepository(owner, repo);
            try {
                List<RepositoryContents> content = conts.getContents(rep, "SCAVA.URL", branch);
                RepositoryContents rc = content.get(0);
                String server = new String(Base64.getDecoder().decode(rc.getContent().strip())).strip();
                logger.info("SCAVA.URL file found");
                addProject(server,owner, repo);
            } catch (IOException e) {
                logger.info("SCAVA.URL file not found");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void addProject(String server, String owner, String repo) {
        logger.info("Adding repo: " + server + " " + owner + " " + repo);
        try {
            restTemplate.postForLocation(server + "/api/artifacts/add/" + owner + "--" + repo, null);
        } catch (Throwable e) {
            logger.error("Crossminer server error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public static RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    private String getToken() {
        String token = null;
        try {
            java.security.Security.addProvider(
                    new org.bouncycastle.jce.provider.BouncyCastleProvider()
            );
            String privateKey = new String(Files.readAllBytes(new File(pem).toPath()));
            privateKey = privateKey.replaceAll("-----.*-----", "").replaceAll("\\n", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey key = kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));

            Instant now = Instant.now();
            Instant exp = now.plus(Duration.ofMinutes(10));

            token = Jwts.builder()
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(exp))
                    .setIssuer(app_id)
                    .signWith(key, SignatureAlgorithm.RS256)
                    .compact();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return token;
    }
}