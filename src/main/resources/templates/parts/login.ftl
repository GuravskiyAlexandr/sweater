<#macro login path isRegisterForm>
<div class="form-width" ">
    <h1><#if isRegisterForm>Registration<#else >Enter login</#if></h1>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name :</label>
            <div class="col-sm-5">
                <input type="text" name="username" class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       value="<#if user??>${user.username}</#if>" placeholder=" Enter user name"/>
                <div class="invalid-feedback">
                    <#if usernameError??>
                        ${usernameError}
                    </#if>
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password :</label>
            <div class="col-sm-5">
                <input type="password" name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder=" Enter password"/> </label>
                <div class="invalid-feedback">
                    <#if passwordError??>
                        ${passwordError}
                    </#if>
                </div>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Password :</label>
                <div class="col-sm-5">
                    <input type="password" name="password2"
                           class="form-control ${(password2Error??)?string('is-invalid', '')}"
                           placeholder=" Retype enter password"/> </label>
                    <div class="invalid-feedback">
                    <#if password2Error??>
                        ${password2Error}
                    </#if>
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email :</label>
                <div class="col-sm-5">
                    <input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid', '')}"
                           value="<#if user??>${user.email}</#if>" placeholder=" some@some.com"/> </label>
                    <div class="invalid-feedback">
                        <#if emailError??>
                            ${emailError}
                        </#if>
                    </div>
                </div>
            </div>
            <div>
                <div class="g-recaptcha" data-sitekey="6LeRiXUUAAAAAHITLCsP5Er_NI3qalUVM4jGBBk5"></div>
                <#if captchaError??>
                <div class="alert alert-danger" role="alert">
                    ${captchaError}
                </div>
                </#if>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <div class="form-group row">
            <div class="col-sm-5">
                <button type="submit" class="btn btn-primary">
                <#if isRegisterForm>Registration<#else>Sign in</#if>
                </button>
            </div>
        </div>
    <#if !isRegisterForm>
        <a href="/registration"><h3>Registration</h3></a>
    </#if>
    </form>
</div>

</#macro>

<#macro logout >
    <form action="/logout" method="post">
        <button type="submit" class="btn btn-primary">Sign Out</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>
</#macro>