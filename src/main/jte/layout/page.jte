@import org.example.dto.UsersPage
@import gg.jte.Content

@param UsersPage page
@param Content content
@param Content footer = null

<head>
    @if(page.getHeader() != null)
        <meta name="description" content="${page.getHeader()}">
    @endif
    <title>${page.getHeader()}</title>
</head>
<body>
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