
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
    <head> 
        <meta name="viewport" content="width=device-width, initial-scale=1">


        <!-- Website CSS style -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/custom.css" rel="stylesheet">
        <!-- Website Font style -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
        <!-- Google Fonts -->
        <link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>

        <script src="scripts/jquery-3.1.1.min.js"></script>
        <script src="scripts/jquery.ba-hashchange.min.js"></script>
        <script src="scripts/jquery.mask.js"></script>
        <script src="scripts/bootstrap.min.js"></script>
        <script src="scripts/bluebank.specific.js"></script>
        <title>BlueBank</title>
    </head>
    <body>
        <div class="container">
            <div class="row main">
                <div class="main-login main-center">
                    <h4><strong>Blue Bank</strong></h4>
                    <ul class="list-inline">
                        <li><a href="#register">Cadastro</a></li>
                        <li><a href="#transactions">Transações</a></li>
                        <li><a href="#operations">Operações</a></li>
                    </ul>
                    <hr />
                    <div id="warning-area" class="alert" style="display: none;">
                    </div>
                    <div id="content"></div>
                </div>
            </div>
        </div>

    </body>
</html>
