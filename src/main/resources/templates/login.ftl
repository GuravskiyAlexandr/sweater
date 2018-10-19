<#import "parts/common.ftl" as comm>
<#import "parts/login.ftl" as log>


<@comm.page>
<div>
    <div class="alert alert-${messegeType?ifExists}" role="alert">
        ${messege?ifExists}
    </div>
</div>
<div>
    <#if Session??&& Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
</div>

<@log.login "/login" false/>

</@comm.page>