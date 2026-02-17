package org.example;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import org.example.dto.UserPage;
import org.example.dto.UsersPage;
import org.example.model.User;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class HelloWorld {
    public static void main(String[] args) {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> ctx.render("index.jte"));

        app.get("/users", ctx -> {
            var pageNum = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
            var users = List.of(
                    new User(1L, "Tom", 45),
                    new User(2L, "Janny", 25),
                    new User(3L, "Jack", 18)
            );
            var usersPage = new UsersPage(users, "List of Users");

            ctx.render("users/page.jte", model("page", usersPage));
        });

        app.get("/users/{id}", ctx -> {
            var id = ctx.pathParamAsClass("id", Long.class).getOrDefault(1L);
            User user = new User(id, "John", 35);
            UserPage page = new UserPage(user);
            if (id == 5) throw new NotFoundResponse("Entity with id " + id + " not found");

            ctx.render("users/show.jte", model("page", page));
        });

        app.post("/users", ctx -> ctx.result("POST /users"));
        app.start();
    }
}
