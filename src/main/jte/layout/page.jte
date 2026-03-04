@import org.example.util.NamedRoutes
@import org.example.dto.BasePage
@import org.example.dto.UsersPage
@import gg.jte.Content

@param UsersPage page
@param BasePage basePage
@param Content content
@param Content footer = null

<head>
    @if(page.getHeader() != null)
        <meta name="description" content="${page.getHeader()}">
    @endif
    <title>${page.getHeader()}</title>
</head>
<body>
@if(basePage != null && basePage.getFlash() != null)
    <p>${page.getFlash()}</p>
@endif

<h1>${page.getHeader()}</h1>
<div class="content">
    ${content}
</div>
@if (footer != null)
    <div class="footer">
        ${footer}
    </div>
@endif
</body>