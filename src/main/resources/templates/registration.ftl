<#import "parts/common.ftl" as comm>
<#import "parts/login.ftl" as log>


<@comm.page>
<div>
    ${messege?ifExists}
</div>
    <@log.login "/registration" true/>

</@comm.page>
