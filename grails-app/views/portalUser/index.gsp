<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'portalUser.label', default: 'PortalUser')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-portalUser" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><g:link controller="login" action="logout"><g:message code="Logout"/></g:link></li>
            </ul>
        </div>
        <div id="list-portalUser" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${portalUserList}" />

            <div class="pagination">
                <g:paginate total="${portalUserCount ?: 0}" />
            </div>
        </div>
    </body>
</html>