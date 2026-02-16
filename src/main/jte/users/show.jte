@import org.example.dto.UserPage
@param UserPage page

<main>
    <h1>${page.getUser().getName()} with id ${page.getUser().getId()}</h1>
    <p>${page.getUser().getAge()} years old</p>
</main>
