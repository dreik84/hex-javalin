@import org.example.util.NamedRoutes
@import org.example.dto.UsersPage
@param UsersPage page

@template.layout.page(
page = page,
content = @`
    Тут шаблон
`
)