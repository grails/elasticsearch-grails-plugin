<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add new tweet</title>
    <meta name="layout" content="main"/>
</head>

<body>
<g:form action="post">
    <div>
        <g:select name="user" from="${users}" optionKey="id"/>
    </div>
    <div style="margin-top: 10px">
        <g:textArea name="message" style="width: 100%; height: 100px"/>
    </div>
    <div>
        <g:each in="${tags}" var="tag">
            <div style="margin: 5px 0">
                <g:checkBox name="tags" value="${tag.id}" checked="false"/><span class="tag" style="margin-left: 10px">${tag.name}</span>
            </div>
        </g:each>
    </div>
    <g:submitButton name="add" value="submit"/>
</g:form>
</body>
</html>