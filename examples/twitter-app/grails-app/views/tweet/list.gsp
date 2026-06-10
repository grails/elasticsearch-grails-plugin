<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>All tweets</title>
    <meta name="layout" content="main"/>
</head>

<body>
<div class="table-responsive">
    <table class="table table-sm table-striped table-hover" data-sortable="true">
        <thead class="table-light small">
        <tr>
            <th scope="col"
                class="text-body-secondary ps-0 fw-semibold sortable"
                data-sort-key="name"
                role="button"
                tabindex="0"
                aria-label="Sort by name">
                Name <span class="sort-hint" aria-hidden="true"></span>
            </th>
            <th scope="col"
                class="text-body-secondary pe-0 sortable"
                data-sort-key="user"
                role="button"
                tabindex="0"
                aria-label="Sort by user">
                <span class="sort-hint" aria-hidden="true"></span>User
            </th>
            <th scope="col"
                class="text-body-secondary ps-0 fw-semibold"
                role="button"
                tabindex="0"
                aria-label="Sort by version"
            >
                Message<span class="sort-hint" aria-hidden="true"></span>
            </th>
        </tr>
        </thead>
        <tbody class="small">
        <g:each var="tweet" in="${tweets}">
            <tr %{-- data-name="${pluginName}" data-version="${tweet.plugin.version}" data-order="${tweet.order}" --}%>
                <td class="text-truncate">
                    ${tweet.dateCreated}
                </td>
                <td class="text-body-secondary" style="font-variant-numeric: tabular-nums;">
                    ${tweet.user.lastname}, ${tweet.user.firstname}
                </td>
                <td style="font-variant-numeric: tabular-nums;">
                    <div class="message">${tweet.message}</div>
                    <div style="margin-top: 5px; ">
                        <g:each in="${tweet.tags}" var="tag">
                            <span class="tag">${tag.name}</span>
                        </g:each>
                    </div>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>