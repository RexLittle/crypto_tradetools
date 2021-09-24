var wsCreateHandler;
var exchange = "binance";
var str;
var jsonObj;
var id = null;
var xhr;

//below 6 var are json's dataname
var pair;
var lastprice;
var _24hChanges;
var _24hHigh;
var _24hLow;
var _24hVolume;
var fragment = document.createDocumentFragment();


//function addDataInTable
var table;
var tableBody = document.createElement("tbody");
var tr;
var td;
var text="";

//table
var menu_btm;
var menu;

window.onload=function() {

    //table
    menu_btm = document.getElementById("in");
    menu = document.getElementById("show");

    <!-- ドロップダウンメニューにhover -->
    function hover(ele, fn1,fn2) {
        ele.onmouseenter = function(){
            fn1.call(ele);
        }
        document.getElementById("out").onmouseleave  = function() {
            fn2.call(ele);
        }
    }
    hover(
    menu_btm,
     function(){
     menu_btm.classList.add("show");
     menu_btm.setAttribute("data-bs-toggle","dropdown");
     menu_btm.setAttribute("aria-expanded","true");

     menu.classList.add("show");
     menu.style ="position: absolute; inset: 0px auto auto 0px; margin: 0px; transform: translate(0px, 40px);";
     menu.setAttribute("data-popper-placement","bottom-start");
    },
    function(){
     menu_btm.classList.remove("show");
     menu_btm.removeAttribute("data-bs-toggle");
     menu_btm.removeAttribute("expanded");

     menu.classList.remove("show");
     menu.removeAttribute("style");
     menu.removeAttribute("data-popper-placement");

    })

    setInterval(()=>{
        if(window.XMLHttpRequest) {
           xhr = new XMLHttpRequest();
        } else {
           xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }
        var url = "";
        switch(exchange){
            case "binance":
                url = "http://localhost:8080/byy/binanceMarket";
                break;

            case "okex":
                url = "http://localhost:8080/byy/okexMarket";
                break;

            case "kucoin":
                url = "http://localhost:8080/byy/kuCoinMarket";
                break;
        }
        xhr.open("GET",url,true);
        xhr.send();
            xhr.onreadystatechange = function() {
                  // readyState == 4リクエスト成功
              if(xhr.readyState==4) {
                  if(xhr.status==200 || xhr.status==304){

                      var jsonStr = xhr.responseText;
                      var jsonArray = eval('(' + jsonStr + ')');
                     addDataInTable(jsonArray);
                  }

              }

            }
    }, 3000);



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

    requestAnimationFrame(()=>{

            tableBody.innerHTML="";
            if(tableBody.childNodes.length == 0){
            //             str = "";
                         for(i = 0, len = array.length; i < len; i++){

                                jsonObj = array[i];
                                 console.log(jsonObj);
                                  for(var name in jsonObj){
                                        switch(name){
                                            case "pair":
                                                pair = jsonObj["pair"];
                                                break;
                                            case "lastprice":
                                                lastprice = parseFloat(jsonObj["lastprice"]);
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

            //                     str += "<tr>" +
            //                            "<td class= \"d-none d-lg-table-cell\">"+ pair + "</td>" +
            //                            "<td class= \"d-none d-lg-table-cell\">"+ lastprice + "</td>" +
            //                            "<td class= \"d-none d-lg-table-cell\">"+ _24hChanges + "</td>" +
            //                            "<td class= \"d-none d-lg-table-cell\">"+ _24hVolume + "</td>" +
            //                            "<tr>";

                           tableBody.id = "myTable";
                           table = document.getElementById("tb");
                            var fragment = document.createDocumentFragment();
                            fragment.appendChild(tableBody);
                                tr = document.createElement("tr");
                                 for (j=1; j<=4; j++){
                                 switch(j){

                                    case 1:
                                        console.log(pair);
                                        text = pair;
                                        break;
                                    case 2:
                                        text = lastprice;
                                        break;
                                    case 3:
                                        text = _24hChanges;
                                        break;
                                    case 4:
                                        text = _24hVolume;
                                        break;
                                 }
                                     td = document.createElement("td");
                                     td.innerText = text;
                                     td.classList.add("d-none");
                                     td.classList.add("d-lg-table-cell");
                                     tr.appendChild(td);
                                 }
                                 fragment.getElementById("myTable").appendChild(tr);


                         }

                        table.appendChild(fragment);


                //                  document.getElementById("myTable").innerHTML = str;
            }


    });


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
     menu_btm.classList.add("show");
     menu_btm.setAttribute("data-bs-toggle","dropdown");
     menu_btm.setAttribute("aria-expanded","true");
     menu.classList.add("show");
     menu.style ="position: absolute; inset: 0px auto auto 0px; margin: 0px; transform: translate(0px, 40px);";
     menu.setAttribute("data-popper-placement","bottom-start");
}

function sortOption(ele) {
     document.getElementById("in").innerHTML = ele.innerHTML;
     menu_btm.classList.remove("show");
     menu_btm.removeAttribute("data-bs-toggle");
     menu_btm.removeAttribute("expanded");
     menu.classList.remove("show");
     menu.removeAttribute("style");
     menu.removeAttribute("data-popper-placement");


     switch(ele.innerHTML.toLowerCase()){
        case "all":break;
        case "usdt":break;
        case "usdc":break;
        case "dai":break;
     }
}




<!-- タブ様式変更と選択された取引所のデータ取得 -->
var times;
var btm1;
var btm2;
function tabChg_GetData(ele){
    if(ele.className.indexOf("active")  == -1) {
          if(xhr.readyState==4) {
                tableBody.remove();
//                document.getElementById("myTable").innerHTML = "";
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

