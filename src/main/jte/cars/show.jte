@import org.example.dto.cars.CarPage
@param CarPage page

@template.layout.page(
page = page,
content = @`
    <h1>${page.getCar().getMake()} ${page.getCar().getModel()}</h1>
`
)
