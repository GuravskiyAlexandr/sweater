<#import "parts/common.ftl" as comm>
<#import "parts/login.ftl" as log>


<@comm.page>

<div class="form-row">
    <div class="form-group col-md-5">
        <form method="get" action="/main" class="form-inline">
            <input type="text" name="filtr" class="form-control" value="${filtr?ifExists}">
            <button class="btn btn-primary ml-2" type="submit">Search</button>
        </form>
    </div>
</div>


<p>
    <a class="btn btn-primary" data-toggle="collapse"
       href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new Messege
    </a>
</p>

<div class="collapse <#if messege??>show</#if>" id="collapseExample">
    <div>
        <div class="form-group ">
            <form method="post" action="/main" enctype="multipart/form-data">

                <div class="form-group">
                    <input type="text" name="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                           placeholder=" Enter message " value="<#if messege??>${messege.text}</#if>">

                    <div class="invalid-feedback">
                        <#if textError??>
                            ${textError}
                        </#if>
                    </div>

                </div>
                <div class="form-group">
                    <input type="text" name="tag" class="form-control ${(tagError??)?string('is-invalid', '')}"
                           placeholder=" TEG " value="<#if messege??>${messege.tag}</#if>">

                    <div class="invalid-feedback">
                        <#if tagError??>
                            ${tagError}
                        </#if>
                    </div>

                </div>


                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" class="custom-file-input" id="customFile">
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="form-group">
                    <button type="submit" class="btn btn-primary ml-2">ADD</button>
                </div>
            </form>
        </div>
    </div>
</div>

<h2>List Messege</h2>

<div class="card-columns">
    <#list messeges as messege>
        <div class="card my-3">
            <div>
                   <#if messege.filename??>
                       <img class="card-img-top" src="/img/${messege.filename}">
                   </#if>
            </div>
            <div>
                <span>${messege.text}</span>
                <i>${messege.tag}</i>
            </div>
            <div class="card-footer text-muted">
                <strong>${messege.getAuthor().getUsername()}</strong>
            </div>
        </div>
    </#list>
</div>

</@comm.page>