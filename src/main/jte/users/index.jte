@import org.example.dto.UsersPage
@param UsersPage page

<html>
<head>
    <title>Hex</title>
</head>
<body>
<h1>${page.getHeader()}</h1>
@if(page.getUsers().isEmpty())
    <p>Пока не добавлено ни одного пользователя</p>
@else
    @for(var user : page.getUsers())
        <div>
            <h2><a href="/users/${user.getId()}">${user.getName()}</a></h2>
            <p>${user.getAge()}</p>
        </div>
    @endfor
@endif
</body>
</html>
