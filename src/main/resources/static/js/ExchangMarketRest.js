var socket;
var intervalPing;
var intervalPong;
var pong;
var lockReconnect = false;
var wsCreateHandler;
var exchange = "binance";
var str;
var jsonObj;
var id = null;
var woqu;
var xhr;

//below 6 var are json's dataname
var pair;
var lastPrice;
var _24hChanges;
var _24hHigh;
var _24hLow;
var _24hVolume;
var fragment = document.createDocumentFragment();

window.onload=function() {
    woqu = document.getElementById("myTable");
    <!-- ドロップダウンメニューにhoverを操作 -->
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


     if(window.XMLHttpRequest) {
           xhr = new XMLHttpRequest();
        }else{
           xhr=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhr.open("GET","http://localhost:8080/binanceMarket",true);
        xhr.send();
        xhr.onreadystatechange = function() {
              // readyState == 4リクエスト成功
          if(xhr.readyState==4) {
              if(xhr.status==200 || xhr.status==304){

                 jsonObj = eval('(' + jsonStr + ')');

                console(jsonObj);
              }




          }
      }



//        var jsonStr = xhr.responseText;

//        var ex1;
//        var ex2;
//        var numLength;
//        var percent;
//        var difference;
//            var data = "<tr>" +
//                        "<td>通貨名</td>" +
//                        "<td>Binance</td>" +
//                        "<td>Bithumb</td>" +
//                        "<td>スプレッド</td>" +
//                        "<td>パーセンテージ</td>" +
//                        "</tr>";
             //行数Num指定
//             for(var i = 1; i <= jsonObj.length; i++) {
//                 ex1 = parseFloat(jsonObj[i-1].exchange1Price);
//                 ex2 = parseFloat(jsonObj[i-1].exchange2Price);
//
//                 if(jsonObj[i-1].exchange1Price < jsonObj[i-1].exchange2Price) {
//                       numLength = ex1.toString().substring(ex1.toString().indexOf('.'),ex1.toString().length).length;
//                       difference = Math.abs(ex2 - ex1).toFixed(numLength);;
//                       percent = (difference/ex1 * 100).toFixed(2);
//                 } else {
//                       numLength = ex2.toString().substring(ex2.toString().indexOf('.'),ex2.toString().length).length;
//                       difference = Math.abs(ex1 - ex2).toFixed(numLength);;
//                       percent = (difference/ex2 * 100).toFixed(2);
//                 }
//                 data += "<tr>";
//                 data += "<td>" + jsonObj[i-1].symbol +"</td>";
//                 data += "<td>" + ex1 +"</td>";
//                 data += "<td>" + ex2 +"</td>";
//                 data += "<td>" + difference + "</td>";
//
//
//                 data += "<td>" + percent +"%</td>";
//                 data += "</tr>";
//             }
//             document.getElementById("myTable").innerHTML = data;


}


//ベースとなるテーブルを追加
function addDataInTable(array){

//                  console.log("原来");
//                  console.log(lastPrice);
//                  console.log("现在");
//                  console.log(parseFloat(lastPrice));
//                  console.log("原来");
//                  console.log(_24hHigh)
//                  console.log("现在");
//                  console.log(parseFloat(_24hHigh))
//                  console.log("原来");
//                  console.log(_24hLow)
//                  console.log("现在");
//                  console.log(parseFloat(_24hLow))
//                  console.log("原来");
//                  console.log(_24hVolume)
//                  console.log("现在");
//                  console.log(parseFloat(_24hVolume))


//        var djj = document.getElementById("myTable");
//        var childs = document.getElementById("myTable").childNodes;
//        for(var i = 0;i < childs.length;i++){
//            alert(childs[i])
//            document.getElementById("myTable").removeChild(childs[i]);
//        }
//
//        while(djj.hasChildNodes()){
//            djj.removeChild(djj.firstChild);
//        }

        str = "";
           for(i = 0, len = array.length; i < len; i++){

                  jsonObj = array[i];
                    for(var name in jsonObj){
                          switch(name){
                              case "pair":
                                  pair = jsonObj["pair"];
                                  break;
                              case "lastPrice":
                                  lastPrice = parseFloat(jsonObj["lastPrice"]);
                                  break;
                              case "_24hChanges":
                                  _24hChanges = parseFloat(jsonObj["_24hChanges"]);
                                  break;
                              case "_24hVolume":
                                  _24hVolume = parseFloat(jsonObj["_24hVolume"]);
                                  break;
                              default: break;
                          }
                  }

                   str += "<tr>" +
                          "<td class= \"d-none d-lg-table-cell\">"+ pair + "</td>" +
                          "<td class= \"d-none d-lg-table-cell\">"+ lastPrice + "</td>" +
                          "<td class= \"d-none d-lg-table-cell\">"+ _24hChanges + "</td>" +
                          "<td class= \"d-none d-lg-table-cell\">"+ _24hVolume + "</td>" +
                          "<tr>";

                document.getElementById("mtTable").innerHTML = str;
           }

}



function modifyTable(){
//requestAnimationFrame(()=>{
//woqu.innerHTML = "";
//});
//setTimeout(woqu.innerHTML = str, 4000 )

}



function createWebSocket(){
    if (typeof (WebSocket) == "undefined") {
        alert("ご利用されてるブラウザがwebsocketをサポートしてません");
    } else {
        try{
            socket = new WebSocket("ws://localhost:8080/marketStream/binance");
            initWSEventHandle();
        }catch(e){
            reConnect();
        }
    }
}


function initWSEventHandle(){

    try{
         //接続できた
            socket.onopen = function() {
                console.log("サーバーへの接続出来ました");
            };
            //messageを受けたs
            socket.onmessage = function(msg) {
                console.log(msg.data);
                 if(msg.data == "ping"){
                    sendMessage("pong");
                }else if(msg.data.search("userId:") != -1){
                    id = msg.data;
                }else{
                    addDataInTable(eval('(' + msg.data + ')'));
                }
                modifyTable();


            };
            //接続切断
            socket.onclose = function() {
                console.log("Socketが閉じました");
            };
            //エラー発生
            socket.onerror = function() {
                console.log("エラー発生、再接続中");
            }
            //ウインドウ閉じる際、接続を切断
            window.unload=function() {
                socket.close();
            };
    }catch(e){
        reConnect();
    }


}

function reConnect() {
    if(lockReconnect) {
        return;
    };

    lockReconnect = true;
    createWebSocket && clearTimeout(wsCreateHandler);
    wsCreateHandler = setTimeout(function (){
        console.log("再接続中");
        createWebSocket();
        lockReconnect = false;
    }, 1000);

}

//他のクライアントと分けるのためid先に送信
function sendMessage(Str){
    socket.send(id + Str);
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

function keepDropdown() {
        document.getElementById("in").click();
}




<!-- タブ様式変更と選択された取引所のデータ取得 -->
var times;
var btm1;
var btm2;
function tabChg_GetData(ele){
    if(ele.className.indexOf("active")  == -1) {
          if(socket.readyState === 1) {
                exchange =  ele.innerHTML.toLowerCase();

            }
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