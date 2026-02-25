package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.dto.UserPage;
import org.example.dto.UsersPage;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.util.NamedRoutes;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UsersController {

    public static void index(Context ctx) {
        var users = UserRepository.getEntities();
        var page = new UsersPage(users, "List of users");
        ctx.render("users/index.jte", model("page", page));
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var user = UserRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new UserPage(user);
        ctx.render("users/show.jte", model("page", page));
    }

    public static void build(Context ctx) {
        ctx.render("users/build.jte");
    }

    public static void create(Context ctx) {
        var name = ctx.formParam("name");
        var email = ctx.formParam("email");
        var password = ctx.formParam("password");
        var age = 18;

        var user = new User(name, email, password, age);
        UserRepository.save(user);
        ctx.redirect(NamedRoutes.usersPath());
    }

    public static void edit(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var user = UserRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new UserPage(user);
        ctx.render("users/edit.jte", model("page", page));
    }


    public static void update(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();

        var name = ctx.formParam("name");
        var email = ctx.formParam("email");
        var password = ctx.formParam("password");

        var user = UserRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        UserRepository.save(user);
        ctx.redirect(NamedRoutes.usersPath());
    }

    public static void destroy(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        UserRepository.delete(id);
        ctx.redirect(NamedRoutes.usersPath());
    }
}
