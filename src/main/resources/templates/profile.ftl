<#import "parts/common.ftl" as comm>


<@comm.page>
<h5>${username?ifExists}</h5>
<div>
    ${messege?ifExists}
</div>

    <form method="post">

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password :</label>
            <div class="col-sm-5">
                <input type="password" name="password" class="form-control" placeholder=" Enter password"/> </label>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email :</label>
            <div class="col-sm-5">
                <input type="email" name="email" class="form-control" value="${email!''}" placeholder=" some@some.com"/> </label>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <div class="form-group row">
            <div class="col-sm-5">
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </div>
    </form>
</div>

</@comm.page>