package ch.zhaw.mathify.controller;

import ch.zhaw.mathify.App;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.util.JsonMapper;
import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;
import io.javalin.security.BasicAuthCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import static ch.zhaw.mathify.controller.UserController.USERS_JSON_FILE;
import static io.javalin.apibuilder.ApiBuilder.crud;

/**
 * Class responsible for creating endpoints to communicate between Front- and Backend
 */
public class Router {
    private static final Logger LOG = LoggerFactory.getLogger(Router.class);
    private Javalin app;

    /**
     * Starts the Javalin instance including the Endpoints
     */
    public void startApplication() {
        UserController handler = new UserController();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> closeApplication(handler)));

        Optional<SslPlugin> sslPluginOptional = doSslPluginConfig();
        app = Javalin.create(config -> {
                            sslPluginOptional.ifPresent(config::registerPlugin);
                            config.router.apiBuilder(() ->
                                    crud("users/{user-guid}", handler, AccessManager.Role.SYSTEM_CRUD)
                            );
                            config.router.mount(router -> {
                                router.beforeMatched(ctx -> {
                                    List<User> userList = handler.getUsers();
                                    Optional<BasicAuthCredentials> credentials = Optional.ofNullable(ctx.basicAuthCredentials());
                                    if (credentials.isEmpty()) {
                                        ctx.attribute("role", AccessManager.Role.ANONYMOUS);
                                    } else {
                                        for (User user : userList) {
                                            if (user.getUsername().equals(credentials.get().getUsername())
                                                    && User.verifyPassword(credentials.get().getPassword(), user.getPassword())) {
                                                LOG.info(user.getUsername() + " was authenticated successfully");
                                                ctx.attribute("role", user.getRole());
                                            }
                                        }
                                    }
                                    AccessManager.validateEndpointAccess(ctx);
                                });
                            });
                        }
                )
                .start(App.getSettings().getHttp().port());
        app.get("/", ctx -> ctx.redirect("/welcome"));
        app.get("/welcome", ctx -> {
            ctx.result("Welcome to Mathify!");
            LOG.info("welcome page was accessed");
        }, AccessManager.Role.ANONYMOUS);
        app.get("/page-not-found", ctx -> {
            ctx.result("Page " + ctx.queryParam("invalid-endpoint") + " not found!");
            LOG.error("Page {} not found!", ctx.queryParam("invalid-endpoint"));
        }, AccessManager.Role.ANONYMOUS);
        app.post("/stop", ctx -> {
            ctx.result("Stopping server...");
            LOG.info("Server was stopped");
            closeApplication(handler);
        }, AccessManager.Role.ADMIN);
        app.error(404, ctx -> ctx.redirect("/page-not-found?invalid-endpoint=" + ctx.path()));
    }

    private Optional<SslPlugin> doSslPluginConfig() {
        File certFile = Paths.get(App.getSettings().getHttps().cert()).toFile();
        File keyFile = Paths.get(App.getSettings().getHttps().key()).toFile();

        boolean sslActive = certFile.exists() && keyFile.exists();

        SslPlugin sslPlugin;
        if (sslActive) {
            LOG.info("SSL certificate found. Using HTTPS!");
            sslPlugin = new SslPlugin(config -> {
                config.pemFromPath(App.getSettings().getHttps().cert(), App.getSettings().getHttps().key());
                config.insecurePort = App.getSettings().getHttp().port();
                config.securePort = App.getSettings().getHttps().port();
                config.sniHostCheck = false;
                config.redirect = true;
            });
        } else {
            sslPlugin = null;
            LOG.warn("SSL certificate not found, using HTTP only! Make sure to provide a mathifyCert.pem and mathifyCertKey.pem in the cert folder as soon as possible.");
        }
        return Optional.ofNullable(sslPlugin);
    }

    /**
     * Shuts the Javalin instance including the Endpoints down
     */
    public void closeApplication(UserController handler) {
        if (app != null) {
            app.stop();
            JsonMapper.writeUsersToJson(USERS_JSON_FILE, handler.getUsers());
            try {
                Path source = Paths.get("build/resources/main/users.json");
                Path target = Paths.get("src/main/resources/users.json");
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                LOG.error("Could not copy users.json file to src/main/resources/ directory", e);
            }
        }
    }
}
