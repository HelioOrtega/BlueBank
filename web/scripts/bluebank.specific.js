$(document).ready(function () {
    $(window).hashchange();
    var operationTypeNumber = 1;
});
$(window).hashchange(function () {
    var hash = location.hash;
    if (hash === "#register") {
        $("#content").load("register.html", function () {
            $("#cpf-conta").keydown(function () {
                try {
                    $("#cpf-conta").unmask();
                } catch (e) {
                }
                var size = $("#cpf-conta").val().length;
                if (size < 11) {
                    $("#cpf-conta").mask("999.999.999-99");
                } else {
                    $("#cpf-conta").mask("99.999.999/9999-99");
                }
            });

        });
    } else if (hash === "#history") {
        $("#content").load("history.html");
    } else if (hash === "#operations") {
        $("#content").load("operations.html", function () {
            $("#deposit-link").click(function () {
                $("#operation-content").load("opt/deposit.html");
                operationTypeNumber = 1;
            });
            $("#withdrawal-link").click(function () {
                $("#operation-content").load("opt/withdrawal.html");
                operationTypeNumber = 2;
            });
            $("#transfer-link").click(function () {
                $("#operation-content").load("opt/transfer.html");
                operationTypeNumber = 3;
            });
            $("#transfer-link").click();
        });
    } else if (hash === "#balance") {
        $("#content").load("balance.html");
        operationTypeNumber = 4;
    } else {
        window.location.hash = "#register";
    }
});
$(document).on("click", "#register-button", function () {
    $("#register-form").submit(function (e) {
        $.ajax({
            type: "POST",
            url: "RegisterServlet",
            data: $("#register-form").serialize(), // serializes the form's elements.
            success: function (data) {
                var parsedData = $.parseJSON(data);
                if (parsedData) {
                    $("#warning-area").addClass("alert-danger").show().html("<h5>Um cadastro com estes dados já existe.</h5>").delay(3000).hide("slow");
                } else {
                    $("#warning-area").removeClass("alert-danger").show().html("<h5>Cadastro efetuado com sucesso.</h5>").delay(2000).hide("slow");
                }
            }
        });
        e.preventDefault();
    });
});
$(document).on("click", "#deposit-button", function () {
    $("#deposit-form").submit(function (e) {
        var formData = $("#deposit-form").serialize();
        formData += '&operationType=' + operationTypeNumber;
        console.log(formData);
        request = $.ajax({
            type: "POST",
            url: "OperationServlet",
            data: formData, // serializes the form's elements.
            success: function (data) {
                // var parsedData = $.parseJSON(data);
                console.log("Yep");
            }
        });

        e.preventDefault();
    });
});
$(document).on("click", "#transfer-button", function () {
    $("#transfer-form").submit(function (e) {
        var formData = $("#transfer-form").serialize();
        formData += '&operationType=' + operationTypeNumber;
        console.log(formData);
        request = $.ajax({
            type: "POST",
            url: "OperationServlet",
            data: formData, // serializes the form's elements.
            success: function (data) {
                // var parsedData = $.parseJSON(data);
                console.log("Yep");
            }
        });

        e.preventDefault();
    });
});
$(document).on("click", "#balance-button", function () {
    $("#balance-form").submit(function (e) {
        var formData = $("#balance-form").serialize();
        formData += '&operationType=' + operationTypeNumber;
        console.log(formData);
        request = $.ajax({
            type: "POST",
            url: "OperationServlet",
            data: formData, // serializes the form's elements.
            success: function (data) {
                $("#resultado-saldo").hide().html("<h5>O saldo atual da conta é: " + data + "</h5>").fadeIn("slow");
                console.log("Yep");
            }
        });

        e.preventDefault();
    });
});



