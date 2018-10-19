<#import "parts/common.ftl" as comm>

<@comm.page>
<h1>User Edit</h1>
<form method="post" action="/user">
    <input type="text" name="username" value="${user.username}">
    <#list roles as role >
    <div>
        <label>
            <input type="checkbox" name="${role}"
                ${user.roles?seq_contains(role)?string("checked", "")}>${role}
        </label>

    </div>
    </#list>
    <input type="hidden" value="${user.id}" name="userId">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit">Save</button>

</form>
</@comm.page>