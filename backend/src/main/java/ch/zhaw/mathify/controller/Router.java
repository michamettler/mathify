package ch.zhaw.mathify.controller;

import ch.zhaw.mathify.App;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

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
        Runtime.getRuntime().addShutdownHook(new Thread(this::closeApplication));

        Optional<SslPlugin> sslPluginOptional = doSslPluginConfig();
        app = Javalin.create(config -> {
                            sslPluginOptional.ifPresent(config::registerPlugin);
                            config.router.apiBuilder(() ->
                                    crud("users/{user-guid}", new UserController())
                            );
                        }
                )
                .start(App.getSettings().getHttp().port());
        app.exception(MismatchedInputException.class, (e, ctx) -> {
            ctx.result("Invalid JSON format!");
            LOG.error("Invalid JSON format!");
            ctx.status(400);
        });
        app.get("/", ctx -> ctx.redirect("/welcome"));
        app.get("/welcome", ctx -> {
            ctx.result("Welcome to Mathify!");
            LOG.info("welcome page was accessed");
        });
        app.get("/page-not-found", ctx -> {
            ctx.result("Page " + ctx.queryParam("invalid-endpoint") + " not found!");
            LOG.error("Page {} not found!", ctx.queryParam("invalid-endpoint"));
        });
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
    public void closeApplication() {
        if (app != null) {
            app.stop();
        }
    }
}
