package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.example.controller.CarController;
import org.example.controller.SessionsController;
import org.example.controller.UsersController;
import org.example.dto.MainPage;
import org.example.repository.BaseRepository;
import org.example.util.NamedRoutes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.stream.Collectors;

import static io.javalin.rendering.template.TemplateUtil.model;

public class HelloWorld {
    public static void main(String[] args) throws SQLException {

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:hex_project;DB_CLOSE_DELAY=-1");

        var dataSource = new HikariDataSource(hikariConfig);

        var url = HelloWorld.class.getClassLoader().getResourceAsStream("schema.sql");
        var sql = new BufferedReader(new InputStreamReader(url))
                .lines().collect(Collectors.joining("\n"));

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.before(ctx -> System.out.println("before middleware"));
//        app.before(Context::skipRemainingHandlers); // terminal
        app.get("/", ctx -> {
            var visited = Boolean.valueOf(ctx.cookie("visited"));
            String currentUser = ctx.sessionAttribute("currentUser");
            String flash = ctx.consumeSessionAttribute("flash");
            var page = new MainPage(visited, currentUser, flash);
            ctx.render("index.jte", model("page", page));
            ctx.cookie("visited", String.valueOf(true));
        });
        app.after(ctx -> System.out.println("after middleware"));

        app.get(NamedRoutes.carsPath(), CarController::index);
        app.get(NamedRoutes.buildCarPath(), CarController::build);
        app.get(NamedRoutes.carPath("{id}"), CarController::show);
        app.post(NamedRoutes.carsPath(), CarController::create);

        app.get("/users", UsersController::index);
        app.get("/users/build", UsersController::build);
        app.get("/users/{id}", UsersController::show);
        app.post("/users", UsersController::create);
        app.get("/users/{id}/edit", UsersController::edit);
        app.patch("/users/{id}", UsersController::update);
        app.delete("/users/{id}", UsersController::destroy);

        // Отображение формы логина
        app.get("/sessions/build", SessionsController::build);
        // Процесс логина
        app.post("/sessions", SessionsController::create);
        // Процесс выхода из аккаунта


//        app.get("/users", ctx -> {
//            var page = new UsersPage(List.of(
//                    new User(1L, "John", "john@gmail.com", "123", 23),
//                    new User(2L, "Sam", "sam@gmail.com", "321", 44)
//            ), "Users Header");
//            page.setFlash(ctx.consumeSessionAttribute("flash"));
//            ctx.render("users/index.jte", model("page", page));
//        });

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
