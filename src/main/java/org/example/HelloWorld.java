package org.example;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import org.apache.commons.text.StringEscapeUtils;
import org.example.dto.UserPage;
import org.example.dto.UsersPage;
import org.example.model.User;
import org.example.repository.UserRepository;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class HelloWorld {
    public static void main(String[] args) {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> ctx.render("index.jte"));

        app.get("users/build", ctx -> {
            ctx.header("content-type: text/html; charset=UTF-8");
            ctx.render("users/build.jte");
        });

        app.get("/users", ctx -> {
//            var pageNum = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
            var pageNum = ctx.queryParam("page");
            var users = List.of(
                    new User(1L, "Tom", "tom@gmail.com", "1234", 45),
                    new User(2L, "Janny", "janny@mail.ru", "4321", 25),
                    new User(3L, "Jack", "jack@ya.ru", "7878", 18)
            );
            var usersPage = new UsersPage(users, pageNum);

            ctx.render("users/page.jte", model("page", usersPage));
        });

        app.get("/users/{id}", ctx -> {
            var id = ctx.pathParamAsClass("id", Long.class).getOrDefault(1L);
            User user = new User(id, "John", "john@ya.ru", "5454", 35);
            UserPage page = new UserPage(user);
            if (id == 5) throw new NotFoundResponse("Entity with id " + id + " not found");

            ctx.render("users/show.jte", model("page", page));
        });

        app.get("/xss/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var escapedId = StringEscapeUtils.escapeHtml4(id);
            ctx.contentType("text/html");

            ctx.result("<h2>" + escapedId + "</h2>");
        });

        app.post("/users", ctx -> {
            var name = ctx.formParam("name").trim();
            var email = ctx.formParam("email").trim().toLowerCase();
            var password = ctx.formParam("password");
            var passwordConfirmation = ctx.formParam("passwordConfirmation");
            var age = 25;

            var user = new User(name, email, password, age);
            UserRepository.save(user);
            ctx.redirect("/users");
        });
        app.start(7070);
    }
}
