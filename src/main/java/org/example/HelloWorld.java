package org.example;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.example.controller.UsersController;

public class HelloWorld {
    public static void main(String[] args) {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.before(ctx -> System.out.println("before middleware"));
//        app.before(Context::skipRemainingHandlers); // terminal
        app.get("/", ctx -> ctx.render("index.jte"));
        app.after(ctx -> System.out.println("after middleware"));

        app.get("/users", UsersController::index);
        app.get("/users/build", UsersController::build);
        app.get("/users/{id}", UsersController::show);
        app.post("/users", UsersController::create);
        app.get("/users/{id}/edit", UsersController::edit);
        app.patch("/users/{id}", UsersController::update);
        app.delete("/users/{id}", UsersController::destroy);

//        // build user
//        app.get(NamedRoutes.buildUserPath(), ctx -> {
//            ctx.contentType("text/html; charset=UTF-8");
//            var page = new BuildUserPage();
//            ctx.render("users/build.jte", model("page", page));
//        });
//
//        // users
//        app.get(NamedRoutes.usersPath(), ctx -> {
////            var pageNum = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
//            var pageNum = ctx.queryParam("page");
//            var users = List.of(
//                    new User(1L, "Tom", "tom@gmail.com", "1234", 45),
//                    new User(2L, "Janny", "janny@mail.ru", "4321", 25),
//                    new User(3L, "Jack", "jack@ya.ru", "7878", 18)
//            );
//            var usersPage = new UsersPage(users, pageNum);
//
//            ctx.render("users/page.jte", model("page", usersPage));
//        });
//
//        // users id
//        app.get(NamedRoutes.usersPath("{id}"), ctx -> {
//            var id = ctx.pathParamAsClass("id", Long.class).getOrDefault(1L);
//            User user = new User(id, "John", "john@ya.ru", "5454", 35);
//            UserPage page = new UserPage(user);
//            if (id == 5) throw new NotFoundResponse("Entity with id " + id + " not found");
//
//            ctx.render("users/show.jte", model("page", page));
//        });
//
//        app.get("/xss/{id}", ctx -> {
//            var id = ctx.pathParam("id");
//            var escapedId = StringEscapeUtils.escapeHtml4(id);
//            ctx.contentType("text/html");
//
//            ctx.result("<h2>" + escapedId + "</h2>");
//        });
//
//        // users
//        app.post(NamedRoutes.usersPath(), ctx -> {
//            var name = ctx.formParam("name").trim();
//            var email = ctx.formParam("email").trim().toLowerCase();
//            var age = 25;
//
//            try {
//                var passwordConfirmation = ctx.formParam("passwordConfirmation");
//                var password = ctx.formParamAsClass("password", String.class)
//                        .check(value -> value.equals(passwordConfirmation), "Пароли не совпадают")
//                        .check(value -> value.length() > 6, "У пароля недостаточная длина")
//                        .get();
//
//                var user = new User(name, email, password, age);
//                UserRepository.save(user);
//                ctx.redirect("/users");
//            } catch (ValidationException e) {
//                var page = new BuildUserPage(name, email, age, e.getErrors());
//                ctx.contentType("text/html; charset=UTF-8");
//                ctx.render("users/build.jte", model("page", page));
//            }
//        });

        app.start(7070);
    }
}
