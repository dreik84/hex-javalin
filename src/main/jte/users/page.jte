@import org.example.dto.UsersPage
@param UsersPage page

@template.layout.page(
page = page,
content = @`
    <p>Welcome, ${page.getHeader()}.</p>

    <form action="/users" method="get">
        <input type="search" name="page" value="${page.getHeader()}">
        <input type="submit" value="Search">
    </form>
`,
footer = @`
    <p>Thanks for visiting, come again soon!</p>
`
)
