<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>Verifying...</title>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src='https://www.recaptcha.net/recaptcha/api.js?render=6LcZlGQaAAAAADXOvTd-hDTkUA9ZnbD9sfep98dE'></script>
</head>
<body>
<span id="span"></span>
</body>
<script>
    grecaptcha.ready(function() {
        grecaptcha.execute('6LcZlGQaAAAAADXOvTd-hDTkUA9ZnbD9sfep98dE').then(function(token) {
            console.log(token);
            $.ajax({
                url: '/amazon/checkBot',
                type: 'POST',
                data: {"response":token},
                success: function(result) {
                   window.location.href=result;
                }
            });
        });
    });
</script>

</html>