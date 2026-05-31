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
                class="text-body-secondary ps-0 fw-semibold text-end"
                role="button"
                tabindex="0"
                aria-label="Sort by version"
            >
                Message<span class="sort-hint" aria-hidden="true"></span>
            </th>
%{--            <th scope="col"--}%
%{--                class="text-body-secondary text-end pe-0 sortable"--}%
%{--                data-sort-key="order"--}%
%{--                role="button"--}%
%{--                tabindex="0"--}%
%{--                aria-label="Sort by load order">--}%
%{--                <span class="sort-hint" aria-hidden="true"></span> Load order--}%
%{--            </th>--}%
        </tr>
        </thead>
        <tbody class="small">
        <g:each var="tweet" in="${tweets}">
            <tr %{-- data-name="${pluginName}" data-version="${tweet.plugin.version}" data-order="${tweet.order}" --}%>
                <td class="text-truncate">
                    ${tweet.dateCreated}
                </td>
                <td class="text-end" style="font-variant-numeric: tabular-nums;">
                    ${tweet.message}
                </td>
%{--                <td class="text-end text-body-secondary" style="font-variant-numeric: tabular-nums;">--}%
%{--                    ${tweet.order}--}%
%{--                </td>--}%
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>