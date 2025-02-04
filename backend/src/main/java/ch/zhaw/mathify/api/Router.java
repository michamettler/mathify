package ch.zhaw.mathify.api;

import ch.zhaw.mathify.App;
import ch.zhaw.mathify.api.controller.ExerciseApiController;
import ch.zhaw.mathify.api.controller.UserApiController;
import ch.zhaw.mathify.api.security.AuthenticationHandler;
import ch.zhaw.mathify.api.security.AuthorizationHandler;
import ch.zhaw.mathify.model.Role;
import ch.zhaw.mathify.model.Scoreboard;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import ch.zhaw.mathify.repository.UserRepository;
import ch.zhaw.mathify.util.JsonMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;
import io.javalin.http.Context;
import io.javalin.router.JavalinDefaultRouting;
import io.javalin.security.BasicAuthCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Class responsible for creating endpoints to communicate between Front- and Backend
 */
public class Router {
    private static final Logger LOG = LoggerFactory.getLogger(Router.class);
    private final ExerciseApiController exerciseApiController;
    private final UserApiController userApiController;
    private final UserRepository userRepository;
    private final AuthorizationHandler authorizationHandler;
    private final AuthenticationHandler authenticationHandler;
    private final Scoreboard scoreboard;
    private final int port;
    private Javalin app;

    /**
     * Constructor to create a new Router instance
     */
    public Router() {
        scoreboard = new Scoreboard();
        userApiController = new UserApiController();
        exerciseApiController = new ExerciseApiController();
        userRepository = UserRepository.getInstance();
        authorizationHandler = new AuthorizationHandler();
        authenticationHandler = new AuthenticationHandler();

        port = App.getSettings().getHttp().port();
    }

    /**
     * Starts the Javalin instance including the Endpoints
     */
    public void startApplication() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::closeApplication));
        Optional<SslPlugin> sslPluginOptional = doSslPluginConfig();
        app = Javalin.create(config -> {
            sslPluginOptional.ifPresent(config::registerPlugin);
            config.router.apiBuilder(this::register);
            config.router.mount(this::handleAuthenticationAndAuthorization);
        });

        registerErrorHandler();

        app.start(port);
    }

    private void register() {
        get("/", ctx -> ctx.redirect("/welcome"));
        get("/welcome", ctx -> {
            ctx.result("Welcome to Mathify!");
            LOG.info("Welcome page was accessed");
        }, Role.ANONYMOUS);
        get("/login", authenticationHandler::login, Role.ANONYMOUS);
        get("/scoreboard", ctx -> {
            ctx.json(scoreboard.createRanking());
            LOG.info("Scoreboard page was accessed");
        }, Role.USER, Role.ADMIN);
        get("/page-not-found", ctx -> {
            ctx.result("Page " + ctx.queryParam("invalid-endpoint") + " not found!");
            LOG.error("Page {} not found!", ctx.queryParam("invalid-endpoint"));
        }, Role.ANONYMOUS);

        path("/users", () -> {
            get("/{user-token}", ctx -> userApiController.getOne(ctx, ctx.pathParam("user-token")), Role.USER, Role.SYSTEM_CRUD, Role.ADMIN);
            get(userApiController::getAll, Role.SYSTEM_CRUD, Role.ADMIN);
            post(userApiController::create, Role.SYSTEM_CRUD, Role.ADMIN);
            patch("/{user-token}", ctx -> userApiController.update(ctx, ctx.pathParam("user-token")), Role.USER, Role.SYSTEM_CRUD, Role.ADMIN);
            delete("/{user-token}", ctx -> userApiController.delete(ctx, ctx.pathParam("user-token")), Role.SYSTEM_CRUD, Role.ADMIN);
        });
        crud("/users/{user-guid}", userApiController, Role.SYSTEM_CRUD, Role.ADMIN);

        path("/exercise", () -> {
            get(exerciseApiController::getExerciseForUser, Role.USER, Role.ADMIN);
            get("/subtypes", ctx -> ctx.json(JsonMapper.toJson(ExerciseSubType.values())), Role.USER, Role.ADMIN);
            post("/verify", exerciseApiController::verifyResult, Role.USER, Role.ADMIN);
        });

        post("/register", authenticationHandler::register, Role.ANONYMOUS);
        post("/stop", this::invokeStop, Role.ADMIN);
    }

    private void registerErrorHandler() {
        app.error(401, ctx -> {
            Optional<BasicAuthCredentials> credentials = Optional.ofNullable(ctx.basicAuthCredentials());
            credentials.ifPresentOrElse(
                    basicAuthCredentials -> LOG.error("User {} is not allowed to access the {} endpoint", basicAuthCredentials.getUsername(), ctx.path()),
                    () -> LOG.error("Anonymous user is not allowed to access the {} endpoint", ctx.path())
            );
            ctx.json("Unauthorized access! Please provide valid credentials!");
        });
        app.exception(MismatchedInputException.class, (e, ctx) -> {
            ctx.result("Invalid JSON format!");
            LOG.error("Invalid JSON format!");
            ctx.status(400);
        });
        app.error(404, ctx -> ctx.redirect("/page-not-found?invalid-endpoint=" + ctx.path()));
    }

    private void handleAuthenticationAndAuthorization(JavalinDefaultRouting router) {
        router.beforeMatched(ctx -> {
            String token = ctx.sessionAttribute("Authorization");

            if (token == null) {
                ctx.attribute("role", Role.ANONYMOUS);
            }

            authorizationHandler.validateEndpointAccess(ctx);
        });
    }

    private void invokeStop(Context ctx) {
        ctx.result("Stopping server...");
        LOG.info("Server was stopped");
        closeApplication();
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

    private void closeApplication() {
        if (app != null) {
            app.stop();
            userRepository.save();
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
