window.onload=function() {

        <!-- ドロップダウンメニューにhoverをつける -->
        function hover(ele, fn1,fn2) {
            ele.onmouseenter = function(){
                fn1.call(ele);
            }
            document.getElementById("out").onmouseleave  = function() {
                fn2.call(ele);
            }
        }

        var menu_btm = document.getElementById("in");
        hover(
        menu_btm,
         function(){
            menu_btm.click();
        },
        function(){
            menu_btm.click();
        })



    <!-- マーケットデータの取得 -->
     var socket;
        if (typeof (WebSocket) == "undefined") {
            console.log("遗憾：您的浏览器不支持WebSocket");
        } else {
            console.log("恭喜：您的浏览器支持WebSocket");
            //实现化WebSocket对象
            //指定要连接的服务器地址与端口建立连接
            //注意ws、wss使用不同的端口。我使用自签名的证书测试，
            //无法使用wss，浏览器打开WebSocket时报错
            //ws对应http、wss对应https。
            socket = new WebSocket("ws://localhost:8081/ws/asset");
            //连接打开事件
            socket.onopen = function() {
                console.log("Socket 已打开");
                socket.send("消息发送测试(From Client)");
            };
            //收到消息事件
            socket.onmessage = function(msg) {
                $("#messageId").append(msg.data+ "\n");
                console.log(msg.data  );
            };
            //连接关闭事件
            socket.onclose = function() {
                console.log("Socket已关闭");
            };
            //发生了错误事件
            socket.onerror = function() {
                console.log("Socket发生了错误");
            }
            //窗口关闭时，关闭连接
            window.unload=function() {
                socket.close();
            };
        }



//XMLHttpRequestオブジェクトがバックエンドでサーバーとデータのやり取り
        $('.dropdown-toggle').dropdown();
        function ObjData(symbol, exchange1Price, exchange2Price) {
            this.symbol = symbol;
            this.exchange1Price = exchange1Price;
            this.exchange2Price = exchange2Price;
        }

<!-- マーケットデータの反映 -->
        var xhr;
        var jsonStr;
        var jsonObj;
             if(window.XMLHttpRequest) {
                       xhr = new XMLHttpRequest();
                    }
                    else
                    {
                       xhr=new ActiveXObject("Microsoft.XMLHTTP");
                    }

                    xhr.open("GET","http://localhost:8081/arbitrage",true);
                    xhr.send();
                    xhr.onreadystatechange = function() {
                          // readyState == 4リクエスト成功
                                  if(xhr.readyState==4) {
                                      if(xhr.status==200 || xhr.status==304){
                                            var jsonStr = xhr.responseText;
                                            var jsonObj = eval('(' + jsonStr + ')');
                                            var ex1;
                                            var ex2;
                                            var numLength;
                                            var percent;
                                            var difference;
                                                var data = "<tr>" +
                                                            "<td>通貨名</td>" +
                                                            "<td>Binance</td>" +
                                                            "<td>Bithumb</td>" +
                                                            "<td>スプレッド</td>" +
                                                            "<td>パーセンテージ</td>" +
                                                            "</tr>";
                                                 //行数Num指定
                                                 for(var i = 1; i <= jsonObj.length; i++) {
                                                     ex1 = parseFloat(jsonObj[i-1].exchange1Price);
                                                     ex2 = parseFloat(jsonObj[i-1].exchange2Price);

                                                     if(jsonObj[i-1].exchange1Price < jsonObj[i-1].exchange2Price) {
                                                           numLength = ex1.toString().substring(ex1.toString().indexOf('.'),ex1.toString().length).length;
                                                           difference = Math.abs(ex2 - ex1).toFixed(numLength);;
                                                           percent = (difference/ex1 * 100).toFixed(2);
                                                     } else {
                                                           numLength = ex2.toString().substring(ex2.toString().indexOf('.'),ex2.toString().length).length;
                                                           difference = Math.abs(ex1 - ex2).toFixed(numLength);;
                                                           percent = (difference/ex2 * 100).toFixed(2);
                                                     }
                                                     data += "<tr>";
                                                     data += "<td>" + jsonObj[i-1].symbol +"</td>";
                                                     data += "<td>" + ex1 +"</td>";
                                                     data += "<td>" + ex2 +"</td>";
                                                     data += "<td>" + difference + "</td>";


                                                     data += "<td>" + percent +"%</td>";
                                                     data += "</tr>";
                                                 }
                                                 document.getElementById("myTable").innerHTML = data;
                                      }
                    }
        }


}


<!-- 疑似要素の変更 -->
function sort(obj) {
    var name = obj.className;
    if(name.indexOf("sort-up-dark")  != -1) {
        obj.classList.remove("sort-up-dark");
        obj.classList.add("sort-down-dark");
    } else if(name.indexOf("sort-down-dark") != -1) {
        obj.classList.remove("sort-down-dark");
        obj.classList.add("sort-up");
    } else if(name.indexOf("sort-up") != -1) {
        obj.classList.remove("sort-up");
        obj.classList.add("sort-up-dark");
    }
}

function keepdropdown() {
        document.getElementById("in").click();
}




<!-- タブ様式変更と選択された取引所のデータ取得 -->
var times;
var btm1;
var btm2;
function tabChg_GetData(ele){
    if(ele.className.indexOf("active")  == -1) {
        if(times == null && btm2 == null){
            document.getElementById("firstTab").classList.remove("active");
            btm1 = ele;
            btm1.classList.add("active");
            times = 1;
        }else if(times == 0){
            btm2.classList.remove("active");
            btm1 = ele;
            btm1.classList.add("active");
            times = 1;
        }else if(times == 1){
            btm1.classList.remove("active");
            btm2 = ele;
            btm2.classList.add("active");
            times = 0;
        }


    }



}


function chgMenuName(ele) {
    document.getElementById("in").innerHTML = ele.innerHTML;
}

<!-- サーバ接続 -->
function connectServer() {
    <!-- マーケットデータの取得 -->
     var socket;
        if (typeof (WebSocket) == "undefined") {
            console.log("申し訳ございません：ご利用されているブラウザがWebSocketをサポートしていません");
        } else {
            //实现化WebSocket对象
            //指定要连接的服务器地址与端口建立连接
            //注意ws、wss使用不同的端口。我使用自签名的证书测试，
            //无法使用wss，浏览器打开WebSocket时报错
            //ws对应http、wss对应https。

            var url = "ws://localhost:8081/ws/"
            socket = new WebSocket("ws://localhost:8081/ws/binance");
            //连接打开事件
            socket.onopen = function() {
                console.log("Socket 已打开");
                socket.send("消息发送测试(From Client)");
            };
            //收到消息事件
            socket.onmessage = function(msg) {
                $("#messageId").append(msg.data+ "\n");
                console.log(msg.data  );
            };
            //连接关闭事件
            socket.onclose = function() {
                console.log("Socket已关闭");
            };
            //发生了错误事件
            socket.onerror = function() {
                console.log("Socket发生了错误");
            }
            //窗口关闭时，关闭连接
            window.unload=function() {
                socket.close();
            };
        }
}




