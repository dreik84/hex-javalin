package org.example;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;

public class HelloWorld {
    public static void main(String[] args) {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        app.get("/", ctx -> ctx.result("Hello World"));

        app.get("/users", ctx -> {
            var page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
            ctx.result("GET /users | Query param page: " + page);
        });

        app.get("/users/{id}", ctx -> {
            var id = ctx.pathParamAsClass("id", Integer.class).getOrDefault(1);
            if (id == 5) throw new NotFoundResponse("Entity with id " + id + " not found");

            ctx.result("GET /users | Path param id: " + id);
        });

        app.post("/users", ctx -> ctx.result("POST /users"));
        app.start();
    }
}
