@import org.example.dto.MainPage
@param MainPage page 

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Hello JTE!</title>
</head>
<body>
<div class="col-lg-8 mx-auto p-4 py-md-5">
    <main>
        <h1 class="text-body-emphasis">Привет, Hex!</h1>
        <p>Javalin + jte</p>
        @if(!page.isVisited())
            <p>Это сообщение показывается только один раз.</p>
        @endif
    </main>
</div>
</body>
</html>