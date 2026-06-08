<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Global Search Results</title>
    <meta name="layout" content="main"/>
</head>

<body>
<div style="display: flex; flex-direction: row; gap: 10px; margin: 10px 0">
    <span>Search results for query:</span>
</div>
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
                Domain Object Type <span class="sort-hint" aria-hidden="true"></span>
            </th>
            <th scope="col"
                class="text-body-secondary pe-0 sortable"
                data-sort-key="user"
                role="button"
                tabindex="0"
                aria-label="Sort by user">
                <span class="sort-hint" aria-hidden="true"></span>Entity
            </th>
            <th scope="col"
                class="text-body-secondary ps-0 fw-semibold"
                role="button"
                tabindex="0"
                aria-label="Sort by version"
            >
                Score<span class="sort-hint" aria-hidden="true"></span>
            </th>
        </tr>
        </thead>
        <tbody class="small">
        <g:each var="hit" in="${hits.searchResults}">
            <tr %{-- data-name="${pluginName}" data-version="${tweet.plugin.version}" data-order="${tweet.order}" --}%>
                <td class="text-truncate">
                    ${hit.class.simpleName}
                </td>
                <td class="text-body-secondary" style="font-variant-numeric: tabular-nums;">
                    ${hit.toString()}
                </td>
                <td style="font-variant-numeric: tabular-nums;">
                    ${hits.scores["${hit.id}"]}
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>