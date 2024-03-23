package ch.zhaw.mathify.controller;

import io.javalin.Javalin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.javalin.community.ssl.SslPlugin;

import static io.javalin.apibuilder.ApiBuilder.crud;

/**
 * Class responsible for creating endpoints to communicate between Front- and Backend
 */
public class Router {
    private static final Logger LOG = LogManager.getLogger(Router.class);
    private Javalin app;

    /**
     * Starts the Javalin instance including the Endpoints
     */
    public void startApplication() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::closeApplication));

        SslPlugin sslPlugin = new SslPlugin(config -> {
            config.pemFromClasspath("cert/cert.pem", "cert/key.pem");
            config.insecurePort = 7000;
            config.securePort = 7001;
            config.sniHostCheck = false;
            config.redirect = true;
        });

        app = Javalin.create(config -> {
                            config.registerPlugin(sslPlugin);
                            config.router.apiBuilder(() ->
                                    crud("users/{user-guid}", new UserController())
                            );
                        }
                )
                .start();
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
