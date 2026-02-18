@import org.example.dto.UsersPage
@param UsersPage page

@template.layout.page(
page = page,
content = @`
    <p>Welcome, ${page.getHeader()}.</p>
`,
footer = @`
    <p>Thanks for visiting, come again soon!</p>
`
)
