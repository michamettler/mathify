package ch.zhaw.mathify.controller;

import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static io.javalin.apibuilder.ApiBuilder.crud;

/**
 * Class responsible for creating endpoints to communicate between Front- and Backend
 */
public class Router {
    private static final Logger LOG = LoggerFactory.getLogger(Router.class);
    public static final int HTTP_PORT = 7000;
    public static final int HTTPS_PORT = 7070;
    private Javalin app;

    /**
     * Starts the Javalin instance including the Endpoints
     */
    public void startApplication() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::closeApplication));

        boolean sslActive = (new File("cert/cert.pem")).exists() && (new File("cert/key.pem")).exists();
        SslPlugin sslPlugin;
        if (sslActive) {
            LOG.info("SSL certificate found");
            sslPlugin = new SslPlugin(config -> {
                config.pemFromClasspath("cert/cert.pem", "cert/key.pem");
                config.insecurePort = HTTP_PORT;
                config.securePort = HTTPS_PORT;
                config.sniHostCheck = false;
                config.redirect = true;
            });
        } else {
            sslPlugin = null;
            LOG.error("SSL certificate not found, using HTTP only!Make sure to provide a cert.pem and key.pem in the cert folder as soon as possible.");
        }

        app = Javalin.create(config -> {
                            if (sslActive) config.registerPlugin(sslPlugin);
                            config.router.apiBuilder(() ->
                                    crud("users/{user-guid}", new UserController())
                            );
                        }
                )
                .start(HTTP_PORT);
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

    /**
     * Shuts the Javalin instance including the Endpoints down
     */
    public void closeApplication() {
        if (app != null) {
            app.stop();
        }
    }
}
